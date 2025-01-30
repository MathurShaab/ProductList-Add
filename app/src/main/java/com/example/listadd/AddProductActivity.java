package com.example.listadd;

import static com.example.listadd.FileUtils.getPath;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.listadd.ApiService.RetrofitClient;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import com.example.listadd.FileUtils;

public class AddProductActivity extends AppCompatActivity {
    private EditText etProductName, etPrice, etTax;
    private Spinner spinnerProductType;
    private ImageView ivProductImage;
    private Button btnPickImage, btnSubmit;
    private ProgressBar progressBar;

    private Uri imageUri;
    private String[] productTypes = {"Product", "Service"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etProductName = findViewById(R.id.etProductName);
        etPrice = findViewById(R.id.etPrice);
        etTax = findViewById(R.id.etTax);
        spinnerProductType = findViewById(R.id.spinnerProductType);
        ivProductImage = findViewById(R.id.ivProductImage);
        btnPickImage = findViewById(R.id.btnPickImage);
        btnSubmit = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressBar);

        // Set Spinner Data
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, productTypes);
        spinnerProductType.setAdapter(adapter);

        // Pick Image
        btnPickImage.setOnClickListener(v -> pickImage());

        // Submit Product
        btnSubmit.setOnClickListener(v -> uploadProduct());
    }

    // Image Picker
    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(ivProductImage);
        }
    }

    // Upload Product
    private void uploadProduct() {
        String productName = etProductName.getText().toString().trim();
        String productType = spinnerProductType.getSelectedItem().toString();
        String price = etPrice.getText().toString().trim();
        String tax = etTax.getText().toString().trim();

        if (productName.isEmpty() || price.isEmpty() || tax.isEmpty() || imageUri == null) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        File imageFile = new File(getPath(AddProductActivity.this, imageUri));


        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

        RetrofitClient.getService().addProduct(
                RequestBody.create(MediaType.parse("text/plain"), productName),
                RequestBody.create(MediaType.parse("text/plain"), productType),
                RequestBody.create(MediaType.parse("text/plain"), price),
                RequestBody.create(MediaType.parse("text/plain"), tax),
                imagePart
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddProductActivity.this, "Product Added!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddProductActivity.this, "Failed to Add Product", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
