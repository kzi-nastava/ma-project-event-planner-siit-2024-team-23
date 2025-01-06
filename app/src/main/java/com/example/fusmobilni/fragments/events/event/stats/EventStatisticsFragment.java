package com.example.fusmobilni.fragments.events.event.stats;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentEventDetailsBinding;
import com.example.fusmobilni.databinding.FragmentEventStatisticsBinding;
import com.example.fusmobilni.responses.events.statistics.EventStatisticsResponse;
import com.example.fusmobilni.responses.events.statistics.GradeStatisticsResponseDTO;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventStatisticsFragment extends Fragment {
    private FragmentEventStatisticsBinding _binding;
    private HorizontalBarChart gradeChart;

    public EventStatisticsFragment() {
        // Required empty public constructor
    }
    public static EventStatisticsFragment newInstance() {
        return new EventStatisticsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentEventStatisticsBinding.inflate(inflater);
        View root = _binding.getRoot();
        if (getArguments() == null) {
            Toast.makeText(requireContext(), "Something went wrong with event or invalid event id is provided!",
                    Toast.LENGTH_LONG).show();
        }
        Long eventId = getArguments().getLong("eventId");
        gradeChart = _binding.gradeChart;
        _binding.generatePDF.setOnClickListener(v-> onGeneratePdfClick(eventId));
        setupGradeChart();
        fetchStatistics(eventId);
        return root;
    }
    private void onGeneratePdfClick(Long eventId) {
        Call<ResponseBody> request = ClientUtils.eventsService.downloadEventStatisticsPdf(eventId);
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        try {
                            String fileName = "event_stats_" + eventId + "_" + System.currentTimeMillis() + ".pdf";
                            File file = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);

                            try (InputStream inputStream = response.body().byteStream();
                                 FileOutputStream outputStream = new FileOutputStream(file)) {
                                byte[] buffer = new byte[4096];
                                int read;
                                while ((read = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, read);
                                }
                                outputStream.flush();
                            }

                            // Notify download complete
                            new Handler(Looper.getMainLooper()).post(() -> {
                                Toast.makeText(requireContext(), "PDF downloaded: " + fileName, Toast.LENGTH_LONG).show();
                                openPdf(requireContext(), file);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            new Handler(Looper.getMainLooper()).post(() ->
                                    Toast.makeText(requireContext(), "Download failed", Toast.LENGTH_SHORT).show()
                            );
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Download failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private static void openPdf(Context context, File file) {
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No PDF viewer installed", Toast.LENGTH_SHORT).show();
        }
    }
    private void setupGradeChart() {
        gradeChart.getDescription().setEnabled(false);
        gradeChart.setDrawGridBackground(false);
        gradeChart.setDrawBarShadow(false);
        gradeChart.setDrawValueAboveBar(true);
        gradeChart.setPinchZoom(false);
        gradeChart.setScaleEnabled(false);

        // Customize X axis (vertical axis for horizontal bar chart)
        XAxis xAxis = gradeChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%.1f", 5.0f - value);
            }
        });

        // Customize Y axis (horizontal axis for horizontal bar chart)
        YAxis leftAxis = gradeChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%.0f%%", value);
            }
        });

        // Disable right axis
        gradeChart.getAxisRight().setEnabled(false);

        // Enable legend
        gradeChart.getLegend().setEnabled(false);
    }
    private void updateGradeChart(List<GradeStatisticsResponseDTO> gradeStats) {
        List<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < gradeStats.size(); i++) {
            GradeStatisticsResponseDTO stat = gradeStats.get(i);
            // i represents the position from top (5.0) to bottom (1.0)
            entries.add(new BarEntry(i, (float) stat.getPercentage()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Grades");
        dataSet.setColor(getResources().getColor(R.color.primary_blue_900));
        dataSet.setValueTextSize(12f);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%.0f%%", value);
            }
        });

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.7f);

        gradeChart.setData(barData);
        gradeChart.setFitBars(true);
        gradeChart.invalidate();
    }
    private void fetchStatistics(Long eventId) {
        Call<EventStatisticsResponse> request = ClientUtils.eventsService.findStatisticsForEvent(eventId);
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<EventStatisticsResponse> call, @NonNull Response<EventStatisticsResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    EventStatisticsResponse data = response.body();
                    fetchEventOrganizerImage(data.getOwnerId());
                    _binding.ownerName.setText(String.format("%s %s", data.getOwnerName(), data.getOwnerLastName()));
                    _binding.eventName.setText(data.getEventTitle());
                    _binding.visitorsCount.setText(String.valueOf(data.getVisitorsCount()));
                    _binding.budgetSpent.setText(String.format("%s€", data.getTotalBudget()));
                    _binding.averageGrade.setText(String.format(Locale.US, "%.2f",data.getAverageGrade()));
                    updateGradeChart(data.getGradeStatistics());
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventStatisticsResponse> call, @NonNull Throwable t) {

            }
        });

    }
    private void fetchEventOrganizerImage(Long id) {
        Call<ResponseBody> request = ClientUtils.authService.findProfileImageByUserId(id);
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        try (ResponseBody responseBody = response.body()) {
                            Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                            _binding.ownerImage.post(() -> _binding.ownerImage.setImageBitmap(bitmap));
                        }
                    }).start();

                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("AuthRepository", "Failed to load image: " + t.getMessage());
                _binding.ownerImage.post(() -> _binding.ownerImage.setImageResource(R.drawable.person));
            }
        });
    }
}