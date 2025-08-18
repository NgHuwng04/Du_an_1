package com.hungnn.du_an_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.Model.NguoiDung;
import com.hungnn.du_an_1.R;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private Context context;
    private List<NguoiDung> membersList;
    private OnMemberActionListener listener;

    public interface OnMemberActionListener {
        void onEditMember(NguoiDung member);
        void onDeleteMember(NguoiDung member, int position);
    }

    public MemberAdapter(Context context, List<NguoiDung> membersList) {
        this.context = context;
        this.membersList = membersList;
    }

    public void setOnMemberActionListener(OnMemberActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_member, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        NguoiDung member = membersList.get(position);
        
        // Hiển thị thông tin khách hàng
        holder.tvUsername.setText("Username : " + member.getHoTen());
        holder.tvEmail.setText("Email : " + member.getEmail());
        holder.tvPassword.setText("Password : " + member.getMatKhau());
        
        // Hiển thị vai trò
        String roleText = "Vai trò : " + (member.getVaiTro().equals("khach_hang") ? "Khách hàng" : "Chủ cửa hàng");
        holder.tvRole.setText(roleText);
        
        // Hiển thị trạng thái
        String statusText = "Trạng thái : " + (member.getTrangThai().equals("hoat_dong") ? "Hoạt động" : "Bị khóa");
        holder.tvStatus.setText(statusText);
        
        // Xử lý click vào nút Edit
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditMember(member);
            }
        });
        
        // Xử lý click vào nút Delete
        holder.btnDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog(member, position);
        });
    }

    @Override
    public int getItemCount() {
        return membersList.size();
    }

    /**
     * Hiển thị dialog xác nhận xóa
     */
    private void showDeleteConfirmationDialog(NguoiDung member, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa khách hàng " + member.getHoTen() + "?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    if (listener != null) {
                        listener.onDeleteMember(member, position);
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    /**
     * Cập nhật danh sách khách hàng
     */
    public void updateMembersList(List<NguoiDung> newList) {
        this.membersList = newList;
        notifyDataSetChanged();
    }

    /**
     * Xóa khách hàng khỏi danh sách
     */
    public void removeMember(int position) {
        if (position >= 0 && position < membersList.size()) {
            membersList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, membersList.size());
        }
    }

    static class MemberViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvEmail, tvPassword, tvRole, tvStatus;
        ImageButton btnEdit, btnDelete;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPassword = itemView.findViewById(R.id.tvPassword);
            tvRole = itemView.findViewById(R.id.tvRole);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
