package com.hungnn.du_an_1.ActivityPhu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hungnn.du_an_1.DAO.CategoryDAO;
import com.hungnn.du_an_1.Model.DanhMuc;
import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryActionListener {

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private CategoryDAO categoryDAO;
    private List<DanhMuc> categoryList;
    private FloatingActionButton fabAddCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        recyclerView = findViewById(R.id.recyclerViewCategories);
        fabAddCategory = findViewById(R.id.fabAddCategory);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        categoryDAO = new CategoryDAO(this);
        categoryList = new ArrayList<>();

        setupRecyclerView();
        loadCategories();

        fabAddCategory.setOnClickListener(v -> showAddEditDialog(null));
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryAdapter(this, categoryList, this);
        recyclerView.setAdapter(adapter);
    }

    private void loadCategories() {
        categoryDAO.open();
        List<DanhMuc> updatedList = categoryDAO.getAllCategories();
        // Auto-seed thiếu mục nào thì thêm mục đó
        String[][] defaults = new String[][]{
                {"iPhone", "Đời mới nhất"},
                {"Samsung", "Màn hình OLED"},
                {"Xiaomi", "Giá tốt, cấu hình cao"},
                {"Oppo", "Thiết kế trẻ trung"}
        };
        for (String[] def : defaults) {
            if (!categoryDAO.existsByName(def[0])) {
                categoryDAO.insertCategory(new DanhMuc(0, def[0], def[1]));
            }
        }
        updatedList = categoryDAO.getAllCategories();
        adapter.updateList(updatedList);
        categoryDAO.close();
    }

    @Override
    public void onEdit(DanhMuc category) {
        showAddEditDialog(category);
    }

    @Override
    public void onDelete(DanhMuc category) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa danh mục '" + category.getTenDanhMuc() + "'?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    categoryDAO.open();
                    boolean success = categoryDAO.deleteCategory(category.getMaDanhMuc());
                    categoryDAO.close();
                    if (success) {
                        Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        loadCategories(); // Tải lại danh sách
                    } else {
                        Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showAddEditDialog(final DanhMuc category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_edit_category, null);
        builder.setView(dialogView);

        final TextView tvTitle = dialogView.findViewById(R.id.tvDialogTitle);
        final EditText edtName = dialogView.findViewById(R.id.edtCategoryName);
        final EditText edtDesc = dialogView.findViewById(R.id.edtCategoryDescription);

        final boolean isEditMode = (category != null);

        if (isEditMode) {
            tvTitle.setText("Sửa Danh Mục");
            edtName.setText(category.getTenDanhMuc());
            edtDesc.setText(category.getMoTa());
        } else {
            tvTitle.setText("Thêm Danh Mục");
        }

        builder.setPositiveButton(isEditMode ? "Lưu" : "Thêm", (dialog, which) -> {
            String name = edtName.getText().toString().trim();
            String desc = edtDesc.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Tên danh mục không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }

            // Xác nhận trước khi lưu
            new AlertDialog.Builder(this)
                .setTitle(isEditMode ? "Xác nhận lưu thay đổi" : "Xác nhận thêm mới")
                .setMessage((isEditMode ? "Lưu thay đổi cho danh mục: " : "Thêm danh mục: ") + name + "?")
                .setPositiveButton(isEditMode ? "Lưu" : "Thêm", (d, w) -> {
                    categoryDAO.open();
                    boolean success;
                    if (isEditMode) {
                        category.setTenDanhMuc(name);
                        category.setMoTa(desc);
                        success = categoryDAO.updateCategory(category);
                    } else {
                        DanhMuc newCategory = new DanhMuc(0, name, desc);
                        success = categoryDAO.insertCategory(newCategory);
                    }
                    categoryDAO.close();

                    if (success) {
                        Toast.makeText(this, (isEditMode ? "Cập nhật" : "Thêm") + " thành công", Toast.LENGTH_SHORT).show();
                        loadCategories();
                    } else {
                        Toast.makeText(this, (isEditMode ? "Cập nhật" : "Thêm") + " thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}