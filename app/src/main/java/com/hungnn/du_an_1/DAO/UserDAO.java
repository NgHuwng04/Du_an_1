package com.hungnn.du_an_1.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hungnn.du_an_1.Database.DbHelper;
import com.hungnn.du_an_1.Model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<NguoiDung> getAllUsers() {
        List<NguoiDung> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM nguoi_dung", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ma_nguoi_dung"));
                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("ho_ten"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String matKhau = cursor.getString(cursor.getColumnIndexOrThrow("mat_khau"));
                String vaiTro = cursor.getString(cursor.getColumnIndexOrThrow("vai_tro"));
                String trangThai = cursor.getString(cursor.getColumnIndexOrThrow("trang_thai"));

                list.add(new NguoiDung(id, hoTen, email, matKhau, vaiTro, trangThai));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<NguoiDung> getCustomersOnly() {
        List<NguoiDung> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM nguoi_dung WHERE vai_tro = 'khach_hang'", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ma_nguoi_dung"));
                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("ho_ten"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String matKhau = cursor.getString(cursor.getColumnIndexOrThrow("mat_khau"));
                String vaiTro = cursor.getString(cursor.getColumnIndexOrThrow("vai_tro"));
                String trangThai = cursor.getString(cursor.getColumnIndexOrThrow("trang_thai"));

                list.add(new NguoiDung(id, hoTen, email, matKhau, vaiTro, trangThai));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean updateUserStatus(int userId, String newStatus) {
        try {
            db.execSQL("UPDATE nguoi_dung SET trang_thai = ? WHERE ma_nguoi_dung = ?", 
                      new String[]{newStatus, String.valueOf(userId)});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        try {
            db.execSQL("DELETE FROM nguoi_dung WHERE ma_nguoi_dung = ?", 
                      new String[]{String.valueOf(userId)});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
