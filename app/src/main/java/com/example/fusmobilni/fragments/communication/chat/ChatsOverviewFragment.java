package com.example.fusmobilni.fragments.communication.chat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.communication.chats.ChatsOverviewAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentChatsOverviewBinding;
import com.example.fusmobilni.databinding.FragmentCreateEventBinding;
import com.example.fusmobilni.helper.UriConverter;
import com.example.fusmobilni.interfaces.ChatListener;
import com.example.fusmobilni.model.communication.chat.ChatOverview;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.communication.chat.ChatGetResponse;
import com.example.fusmobilni.responses.communication.chat.ChatsGetResponse;
import com.example.fusmobilni.viewModels.communication.chat.ChatViewModel;
import com.example.fusmobilni.viewModels.users.serviceProvider.ServiceProviderViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatsOverviewFragment extends Fragment implements ChatListener {

    private FragmentChatsOverviewBinding binding;
    private List<ChatOverview> chatOverviews = new ArrayList<>();
    private List<ChatGetResponse> chats = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatsOverviewAdapter adapter;
    private ChatViewModel viewModel;

    public ChatsOverviewFragment() {}


    public static ChatsOverviewFragment newInstance() {

        return new ChatsOverviewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatsOverviewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Long userId = getUserId();
        if (userId == null) {
            return view;
        }
        viewModel = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);
        initializeRecycler();

        Call<ChatsGetResponse> callback = ClientUtils.chatService.findByUserId(userId);
        callback.enqueue(new Callback<ChatsGetResponse>() {
            @Override
            public void onResponse(Call<ChatsGetResponse> call, Response<ChatsGetResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chats.clear();
                    chatOverviews.clear();
                    chats = response.body().chats;
                    for (ChatGetResponse chat: chats) {
                        try {
                            chatOverviews.add(getOverviewFromChat(chat, getContext()));
                        } catch (IOException e) {
                            continue;
                        }
                    }
                    initializeRecycler();
                }
            }

            @Override
            public void onFailure(Call<ChatsGetResponse> call, Throwable t) {

            }
        });

        return view;
    }

    @Override
    public void onChatClick(int position) {
        Long userId = getUserId();
        if (userId == null) {
            return;
        }
        ChatOverview overview = this.chatOverviews.get(position);
        ChatGetResponse chat = this.chats.get(position);
        viewModel.populate(chat.id, overview.name, overview.avatar, getRecipientId(chat, userId));
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_chatOverviews_toChat);
    }

    private Long getUserId() {
        LoginResponse user = CustomSharedPrefs.getInstance(getContext()).getUser();
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    private Long getRecipientId(ChatGetResponse chat, Long userId) {
        return Objects.equals(chat.primaryUser.id, userId) ? chat.secondaryUser.id: chat.primaryUser.id;
    }

    private ChatOverview getOverviewFromChat(ChatGetResponse chat, Context context) throws IOException {
        if (Objects.equals(getUserId(), chat.primaryUser.id)) {
            return new ChatOverview(
                    chat.secondaryUser.name,
                    UriConverter.convertToUriFromBase64(context,chat.secondaryUser.avatar),
                    chat.latestMessageContent
            );
        } else {
            return new ChatOverview(
                    chat.primaryUser.name,
                    UriConverter.convertToUriFromBase64(context, chat.primaryUser.avatar),
                    chat.latestMessageContent
            );
        }
    }

    private void initializeRecycler() {
        recyclerView = binding.chatsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatsOverviewAdapter(chatOverviews, this);
        recyclerView.setAdapter(adapter);
    }
}