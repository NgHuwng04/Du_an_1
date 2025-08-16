package com.hungnn.du_an_1.ActivityPhu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hungnn.du_an_1.Model.BaiViet;
import com.hungnn.du_an_1.R;

public class NewsDetailActivity extends AppCompatActivity {

    private ImageView imgNewsDetail;
    private TextView tvNewsTitle, tvAuthor, tvDate, tvNewsContent;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        initViews();
        setupClickListeners();
        loadNewsData();
    }

    private void initViews() {
        imgNewsDetail = findViewById(R.id.imgNewsDetail);
        tvNewsTitle = findViewById(R.id.tvNewsTitle);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvDate = findViewById(R.id.tvDate);
        tvNewsContent = findViewById(R.id.tvNewsContent);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadNewsData() {
        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            BaiViet baiViet = (BaiViet) intent.getSerializableExtra("baiViet");
            if (baiViet != null) {
                displayNewsDetail(baiViet);
            }
        }
    }

    private void displayNewsDetail(BaiViet baiViet) {
        // Hiển thị tiêu đề
        tvNewsTitle.setText(baiViet.getTieuDe());
        
        // Hiển thị tác giả
        tvAuthor.setText(baiViet.getTenTacGia());
        
        // Hiển thị ngày đăng
        tvDate.setText(baiViet.getNgayDang());
        
        // Hiển thị nội dung
        tvNewsContent.setText(baiViet.getNoiDung());
        
        // Hiển thị hình ảnh
        String imageName = baiViet.getHinhAnh();
        if (imageName != null && !imageName.isEmpty()) {
            // Loại bỏ phần mở rộng .png hoặc .jpg để lấy tên drawable
            String drawableName = imageName.substring(0, imageName.lastIndexOf('.'));
            int imageResId = getResources().getIdentifier(drawableName, "drawable", getPackageName());
            if (imageResId != 0) {
                imgNewsDetail.setImageResource(imageResId);
            } else {
                // Fallback nếu không tìm thấy hình ảnh
                imgNewsDetail.setImageResource(R.drawable.news_placeholder);
            }
        } else {
            // Fallback nếu không có tên hình ảnh
            imgNewsDetail.setImageResource(R.drawable.news_placeholder);
        }
    }
}
