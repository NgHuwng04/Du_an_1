package com.hungnn.du_an_1.Model;

public class DanhGia {
    private int maDanhGia;
    private int maNguoiDung;
    private int maSanPham;
    private int soSao;
    private String binhLuan;
    private String ngayDanhGia;

    public DanhGia(int maDanhGia, int maNguoiDung, int maSanPham, int soSao, String binhLuan, String ngayDanhGia) {
        this.maDanhGia = maDanhGia;
        this.maNguoiDung = maNguoiDung;
        this.maSanPham = maSanPham;
        this.soSao = soSao;
        this.binhLuan = binhLuan;
        this.ngayDanhGia = ngayDanhGia;
    }

    public DanhGia() {
    }

    public int getMaDanhGia() {
        return maDanhGia;
    }

    public void setMaDanhGia(int maDanhGia) {
        this.maDanhGia = maDanhGia;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getSoSao() {
        return soSao;
    }

    public void setSoSao(int soSao) {
        this.soSao = soSao;
    }

    public String getBinhLuan() {
        return binhLuan;
    }

    public void setBinhLuan(String binhLuan) {
        this.binhLuan = binhLuan;
    }

    public String getNgayDanhGia() {
        return ngayDanhGia;
    }

    public void setNgayDanhGia(String ngayDanhGia) {
        this.ngayDanhGia = ngayDanhGia;
    }
}
