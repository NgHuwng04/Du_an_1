package com.hungnn.du_an_1.Model;

public class SanPham {
    private int maSanPham;
    private String tenSanPham;
    private String moTa;
    private double gia;
    private int soLuongTon;
    private int maDanhMuc;
    private int hinhAnhResId; // truyền vào hình ảnh lấy từ Drawable

    public SanPham(int maSanPham, String tenSanPham, String moTa, double gia, int soLuongTon) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.moTa = moTa;
        this.gia = gia;
        this.soLuongTon = soLuongTon;
        this.maDanhMuc = maDanhMuc;
    }

    public SanPham(int maSanPham, String tenSanPham, String moTa, double gia, int soLuongTon, int maDanhMuc, int hinhAnhResId) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.moTa = moTa;
        this.gia = gia;
        this.soLuongTon = soLuongTon;
        this.maDanhMuc = maDanhMuc;
        this.hinhAnhResId = hinhAnhResId;
    }

    public SanPham() {
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public int getHinhAnhResId() {
        return hinhAnhResId;
    }

    public void setHinhAnhResId(int hinhAnhResId) {
        this.hinhAnhResId = hinhAnhResId;
    }
}
