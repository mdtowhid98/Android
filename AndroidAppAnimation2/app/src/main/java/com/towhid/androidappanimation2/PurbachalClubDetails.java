package com.towhid.androidappanimation2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.towhid.androidappanimation2.adapter.PurbachalModelAdapter;
import com.towhid.androidappanimation2.api.PurbachalClubApi;
import com.towhid.androidappanimation2.apiClient.ApiClient;
import com.towhid.androidappanimation2.model.PurbachalModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurbachalClubDetails extends AppCompatActivity {

    private RecyclerView purbachalRecyclerView;
    private PurbachalModelAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_purbachal_club_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        purbachalRecyclerView=findViewById(R.id.purbachalRecyclerView);

        purbachalRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        PurbachalClubApi clubApi = ApiClient.getRetrofit().create(PurbachalClubApi.class);

        Call<List<PurbachalModel>> call = clubApi.getPurbachalModels();


        call.enqueue(new Callback<List<PurbachalModel>>() {
            @Override
            public void onResponse(Call<List<PurbachalModel>> call, Response<List<PurbachalModel>> response) {
                if (response.isSuccessful()) {
                    List<PurbachalModel> purbachalModelList = response.body();

                    // Set up RecyclerView with the adapter
                    adapter = new PurbachalModelAdapter(purbachalModelList, getApplicationContext() );
                    purbachalRecyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<List<PurbachalModel>> call, Throwable t) {

            }
        });










    }
}