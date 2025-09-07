package com.hungnn.du_an_1.ActivityPhu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hungnn.du_an_1.DAO.CartDAO;
import com.hungnn.du_an_1.DAO.OrderDAO;
import com.hungnn.du_an_1.Model.GioHang;
import com.hungnn.du_an_1.Model.DonHang;
import com.hungnn.du_an_1.Model.ChiTietDonHang;
import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.Utils.UserManager;
import com.hungnn.du_an_1.MainActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvChange;
    private TextView tvUserName, tvAddress, tvPhone, tvDate, tvTotal, tvOldTotal;
    private TextView tvAddOffers;
    private RadioGroup rgPayment;
    private RadioButton rbCard, rbBank, rbCash;
    private Button btnProceed;

    private CartDAO cartDAO;
    private OrderDAO orderDAO;
    private UserManager userManager;
    private SharedPreferences prefs;
    private double subtotal = 0.0;
    private int discountPercent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        cartDAO = new CartDAO(this);
        orderDAO = new OrderDAO(this);
        userManager = UserManager.getInstance(this);
        prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        initViews();
        bindHeader();
        bindAddressBlock();
        bindPaymentMethods();
        bindTotal();
        setListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvChange = findViewById(R.id.tvChange);
        tvUserName = findViewById(R.id.tvUserName);
        tvAddress = findViewById(R.id.tvAddress);
        tvPhone = findViewById(R.id.tvPhone);
        tvDate = findViewById(R.id.tvDate);
        tvTotal = findViewById(R.id.tvTotalAmount);
        tvOldTotal = findViewById(R.id.tvOldTotalAmount);
        tvAddOffers = findViewById(R.id.tvAddOffers);
        rgPayment = findViewById(R.id.rgPayment);
        rbCard = findViewById(R.id.rbCard);
        rbBank = findViewById(R.id.rbBank);
        rbCash = findViewById(R.id.rbCash);
        btnProceed = findViewById(R.id.btnProceed);
    }

    private void bindHeader() {
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void bindAddressBlock() {
        String name = userManager.getUserName();
        if (name == null || name.isEmpty()) name = "User";
        tvUserName.setText(name);

        String address = prefs.getString("shipping_address", "Tòa nhà FPT Polytechnic, 13 Trịnh Văn Bô, Phường Xuân Phương, TP Hà Nội");
        String phone = prefs.getString("shipping_phone", "024 7300 1955");
        tvAddress.setText(address);
        tvPhone.setText(phone);

        String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        tvDate.setText(today);
    }

    private void bindPaymentMethods() {
        rbCard.setChecked(true);
    }

    private void bindTotal() {
        int userId = userManager.getUserId();
        if (userId == -1) {
            subtotal = 0.0;
            tvTotal.setText("0");
            return;
        }
        List<GioHang> items = cartDAO.getCartItems(userId);
        subtotal = 0.0;
        for (GioHang it : items) {
            subtotal += it.getGia() * it.getSoLuong();
        }
        double total = applyDiscount(subtotal, discountPercent);
        String formatted = NumberFormat.getInstance(new Locale("vi","VN")).format(total);
        tvTotal.setText(formatted);

        if (discountPercent > 0) {
            String oldFormatted = NumberFormat.getInstance(new Locale("vi","VN")).format(subtotal);
            tvOldTotal.setText(oldFormatted);
            tvOldTotal.setVisibility(View.VISIBLE);
            tvOldTotal.setPaintFlags(tvOldTotal.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tvOldTotal.setVisibility(View.GONE);
        }
    }

    private double applyDiscount(double base, int percent) {
        if (percent <= 0) return base;
        return base * (100 - percent) / 100.0;
    }

    private void setListeners() {
        tvChange.setOnClickListener(v -> showChangeAddressDialog());
        btnProceed.setOnClickListener(v -> processOrder());
        tvAddOffers.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(this, OffersActivity.class);
            startActivityForResult(intent, 2001);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2001 && resultCode == RESULT_OK && data != null) {
            discountPercent = data.getIntExtra("discount_percent", 0);
            bindTotal();
        }
    }

    private void showChangeAddressDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_change_address, null, false);
        final TextView edtName = dialogView.findViewById(R.id.edtName);
        final TextView edtAddress = dialogView.findViewById(R.id.edtAddress);
        final TextView edtPhone = dialogView.findViewById(R.id.edtPhone);

        edtName.setText(userManager.getUserName());
        edtAddress.setText(prefs.getString("shipping_address", ""));
        edtPhone.setText(prefs.getString("shipping_phone", ""));

        new AlertDialog.Builder(this)
                .setTitle("Cập nhật địa chỉ")
                .setView(dialogView)
                .setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = edtName.getText().toString().trim();
                        String address = edtAddress.getText().toString().trim();
                        String phone = edtPhone.getText().toString().trim();

                        if (!name.isEmpty()) tvUserName.setText(name);
                        if (!address.isEmpty()) tvAddress.setText(address);
                        if (!phone.isEmpty()) tvPhone.setText(phone);

                        prefs.edit()
                                .putString("shipping_address", address)
                                .putString("shipping_phone", phone)
                                .apply();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void processOrder() {
        try {
            orderDAO.open();
            
            // Lấy thông tin người dùng
            int userId = userManager.getUserId();
            if (userId == -1) {
                android.widget.Toast.makeText(this, "Vui lòng đăng nhập để thanh toán", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }
            
            android.util.Log.d("CheckoutActivity", "User ID: " + userId);
            
            // Lấy thông tin giỏ hàng
            List<GioHang> cartItems = cartDAO.getCartItems(userId);
            android.util.Log.d("CheckoutActivity", "Số sản phẩm trong giỏ hàng: " + cartItems.size());
            
            if (cartItems.isEmpty()) {
                android.widget.Toast.makeText(this, "Giỏ hàng trống!", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }
            
            // Kiểm tra thông tin sản phẩm
            for (GioHang item : cartItems) {
                android.util.Log.d("CheckoutActivity", "Sản phẩm: ID=" + item.getMaSanPham() + 
                    ", Số lượng=" + item.getSoLuong() + ", Giá=" + item.getGia());
            }
            
            // Tạo đơn hàng mới
            DonHang donHang = new DonHang();
            donHang.setMaNguoiDung(userId);
            // Lưu ngày theo định dạng ISO để dễ truy vấn: yyyy-MM-dd
            donHang.setNgayDat(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
            donHang.setDiaChiGiao(tvAddress.getText().toString());
            donHang.setSoDienThoai(tvPhone.getText().toString());
            
            // Xác định phương thức thanh toán
            String paymentMethod = "Card";
            if (rbBank.isChecked()) paymentMethod = "Bank account";
            else if (rbCash.isChecked()) paymentMethod = "Cash";
            donHang.setPhuongThucThanhToan(paymentMethod);
            
            // Tính tổng tiền sau khi áp dụng ưu đãi
            double totalAmount = applyDiscount(subtotal, discountPercent);
            donHang.setTongTien(totalAmount);
            donHang.setTrangThai("cho_xu_ly");
            
            // Log thông tin đơn hàng
            android.util.Log.d("CheckoutActivity", "Thông tin đơn hàng:");
            android.util.Log.d("CheckoutActivity", "- Mã người dùng: " + donHang.getMaNguoiDung());
            android.util.Log.d("CheckoutActivity", "- Ngày đặt: " + donHang.getNgayDat());
            android.util.Log.d("CheckoutActivity", "- Địa chỉ: " + donHang.getDiaChiGiao());
            android.util.Log.d("CheckoutActivity", "- SĐT: " + donHang.getSoDienThoai());
            android.util.Log.d("CheckoutActivity", "- Phương thức TT: " + donHang.getPhuongThucThanhToan());
            android.util.Log.d("CheckoutActivity", "- Tổng tiền: " + donHang.getTongTien());
            android.util.Log.d("CheckoutActivity", "- Trạng thái: " + donHang.getTrangThai() + " (cho_xu_ly)");
            
            // Lưu đơn hàng vào database
            android.util.Log.d("CheckoutActivity", "Đang tạo đơn hàng...");
            long orderId = orderDAO.createOrder(donHang);
            android.util.Log.d("CheckoutActivity", "Mã đơn hàng: " + orderId);
            
            if (orderId > 0) {
                // Lưu chi tiết đơn hàng
                for (GioHang item : cartItems) {
                    ChiTietDonHang chiTiet = new ChiTietDonHang();
                    chiTiet.setMaDonHang((int) orderId);
                    chiTiet.setMaSanPham(item.getMaSanPham());
                    chiTiet.setSoLuong(item.getSoLuong());
                    chiTiet.setGia(item.getGia());
                    
                    orderDAO.addOrderDetail(chiTiet);
                }
                
                // Xóa giỏ hàng
                orderDAO.clearCart(userId);
                
                // Hiển thị thông báo thành công
                new AlertDialog.Builder(this)
                    .setTitle("Đặt hàng thành công!")
                    .setMessage("Đơn hàng của bạn đã được xác nhận. Mã đơn hàng: #" + orderId)
                    .setPositiveButton("Xem lịch sử", (dialog, which) -> {
                        // Chuyển sang màn hình lịch sử đơn hàng
                        android.content.Intent intent = new android.content.Intent(this, HistoryActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Về trang chủ", (dialog, which) -> {
                        // Quay về MainActivity
                        android.content.Intent intent = new android.content.Intent(this, MainActivity.class);
                        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP | android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setCancelable(false)
                    .show();
                    
            } else {
                android.widget.Toast.makeText(this, "Có lỗi xảy ra khi đặt hàng!", android.widget.Toast.LENGTH_SHORT).show();
            }
            
        } catch (Exception e) {
            android.util.Log.e("CheckoutActivity", "Lỗi khi đặt hàng: ", e);
            android.widget.Toast.makeText(this, "Lỗi: " + e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
        } finally {
            orderDAO.close();
        }
    }
}
