package com.hungnn.du_an_1;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProducts;
    
    // Các View cho Category Navigation
    private LinearLayout tabIphone, tabSamsung, tabXiaomi, tabOppo;
    private TextView tvIphone, tvSamsung, tvXiaomi, tvOppo;
    private View lineIphone, lineSamsung, lineXiaomi, lineOppo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Khởi tạo các View
        initViews();
        
        // Khởi tạo RecyclerView
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Thiết lập click listeners cho category navigation
        setCategoryClickListeners();
    }
    
    private void initViews() {
        // Khởi tạo các tab category
        tabIphone = findViewById(R.id.tab_iphone);
        tabSamsung = findViewById(R.id.tab_samsung);
        tabXiaomi = findViewById(R.id.tab_xiaomi);
        tabOppo = findViewById(R.id.tab_oppo);
        
        // Lấy tham chiếu TextView và line từ mỗi tab
        tvIphone = (TextView) tabIphone.getChildAt(0);
        tvSamsung = (TextView) tabSamsung.getChildAt(0);
        tvXiaomi = (TextView) tabXiaomi.getChildAt(0);
        tvOppo = (TextView) tabOppo.getChildAt(0);
        
        lineIphone = tabIphone.getChildAt(1);
        lineSamsung = tabSamsung.getChildAt(1);
        lineXiaomi = tabXiaomi.getChildAt(1);
        lineOppo = tabOppo.getChildAt(1);
    }
    
    private void setCategoryClickListeners() {
        tabIphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCategory(0); // Chọn IPHONE
            }
        });
        
        tabSamsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCategory(1); // Chọn SAMSUNG
            }
        });
        
        tabXiaomi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCategory(2); // Chọn XIAOMI
            }
        });
        
        tabOppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCategory(3); // Chọn OPPO
            }
        });
    }
    
    private void selectCategory(int categoryIndex) {
        // Đặt lại tất cả các tab về trạng thái mặc định
        resetAllTabs();
        
        // Thiết lập trạng thái cho tab được chọn
        switch (categoryIndex) {
            case 0: // IPHONE
                tvIphone.setTextColor(0xFFFA4A0C); // Màu cam
                tvIphone.setTypeface(null, android.graphics.Typeface.BOLD);
                lineIphone.setBackgroundColor(0xFFFA4A0C);
                // TODO: Tải danh sách sản phẩm iPhone vào RecyclerView
                break;
            case 1: // SAMSUNG
                tvSamsung.setTextColor(0xFFFA4A0C);
                tvSamsung.setTypeface(null, android.graphics.Typeface.BOLD);
                lineSamsung.setBackgroundColor(0xFFFA4A0C);
                // TODO: Tải danh sách sản phẩm Samsung vào RecyclerView
                break;
            case 2: // XIAOMI
                tvXiaomi.setTextColor(0xFFFA4A0C);
                tvXiaomi.setTypeface(null, android.graphics.Typeface.BOLD);
                lineXiaomi.setBackgroundColor(0xFFFA4A0C);
                // TODO: Tải danh sách sản phẩm Xiaomi vào RecyclerView
                break;
            case 3: // OPPO
                tvOppo.setTextColor(0xFFFA4A0C);
                tvOppo.setTypeface(null, android.graphics.Typeface.BOLD);
                lineOppo.setBackgroundColor(0xFFFA4A0C);
                // TODO: Tải danh sách sản phẩm OPPO vào RecyclerView
                break;
        }
    }
    
    private void resetAllTabs() {
        // Đặt lại tất cả chữ về màu đen và bỏ in đậm
        tvIphone.setTextColor(0xFF000000);
        tvIphone.setTypeface(null, android.graphics.Typeface.NORMAL);
        tvSamsung.setTextColor(0xFF000000);
        tvSamsung.setTypeface(null, android.graphics.Typeface.NORMAL);
        tvXiaomi.setTextColor(0xFF000000);
        tvXiaomi.setTypeface(null, android.graphics.Typeface.NORMAL);
        tvOppo.setTextColor(0xFF000000);
        tvOppo.setTypeface(null, android.graphics.Typeface.NORMAL);
        
        // Đặt lại tất cả các gạch chân về trong suốt
        lineIphone.setBackgroundColor(0x00000000);
        lineSamsung.setBackgroundColor(0x00000000);
        lineXiaomi.setBackgroundColor(0x00000000);
        lineOppo.setBackgroundColor(0x00000000);
    }
}
