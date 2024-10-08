package com.towhid.androidappanimation2;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.towhid.androidappanimation2.adapter.NotificationAdapter;
import com.towhid.androidappanimation2.api.NotificationApi;
import com.towhid.androidappanimation2.apiClient.ApiClient;
import com.towhid.androidappanimation2.model.NotificationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends AppCompatActivity {

    private RecyclerView notificationList;
    private NotificationAdapter notificationAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        notificationList=findViewById(R.id.noticeList);

        notificationList.setLayoutManager(new LinearLayoutManager(this));

        NotificationApi notificationApi = ApiClient.getRetrofit().create(NotificationApi.class);

        Call<List<NotificationModel>> call = notificationApi.getNotifications();


        call.enqueue(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {

                if (response.isSuccessful()) {
                    List<NotificationModel> noticeList = response.body();

                    // Set up RecyclerView with the adapter
                    notificationAdapter = new NotificationAdapter(noticeList, getApplicationContext() );
                    notificationList.setAdapter(notificationAdapter);
                }

            }

            @Override
            public void onFailure(Call<List<NotificationModel>> call, Throwable t) {

            }
        });






    }
}