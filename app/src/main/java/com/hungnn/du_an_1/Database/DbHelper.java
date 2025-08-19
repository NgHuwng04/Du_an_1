package com.hungnn.du_an_1.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private  static final String DB_NANE ="ZSHop.db";
    private static final int DB_VERSION = 2;
    public DbHelper(Context context) {
        super(context, DB_NANE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE nguoi_dung (" +
                "ma_nguoi_dung INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ho_ten TEXT," +
                "email TEXT UNIQUE," +
                "mat_khau TEXT," +
                "vai_tro TEXT CHECK(vai_tro IN ('khach_hang', 'chu_cua_hang'))," +
                "trang_thai TEXT DEFAULT 'hoat_dong' CHECK(trang_thai IN ('hoat_dong', 'bi_khoa')))");

        // Chèn dữ liệu admin mặc định
        ContentValues adminValues = new ContentValues();
        adminValues.put("ho_ten", "Admin");
        adminValues.put("email", "admin@zshop.com");
        adminValues.put("mat_khau", "123");
        adminValues.put("vai_tro", "chu_cua_hang");
        adminValues.put("trang_thai", "hoat_dong");
        db.insert("nguoi_dung", null, adminValues);


        // 2. Bảng danh mục
        db.execSQL("CREATE TABLE danh_muc (" +
                "ma_danh_muc INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ten_danh_muc TEXT," +
                "mo_ta TEXT)");
        db.execSQL("INSERT INTO danh_muc (ten_danh_muc, mo_ta) VALUES (1,'Iphone', 'Đời mới nhất')");
        db.execSQL("INSERT INTO danh_muc (ten_danh_muc, mo_ta) VALUES (2,'Samsung', 'Màn hình OLED')");

        // 3. Bảng sản phẩm
        db.execSQL("CREATE TABLE san_pham (" +
                "ma_san_pham INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ten_san_pham TEXT," +
                "mo_ta TEXT," +
                "gia REAL," +
                "so_luong_ton INTEGER," +
                "ma_danh_muc INTEGER," +
                "hinh_anh TEXT," +
                "FOREIGN KEY (ma_danh_muc) REFERENCES danh_muc(ma_danh_muc))");

        // 4. Bảng giỏ hàng
        db.execSQL("CREATE TABLE gio_hang (" +
                "ma_gio_hang INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ma_nguoi_dung INTEGER," +
                "ma_san_pham INTEGER," +
                "so_luong INTEGER," +
                "FOREIGN KEY (ma_nguoi_dung) REFERENCES nguoi_dung(ma_nguoi_dung)," +
                "FOREIGN KEY (ma_san_pham) REFERENCES san_pham(ma_san_pham))");

        // 5. Bảng đơn hàng
        db.execSQL("CREATE TABLE don_hang (" +
                "ma_don_hang INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ma_nguoi_dung INTEGER," +
                "ngay_dat TEXT," +
                "trang_thai TEXT CHECK(trang_thai IN ('cho_xu_ly', 'dang_giao', 'da_giao', 'huy'))," +
                "tong_tien REAL," +
                "FOREIGN KEY (ma_nguoi_dung) REFERENCES nguoi_dung(ma_nguoi_dung))");

        // 6. Bảng chi tiết đơn hàng
        db.execSQL("CREATE TABLE chi_tiet_don_hang (" +
                "ma_chi_tiet INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ma_don_hang INTEGER," +
                "ma_san_pham INTEGER," +
                "so_luong INTEGER," +
                "gia REAL," +
                "FOREIGN KEY (ma_don_hang) REFERENCES don_hang(ma_don_hang)," +
                "FOREIGN KEY (ma_san_pham) REFERENCES san_pham(ma_san_pham))");

        // 7. Bảng đánh giá
        db.execSQL("CREATE TABLE danh_gia (" +
                "ma_danh_gia INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ma_nguoi_dung INTEGER," +
                "ma_san_pham INTEGER," +
                "so_sao INTEGER CHECK(so_sao BETWEEN 1 AND 5)," +
                "binh_luan TEXT," +
                "ngay_danh_gia TEXT," +
                "FOREIGN KEY (ma_nguoi_dung) REFERENCES nguoi_dung(ma_nguoi_dung)," +
                "FOREIGN KEY (ma_san_pham) REFERENCES san_pham(ma_san_pham))");

        // 8. Bảng bài viết
        db.execSQL("CREATE TABLE bai_viet (" +
                "ma_bai_viet INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tieu_de TEXT," +
                "noi_dung TEXT," +
                "ngay_dang TEXT," +
                "ma_tac_gia INTEGER," +
                "FOREIGN KEY (ma_tac_gia) REFERENCES nguoi_dung(ma_nguoi_dung))");

        // 9. Bảng thống kê
        db.execSQL("CREATE TABLE thong_ke (" +
                "ma_thong_ke INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ngay TEXT," +
                "tong_don_hang INTEGER," +
                "tong_doanh_thu REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS thong_ke");
        db.execSQL("DROP TABLE IF EXISTS bai_viet");
        db.execSQL("DROP TABLE IF EXISTS danh_gia");
        db.execSQL("DROP TABLE IF EXISTS chi_tiet_don_hang");
        db.execSQL("DROP TABLE IF EXISTS don_hang");
        db.execSQL("DROP TABLE IF EXISTS gio_hang");
        db.execSQL("DROP TABLE IF EXISTS san_pham");
        db.execSQL("DROP TABLE IF EXISTS danh_muc");
        db.execSQL("DROP TABLE IF EXISTS nguoi_dung");
        onCreate(db);
    }

}