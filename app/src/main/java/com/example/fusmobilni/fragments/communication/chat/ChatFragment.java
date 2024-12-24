package com.example.fusmobilni.fragments.communication.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.communication.chats.ChatAdapter;
import com.example.fusmobilni.adapters.communication.chats.ChatsOverviewAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentChatBinding;
import com.example.fusmobilni.databinding.FragmentChatsOverviewBinding;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.communication.chat.messages.GetMessageResponse;
import com.example.fusmobilni.responses.communication.chat.messages.GetMessagesResponse;
import com.example.fusmobilni.viewModels.communication.chat.ChatViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatFragment extends Fragment {


    private FragmentChatBinding binding;
    private ChatAdapter adapter;
    private List<GetMessageResponse> messages = new ArrayList<>();

    private RecyclerView recyclerView;
    private ChatViewModel viewModel;


    public ChatFragment() {}


    public static ChatFragment newInstance(String param1, String param2) {
        return new ChatFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Long userId = getUserId();
        if (userId == null) {
            return view;
        }
        setUpRecycler(userId);
        viewModel = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);
        initializeRecipientInfo();
        Call<GetMessagesResponse> callback = ClientUtils.chatService.findAllMessagesByChatId(userId,
                viewModel.getChatId().getValue());
        callback.enqueue(new Callback<GetMessagesResponse>() {
            @Override
            public void onResponse(Call<GetMessagesResponse> call, Response<GetMessagesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    messages.clear();
                    messages = response.body().messages;
                    setUpRecycler(userId);
                }
            }

            @Override
            public void onFailure(Call<GetMessagesResponse> call, Throwable t) {

            }
        });
        return view;
    }

    private void initializeRecipientInfo() {
        binding.avatar.setImageURI(viewModel.getRecipientAvatar().getValue());
        binding.personName.setText(viewModel.getRecipientName().getValue());
    }

    private void setUpRecycler(Long userId) {
        recyclerView = binding.chatRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatAdapter(messages, userId);
        recyclerView.setAdapter(adapter);
    }

    private Long getUserId() {
        LoginResponse user = CustomSharedPrefs.getInstance(getContext()).getUser();
        if (user != null) {
            return user.getId();
        }
        return null;
    }
}