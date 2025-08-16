package com.hungnn.du_an_1.ActivityPhu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.Utils.FavoritesManager;
import com.hungnn.du_an_1.adapter.FavoritesAdapter;
import com.hungnn.du_an_1.Model.SanPham;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements FavoritesAdapter.OnFavoriteRemovedListener {

    private RecyclerView recyclerViewFavorites;
    private ImageButton btnBack;
    private FavoritesManager favoritesManager;
    private FavoritesAdapter favoritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Khởi tạo FavoritesManager
        favoritesManager = FavoritesManager.getInstance(this);

        // Khởi tạo RecyclerView cho danh sách yêu thích
        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo nút Back
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity và quay về MainActivity
            }
        });

        // Thiết lập adapter và tải danh sách yêu thích
        setupRecyclerView();
        loadFavorites();
    }
    
    /**
     * Thiết lập RecyclerView
     */
    private void setupRecyclerView() {
        List<SanPham> favoritesList = favoritesManager.getFavorites();
        favoritesAdapter = new FavoritesAdapter(this, favoritesList);
        favoritesAdapter.setOnFavoriteRemovedListener(this);
        recyclerViewFavorites.setAdapter(favoritesAdapter);
    }
    
    /**
     * Tải danh sách sản phẩm yêu thích
     */
    private void loadFavorites() {
        List<SanPham> favoritesList = favoritesManager.getFavorites();
        if (favoritesList.isEmpty()) {
            // Hiển thị thông báo khi không có sản phẩm yêu thích
            Toast.makeText(this, "Bạn chưa có sản phẩm yêu thích nào", Toast.LENGTH_SHORT).show();
        }
        favoritesAdapter.updateFavoritesList(favoritesList);
    }
    
    @Override
    public void onFavoriteRemoved(SanPham sanPham) {
        // Cập nhật UI khi có sản phẩm bị xóa khỏi yêu thích
        if (favoritesManager.getFavoritesCount() == 0) {
            Toast.makeText(this, "Đã xóa sản phẩm cuối cùng khỏi yêu thích", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật danh sách khi quay lại màn hình
        loadFavorites();
    }
}
