package com.hungnn.du_an_1.Model;

public class DonHang {
        private int maDonHang;
        private int maNguoiDung;
        private String ngayDat;
        private String diaChiGiao;
        private String soDienThoai;
        private String phuongThucThanhToan;
        private String trangThai;
        private double tongTien;

    public DonHang() {
    }

    public DonHang(int maDonHang, int maNguoiDung, String ngayDat, String trangThai, double tongTien) {
        this.maDonHang = maDonHang;
        this.maNguoiDung = maNguoiDung;
        this.ngayDat = ngayDat;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
    }

    public DonHang(int maDonHang, int maNguoiDung, String ngayDat, String diaChiGiao, String soDienThoai, String phuongThucThanhToan, String trangThai, double tongTien) {
        this.maDonHang = maDonHang;
        this.maNguoiDung = maNguoiDung;
        this.ngayDat = ngayDat;
        this.diaChiGiao = diaChiGiao;
        this.soDienThoai = soDienThoai;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
    }

    public int getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(int maDonHang) {
        this.maDonHang = maDonHang;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getDiaChiGiao() {
        return diaChiGiao;
    }

    public void setDiaChiGiao(String diaChiGiao) {
        this.diaChiGiao = diaChiGiao;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }
}
