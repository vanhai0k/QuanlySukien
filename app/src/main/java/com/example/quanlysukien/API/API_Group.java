package com.example.quanlysukien.API;

import com.example.quanlysukien.model.ChatGroup;
import com.example.quanlysukien.model.Group;
import com.example.quanlysukien.model.GroupChatModel;
import com.example.quanlysukien.model.JoinGroup;
import com.example.quanlysukien.model.MemberModel;
import com.example.quanlysukien.model.MessageChat;
import com.example.quanlysukien.model.RecaChatGroup;
import com.example.quanlysukien.model.ReceChat;
import com.example.quanlysukien.model.ReceJoinGroup;
import com.example.quanlysukien.model.UserEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API_Group {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    API_Group apiGroup = new Retrofit.Builder()
            .baseUrl(URL.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(API_Group.class);

    @POST("addGroup")
    Call<Group> joinGroup(@Body Group group);

    @GET("getGroup")
    Call<RecaChatGroup> getGroup(@Query("userID") String userID);

    @GET("/api/getGroupChat/{userID}")
    Call<List<ChatGroup>> getGroupChat(@Path("userID") String userID);

    @Multipart
    @POST("addchat")
    Call<MessageChat> message(
            @Part("chatId") RequestBody chatId,
            @Part("userId") RequestBody userId,
            @Part("message") RequestBody message,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("addgroups")
    Call<GroupChatModel> addGroup(
            @Part("name") RequestBody name,
            @Part("admin") RequestBody admin,
            @Part("numbers") RequestBody numbers,
            @Part("eventID") RequestBody eventID,
            @Part("chatHistory") RequestBody chatHistory,
            @Part MultipartBody.Part image
    );

    @DELETE("logoutChat/{id}")
    Call<MemberModel> logoutGroup(@Path("id") String id);


    @GET("getUserChatGroup")
    Call<ReceJoinGroup> getJoinGroup(@Query("groupID") String groupID);

    @PUT("udgroupss")
    Call<Group> updateJoinGroup(@Body Group group);
}
