package com.example.quanlysukien.API;

import com.example.quanlysukien.model.EventChennal;
import com.example.quanlysukien.model.JoinEventRequest;
import com.example.quanlysukien.model.ReceChat;
import com.example.quanlysukien.model.ReceEvent;
import com.example.quanlysukien.model.ReceEventStart;
import com.example.quanlysukien.model.ReceUpcomingEvents;
import com.example.quanlysukien.model.UserEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface API {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    API api = new Retrofit.Builder()
            .baseUrl(URL.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(API.class);

    @Multipart
    @POST("postEvent")
    Call<EventChennal> postUserMulter (
            @Part("eventName") RequestBody eventName,
            @Part("location") RequestBody location,
            @Part("description") RequestBody description,
            @Part("startTime") RequestBody startTime,
            @Part("endTime") RequestBody endTime,
            @Part("user_id") RequestBody user_id,
            @Part("participants") RequestBody participants,
            @Part("createdAt") RequestBody createdAt,
            @Part("updatedAt") RequestBody updatedAt,
            @Part("status") RequestBody status,
            @Part("limit") RequestBody limit,
            @Part MultipartBody.Part image
    );

    @GET("postEvent")
    Call<ReceEvent> getData();

    @GET("getListEventStart")
    Call<ReceEventStart> getListEventStart();
    @GET("getEventChuaStart")
    Call<ReceUpcomingEvents> getEventChuaStart();

    @GET("eventweek/{user_id}")
    Call<List<EventChennal>> getEventweek(@Path("user_id") String userId);
    @GET("eventmonth/{user_id}")
    Call<List<EventChennal>> getEventmonth(@Path("user_id") String userId);

    @GET("eventme/{user_id}")
    Call<ReceEvent> getDataMe( @Path("user_id") String userId );

    @POST("login")
    Call<UserEvent> login(@Body UserEvent userEvent);

    @POST("joinEvent")
    Call<ResponseBody> joinEvent(@Body JoinEventRequest joinEventRequest);

    @GET("getChat/{user_event}")
    Call<ReceChat> getDataChat(@Path("user_event") String user_event);

    @PUT("updatePassword/{id}")
    Call<UserEvent> updatePassword(@Path("id") String id, @Body UserEvent user);

}
