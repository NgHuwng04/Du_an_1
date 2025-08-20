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

public class OrderManagementAdapter extends RecyclerView.Adapter<OrderManagementAdapter.OrderManagementViewHolder> {

    private Context context;
    private List<DonHang> orderList;
    
    // Interface cho click events
    public interface OnOrderActionListener {
        void onEditOrder(DonHang order, int position);
        void onDeleteOrder(DonHang order, int position);
    }
    
    private OnOrderActionListener actionListener;

    public OrderManagementAdapter(Context context, List<DonHang> orderList) {
        this.context = context;
        this.orderList = orderList;
    }
    
    public void setOnOrderActionListener(OnOrderActionListener listener) {
        this.actionListener = listener;
    }

    @NonNull
    @Override
    public OrderManagementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_history, parent, false);
        return new OrderManagementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderManagementViewHolder holder, int position) {
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
        
        // Set click listeners cho action buttons
        holder.btnEditOrder.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onEditOrder(order, position);
            }
        });
        
        holder.btnDeleteOrder.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onDeleteOrder(order, position);
            }
        });
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

    public static class OrderManagementViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderDate, tvOrderStatus, tvOrderTotal, tvOrderAddress, tvPaymentMethod;
        ImageButton btnEditOrder, btnDeleteOrder;

        public OrderManagementViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            tvOrderAddress = itemView.findViewById(R.id.tvOrderAddress);
            tvPaymentMethod = itemView.findViewById(R.id.tvPaymentMethod);
            btnEditOrder = itemView.findViewById(R.id.btnEditOrder);
            btnDeleteOrder = itemView.findViewById(R.id.btnDeleteOrder);
        }
    }
}
