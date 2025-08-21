package com.hungnn.du_an_1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hungnn.du_an_1.Database.DbHelper;
import com.hungnn.du_an_1.Model.DonHang;
import com.hungnn.du_an_1.Model.ChiTietDonHang;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public OrderDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Tạo đơn hàng mới
    public long createOrder(DonHang donHang) {
        ContentValues values = new ContentValues();
        values.put("ma_nguoi_dung", donHang.getMaNguoiDung());
        values.put("ngay_dat", donHang.getNgayDat());
        values.put("dia_chi_giao", donHang.getDiaChiGiao());
        values.put("so_dien_thoai", donHang.getSoDienThoai());
        values.put("phuong_thuc_thanh_toan", donHang.getPhuongThucThanhToan());
        values.put("tong_tien", donHang.getTongTien());
        values.put("trang_thai", donHang.getTrangThai());

        return database.insert("don_hang", null, values);
    }

    // Thêm chi tiết đơn hàng
    public long addOrderDetail(ChiTietDonHang chiTiet) {
        ContentValues values = new ContentValues();
        values.put("ma_don_hang", chiTiet.getMaDonHang());
        values.put("ma_san_pham", chiTiet.getMaSanPham());
        values.put("so_luong", chiTiet.getSoLuong());
        // Cột trong bảng là "gia" theo DbHelper
        values.put("gia", chiTiet.getGia());

        return database.insert("chi_tiet_don_hang", null, values);
    }

    // Lấy danh sách đơn hàng của người dùng
    public List<DonHang> getOrdersByUser(int maNguoiDung) {
        List<DonHang> orders = new ArrayList<>();
        
        String[] columns = {"ma_don_hang", "ma_nguoi_dung", "ngay_dat", "dia_chi_giao", 
                           "so_dien_thoai", "phuong_thuc_thanh_toan", "tong_tien", "trang_thai"};
        String selection = "ma_nguoi_dung = ?";
        String[] selectionArgs = {String.valueOf(maNguoiDung)};
        String orderBy = "ngay_dat DESC";

        Cursor cursor = database.query("don_hang", columns, selection, selectionArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                DonHang donHang = new DonHang();
                donHang.setMaDonHang(cursor.getInt(0));
                donHang.setMaNguoiDung(cursor.getInt(1));
                donHang.setNgayDat(cursor.getString(2));
                donHang.setDiaChiGiao(cursor.getString(3));
                donHang.setSoDienThoai(cursor.getString(4));
                donHang.setPhuongThucThanhToan(cursor.getString(5));
                donHang.setTongTien(cursor.getDouble(6));
                donHang.setTrangThai(cursor.getString(7));
                
                orders.add(donHang);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return orders;
    }

    // Lấy tất cả đơn hàng (cho admin)
    public List<DonHang> getAllOrders() {
        List<DonHang> orders = new ArrayList<>();
        
        String[] columns = {"ma_don_hang", "ma_nguoi_dung", "ngay_dat", "dia_chi_giao", 
                           "so_dien_thoai", "phuong_thuc_thanh_toan", "tong_tien", "trang_thai"};
        String orderBy = "ngay_dat DESC";

        Cursor cursor = database.query("don_hang", columns, null, null, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                DonHang donHang = new DonHang();
                donHang.setMaDonHang(cursor.getInt(0));
                donHang.setMaNguoiDung(cursor.getInt(1));
                donHang.setNgayDat(cursor.getString(2));
                donHang.setDiaChiGiao(cursor.getString(3));
                donHang.setSoDienThoai(cursor.getString(4));
                donHang.setPhuongThucThanhToan(cursor.getString(5));
                donHang.setTongTien(cursor.getDouble(6));
                donHang.setTrangThai(cursor.getString(7));
                
                orders.add(donHang);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return orders;
    }

    // Lấy chi tiết đơn hàng
    public List<ChiTietDonHang> getOrderDetails(int maDonHang) {
        List<ChiTietDonHang> details = new ArrayList<>();
        
        String[] columns = {"ma_chi_tiet", "ma_don_hang", "ma_san_pham", "so_luong", "don_gia"};
        String selection = "ma_don_hang = ?";
        String[] selectionArgs = {String.valueOf(maDonHang)};

        Cursor cursor = database.query("chi_tiet_don_hang", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ChiTietDonHang chiTiet = new ChiTietDonHang();
                chiTiet.setMaChiTiet(cursor.getInt(0));
                chiTiet.setMaDonHang(cursor.getInt(1));
                chiTiet.setMaSanPham(cursor.getInt(2));
                chiTiet.setSoLuong(cursor.getInt(3));
                chiTiet.setGia(cursor.getDouble(4));
                
                details.add(chiTiet);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return details;
    }

    // Xóa giỏ hàng sau khi đặt hàng thành công
    public void clearCart(int maNguoiDung) {
        String whereClause = "ma_nguoi_dung = ?";
        String[] whereArgs = {String.valueOf(maNguoiDung)};
        database.delete("gio_hang", whereClause, whereArgs);
    }

    // Cập nhật trạng thái đơn hàng
    public boolean updateOrderStatus(int maDonHang, String trangThai) {
        ContentValues values = new ContentValues();
        values.put("trang_thai", trangThai);
        
        String whereClause = "ma_don_hang = ?";
        String[] whereArgs = {String.valueOf(maDonHang)};
        
        int rowsAffected = database.update("don_hang", values, whereClause, whereArgs);
        return rowsAffected > 0;
    }

        // Xóa đơn hàng
    public boolean deleteOrder(int maDonHang) {
        // Xóa chi tiết đơn hàng trước
        String detailWhereClause = "ma_don_hang = ?";
        String[] detailWhereArgs = {String.valueOf(maDonHang)};
        database.delete("chi_tiet_don_hang", detailWhereClause, detailWhereArgs);

        // Sau đó xóa đơn hàng
        String orderWhereClause = "ma_don_hang = ?";
        String[] orderWhereArgs = {String.valueOf(maDonHang)};
        int rowsAffected = database.delete("don_hang", orderWhereClause, orderWhereArgs);

        return rowsAffected > 0;
    }

    // Lấy đơn hàng hoàn tất trong khoảng thời gian
    public List<DonHang> getCompletedOrdersInDateRange(String startDate, String endDate) {
        List<DonHang> orders = new ArrayList<>();

        String[] columns = {"ma_don_hang", "ma_nguoi_dung", "ngay_dat", "dia_chi_giao",
                           "so_dien_thoai", "phuong_thuc_thanh_toan", "tong_tien", "trang_thai"};
        
        // Chỉ lấy đơn hàng có trạng thái "da_giao" (hoàn tất) và trong khoảng thời gian
        String selection = "trang_thai = ? AND ngay_dat BETWEEN ? AND ?";
        String[] selectionArgs = {"da_giao", startDate, endDate};
        String orderBy = "ngay_dat DESC";

        Cursor cursor = database.query("don_hang", columns, selection, selectionArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                DonHang donHang = new DonHang();
                donHang.setMaDonHang(cursor.getInt(0));
                donHang.setMaNguoiDung(cursor.getInt(1));
                donHang.setNgayDat(cursor.getString(2));
                donHang.setDiaChiGiao(cursor.getString(3));
                donHang.setSoDienThoai(cursor.getString(4));
                donHang.setPhuongThucThanhToan(cursor.getString(5));
                donHang.setTongTien(cursor.getDouble(6));
                donHang.setTrangThai(cursor.getString(7));

                orders.add(donHang);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return orders;
    }
}
