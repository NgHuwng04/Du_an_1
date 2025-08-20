package com.hungnn.du_an_1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hungnn.du_an_1.Database.DbHelper;
import com.hungnn.du_an_1.Model.DonHang;

import java.util.ArrayList;
import java.util.List;

public class DonHangDAO {
    private SQLiteDatabase db;

    public DonHangDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<DonHang> layTatCaDonHang() {
        List<DonHang> danhSach = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM don_hang ORDER BY ngay_dat DESC", null);
        if (cursor.moveToFirst()) {
            do {
                DonHang dh = new DonHang();
                dh.setMaDonHang(cursor.getInt(cursor.getColumnIndexOrThrow("ma_don_hang")));
                dh.setMaNguoiDung(cursor.getInt(cursor.getColumnIndexOrThrow("ma_nguoi_dung")));
                dh.setNgayDat(cursor.getString(cursor.getColumnIndexOrThrow("ngay_dat")));
                dh.setTrangThai(cursor.getString(cursor.getColumnIndexOrThrow("trang_thai")));
                dh.setTongTien(cursor.getDouble(cursor.getColumnIndexOrThrow("tong_tien")));
                danhSach.add(dh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }

    public long themDonHang(DonHang dh) {
        ContentValues values = new ContentValues();
        values.put("ma_nguoi_dung", dh.getMaNguoiDung());
        values.put("ngay_dat", dh.getNgayDat());
        values.put("trang_thai", dh.getTrangThai());
        values.put("tong_tien", dh.getTongTien());

        long result = db.insert("don_hang", null, values);
        if (result != -1) {
            dh.setMaDonHang((int) result); // Gán lại mã đơn hàng
        }
        return result;
    }

    //  Sửa đơn hàng (toàn bộ thông tin)
    public boolean suaDonHang(DonHang dh) {
        ContentValues values = new ContentValues();
        values.put("ma_nguoi_dung", dh.getMaNguoiDung());
        values.put("ngay_dat", dh.getNgayDat());
        values.put("trang_thai", dh.getTrangThai());
        values.put("tong_tien", dh.getTongTien());
        int result = db.update("don_hang", values, "ma_don_hang = ?", new String[]{String.valueOf(dh.getMaDonHang())});
        return result > 0;
    }

    public boolean capNhatTrangThai(int maDonHang, String trangThaiMoi) {
        ContentValues values = new ContentValues();
        values.put("trang_thai", trangThaiMoi);
        int result = db.update("don_hang", values, "ma_don_hang = ?", new String[]{String.valueOf(maDonHang)});
        return result > 0;
    }

    public boolean xoaDonHang(int maDonHang) {
        return db.delete("don_hang", "ma_don_hang = ?", new String[]{String.valueOf(maDonHang)}) > 0;
    }
    // Lấy đơn hàng theo mã
    public DonHang layDonHangTheoMa(int maDonHang) {
        Cursor cursor = db.rawQuery("SELECT * FROM don_hang WHERE ma_don_hang = ?", new String[]{String.valueOf(maDonHang)});
        if (cursor.moveToFirst()) {
            DonHang dh = new DonHang();
            dh.setMaDonHang(cursor.getInt(cursor.getColumnIndexOrThrow("ma_don_hang")));
            dh.setMaNguoiDung(cursor.getInt(cursor.getColumnIndexOrThrow("ma_nguoi_dung")));
            dh.setNgayDat(cursor.getString(cursor.getColumnIndexOrThrow("ngay_dat")));
            dh.setTrangThai(cursor.getString(cursor.getColumnIndexOrThrow("trang_thai")));
            dh.setTongTien(cursor.getDouble(cursor.getColumnIndexOrThrow("tong_tien")));
            cursor.close();
            return dh;
        }
        cursor.close();
        return null;
    }
}
