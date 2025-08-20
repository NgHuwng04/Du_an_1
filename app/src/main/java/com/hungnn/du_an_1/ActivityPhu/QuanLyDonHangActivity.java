package com.hungnn.du_an_1.ActivityPhu;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.DAO.OrderDAO;
import com.hungnn.du_an_1.Model.DonHang;
import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.adapter.OrderManagementAdapter;
import android.content.DialogInterface;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class QuanLyDonHangActivity extends AppCompatActivity implements OrderManagementAdapter.OnOrderActionListener {

    private RecyclerView recyclerView;
    private OrderManagementAdapter adapter;
    private OrderDAO orderDAO;
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

        // Khởi tạo OrderDAO
        orderDAO = new OrderDAO(this);

        // Tải dữ liệu đơn hàng từ database
        loadDonHangFromDatabase();

        // Thiết lập RecyclerView
        adapter = new OrderManagementAdapter(this, danhSachLoc);
        adapter.setOnOrderActionListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



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
            // Map tên tiếng Việt sang giá trị database
            String dbTrangThai = mapTrangThaiToDatabase(trangThai);
            for (DonHang dh : danhSachDonHang) {
                if (dh.getTrangThai().equals(dbTrangThai)) {
                    danhSachLoc.add(dh);
                }
            }
        }
        adapter.updateOrderList(danhSachLoc);
    }

    private String mapTrangThaiToDatabase(String trangThaiTiengViet) {
        switch (trangThaiTiengViet) {
            case "Chờ xử lý":
                return "cho_xu_ly";
            case "Đang giao":
                return "dang_giao";
            case "Hoàn tất":
                return "da_giao";
            case "Hủy":
                return "huy";
            default:
                return trangThaiTiengViet;
        }
    }

    private void loadDonHangFromDatabase() {
        try {
            orderDAO.open();
            
            // Lấy tất cả đơn hàng từ database
            danhSachDonHang = orderDAO.getAllOrders();
            danhSachLoc = new ArrayList<>(danhSachDonHang);
            
            // Cập nhật adapter nếu đã được khởi tạo
            if (adapter != null) {
                adapter.updateOrderList(danhSachLoc);
            }
            
        } catch (Exception e) {
            // Nếu có lỗi, khởi tạo danh sách rỗng
            danhSachDonHang = new ArrayList<>();
            danhSachLoc = new ArrayList<>();
        } finally {
            orderDAO.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh dữ liệu khi quay lại màn hình
        loadDonHangFromDatabase();
    }

    // Implement OnOrderActionListener
    @Override
    public void onEditOrder(DonHang order, int position) {
        showEditStatusDialog(order);
    }

    @Override
    public void onDeleteOrder(DonHang order, int position) {
        showDeleteConfirmDialog(order, position);
    }

    // Hiển thị dialog để sửa trạng thái
    private void showEditStatusDialog(DonHang order) {
        String[] statusOptions = {"Chờ xử lý", "Đang giao", "Hoàn tất", "Hủy"};
        String currentStatus = getStatusText(order.getTrangThai());
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cập nhật trạng thái đơn hàng #" + order.getMaDonHang());
        builder.setSingleChoiceItems(statusOptions, getStatusIndex(order.getTrangThai()), 
            (dialog, which) -> {
                String newStatus = mapTrangThaiToDatabase(statusOptions[which]);
                updateOrderStatus(order, newStatus);
                dialog.dismiss();
            });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    // Hiển thị dialog xác nhận xóa
    private void showDeleteConfirmDialog(DonHang order, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa đơn hàng #" + order.getMaDonHang() + "?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            deleteOrder(order, position);
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    // Cập nhật trạng thái đơn hàng
    private void updateOrderStatus(DonHang order, String newStatus) {
        try {
            orderDAO.open();
            boolean success = orderDAO.updateOrderStatus(order.getMaDonHang(), newStatus);
            if (success) {
                Toast.makeText(this, "Cập nhật trạng thái thành công!", Toast.LENGTH_SHORT).show();
                loadDonHangFromDatabase(); // Refresh dữ liệu
            } else {
                Toast.makeText(this, "Có lỗi xảy ra khi cập nhật!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            orderDAO.close();
        }
    }

    // Xóa đơn hàng
    private void deleteOrder(DonHang order, int position) {
        try {
            orderDAO.open();
            boolean success = orderDAO.deleteOrder(order.getMaDonHang());
            if (success) {
                Toast.makeText(this, "Xóa đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                loadDonHangFromDatabase(); // Refresh dữ liệu
            } else {
                Toast.makeText(this, "Có lỗi xảy ra khi xóa!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            orderDAO.close();
        }
    }

    // Lấy index của trạng thái hiện tại
    private int getStatusIndex(String status) {
        switch (status) {
            case "cho_xu_ly":
                return 0;
            case "dang_giao":
                return 1;
            case "da_giao":
                return 2;
            case "huy":
                return 3;
            default:
                return 0;
        }
    }

    // Chuyển đổi trạng thái database sang tiếng Việt
    private String getStatusText(String status) {
        switch (status) {
            case "cho_xu_ly":
                return "Chờ xử lý";
            case "dang_giao":
                return "Đang giao";
            case "da_giao":
                return "Hoàn tất";
            case "huy":
                return "Hủy";
            default:
                return status;
        }
    }
}