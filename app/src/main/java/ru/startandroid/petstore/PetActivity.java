package ru.startandroid.petstore;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class PetActivity extends AppCompatActivity {

    private Button btnPet;
    private EditText etPet;
    private EditText etCategory;
    private EditText etPhoto;
    private EditText etId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        btnPet = findViewById(R.id.btn_pet);
        etPet = findViewById(R.id.et_petName);
        //etCategory = findViewById(R.id.et_categoryName);
        etPhoto = findViewById(R.id.et_petPhoto);
        etId = findViewById(R.id.et_petId);

        PetInterface petAPI = PetInterface.retrofit.create(PetInterface.class);
        btnPet.setOnClickListener(click -> {

            String IdInput = etId.getText().toString();
            String Name = etPet.getText().toString();
            String photoUrl = etPhoto.getText().toString();
            //String Category = etCategory.getText().toString();
            List<String> PhotoUrls = new ArrayList<>();
            PhotoUrls.add(photoUrl);
            int ID = Integer.parseInt(IdInput);
            Pets newPet = new Pets();
            Category category = new Category();

            //category.setName(Category);

            newPet.setId(ID);
            newPet.setName(Name);
            newPet.setPhotoUrls(PhotoUrls);
            //newPet.setCategory(Category);
            Call<Pets> Call = petAPI.addPet(newPet);

            Call.enqueue(new Callback<Pets>() {
                @Override
                public void onResponse(Call<Pets> call, Response<Pets> response) {
                    if(response.isSuccessful()) {
                        Pets createdPet = response.body();
                    } else {
                        ResponseBody errorBody = response.errorBody();
                        try {
                            Toast.makeText(PetActivity.this, errorBody.string(),
                                    Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<Pets> call, Throwable throwable) {
                    Toast.makeText(PetActivity.this, "Что-то пошло не так " + throwable.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void toGet(View v){
        Intent getIntent = new Intent(this, MainActivity.class);
        startActivity(getIntent);
    }
}