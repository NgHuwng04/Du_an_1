package com.hungnn.du_an_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.Model.DonHang;
import com.hungnn.du_an_1.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    private Context context;
    private List<DonHang> orderList;
    


    public OrderHistoryAdapter(Context context, List<DonHang> orderList) {
        this.context = context;
        this.orderList = orderList;
    }
    


    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_history_view_only, parent, false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        DonHang order = orderList.get(position);
        
        // Hiển thị thông tin đơn hàng
        holder.tvOrderId.setText("Đơn hàng #" + order.getMaDonHang());
        holder.tvOrderDate.setText("Ngày: " + order.getNgayDat());
        holder.tvOrderStatus.setText("Trạng thái: " + getStatusText(order.getTrangThai()));
        holder.tvOrderTotal.setText("Tổng tiền: " + formatPrice(order.getTongTien()));
        
        // Hiển thị địa chỉ giao hàng nếu có
        if (order.getDiaChiGiao() != null && !order.getDiaChiGiao().isEmpty()) {
            holder.tvOrderAddress.setText("Địa chỉ: " + order.getDiaChiGiao());
            holder.tvOrderAddress.setVisibility(View.VISIBLE);
        } else {
            holder.tvOrderAddress.setVisibility(View.GONE);
        }
        
        // Hiển thị phương thức thanh toán nếu có
        if (order.getPhuongThucThanhToan() != null && !order.getPhuongThucThanhToan().isEmpty()) {
            holder.tvPaymentMethod.setText("TT: " + getPaymentMethodText(order.getPhuongThucThanhToan()));
            holder.tvPaymentMethod.setVisibility(View.VISIBLE);
        } else {
            holder.tvPaymentMethod.setVisibility(View.GONE);
        }
        

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void updateOrderList(List<DonHang> newOrderList) {
        this.orderList = newOrderList;
        notifyDataSetChanged();
    }

    private String getStatusText(String status) {
        switch (status) {
            case "cho_xu_ly":
                return "Chờ xử lý";
            case "dang_giao":
                return "Đang giao";
            case "da_giao":
                return "Đã giao";
            case "huy":
                return "Đã hủy";
            default:
                return status;
        }
    }

    private String getPaymentMethodText(String method) {
        switch (method) {
            case "Card":
                return "Thẻ";
            case "Bank account":
                return "Chuyển khoản";
            case "Cash":
                return "Tiền mặt";
            default:
                return method;
        }
    }

    private String formatPrice(double price) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(price) + " VNĐ";
    }

    public static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderDate, tvOrderStatus, tvOrderTotal, tvOrderAddress, tvPaymentMethod;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            tvOrderAddress = itemView.findViewById(R.id.tvOrderAddress);
            tvPaymentMethod = itemView.findViewById(R.id.tvPaymentMethod);
        }
    }
}
