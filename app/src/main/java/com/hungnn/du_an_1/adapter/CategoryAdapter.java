package com.hungnn.du_an_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.Model.DanhMuc;
import com.hungnn.du_an_1.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<DanhMuc> categoryList;

    public CategoryAdapter(Context context, List<DanhMuc> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danh_muc, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        DanhMuc danhMuc = categoryList.get(position);
        holder.tvTenDanhMuc.setText(danhMuc.getTenDanhMuc());
        holder.tvMoTa.setText(danhMuc.getMoTa());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenDanhMuc, tvMoTa;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenDanhMuc = itemView.findViewById(R.id.tvTenDanhMuc);
            tvMoTa = itemView.findViewById(R.id.tvMoTa);
        }
    }
}
