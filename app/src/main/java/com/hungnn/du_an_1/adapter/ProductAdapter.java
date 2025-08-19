package com.hungnn.du_an_1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.Model.SanPham;
import com.hungnn.du_an_1.ActivityPhu.ProductDetailActivity;


import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private List<SanPham> products;

    public ProductAdapter(Context context, List<SanPham> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        SanPham sanPham = products.get(position);
        holder.tvName.setText(sanPham.getTenSanPham());
        holder.tvDes.setText(sanPham.getMoTa());
        NumberFormat vnFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        holder.tvPrice.setText(vnFormat.format(sanPham.getGia()) + "VNĐ");
        int resId = sanPham.getHinhAnhResId() == 0 ? R.mipmap.ic_launcher : sanPham.getHinhAnhResId();
        holder.imgProduct.setImageResource(resId);

        // Thêm click listener cho item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("maSanPham", sanPham.getMaSanPham());
                intent.putExtra("tenSanPham", sanPham.getTenSanPham());
                intent.putExtra("moTa", sanPham.getMoTa());
                intent.putExtra("gia", sanPham.getGia());
                intent.putExtra("maDanhMuc", sanPham.getMaDanhMuc());
                intent.putExtra("hinhAnhResId", sanPham.getHinhAnhResId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    // Phương thức cập nhật danh sách sản phẩm
    public void updateProducts(List<SanPham> newProducts) {
        this.products = newProducts;
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName;
        TextView tvPrice;
        TextView tvDes;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvDes = itemView.findViewById(R.id.tvDes);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}