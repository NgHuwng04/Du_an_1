package com.hungnn.du_an_1.DAO;

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

    public List<DanhMuc> getAllCategories() {
        List<DanhMuc> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM danh_muc", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ma_danh_muc"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("ten_danh_muc"));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow("mo_ta"));

                list.add(new DanhMuc(id, name, desc));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
