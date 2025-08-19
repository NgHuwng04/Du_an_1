package com.hungnn.du_an_1.ActivityPhu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.hungnn.du_an_1.DAO.CartDAO;
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
    private CartDAO cartDAO; // Khai báo CartDAO

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

        // Khởi tạo đối tượng sản phẩm đầy đủ thông tin
        sanPham = new SanPham();
        sanPham.setMaSanPham(maSanPham);
        sanPham.setTenSanPham(tenSanPham);
        sanPham.setMoTa(moTa);
        sanPham.setGia(gia);
        sanPham.setHinhAnhResId(hinhAnhResId);
        favoritesManager = FavoritesManager.getInstance(this);
        cartDAO = new CartDAO(this); // Khởi tạo CartDAO

        // Ánh xạ các View
        btnBack = findViewById(R.id.btnBack);
        btnFavorite = findViewById(R.id.btnFavorite);
        imgProduct = findViewById(R.id.imgProduct);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        // Hiển thị dữ liệu sản phẩm lên giao diện
        tvProductName.setText(sanPham.getTenSanPham());
        NumberFormat vnFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedPrice = vnFormat.format(sanPham.getGia()) + " VNĐ";
        tvProductPrice.setText(formattedPrice);
        imgProduct.setImageResource(sanPham.getHinhAnhResId());

        // Kiểm tra và đặt trạng thái nút yêu thích
        updateFavoriteButtonState();

        // Xử lý sự kiện
        btnBack.setOnClickListener(v -> finish());
        btnFavorite.setOnClickListener(v -> toggleFavorite());

        // Xử lý sự kiện nút Thêm vào giỏ hàng
        btnAddToCart.setOnClickListener(v -> addToCart());
    }

    private void updateFavoriteButtonState() {
        if (favoritesManager.isFavorite(sanPham.getMaSanPham())) {
            btnFavorite.setImageResource(R.drawable.ic_favorites_filled);
            btnFavorite.setTag(true);
        } else {
            btnFavorite.setImageResource(R.drawable.ic_favorites);
            btnFavorite.setTag(false);
        }
    }

    private void toggleFavorite() {
        if (isProcessingFavoriteClick) {
            return;
        }
        isProcessingFavoriteClick = true;

        boolean isCurrentlyFavorite = favoritesManager.isFavorite(sanPham.getMaSanPham());
        if (isCurrentlyFavorite) {
            if (favoritesManager.removeFromFavorites(sanPham.getMaSanPham())) {
                btnFavorite.setImageResource(R.drawable.ic_favorites);
                btnFavorite.setTag(false);
                Toast.makeText(this, "Đã bỏ khỏi yêu thích", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (favoritesManager.addToFavorites(sanPham)) {
                btnFavorite.setImageResource(R.drawable.ic_favorites_filled);
                btnFavorite.setTag(true);
                Toast.makeText(this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
            }
        }
        new android.os.Handler().postDelayed(() -> isProcessingFavoriteClick = false, 500);
    }

    // Phương thức thêm sản phẩm vào giỏ hàng
    private void addToCart() {
        // Giả sử maNguoiDung = 1, bạn có thể lấy ID người dùng từ SharedPreferences hoặc Auth
        int maNguoiDung = 1;
        int soLuong = 1; // Số lượng mặc định là 1

        boolean success = cartDAO.addToCartWithProduct(maNguoiDung, sanPham, soLuong);
        if (success) {
            // Sau khi thêm thành công, chuyển sang màn hình giỏ hàng
            android.content.Intent intent = new android.content.Intent(this, CartActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}