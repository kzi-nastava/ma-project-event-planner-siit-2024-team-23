package com.example.fusmobilni.clients.services.communication.chat;

import com.example.fusmobilni.requests.communication.chat.ChatCreateRequest;
import com.example.fusmobilni.responses.communication.chat.ChatsGetResponse;
import com.example.fusmobilni.responses.communication.chat.messages.GetMessagesResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatService {

    @GET("users/{userId}/chats")
    public Call<ChatsGetResponse> findByUserId(@Path("userId") Long userId);

    @GET("users/{userId}/chats/{chatId}/messages")
    public Call<GetMessagesResponse> findAllMessagesByChatId(@Path("userId") Long userId,
                                                             @Path("chatId") Long chatId);

    @POST("users/{userId}/chats")
    public Call<Void> create(@Path("userId") Long userId,
                             @Body ChatCreateRequest request);
}
