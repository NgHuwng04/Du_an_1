package com.hungnn.du_an_1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.hungnn.du_an_1.Database.DbHelper;
import com.hungnn.du_an_1.Model.DanhMuc;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public CategoryDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Lấy tất cả danh mục
    public List<DanhMuc> getAllCategories() {
        List<DanhMuc> list = new ArrayList<>();
        String query = "SELECT * FROM danh_muc";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                DanhMuc category = new DanhMuc();
                category.setMaDanhMuc(cursor.getInt(cursor.getColumnIndexOrThrow("ma_danh_muc")));
                category.setTenDanhMuc(cursor.getString(cursor.getColumnIndexOrThrow("ten_danh_muc")));
                category.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow("mo_ta")));
                list.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Thêm danh mục mới
    public boolean insertCategory(DanhMuc category) {
        ContentValues values = new ContentValues();
        values.put("ten_danh_muc", category.getTenDanhMuc());
        values.put("mo_ta", category.getMoTa());
        long result = db.insert("danh_muc", null, values);
        return result != -1;
    }

    // Cập nhật danh mục
    public boolean updateCategory(DanhMuc category) {
        ContentValues values = new ContentValues();
        values.put("ten_danh_muc", category.getTenDanhMuc());
        values.put("mo_ta", category.getMoTa());
        int result = db.update("danh_muc", values, "ma_danh_muc = ?", new String[]{String.valueOf(category.getMaDanhMuc())});
        return result > 0;
    }

    // Xóa danh mục
    public boolean deleteCategory(int categoryId) {
        int result = db.delete("danh_muc", "ma_danh_muc = ?", new String[]{String.valueOf(categoryId)});
        return result > 0;
    }
}