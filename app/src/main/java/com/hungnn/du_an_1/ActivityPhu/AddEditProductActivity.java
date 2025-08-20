package com.hungnn.du_an_1.ActivityPhu;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.hungnn.du_an_1.DAO.CategoryDAO;
import com.hungnn.du_an_1.DAO.SanPhamDAO;
import com.hungnn.du_an_1.Model.DanhMuc;
import com.hungnn.du_an_1.Model.SanPham;
import com.hungnn.du_an_1.R;
import java.util.List;

public class AddEditProductActivity extends AppCompatActivity {

    private EditText edtTenSP, edtMoTa, edtGia, edtSoLuong;
    private Spinner spinnerDanhMuc;
    private SanPhamDAO sanPhamDAO;
    private SanPham currentSanPham;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);

        Toolbar toolbar = findViewById(R.id.toolbar_add_edit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sanPhamDAO = new SanPhamDAO(this);
        edtTenSP = findViewById(R.id.edt_ten_san_pham);
        edtMoTa = findViewById(R.id.edt_mo_ta);
        edtGia = findViewById(R.id.edt_gia);
        edtSoLuong = findViewById(R.id.edt_so_luong);
        spinnerDanhMuc = findViewById(R.id.spinner_danh_muc);
        Button btnSave = findViewById(R.id.btn_save_product);

        setupSpinner();

        int productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        if (productId != -1) {
            isEditMode = true;
            getSupportActionBar().setTitle("Sửa sản phẩm");
            currentSanPham = sanPhamDAO.getById(productId);
            populateFields();
        } else {
            getSupportActionBar().setTitle("Thêm sản phẩm");
        }

        btnSave.setOnClickListener(v -> saveProduct());
    }

    private void setupSpinner() {
        CategoryDAO categoryDAO = new CategoryDAO(this);
        categoryDAO.open();
        List<DanhMuc> danhMucList = categoryDAO.getAllCategories();
        categoryDAO.close();

        ArrayAdapter<DanhMuc> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, danhMucList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDanhMuc.setAdapter(adapter);
    }

    private void populateFields() {
        if (currentSanPham != null) {
            edtTenSP.setText(currentSanPham.getTenSanPham());
            edtMoTa.setText(currentSanPham.getMoTa());
            edtGia.setText(String.valueOf(currentSanPham.getGia()));
            edtSoLuong.setText(String.valueOf(currentSanPham.getSoLuongTon()));
            // Set spinner selection
            ArrayAdapter<DanhMuc> adapter = (ArrayAdapter<DanhMuc>) spinnerDanhMuc.getAdapter();
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).getMaDanhMuc() == currentSanPham.getMaDanhMuc()) {
                    spinnerDanhMuc.setSelection(i);
                    break;
                }
            }
        }
    }

    private void saveProduct() {
        String ten = edtTenSP.getText().toString().trim();
        // ... (validation logic as before) ...

        SanPham sp = isEditMode ? currentSanPham : new SanPham();
        sp.setTenSanPham(ten);
        // ... (set other fields from EditTexts) ...
        sp.setMaDanhMuc(((DanhMuc) spinnerDanhMuc.getSelectedItem()).getMaDanhMuc());

        boolean success;
        if (isEditMode) {
            success = sanPhamDAO.update(sp);
        } else {
            success = sanPhamDAO.insert(sp);
        }

        if (success) {
            Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}