package com.hungnn.du_an_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hungnn.du_an_1.Model.SanPham;
import com.hungnn.du_an_1.R;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class QuanLySanPhamAdapter extends RecyclerView.Adapter<QuanLySanPhamAdapter.ViewHolder> {

    private final Context context;
    private List<SanPham> list;
    private final OnItemInteractionListener listener;

    // Interface để gửi sự kiện click về Activity
    public interface OnItemInteractionListener {
        void onEditClick(SanPham sanPham);
        void onDeleteClick(SanPham sanPham);
    }

    public QuanLySanPhamAdapter(Context context, List<SanPham> list, OnItemInteractionListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public void setData(List<SanPham> newList) {
        this.list.clear();
        this.list.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quan_ly_san_pham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = list.get(position);
        if (sanPham == null) return;

        holder.tvTenSP.setText(sanPham.getTenSanPham());

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        holder.tvGiaSP.setText("Giá: " + currencyFormatter.format(sanPham.getGia()));
        holder.tvSoLuongTon.setText("Tồn kho: " + sanPham.getSoLuongTon());

        int resId = sanPham.getHinhAnhResId();
        if (resId == 0) {
            resId = guessImageByName(sanPham.getTenSanPham());
            if (resId == 0) resId = R.mipmap.ic_launcher_round;
        }
        holder.imgSP.setImageResource(resId);

        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(sanPham));
        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(sanPham));
        holder.itemContainer.setOnClickListener(v -> listener.onEditClick(sanPham));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSP, tvGiaSP, tvSoLuongTon;
        ImageView imgSP;
        ImageButton btnDelete;
        ImageButton btnEdit;
        LinearLayout itemContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSP = itemView.findViewById(R.id.tv_ten_san_pham);
            tvGiaSP = itemView.findViewById(R.id.tv_gia_san_pham);
            tvSoLuongTon = itemView.findViewById(R.id.tv_so_luong_ton);
            imgSP = itemView.findViewById(R.id.img_san_pham);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            itemContainer = itemView.findViewById(R.id.item_container);
        }
    }

    private int guessImageByName(String productName) {
        if (productName == null) return 0;
        String name = productName.toLowerCase();
        if (name.contains("iphone 16")) return R.drawable.ic_iphone16_pro_max;
        if (name.contains("iphone 15")) return R.drawable.ic_iphone15_pro_max;
        if (name.contains("iphone 14") || name.contains("iphone")) return R.drawable.ic_iphone14_pro_max;
        if (name.contains("s24") || name.contains("galaxy s24")) return R.drawable.ic_samsung_galaxy_s24;
        if (name.contains("fold") || name.contains("zfold")) return R.drawable.ic_samsung_galaxy_zfold7;
        if (name.contains("a16")) return R.drawable.ic_samsung_galaxy_a16;
        if (name.contains("xiaomi 15")) return R.drawable.ic_xiaomi_15_ultra;
        if (name.contains("xiaomi 14") || name.contains("14t")) return R.drawable.ic_xiaomi_14t_pro;
        if (name.contains("poco")) return R.drawable.ic_xiaomi_poco_x7_pro;
        if (name.contains("oppo reno14")) return R.drawable.ic_oppo_reno14;
        if (name.contains("oppo reno10")) return R.drawable.ic_oppo_reno10_pro;
        if (name.contains("oppo find")) return R.drawable.ic_oppo_find_n5;
        return 0;
    }
}