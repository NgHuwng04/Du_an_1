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

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageButton btnBack, btnFavorite;
    private ImageView imgProduct;
    private TextView tvProductName, tvProductPrice;
    private Button btnAddToCart;
    private SanPham sanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Lấy dữ liệu sản phẩm từ Intent
        int maSanPham = getIntent().getIntExtra("maSanPham", 0);
        String tenSanPham = getIntent().getStringExtra("tenSanPham");
        double gia = getIntent().getDoubleExtra("gia", 0);
        int hinhAnhResId = getIntent().getIntExtra("hinhAnhResId", 0);

        // Tạo đối tượng SanPham
        sanPham = new SanPham(maSanPham, tenSanPham, "", gia, 0, 0, hinhAnhResId);

        initViews();
        displayProductInfo();
        setClickListeners();
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
                // Toggle trạng thái yêu thích
                if (btnFavorite.getTag() == null || !(Boolean) btnFavorite.getTag()) {
                    btnFavorite.setImageResource(R.drawable.ic_favorites);
                    btnFavorite.setTag(true);
                    Toast.makeText(ProductDetailActivity.this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    btnFavorite.setImageResource(R.drawable.ic_favorites);
                    btnFavorite.setTag(false);
                    Toast.makeText(ProductDetailActivity.this, "Đã bỏ khỏi yêu thích", Toast.LENGTH_SHORT).show();
                }
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
}
