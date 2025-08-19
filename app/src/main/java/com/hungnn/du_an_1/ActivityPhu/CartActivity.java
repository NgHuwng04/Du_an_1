package com.hungnn.du_an_1.ActivityPhu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.DAO.CartDAO;
import com.hungnn.du_an_1.Model.GioHang;
import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.adapter.CartAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private RecyclerView rvCartItems;
    private TextView tvEmptyCart; // Thêm TextView này
    private TextView tvComplete; // dùng Button trong layout, nhưng để tương thích tìm theo id
    private CartDAO cartDAO;
    private List<GioHang> cartItems;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Ánh xạ các View
        btnBack = findViewById(R.id.btnBack);
        rvCartItems = findViewById(R.id.rvCartItems);
        tvEmptyCart = findViewById(R.id.tvEmptyCart); // Ánh xạ TextView
        android.widget.Button btnComplete = findViewById(R.id.btnComplete);

        // Khởi tạo DAO
        cartDAO = new CartDAO(this);

        // Thiết lập RecyclerView và Adapter
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        cartItems = new ArrayList<>();
        cartAdapter = new CartAdapter(cartItems, new CartAdapter.OnCartItemActionListener() {
            @Override
            public void onRemove(int position) {
                if (position < 0 || position >= cartItems.size()) return;
                int maGioHang = cartItems.get(position).getMaGioHang();
                boolean success = cartDAO.removeItemFromCart(maGioHang);
                if (success) {
                    cartItems.remove(position);
                    cartAdapter.notifyItemRemoved(position);
                    Toast.makeText(CartActivity.this, "Đã xóa sản phẩm khỏi giỏ hàng", Toast.LENGTH_SHORT).show();

                    if (cartItems.isEmpty()) {
                        rvCartItems.setVisibility(View.GONE);
                        tvEmptyCart.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onChangeQuantity(int position, int newQty) {
                if (position < 0 || position >= cartItems.size()) return;
                int maGioHang = cartItems.get(position).getMaGioHang();
                if (cartDAO.updateQuantity(maGioHang, newQty)) {
                    loadCartItems();
                }
            }
        });
        rvCartItems.setAdapter(cartAdapter);

        // Xử lý sự kiện nút Back
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (btnComplete != null) {
            btnComplete.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Tải dữ liệu giỏ hàng mỗi khi màn hình hiển thị
        loadCartItems();
    }

    private void loadCartItems() {
        // Giả sử maNguoiDung = 1, bạn có thể thay thế bằng ID người dùng thực tế
        int maNguoiDung = 1;
        List<GioHang> updatedCartItems = cartDAO.getCartItems(maNguoiDung);

        // Cập nhật giao diện dựa trên dữ liệu giỏ hàng
        if (updatedCartItems.isEmpty()) {
            rvCartItems.setVisibility(View.GONE);
            tvEmptyCart.setVisibility(View.VISIBLE);
        } else {
            rvCartItems.setVisibility(View.VISIBLE);
            tvEmptyCart.setVisibility(View.GONE);
        }

        // Đồng bộ dữ liệu activity và adapter
        cartItems.clear();
        cartItems.addAll(updatedCartItems);
        cartAdapter.updateCartList(updatedCartItems);
    }
}