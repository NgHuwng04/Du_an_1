package com.hungnn.du_an_1.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hungnn.du_an_1.Database.DbHelper;
import com.hungnn.du_an_1.Model.SanPham;

import java.util.ArrayList;
import java.util.List;

/**
 * SanPhamDAO (Data Access Object)
 * Lớp này chịu trách nhiệm cho tất cả các hoạt động truy cập cơ sở dữ liệu
 * liên quan đến đối tượng SanPham (Sản phẩm).
 */
public class SanPhamDAO {
    private final DbHelper dbHelper;

    public SanPhamDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    /**
     * Hàm tiện ích để chuyển đổi một dòng dữ liệu từ Cursor thành đối tượng SanPham.
     * @param cursor Con trỏ đang trỏ đến dòng dữ liệu cần chuyển đổi.
     * @return một đối tượng SanPham.
     */
    private SanPham cursorToSanPham(Cursor cursor) {
        SanPham sanPham = new SanPham();
        sanPham.setMaSanPham(cursor.getInt(cursor.getColumnIndexOrThrow("ma_san_pham")));
        sanPham.setTenSanPham(cursor.getString(cursor.getColumnIndexOrThrow("ten_san_pham")));
        sanPham.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow("mo_ta")));
        sanPham.setGia(cursor.getDouble(cursor.getColumnIndexOrThrow("gia")));
        sanPham.setSoLuongTon(cursor.getInt(cursor.getColumnIndexOrThrow("so_luong_ton")));
        sanPham.setMaDanhMuc(cursor.getInt(cursor.getColumnIndexOrThrow("ma_danh_muc")));

        // Cột hinh_anh trong DB là TEXT, nhưng trong model là int (Resource ID)
        // Cần chuyển đổi từ String sang int khi đọc.
        try {
            String hinhAnhStr = cursor.getString(cursor.getColumnIndexOrThrow("hinh_anh"));
            sanPham.setHinhAnhResId(Integer.parseInt(hinhAnhStr));
        } catch (NumberFormatException e) {
            sanPham.setHinhAnhResId(0); // Gán giá trị mặc định nếu dữ liệu không hợp lệ
        }
        return sanPham;
    }

    /**
     * Lấy tất cả sản phẩm từ cơ sở dữ liệu.
     * @return Danh sách (List) các đối tượng SanPham.
     */
    public List<SanPham> getAll() {
        List<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM san_pham", null)) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursorToSanPham(cursor));
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return list;
    }

    /**
     * Lấy một sản phẩm duy nhất bằng ID của nó.
     * @param id Mã của sản phẩm cần tìm.
     * @return Đối tượng SanPham nếu tìm thấy, ngược lại trả về null.
     */
    public SanPham getById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SanPham sanPham = null;
        try (Cursor cursor = db.rawQuery("SELECT * FROM san_pham WHERE ma_san_pham = ?", new String[]{String.valueOf(id)})) {
            if (cursor.moveToFirst()) {
                sanPham = cursorToSanPham(cursor);
            }
        }
        db.close();
        return sanPham;
    }

    /**
     * Chèn một sản phẩm mới vào cơ sở dữ liệu.
     * @param sanPham Đối tượng SanPham chứa thông tin cần chèn.
     * @return true nếu chèn thành công, false nếu thất bại.
     */
    public boolean insert(SanPham sanPham) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten_san_pham", sanPham.getTenSanPham());
        values.put("mo_ta", sanPham.getMoTa());
        values.put("gia", sanPham.getGia());
        values.put("so_luong_ton", sanPham.getSoLuongTon());
        values.put("ma_danh_muc", sanPham.getMaDanhMuc());
        // Chuyển int hinhAnhResId thành String để lưu vào DB
        values.put("hinh_anh", String.valueOf(sanPham.getHinhAnhResId()));

        long row = db.insert("san_pham", null, values);
        db.close();
        return row != -1;
    }

    /**
     * Cập nhật thông tin của một sản phẩm đã có.
     * @param sanPham Đối tượng SanPham chứa thông tin cần cập nhật.
     * @return true nếu cập nhật thành công, false nếu thất bại.
     */
    public boolean update(SanPham sanPham) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten_san_pham", sanPham.getTenSanPham());
        values.put("mo_ta", sanPham.getMoTa());
        values.put("gia", sanPham.getGia());
        values.put("so_luong_ton", sanPham.getSoLuongTon());
        values.put("ma_danh_muc", sanPham.getMaDanhMuc());
        values.put("hinh_anh", String.valueOf(sanPham.getHinhAnhResId()));

        int row = db.update("san_pham", values, "ma_san_pham = ?", new String[]{String.valueOf(sanPham.getMaSanPham())});
        db.close();
        return row > 0;
    }

    /**
     * Xóa một sản phẩm khỏi cơ sở dữ liệu dựa vào mã sản phẩm.
     * @param maSanPham Mã của sản phẩm cần xóa.
     * @return true nếu xóa thành công, false nếu thất bại.
     */
    public boolean delete(int maSanPham) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int row = db.delete("san_pham", "ma_san_pham = ?", new String[]{String.valueOf(maSanPham)});
        db.close();
        return row > 0;
    }

    /**
     * Tìm kiếm sản phẩm theo tên.
     * @param query Từ khóa tìm kiếm.
     * @return Danh sách các sản phẩm có tên chứa từ khóa.
     */
    public List<SanPham> search(String query) {
        List<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM san_pham WHERE ten_san_pham LIKE ?", new String[]{"%" + query + "%"})) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursorToSanPham(cursor));
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return list;
    }
}