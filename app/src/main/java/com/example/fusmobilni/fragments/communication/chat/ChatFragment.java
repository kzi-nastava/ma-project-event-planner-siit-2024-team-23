package com.example.fusmobilni.fragments.communication.chat;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.adapters.communication.chats.ChatAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.clients.webSockets.ChatWebSocketClient;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentChatBinding;
import com.example.fusmobilni.requests.communication.chat.MessageCreateRequest;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.communication.chat.messages.GetMessageResponse;
import com.example.fusmobilni.responses.communication.chat.messages.GetMessagesResponse;
import com.example.fusmobilni.viewModels.communication.chat.ChatViewModel;
import com.google.gson.Gson;

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

    private ChatWebSocketClient chatWebSocketClient;


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
        connectToSocket();
        setUpButton(userId);
        return view;
    }

    @SuppressLint("CheckResult")
    private void connectToSocket() {
        Gson gson = new Gson();
        chatWebSocketClient = new ChatWebSocketClient();
        chatWebSocketClient.connect();
        chatWebSocketClient.subscribeToTopic("/socket-publisher/" + getSubscriptionExtension())
                .subscribe(topicMessage -> {
                    GetMessageResponse newMessage = gson.fromJson(topicMessage.getPayload(), GetMessageResponse.class);

                    requireActivity().runOnUiThread(() -> {
                        messages.add(newMessage);
                        adapter.notifyItemInserted(messages.size() - 1);
                        recyclerView.scrollToPosition(messages.size() - 1);
                    });
                }, throwable -> {
                    Log.e("Tag", throwable.getMessage());
                });
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

    private void setUpButton(Long userId) {
        Gson gson = new Gson();
        binding.sendButton.setOnClickListener(v -> {
            String messageContent = binding.messageInput.getText().toString();
            MessageCreateRequest request = new MessageCreateRequest(
                    userId,
                    viewModel.getRecipientId().getValue(),
                    viewModel.getChatId().getValue(),
                    messageContent
            );
            chatWebSocketClient.sendMessage("/socket-subscriber/send/message", gson.toJson(request));
            binding.messageInput.setText("");
        });
    }

    private String getSubscriptionExtension() {
        if (getUserId() == null || viewModel.getRecipientId().getValue() == null) {
            return "";
        }
        if (getUserId() <= viewModel.getRecipientId().getValue()) {
            return getUserId() + "/" + viewModel.getRecipientId().getValue();
        } else {
            return viewModel.getRecipientId().getValue() + "/" + getUserId();
        }
    }
}