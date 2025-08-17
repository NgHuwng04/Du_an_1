package com.hungnn.du_an_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.DAO.DonHangDAO;
import com.hungnn.du_an_1.Model.DonHang;
import com.hungnn.du_an_1.adapter.DonHangAdapter;

import java.util.ArrayList;
import java.util.List;

public class QuanLyDonHangActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DonHangAdapter adapter;
    private DonHangDAO dao ;
    private Spinner spinnerTrangThai;
    private TextView tvTitle;
    private List<DonHang> danhSachDonHang; // Danh sách đầy đủ
    private List<DonHang> danhSachLoc;     // Danh sách sau khi lọc
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_don_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        tvTitle = findViewById(R.id.tvTitle);
        spinnerTrangThai = findViewById(R.id.spinnerTrangThai);
        recyclerView = findViewById(R.id.recyclerViewDonHang);

        // Khởi tạo dữ liệu mẫu
        danhSachDonHang = getDonHangFakeData();
        danhSachLoc = new ArrayList<>(danhSachDonHang);


        // Thiết lập RecyclerView
        adapter = new DonHangAdapter(danhSachLoc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Button btnThemDonHang = findViewById(R.id.btnThemDonHang);
        btnThemDonHang.setOnClickListener(v -> {
            showDialogThemDonHang();
        });




        // Thiết lập Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Tất cả", "Chờ xử lý", "Đang giao", "Hoàn tất","Hủy"});
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrangThai.setAdapter(spinnerAdapter);

        // Xử lý chọn trạng thái
        spinnerTrangThai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String trangThai = parent.getItemAtPosition(position).toString();
                locDonHangTheoTrangThai(trangThai);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


    }

    private void locDonHangTheoTrangThai(String trangThai) {
        danhSachLoc.clear();
        if (trangThai.equals("Tất cả")) {
            danhSachLoc.addAll(danhSachDonHang);
        } else {
            for (DonHang dh : danhSachDonHang) {
                if (dh.getTrangThai().equals(trangThai)) {
                    danhSachLoc.add(dh);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private List<DonHang> getDonHangFakeData() {
        List<DonHang> list = new ArrayList<>();
        list.add(new DonHang(1,1,"12-7-2025", "Chờ xử lý",1200000));
        list.add(new DonHang(1,1,"12-7-2025", "Chờ xử lý",1200000));
        list.add(new DonHang(1,1,"12-7-2025", "Chờ xử lý",1200000));
        list.add(new DonHang(1,1,"12-7-2025", "Chờ xử lý",1200000));
        return list;
    }

    private void showDialogThemDonHang() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_them_don_hang, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        EditText edtMaNguoiDung = view.findViewById(R.id.edtMaNguoiDung);
        EditText edtNgayDat = view.findViewById(R.id.edtNgayDat);
        EditText edtTongTien = view.findViewById(R.id.edtTongTien);
        Spinner spTrangThai = view.findViewById(R.id.spTrangThai);
        Button btnLuu = view.findViewById(R.id.btnLuu);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        ArrayAdapter<String> adapterTrangThai = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Chờ cử lý", "đang giao", "đã giao", "hủy"});
        adapterTrangThai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTrangThai.setAdapter(adapterTrangThai);

        btnLuu.setOnClickListener(v -> {
            try {
                DonHang dh = new DonHang();
                dh.setMaNguoiDung(Integer.parseInt(edtMaNguoiDung.getText().toString()));
                dh.setNgayDat(edtNgayDat.getText().toString());
                dh.setTongTien(Double.parseDouble(edtTongTien.getText().toString()));
                dh.setTrangThai(spTrangThai.getSelectedItem().toString());

                DonHangDAO dao = new DonHangDAO(this);
                long result = dao.themDonHang(dh);
                if (result!=1) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    danhSachDonHang.add(0, dh);
                    locDonHangTheoTrangThai(spinnerTrangThai.getSelectedItem().toString());
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Lỗi nhập dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });

        btnHuy.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }



}