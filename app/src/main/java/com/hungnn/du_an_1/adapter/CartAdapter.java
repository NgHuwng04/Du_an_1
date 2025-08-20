package com.hungnn.du_an_1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hungnn.du_an_1.Model.GioHang;
import com.hungnn.du_an_1.R;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface OnCartItemActionListener {
        void onRemove(int position);
        void onChangeQuantity(int position, int newQty);
    }

    private final List<GioHang> cartItems;
    private final OnCartItemActionListener listener;

    public CartAdapter(List<GioHang> items, OnCartItemActionListener listener) {
        this.cartItems = new ArrayList<>(items);
        this.listener = listener;
    }

    public void updateCartList(List<GioHang> newItems) {
        this.cartItems.clear();
        this.cartItems.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        GioHang item = cartItems.get(position);
        holder.tvName.setText(item.getTenSanPham());
        String price = NumberFormat.getInstance(new Locale("vi", "VN")).format(item.getGia());
        holder.tvPrice.setText(price);
        holder.tvQuantity.setText(String.valueOf(item.getSoLuong()));
        if (holder.tvQty != null) {
            holder.tvQty.setText(String.valueOf(item.getSoLuong()));
        }
        if (item.getHinhAnhResId() != 0) {
            holder.ivThumb.setImageResource(item.getHinhAnhResId());
        } else {
            holder.ivThumb.setImageResource(R.drawable.ic_product_storage); // ảnh mặc định
        }
        holder.btnRemove.setOnClickListener(v -> {
            if (listener != null) listener.onRemove(holder.getAdapterPosition());
        });

        holder.tvMinus.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            int q = Math.max(0, cartItems.get(pos).getSoLuong() - 1);
            if (listener != null) listener.onChangeQuantity(pos, q);
        });

        holder.tvPlus.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            int q = cartItems.get(pos).getSoLuong() + 1;
            if (listener != null) listener.onChangeQuantity(pos, q);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity, tvMinus, tvPlus, tvQty;
        ImageView ivThumb;
        ImageButton btnRemove;
        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCartProductName);
            tvPrice = itemView.findViewById(R.id.tvCartProductPrice);
            tvQuantity = itemView.findViewById(R.id.tvCartQuantity);
            ivThumb = itemView.findViewById(R.id.ivCartThumb);
            btnRemove = itemView.findViewById(R.id.btnRemoveCartItem);
            tvMinus = itemView.findViewById(R.id.tvMinus);
            tvPlus = itemView.findViewById(R.id.tvPlus);
            tvQty = itemView.findViewById(R.id.tvQty);
        }
    }
}