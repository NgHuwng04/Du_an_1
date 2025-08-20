package com.hungnn.du_an_1.ActivityPhu;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.DAO.CategoryDAO;
import com.hungnn.du_an_1.Model.DanhMuc;
import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.adapter.CategoryAdapter;

import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private CategoryDAO categoryDAO;
    private List<DanhMuc> categoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoryDAO = new CategoryDAO(this);
        categoryDAO.open();

        categoryList = categoryDAO.getAllCategories();
        adapter = new CategoryAdapter(this, categoryList);
        recyclerView.setAdapter(adapter);
    }
}