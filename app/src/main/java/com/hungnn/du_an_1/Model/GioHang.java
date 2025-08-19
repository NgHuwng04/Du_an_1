package com.hungnn.du_an_1.Model;

public class GioHang {
    private int maGioHang;
    private int maSanPham;
    private String tenSanPham;
    private double gia;
    private int soLuong;
    private int hinhAnhResId;

    // Hàm tạo đầy đủ để nhận tất cả các giá trị
    public GioHang(int maGioHang, int maSanPham, String tenSanPham, double gia, int soLuong, int hinhAnhResId) {
        this.maGioHang = maGioHang;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.gia = gia;
        this.soLuong = soLuong;
        this.hinhAnhResId = hinhAnhResId;
    }

    // Các getters và setters
    public int getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(int maGioHang) {
        this.maGioHang = maGioHang;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getHinhAnhResId() {
        return hinhAnhResId;
    }

    public void setHinhAnhResId(int hinhAnhResId) {
        this.hinhAnhResId = hinhAnhResId;
    }
}