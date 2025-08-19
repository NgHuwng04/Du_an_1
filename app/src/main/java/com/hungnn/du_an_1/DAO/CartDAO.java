package com.hungnn.du_an_1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hungnn.du_an_1.Database.DbHelper;
import com.hungnn.du_an_1.Model.GioHang;
import com.hungnn.du_an_1.Model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private final DbHelper dbHelper;
    private final Context context;

    public CartDAO(Context context) {
        this.dbHelper = new DbHelper(context);
        this.context = context.getApplicationContext();
    }

    public boolean addToCartWithProduct(int maNguoiDung, SanPham sanPham, int soLuong) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            // Đảm bảo sản phẩm tồn tại trong bảng san_pham để join lấy tên và giá
            Cursor c = db.rawQuery("SELECT ma_san_pham FROM san_pham WHERE ma_san_pham = ?", new String[]{String.valueOf(sanPham.getMaSanPham())});
            boolean exists = c.moveToFirst();
            c.close();
            if (!exists) {
                ContentValues spValues = new ContentValues();
                spValues.put("ma_san_pham", sanPham.getMaSanPham());
                spValues.put("ten_san_pham", sanPham.getTenSanPham());
                spValues.put("mo_ta", sanPham.getMoTa() == null ? "" : sanPham.getMoTa());
                spValues.put("gia", sanPham.getGia());
                spValues.put("so_luong_ton", sanPham.getSoLuongTon() == 0 ? 100 : sanPham.getSoLuongTon());
                spValues.put("ma_danh_muc", sanPham.getMaDanhMuc() == 0 ? 1 : sanPham.getMaDanhMuc());
                // Lưu tên resource ảnh để có thể khôi phục về ID khi hiển thị
                try {
                    if (sanPham.getHinhAnhResId() != 0) {
                        String name = context.getResources().getResourceEntryName(sanPham.getHinhAnhResId());
                        spValues.put("hinh_anh", name);
                    }
                } catch (Exception ignored) {}
                db.insert("san_pham", null, spValues);
            }

            // Nếu đã có trong giỏ thì tăng số lượng, ngược lại thêm mới
            Cursor cur = db.rawQuery("SELECT ma_gio_hang, so_luong FROM gio_hang WHERE ma_nguoi_dung = ? AND ma_san_pham = ?", new String[]{String.valueOf(maNguoiDung), String.valueOf(sanPham.getMaSanPham())});
            if (cur.moveToFirst()) {
                int maGio = cur.getInt(cur.getColumnIndexOrThrow("ma_gio_hang"));
                int soLuongHienTai = cur.getInt(cur.getColumnIndexOrThrow("so_luong"));
                ContentValues up = new ContentValues();
                up.put("so_luong", soLuongHienTai + soLuong);
                db.update("gio_hang", up, "ma_gio_hang = ?", new String[]{String.valueOf(maGio)});
            } else {
                ContentValues gh = new ContentValues();
                gh.put("ma_nguoi_dung", maNguoiDung);
                gh.put("ma_san_pham", sanPham.getMaSanPham());
                gh.put("so_luong", soLuong);
                db.insert("gio_hang", null, gh);
            }
            cur.close();

            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public List<GioHang> getCartItems(int maNguoiDung) {
        List<GioHang> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT gh.ma_gio_hang, sp.ma_san_pham, sp.ten_san_pham, sp.gia, gh.so_luong, sp.hinh_anh " +
                        "FROM gio_hang gh JOIN san_pham sp ON gh.ma_san_pham = sp.ma_san_pham " +
                        "WHERE gh.ma_nguoi_dung = ?",
                new String[]{String.valueOf(maNguoiDung)}
        );
        if (cursor.moveToFirst()) {
            do {
                int maGio = cursor.getInt(cursor.getColumnIndexOrThrow("ma_gio_hang"));
                int maSP = cursor.getInt(cursor.getColumnIndexOrThrow("ma_san_pham"));
                String tenSP = cursor.getString(cursor.getColumnIndexOrThrow("ten_san_pham"));
                double gia = cursor.getDouble(cursor.getColumnIndexOrThrow("gia"));
                int soLuong = cursor.getInt(cursor.getColumnIndexOrThrow("so_luong"));
                int imgResId = 0;
                int colIndex = cursor.getColumnIndex("hinh_anh");
                if (colIndex >= 0) {
                    String imgName = cursor.getString(colIndex);
                    if (imgName != null) {
                        imgResId = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
                    }
                }
                list.add(new GioHang(maGio, maSP, tenSP, gia, soLuong, imgResId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean removeItemFromCart(int maGioHang) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            int rows = db.delete("gio_hang", "ma_gio_hang = ?", new String[]{String.valueOf(maGioHang)});
            return rows > 0;
        } finally {
            db.close();
        }
    }

    public boolean updateQuantity(int maGioHang, int newQty) {
        if (newQty <= 0) return removeItemFromCart(maGioHang);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("so_luong", newQty);
        int rows = db.update("gio_hang", values, "ma_gio_hang = ?", new String[]{String.valueOf(maGioHang)});
        db.close();
        return rows > 0;
    }
}
