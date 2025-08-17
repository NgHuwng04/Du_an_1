package com.hungnn.du_an_1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.DAO.DonHangDAO;
import com.hungnn.du_an_1.Model.DonHang;
import com.hungnn.du_an_1.QuanLyDonHangActivity;
import com.hungnn.du_an_1.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> {
    private  List<DonHang> danhSach;
    private OnItemClickListener listener;
    private Context context;


    public interface OnItemClickListener {
        void onItemClick(DonHang donHang);
    }
    public DonHangAdapter(List<DonHang> danhSach) {
        this.context = context;
        this.danhSach = danhSach;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_don_hang_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang dh = danhSach.get(position);

        holder.tvMaDonHang.setText("Mã đơn: #" + dh.getMaDonHang());
        holder.tvNgayDat.setText("Ngày đặt: " + dh.getNgayDat());

        //  Ánh xạ trạng thái hiển thị
        String trangThaiHienThi = hienThiTrangThai(dh.getTrangThai());
        holder.tvTrangThai.setText("Trạng thái: " + trangThaiHienThi);

        //  Ánh xạ màu trạng thái
        switch (dh.getTrangThai()) {
            case "cho_xu_ly":
                holder.tvTrangThai.setTextColor(Color.parseColor("#FFA000")); // vàng
                break;
            case "dang_giao":
                holder.tvTrangThai.setTextColor(Color.parseColor("#1976D2")); // xanh
                break;
            case "da_giao":
                holder.tvTrangThai.setTextColor(Color.parseColor("#388E3C")); // xanh lá
                break;
            case "huy":
                holder.tvTrangThai.setTextColor(Color.parseColor("#D32F2F")); // đỏ
                break;
            default:
                holder.tvTrangThai.setTextColor(Color.GRAY);
        }

        // Hiển thị tổng tiền
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String tongTienFormatted = formatter.format(dh.getTongTien());
        holder.tvTongTien.setText("Tổng tiền: " + tongTienFormatted);

        //  Xử lý click item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(dh);
            } else {
                Toast.makeText(v.getContext(), "Không có hành động khi click", Toast.LENGTH_SHORT).show();
            }
        });

        //  Cho phép đổi trạng thái
        holder.tvTrangThai.setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_doi_trang_thai, null);
            AlertDialog dialog = new AlertDialog.Builder(context).setView(dialogView).create();

            RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroupTrangThai);
            Button btnLuu = dialogView.findViewById(R.id.btnLuu);
            Button btnHuy = dialogView.findViewById(R.id.btnHuy);

            // Chọn trạng thái hiện tại
            switch (dh.getTrangThai()) {
                case "cho_xu_ly":
                    radioGroup.check(R.id.rbChoXuLy);
                    break;
                case "dang_giao":
                    radioGroup.check(R.id.rbDangGiao);
                    break;
                case "da_giao":
                    radioGroup.check(R.id.rbDaGiao);
                    break;
                case "huy":
                    radioGroup.check(R.id.rbHuy);
                    break;
            }

            btnLuu.setOnClickListener(v1 -> {
                String trangThaiMoi = "";
                int checkedId = radioGroup.getCheckedRadioButtonId();
                if (checkedId == R.id.rbChoXuLy) trangThaiMoi = "cho_xu_ly";
                else if (checkedId == R.id.rbDangGiao) trangThaiMoi = "dang_giao";
                else if (checkedId == R.id.rbDaGiao) trangThaiMoi = "da_giao";
                else if (checkedId == R.id.rbHuy) trangThaiMoi = "huy";

                dh.setTrangThai(trangThaiMoi);
                DonHangDAO dao = new DonHangDAO(context);
                if (dao.suaDonHang(dh)) {
                    Toast.makeText(context, "Đã cập nhật trạng thái", Toast.LENGTH_SHORT).show();
                    notifyItemChanged(holder.getAdapterPosition());
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            });

            btnHuy.setOnClickListener(v2 -> dialog.dismiss());
            dialog.show();
        });



        //Sửa
        holder.btnSua.setOnClickListener(v -> {
            Context context = v.getContext();
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_them_don_hang, null);
            AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();

            EditText edtMaNguoiDung = view.findViewById(R.id.edtMaNguoiDung);
            EditText edtNgayDat = view.findViewById(R.id.edtNgayDat);
            EditText edtTongTien = view.findViewById(R.id.edtTongTien);
            Spinner spTrangThai = view.findViewById(R.id.spTrangThai);
            Button btnLuu = view.findViewById(R.id.btnLuu);
            Button btnHuy = view.findViewById(R.id.btnHuy);

            edtMaNguoiDung.setText(String.valueOf(dh.getMaNguoiDung()));
            edtNgayDat.setText(dh.getNgayDat());
            edtTongTien.setText(String.valueOf(dh.getTongTien()));

            ArrayAdapter<String> adapterTrangThai = new ArrayAdapter<>(context,
                    android.R.layout.simple_spinner_item,
                    new String[]{"cho_xu_ly", "dang_giao", "da_giao", "huy"});
            spTrangThai.setAdapter(adapterTrangThai);
            spTrangThai.setSelection(adapterTrangThai.getPosition(dh.getTrangThai()));

            btnLuu.setOnClickListener(v1 -> {
                dh.setMaNguoiDung(Integer.parseInt(edtMaNguoiDung.getText().toString()));
                dh.setNgayDat(edtNgayDat.getText().toString());
                dh.setTongTien(Double.parseDouble(edtTongTien.getText().toString()));
                dh.setTrangThai(spTrangThai.getSelectedItem().toString());

                DonHangDAO dao = new DonHangDAO(context);
                if (dao.suaDonHang(dh)) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    notifyItemChanged(holder.getAdapterPosition());
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            });

            btnHuy.setOnClickListener(v2 -> dialog.dismiss());
            dialog.show();
        });

        holder.btnXoa.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa đơn hàng này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        DonHangDAO dao = new DonHangDAO(v.getContext());
                        if (dao.xoaDonHang(dh.getMaDonHang())) {
                            danhSach.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                            Toast.makeText(v.getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return danhSach.size();
    }

    // Hàm ánh xạ trạng thái từ mã sang tiếng Việt
    private String hienThiTrangThai(String maTrangThai) {
        switch (maTrangThai) {
            case "cho_xu_ly":
                return "Chờ xử lý";
            case "dang_giao":
                return "Đang giao";
            case "da_giao":
                return "Hoàn tất";
            case "huy":
                return "Hủy";
            default:
                return "Không xác định";
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaDonHang, tvNgayDat, tvTrangThai, tvTongTien;
        Button btnSua, btnXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaDonHang = itemView.findViewById(R.id.tvMaDonHang);
            tvNgayDat = itemView.findViewById(R.id.tvNgayDat);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            tvTongTien = itemView.findViewById(R.id.tvTongTien);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}
