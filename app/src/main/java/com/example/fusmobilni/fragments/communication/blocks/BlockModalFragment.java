package com.example.fusmobilni.fragments.communication.blocks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentBlockModalBinding;
import com.example.fusmobilni.interfaces.BlockDialogResultListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlockModalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlockModalFragment extends DialogFragment {


    private FragmentBlockModalBinding _binding;

    private BlockDialogResultListener listener;

    public BlockModalFragment() {
    }

    public static BlockModalFragment newInstance(String param1, String param2) {
        BlockModalFragment fragment = new BlockModalFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentBlockModalBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BlockDialogResultListener) {
            listener = (BlockDialogResultListener) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you confirm").setPositiveButton("Yes", (dialog, id) -> {
                    sendResult(true);
                })
                .setNegativeButton("No", ((dialog, which) ->
                {
                    sendResult(false);
                }));
        return builder.create();
    }

    private void sendResult(boolean result) {
        Fragment targetFragment = getTargetFragment();
        if (targetFragment instanceof BlockDialogResultListener) {
            ((BlockDialogResultListener) targetFragment).onBlockDialogResult(result);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}