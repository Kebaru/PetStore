package ru.startandroid.petstore;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetActivity extends AppCompatActivity {

    private Button btnPet;
    private EditText etPet;
    private EditText etCategory;
    private EditText etTag;
    private EditText etStatus;
    private EditText etPhoto;
    private EditText etId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        btnPet = findViewById(R.id.btn_pet);
        etPet = findViewById(R.id.et_petName);
        etCategory = findViewById(R.id.et_categoryName);
        etPhoto = findViewById(R.id.et_petPhoto);
        etStatus = findViewById(R.id.et_status);
        etTag = findViewById(R.id.et_pettagName);
        etId = findViewById(R.id.et_petId);

        String petName = etPet.getText().toString();
        String petId = etId.getText().toString();

        PetInterface petAPI = PetInterface.retrofit.create(PetInterface.class);
        btnPet.setOnClickListener(click -> {

            Pets pets = new Pets(petId);
            Call<Pets> Call = petAPI.addPet(pets);

            Call.enqueue(new Callback<Pets>() {
                @Override
                public void onResponse(Call<Pets> call, Response<Pets> response) {

                }

                @Override
                public void onFailure(Call<Pets> call, Throwable throwable) {
                    Toast.makeText(PetActivity.this, "Что-то пошло не так " + throwable.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}