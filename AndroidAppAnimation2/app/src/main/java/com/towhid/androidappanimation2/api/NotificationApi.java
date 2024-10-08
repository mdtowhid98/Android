package com.towhid.androidappanimation2.api;

import com.towhid.androidappanimation2.model.NotificationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NotificationApi {

    @GET("get_notices.php")
    Call<List<NotificationModel>> getNotifications();
}
