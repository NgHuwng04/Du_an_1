package com.hungnn.du_an_1.ActivityPhu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.DAO.OrderDAO;
import com.hungnn.du_an_1.Model.DonHang;
import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.Utils.UserManager;
import com.hungnn.du_an_1.adapter.OrderHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHistory;
    private ImageButton btnBack;
    private TextView tvEmptyHistory;
    private OrderHistoryAdapter orderHistoryAdapter;
    private OrderDAO orderDAO;
    private UserManager userManager;
    private List<DonHang> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Khởi tạo các thành phần
        initViews();
        initData();
        setupRecyclerView();
        loadOrderHistory();
    }

    private void initViews() {
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        btnBack = findViewById(R.id.btnBack);
        tvEmptyHistory = findViewById(R.id.tvEmptyHistory);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        orderDAO = new OrderDAO(this);
        userManager = UserManager.getInstance(this);
        orderList = new ArrayList<>();
    }

    private void setupRecyclerView() {
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        orderHistoryAdapter = new OrderHistoryAdapter(this, orderList);
        recyclerViewHistory.setAdapter(orderHistoryAdapter);
    }

    private void loadOrderHistory() {
        try {
            orderDAO.open();
            
            int userId = userManager.getUserId();
            if (userId == -1) userId = 1; // Fallback nếu không có userId
            
            List<DonHang> orders = orderDAO.getOrdersByUser(userId);
            
            if (orders != null && !orders.isEmpty()) {
                orderList.clear();
                orderList.addAll(orders);
                orderHistoryAdapter.updateOrderList(orderList);
                
                recyclerViewHistory.setVisibility(View.VISIBLE);
                tvEmptyHistory.setVisibility(View.GONE);
            } else {
                recyclerViewHistory.setVisibility(View.GONE);
                tvEmptyHistory.setVisibility(View.VISIBLE);
                tvEmptyHistory.setText("Bạn chưa có đơn hàng nào");
            }
            
        } catch (Exception e) {
            recyclerViewHistory.setVisibility(View.GONE);
            tvEmptyHistory.setVisibility(View.VISIBLE);
            tvEmptyHistory.setText("Có lỗi xảy ra khi tải lịch sử");
        } finally {
            orderDAO.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh dữ liệu khi quay lại màn hình
        loadOrderHistory();
    }
}
