package com.hungnn.du_an_1.ActivityPhu;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hungnn.du_an_1.DAO.OrderDAO;
import com.hungnn.du_an_1.Model.DonHang;
import com.hungnn.du_an_1.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RevenueActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvStartDate, tvEndDate, tvTotalRevenue, tvOrderCount, tvAverageOrder, tvDateRange;
    private Button btnCalculate;
    private OrderDAO orderDAO;
    
    private Calendar startDate, endDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);

        initViews();
        initData();
        setupClickListeners();
        calculateRevenue();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        tvTotalRevenue = findViewById(R.id.tvTotalRevenue);
        tvOrderCount = findViewById(R.id.tvOrderCount);
        tvAverageOrder = findViewById(R.id.tvAverageOrder);
        tvDateRange = findViewById(R.id.tvDateRange);
        btnCalculate = findViewById(R.id.btnCalculate);
    }

    private void initData() {
        orderDAO = new OrderDAO(this);
        
        // Khởi tạo ngày mặc định (tuần hiện tại)
        startDate = Calendar.getInstance();
        startDate.add(Calendar.DAY_OF_MONTH, -7); // 7 ngày trước
        
        endDate = Calendar.getInstance(); // Hôm nay
        
        updateDateDisplay();
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
        
        tvStartDate.setOnClickListener(v -> showDatePicker(true));
        tvEndDate.setOnClickListener(v -> showDatePicker(false));
        
        btnCalculate.setOnClickListener(v -> calculateRevenue());
    }

    private void showDatePicker(boolean isStartDate) {
        Calendar currentDate = isStartDate ? startDate : endDate;
        
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            (view, year, month, dayOfMonth) -> {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);
                
                if (isStartDate) {
                    startDate = selectedDate;
                    // Đảm bảo ngày bắt đầu không lớn hơn ngày kết thúc
                    if (startDate.after(endDate)) {
                        endDate = startDate;
                    }
                } else {
                    endDate = selectedDate;
                    // Đảm bảo ngày kết thúc không nhỏ hơn ngày bắt đầu
                    if (endDate.before(startDate)) {
                        startDate = endDate;
                    }
                }
                
                updateDateDisplay();
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        );
        
        datePickerDialog.show();
    }

    private void updateDateDisplay() {
        tvStartDate.setText(dateFormat.format(startDate.getTime()));
        tvEndDate.setText(dateFormat.format(endDate.getTime()));
        tvDateRange.setText("Khoảng thời gian: " + dateFormat.format(startDate.getTime()) + " - " + dateFormat.format(endDate.getTime()));
    }

    private void calculateRevenue() {
        try {
            orderDAO.open();
            
            // Lấy tất cả đơn hàng trong khoảng thời gian và có trạng thái "da_giao"
            List<DonHang> completedOrders = orderDAO.getCompletedOrdersInDateRange(
                dateFormat.format(startDate.getTime()),
                dateFormat.format(endDate.getTime())
            );
            
            double totalRevenue = 0;
            int orderCount = 0;
            
            if (completedOrders != null && !completedOrders.isEmpty()) {
                for (DonHang order : completedOrders) {
                    if ("da_giao".equals(order.getTrangThai())) {
                        totalRevenue += order.getTongTien();
                        orderCount++;
                    }
                }
            }
            
            // Hiển thị kết quả
            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            tvTotalRevenue.setText(formatter.format(totalRevenue) + " VNĐ");
            tvOrderCount.setText("Số đơn hàng: " + orderCount);
            
            double averageOrder = orderCount > 0 ? totalRevenue / orderCount : 0;
            tvAverageOrder.setText("Trung bình/đơn: " + formatter.format(averageOrder) + " VNĐ");
            
            if (orderCount == 0) {
                Toast.makeText(this, "Không có đơn hàng hoàn tất trong khoảng thời gian này", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đã tính doanh thu thành công", Toast.LENGTH_SHORT).show();
            }
            
        } catch (Exception e) {
            Toast.makeText(this, "Có lỗi xảy ra khi tính doanh thu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            orderDAO.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orderDAO != null) {
            orderDAO.close();
        }
    }
}
