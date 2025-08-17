package com.hungnn.du_an_1.ActivityPhu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.hungnn.du_an_1.Model.SanPham;
import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.Utils.FavoritesManager;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageButton btnBack, btnFavorite;
    private ImageView imgProduct;
    private TextView tvProductName, tvProductPrice;
    private Button btnAddToCart;
    private SanPham sanPham;
    private FavoritesManager favoritesManager;
    private boolean isProcessingFavoriteClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Lấy dữ liệu sản phẩm từ Intent
        int maSanPham = getIntent().getIntExtra("maSanPham", 0);
        String tenSanPham = getIntent().getStringExtra("tenSanPham");
        String moTa = getIntent().getStringExtra("moTa");
        double gia = getIntent().getDoubleExtra("gia", 0);
        int hinhAnhResId = getIntent().getIntExtra("hinhAnhResId", 0);
        int maDanhMuc = getIntent().getIntExtra("maDanhMuc", 0);

        // Tạo đối tượng SanPham với đầy đủ thông tin
        sanPham = new SanPham(maSanPham, tenSanPham, moTa != null ? moTa : "", gia, 0, maDanhMuc, hinhAnhResId);

        // Khởi tạo FavoritesManager
        favoritesManager = FavoritesManager.getInstance(this);

        initViews();
        displayProductInfo();
        setClickListeners();
        updateFavoriteButtonState();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnFavorite = findViewById(R.id.btnFavorite);
        imgProduct = findViewById(R.id.imgProduct);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }

    private void displayProductInfo() {
        if (sanPham != null) {
            tvProductName.setText(sanPham.getTenSanPham());
            
            // Format giá tiền theo định dạng Việt Nam
            NumberFormat vnFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
            String formattedPrice = vnFormat.format(sanPham.getGia()) + " VNĐ";
            tvProductPrice.setText(formattedPrice);

            // Hiển thị hình ảnh sản phẩm
            if (sanPham.getHinhAnhResId() != 0) {
                imgProduct.setImageResource(sanPham.getHinhAnhResId());
            }
        }
    }

    private void setClickListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thêm sản phẩm vào giỏ hàng
                Toast.makeText(ProductDetailActivity.this, "Đã thêm " + sanPham.getTenSanPham() + " vào giỏ hàng", Toast.LENGTH_SHORT).show();
                // TODO: Implement logic thêm vào giỏ hàng
            }
        });
    }
    
    /**
     * Cập nhật trạng thái nút favorite
     */
    private void updateFavoriteButtonState() {
        if (favoritesManager.isFavorite(sanPham.getMaSanPham())) {
            btnFavorite.setImageResource(R.drawable.ic_favorites_filled);
            btnFavorite.setTag(true);
        } else {
            btnFavorite.setImageResource(R.drawable.ic_favorites);
            btnFavorite.setTag(false);
        }
    }
    
    /**
     * Toggle trạng thái yêu thích
     */
    private void toggleFavorite() {
        // Kiểm tra nếu đang xử lý click thì bỏ qua
        if (isProcessingFavoriteClick) {
            return;
        }
        
        isProcessingFavoriteClick = true;
        
        boolean isCurrentlyFavorite = favoritesManager.isFavorite(sanPham.getMaSanPham());
        
        if (isCurrentlyFavorite) {
            // Nếu đã yêu thích thì xóa khỏi yêu thích
            if (favoritesManager.removeFromFavorites(sanPham.getMaSanPham())) {
                btnFavorite.setImageResource(R.drawable.ic_favorites);
                btnFavorite.setTag(false);
                Toast.makeText(this, "Đã bỏ khỏi yêu thích", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Nếu chưa yêu thích thì thêm vào yêu thích
            if (favoritesManager.addToFavorites(sanPham)) {
                btnFavorite.setImageResource(R.drawable.ic_favorites_filled);
                btnFavorite.setTag(true);
                Toast.makeText(this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
            }
        }
        
        // Reset trạng thái sau 500ms
        new android.os.Handler().postDelayed(() -> isProcessingFavoriteClick = false, 500);
    }
}
