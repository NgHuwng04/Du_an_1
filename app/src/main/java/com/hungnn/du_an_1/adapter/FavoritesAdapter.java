package com.hungnn.du_an_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.Model.SanPham;
import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.Utils.FavoritesManager;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private Context context;
    private List<SanPham> favoritesList;
    private FavoritesManager favoritesManager;
    private OnFavoriteRemovedListener listener;
    private boolean isProcessingClick = false;

    public interface OnFavoriteRemovedListener {
        void onFavoriteRemoved(SanPham sanPham);
    }

    public FavoritesAdapter(Context context, List<SanPham> favoritesList) {
        this.context = context;
        this.favoritesList = favoritesList;
        this.favoritesManager = FavoritesManager.getInstance(context);
    }

    public void setOnFavoriteRemovedListener(OnFavoriteRemovedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite_product, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        SanPham sanPham = favoritesList.get(position);
        
        // Hiển thị thông tin sản phẩm
        holder.tvProductName.setText(sanPham.getTenSanPham());
        
        // Format giá tiền
        NumberFormat vnFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedPrice = vnFormat.format(sanPham.getGia()) + " VNĐ";
        holder.tvProductPrice.setText(formattedPrice);
        
        // Hiển thị hình ảnh sản phẩm
        if (sanPham.getHinhAnhResId() != 0) {
            holder.imgProduct.setImageResource(sanPham.getHinhAnhResId());
        }
        
        // Xử lý click vào nút favorite để bỏ yêu thích
        holder.btnFavorite.setOnClickListener(v -> {
            removeFavorite(sanPham, position);
        });
        
        // Xử lý click vào sản phẩm để xem chi tiết
        holder.itemView.setOnClickListener(v -> {
            // TODO: Chuyển sang màn hình chi tiết sản phẩm
            Toast.makeText(context, "Xem chi tiết: " + sanPham.getTenSanPham(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    /**
     * Xóa sản phẩm khỏi danh sách yêu thích
     */
    private void removeFavorite(SanPham sanPham, int position) {
        // Kiểm tra nếu đang xử lý click thì bỏ qua
        if (isProcessingClick) {
            return;
        }
        
        isProcessingClick = true;
        
        if (favoritesManager.removeFromFavorites(sanPham.getMaSanPham())) {
            // Xóa khỏi danh sách hiển thị
            favoritesList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, favoritesList.size());
            
            // Thông báo cho Activity
            if (listener != null) {
                listener.onFavoriteRemoved(sanPham);
            }
            
            Toast.makeText(context, "Đã bỏ khỏi yêu thích", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Không thể bỏ khỏi yêu thích", Toast.LENGTH_SHORT).show();
        }
        
        // Reset trạng thái sau 500ms
        new android.os.Handler().postDelayed(() -> isProcessingClick = false, 500);
    }

    /**
     * Cập nhật danh sách sản phẩm yêu thích
     */
    public void updateFavoritesList(List<SanPham> newList) {
        this.favoritesList = newList;
        notifyDataSetChanged();
    }

    static class FavoritesViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvProductPrice;
        ImageButton btnFavorite;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }
    }
}
