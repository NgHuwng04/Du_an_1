package com.hungnn.du_an_1.ActivityPhu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.adapter.QuanLySanPhamAdapter;
import com.hungnn.du_an_1.DAO.CategoryDAO;
import com.hungnn.du_an_1.DAO.SanPhamDAO;
import com.hungnn.du_an_1.Model.DanhMuc;
import com.hungnn.du_an_1.Model.SanPham;
import java.util.ArrayList;
import java.util.List;

public class QuanLySanPhamActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuanLySanPhamAdapter adapter;
    private List<SanPham> sanPhamList;
    private SanPhamDAO sanPhamDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);

        recyclerView = findViewById(R.id.recycler_view_san_pham);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }
        FloatingActionButton fab = findViewById(R.id.fab_add_san_pham);
        SearchView searchView = findViewById(R.id.search_view);
        sanPhamDAO = new SanPhamDAO(this);

        setupRecyclerView();
        loadData();

        fab.setOnClickListener(v -> showProductDialog(null));
        setupSearchView(searchView);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sanPhamList = new ArrayList<>();
        adapter = new QuanLySanPhamAdapter(this, sanPhamList, new QuanLySanPhamAdapter.OnItemInteractionListener() {
            @Override
            public void onEditClick(SanPham sanPham) {
                showProductDialog(sanPham);
            }

            @Override
            public void onDeleteClick(SanPham sanPham) {
                showDeleteConfirmDialog(sanPham);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        List<SanPham> updatedList = sanPhamDAO.getAll();
        adapter.setData(updatedList);
    }

    private void setupSearchView(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<SanPham> searchResult = sanPhamDAO.search(newText);
                adapter.setData(searchResult);
                return true;
            }
        });
    }

    private void showProductDialog(final SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate layout XML thành một đối tượng View
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_san_pham, null);
        builder.setView(view);

        // Dùng 'view.findViewById' để tìm các thành phần bên trong dialog
        final TextView tvTitle = view.findViewById(R.id.tv_dialog_title);
        final EditText edtTenSP = view.findViewById(R.id.edt_ten_san_pham);
        final EditText edtMoTa = view.findViewById(R.id.edt_mo_ta);
        final EditText edtGia = view.findViewById(R.id.edt_gia);
        final EditText edtSoLuong = view.findViewById(R.id.edt_so_luong);
        final Spinner spinnerDanhMuc = view.findViewById(R.id.spinner_danh_muc);

        CategoryDAO categoryDAO = new CategoryDAO(this);
        categoryDAO.open();
        List<DanhMuc> danhMucList = categoryDAO.getAllCategories();
        categoryDAO.close();

        ArrayAdapter<DanhMuc> danhMucAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, danhMucList);
        danhMucAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDanhMuc.setAdapter(danhMucAdapter);

        final boolean isEditMode = sanPham != null;
        if (isEditMode) {
            tvTitle.setText("Sửa Sản Phẩm");
            edtTenSP.setText(sanPham.getTenSanPham());
            edtMoTa.setText(sanPham.getMoTa());
            java.text.NumberFormat vnFmtInit = java.text.NumberFormat.getInstance(new java.util.Locale("vi","VN"));
            vnFmtInit.setMaximumFractionDigits(0);
            vnFmtInit.setMinimumFractionDigits(0);
            edtGia.setText(vnFmtInit.format(Math.round(sanPham.getGia())));
            edtSoLuong.setText(String.valueOf(sanPham.getSoLuongTon()));
            for (int i = 0; i < danhMucList.size(); i++) {
                if (danhMucList.get(i).getMaDanhMuc() == sanPham.getMaDanhMuc()) {
                    spinnerDanhMuc.setSelection(i);
                    break;
                }
            }
        } else {
            tvTitle.setText("Thêm Sản Phẩm");
        }

        // Format giá nhập: tự động chèn dấu chấm phần nghìn
        final android.text.TextWatcher priceWatcher = new android.text.TextWatcher() {
            private String current = "";
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(android.text.Editable s) {
                String newText = s.toString();
                if (newText.equals(current)) return;
                edtGia.removeTextChangedListener(this);
                String digits = newText.replace(".", "").replace(",", "").replaceAll("[^0-9]", "");
                if (digits.isEmpty()) {
                    current = "";
                    edtGia.setText("");
                    edtGia.addTextChangedListener(this);
                    return;
                }
                try {
                    long value = Long.parseLong(digits);
                    java.text.NumberFormat vnFmt = java.text.NumberFormat.getInstance(new java.util.Locale("vi","VN"));
                    vnFmt.setMaximumFractionDigits(0);
                    vnFmt.setMinimumFractionDigits(0);
                    current = vnFmt.format(value);
                    edtGia.setText(current);
                    edtGia.setSelection(current.length());
                } catch (Exception ignored) {
                }
                edtGia.addTextChangedListener(this);
            }
        };
        edtGia.addTextChangedListener(priceWatcher);

        builder.setPositiveButton(isEditMode ? "Lưu" : "Thêm", null);
        builder.setNegativeButton("Hủy", (d, which) -> d.dismiss());

        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                String ten = edtTenSP.getText().toString().trim();
                String giaStr = edtGia.getText().toString().trim().replace(",", "").replace(".", "");
                String soLuongStr = edtSoLuong.getText().toString().trim();
                DanhMuc selectedDanhMuc = (DanhMuc) spinnerDanhMuc.getSelectedItem();

                if (ten.isEmpty() || giaStr.isEmpty() || soLuongStr.isEmpty() || selectedDanhMuc == null) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                SanPham spToSave = isEditMode ? sanPham : new SanPham();
                spToSave.setTenSanPham(ten);
                spToSave.setMoTa(edtMoTa.getText().toString().trim());
                try {
                    spToSave.setGia(Double.parseDouble(giaStr));
                } catch (NumberFormatException ex) {
                    Toast.makeText(this, "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                spToSave.setSoLuongTon(Integer.parseInt(soLuongStr));
                spToSave.setMaDanhMuc(selectedDanhMuc.getMaDanhMuc());
                spToSave.setHinhAnhResId(R.mipmap.ic_launcher);

                boolean success = isEditMode ? sanPhamDAO.update(spToSave) : sanPhamDAO.insert(spToSave);

                if (success) {
                    Toast.makeText(this, (isEditMode ? "Cập nhật" : "Thêm") + " thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, (isEditMode ? "Cập nhật" : "Thêm") + " thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }

    private void showDeleteConfirmDialog(final SanPham sanPham) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm '" + sanPham.getTenSanPham() + "'?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    if (sanPhamDAO.delete(sanPham.getMaSanPham())) {
                        Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    } else {
                        Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}