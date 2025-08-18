package com.hungnn.du_an_1.ActivityPhu;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.Model.SanPham;
import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAllProducts;
    private ImageButton btnBack;
    private TextView tvTitle;
    private ProductAdapter productAdapter;
    private List<SanPham> allProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        initViews();
        initializeProducts();
        setupRecyclerView();
        setClickListeners();
    }

    private void initViews() {
        recyclerViewAllProducts = findViewById(R.id.recyclerViewAllProducts);
        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
    }

    private void initializeProducts() {
        allProducts = new ArrayList<>();
        
        // Lấy resource ID cho các hình ảnh
        int img16ProMax = getDrawableIdByName("ic_iphone16_pro_max");
        int img15ProMax = getDrawableIdByName("ic_iphone15_pro_max");
        int img14ProMax = getDrawableIdByName("ic_iphone14_pro_max");
        
        int imgSamSungA16 = getDrawableIdByName("ic_samsung_galaxy_a16");
        int imgSamSungS24 = getDrawableIdByName("ic_samsung_galaxy_s24");
        int imgSamSungZFold7 = getDrawableIdByName("ic_samsung_galaxy_zfold7");
        
        int imgXiaomi14TPro = getDrawableIdByName("ic_xiaomi_14t_pro");
        int imgXiaomi15Ultra = getDrawableIdByName("ic_xiaomi_15_ultra");
        int imgXiaomiPocoX7 = getDrawableIdByName("ic_xiaomi_poco_x7_pro");
        
        int imgOppoFindN5 = getDrawableIdByName("ic_oppo_find_n5");
        int imgOppoReno14 = getDrawableIdByName("ic_oppo_reno14");
        int imgOppoReno10Pro = getDrawableIdByName("ic_oppo_reno10_pro");
        
        // Thêm tất cả sản phẩm iPhone (maDanhMuc = 1)
        allProducts.add(new SanPham(1, "IPhone 16 Pro Max 256GB", "IPhone 16 Pro Max sử dụng IOS 18, Cáp sạc USB-C (1m), Kích thước màn hình 6.9 inches", 29990000, 10, 1, img16ProMax));
        allProducts.add(new SanPham(2, "IPhone 15 Pro Max 256GB", "IPhone 15 Pro Max sử dụng IOS 17, Cáp sạc USB-C, Kích thước màn hình 6.7 inches", 27690000, 10, 1, img15ProMax));
        allProducts.add(new SanPham(3, "IPhone 14 Pro Max 128GB", "IPhone 14 Pro Max sử dụng IOS 16, Cáp sạc Lightning, Kích thước màn hình 6.7 inches", 25590000, 10, 1, img14ProMax));

        // Thêm tất cả sản phẩm Samsung (maDanhMuc = 2)
        allProducts.add(new SanPham(4, "Samsung Galaxy A16 5G 8GB 128GB", "Samsung Galaxy A16 5G 8GB 128GB Công nghệ màn hình SuperAmoled, Tần số quét 90HZ, độ sáng tối đa 800nits, Kích thước màn hình 6.7 inches", 26990000, 10, 2, imgSamSungA16));
        allProducts.add(new SanPham(5, "Samsung Galaxy S24 FE 5G 8GB 128GB", "Samsung Galaxy S24 FE 5G 8GB 128GB sử dụng Android 14, 120HZ, Pin 4700 mAH , Kích thước màn hình 6.7 inches", 12590000, 10, 2, imgSamSungS24));
        allProducts.add(new SanPham(6, "Samsung Galaxy Z Fold7 12GB 256GB", "Samsung Galaxy Z Fold7 12GB 256GB sử dụng Android, Loại CPU 8 nhân 4.47GHz 3.5GHz ,Kích thước màn hình 8 inches", 44990000, 10, 2, imgSamSungZFold7));

        // Thêm tất cả sản phẩm Xiaomi (maDanhMuc = 3)
        allProducts.add(new SanPham(7, "Xiaomi 14T Pro 12GB 512GB", "Xiaomi 14T Pro 12GB 512GB sử dụng công nghệ màn hình Amoled, công nghệ NFC, Kích thước màn hình 6.67 inches", 14870000, 10, 3, imgXiaomi14TPro));
        allProducts.add(new SanPham(8, "Xiaomi 15 Ultra 5G 16GB 1TB", "Xiaomi 15 Ultra 5G 16GB 1TB sử dụng hệ điều hành Xiaomi HyperOS 2, Pin 5410mAH, Kích thước màn hình 6.73 inches", 33590000, 10, 3, imgXiaomi15Ultra));
        allProducts.add(new SanPham(9, "Xiaomi POCO X7 Pro 5G 12GB 256GB", "Xiaomi POCO X7 Pro 5G 12GB 256GB sử dụng công nghệ màn hình Amoled, Hệ điều hành Android 14, Kích thước màn hình 6.67 inches", 15990000, 10, 3, imgXiaomiPocoX7));

        // Thêm tất cả sản phẩm OPPO (maDanhMuc = 4)
        allProducts.add(new SanPham(10, "OPPO FIND N5", "OPPO FIND N5 sử dụng ColorOS 15, nền tảng Android 15, Tần số: 120Hz, Kích thước màn hình 8.12 inches", 44180000, 10, 4, imgOppoFindN5));
        allProducts.add(new SanPham(11, "OPPO Reno14 F 5G 8GB 256GB", "OPPO Reno14 F 5G 8GB 256GB sử dụng ColorOS 15, Android 15, 8 nhân tối đa 2.2GHz, Kích thước màn hình 6.57 inches", 10300000, 10, 4, imgOppoReno14));
        allProducts.add(new SanPham(12, "OPPO Reno10 Pro+ 5G 12GB 256GB", "OPPO Reno10 Pro+ 5G 12GB 256GB sử dụng Android 13, độ phân giải màn hình 1240x2772 pixels, Kích thước màn hình 6.74 inch", 10990000, 10, 4, imgOppoReno10Pro));
    }

    private int getDrawableIdByName(String name) {
        return getResources().getIdentifier(name, "drawable", getPackageName());
    }

    private void setupRecyclerView() {
        // Sử dụng GridLayoutManager để hiển thị 2 cột
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewAllProducts.setLayoutManager(gridLayoutManager);
        
        productAdapter = new ProductAdapter(this, allProducts);
        recyclerViewAllProducts.setAdapter(productAdapter);
    }

    private void setClickListeners() {
        btnBack.setOnClickListener(v -> finish());
    }
}
