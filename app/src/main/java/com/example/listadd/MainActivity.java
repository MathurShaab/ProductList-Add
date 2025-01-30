package com.example.listadd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadd.ApiService.RetrofitClient;
import com.example.listadd.ProductAdapter;
import com.example.listadd.model.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        submit = findViewById(R.id.btnAddProduct);

        fetchProducts();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddProductActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchProducts() {
        RetrofitClient.getService().getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body();

                    // Log all fetched products
                    for (Product product : products) {
                        android.util.Log.d("ProductData", "Name: " + product.getProductName() +
                                ", Price: " + product.getPrice() +
                                ", Tax: " + product.getTax() +
                                ", Image: " + product.getImage());
                    }

                    adapter = new ProductAdapter(products);
                    recyclerView.setAdapter(adapter);
                } else {
                    android.util.Log.e("ProductData", "Response unsuccessful or empty");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                android.util.Log.e("ProductData", "Failed to load products: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
