package ru.startandroid.petstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnPet;
    private EditText etPet;
    private ImageView imgPet;
    private TextView tvPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPet = findViewById(R.id.btn_pet);
        imgPet = findViewById(R.id.img_pet);
        etPet = findViewById(R.id.et_petId);
        tvPet = findViewById(R.id.tv_petname);



        PetInterface petAPI = PetInterface.retrofit.create(PetInterface.class);
        btnPet.setOnClickListener(click -> {

            Call<Pets> Call = petAPI.getId(etPet.getText().toString());

            Call.enqueue(new Callback<Pets>() {
                @Override
                public void onResponse(Call<Pets> call, Response<Pets> response) {
                    Pets pet = response.body();
                    String text = "Your pet is " + pet.getName();
                    tvPet.setText(text);
                    Glide.with(MainActivity.this)
                            .load(pet.getPhotoUrls().get(0))
                            .apply(new RequestOptions().override(500, 500))
                            .into(imgPet);
                }

                @Override
                public void onFailure(Call<Pets> call, Throwable throwable) {
                    Toast.makeText(MainActivity.this, "Что-то пошло не так " + throwable.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void toPost(View v){
        Intent postIntent = new Intent(this, PetActivity.class);
        startActivity(postIntent);
    }
}
