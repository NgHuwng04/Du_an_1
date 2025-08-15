package com.hungnn.du_an_1.ActivityPhu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.Model.BaiViet;
import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class HotNewsActivity extends AppCompatActivity {

    private RecyclerView rvNews;
    private NewsAdapter newsAdapter;
    private List<BaiViet> newsList;
    private List<BaiViet> allNewsList; // Danh sách gốc để tìm kiếm
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_news);

        initViews();
        setupRecyclerView();
        loadSampleData();
        setupClickListeners();
    }

    private void initViews() {
        rvNews = findViewById(R.id.rvNews);
        etSearch = findViewById(R.id.etSearch);
        ImageButton btnBack = findViewById(R.id.btnBack);
        
        btnBack.setOnClickListener(v -> finish());
        
        // Thiết lập tìm kiếm
        setupSearch();
    }

    private void setupRecyclerView() {
        newsList = new ArrayList<>();
        allNewsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, newsList);
        
        rvNews.setLayoutManager(new LinearLayoutManager(this));
        rvNews.setAdapter(newsAdapter);
        
        newsAdapter.setOnNewsClickListener(baiViet -> {
            // TODO: Navigate to news detail activity
            Toast.makeText(this, "Đang mở: " + baiViet.getTieuDe(), Toast.LENGTH_SHORT).show();
        });
    }

    private void loadSampleData() {
        // Dữ liệu mẫu dựa trên hình ảnh và thêm nhiều bài viết khác
        allNewsList.add(new BaiViet(1, 
            "Đây là thiết kế Galaxy S25 FE: Vẻ ngoài quen thuộc, thiết kế mỏng hơn",
            "Amazfit Ative 2 Square Premium là smartwatch cao cấp với thiết kế mặt vuông độc đáo, pin trâu lên tới 10 ngày sử dụng. Sản phẩm tích hợp nhiều tính năng theo dõi sức khỏe và thể thao chuyên nghiệp.", 
            "15/08/2025 14:51",
            1, 
            "Hải Nam",
            "baiviet1.png"));
            
        allNewsList.add(new BaiViet(2, 
            "So sánh Sony Xperia 1 VII vs Samsung Galaxy S25 Ultra: Cuộc đối đầu không khoan nhượng của hai ông lớn!",
            "Samsung Galaxy S25 FE sẽ có thiết kế tương tự Galaxy S25 nhưng mỏng hơn và nhẹ hơn. Sản phẩm dự kiến ra mắt vào cuối năm 2025 với giá cả phải chăng.", 
            "15/08/2025 10:00", 
            1,
            "Hải Nam", 
            "baiviet2.png"));
            
        allNewsList.add(new BaiViet(3, 
            "So sánh Sony Xperia 1 VII vs iPhone 16 Pro Max: Cuộc chiến đỉnh cao giữa hai “ông hoàng” smartphone",
            "Cuộc so sánh chi tiết giữa hai flagship hàng đầu: Sony Xperia 1 VII với camera chuyên nghiệp và Samsung Galaxy S25 Ultra với hiệu năng mạnh mẽ. Cả hai đều có những ưu điểm riêng biệt.", 
            "15/08/2025 07:05", 
            1,
            "Hải Nam", 
            "baiviet3.png"));
            
        allNewsList.add(new BaiViet(4, 
            "Redmi Note 15 Pro sẽ thách thức mọi giới hạn về độ bền và khả năng chống nước",
            "Apple vừa ra mắt iPhone 16 Pro Max với chip A18 Pro mạnh mẽ nhất từ trước đến nay. Camera chính được nâng cấp lên 48MP, hỗ trợ quay video 8K.", 
            "14/08/2025 09:30",
            2,
            "Minh Tuấn", 
            "baiviet4.png"));
            
        allNewsList.add(new BaiViet(5, 
            "Đã iOS 26 và iPadOS 26 public beta 3, bạn đã cập nhật chưa?",
            "Xiaomi tiếp tục cải tiến camera với sự hợp tác của Leica. Xiaomi 15 Ultra có màn hình 2K sắc nét, chip Snapdragon 8 Gen 4 và camera 200MP.", 
            "15/08/2025 11:15",
            3,
            "Lan Anh", 
            "baiviet5.png"));


        // Sao chép dữ liệu vào danh sách hiển thị
        newsList.addAll(allNewsList);
        newsAdapter.notifyDataSetChanged();
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filterNews(s.toString());
            }
        });
    }

    private void filterNews(String query) {
        newsList.clear();
        if (query.isEmpty()) {
            newsList.addAll(allNewsList);
        } else {
            for (BaiViet baiViet : allNewsList) {
                if (baiViet.getTieuDe().toLowerCase().contains(query.toLowerCase()) ||
                    baiViet.getTenTacGia().toLowerCase().contains(query.toLowerCase())) {
                    newsList.add(baiViet);
                }
            }
        }
        newsAdapter.notifyDataSetChanged();
    }

    private void setupClickListeners() {
        // Có thể thêm các click listener khác ở đây
    }
}
