package com.example.listadd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.listadd.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getProductName());
        holder.price.setText("₹" + product.getPrice());
        holder.tax.setText("Tax: " + product.getTax() + "%");

        Glide.with(holder.itemView.getContext())
                .load(product.getImage().isEmpty() ? R.drawable.ic_launcher_background : product.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() { return productList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView productName, price, tax;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            tax = itemView.findViewById(R.id.productTax);
        }
    }
}
