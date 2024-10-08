package com.towhid.androidappanimation2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.towhid.androidappanimation2.api.SlideApi;
import com.towhid.androidappanimation2.apiClient.ApiClient;
import com.towhid.androidappanimation2.model.Slide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    private ImageView notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SlideApi slideApi = ApiClient.getRetrofit().create(SlideApi.class);

        Call<List<Slide>> call = slideApi.getSlides();

        call.enqueue(new Callback<List<Slide>>() {
            @Override
            public void onResponse(Call<List<Slide>> call, Response<List<Slide>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<Slide> slides = response.body();

                    // Convert Slide objects to SlideModel
                    ArrayList<SlideModel> imageList = new ArrayList<>();
                    for (Slide slide : slides) {

                        String relativeImagePath = slide.getImage();
                        String baseUrl = "https://purbachal.emranhss.com/";
                        String imageUrl = baseUrl + relativeImagePath;

                        imageList.add(new SlideModel(imageUrl, slide.getTitle(), ScaleTypes.CENTER_CROP));
                        System.out.println(slide.getImage());
                    }

                    // Set images in the ImageSlider
                    ImageSlider imageSlider = findViewById(R.id.image_slider);
                    imageSlider.setImageList(imageList);
                }


            }

            @Override
            public void onFailure(Call<List<Slide>> call, Throwable t) {

            }
        });

//For Notification Image in
        notification = findViewById(R.id.notification);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Notification.class);
                startActivity(intent);
            }
        });

    }
}