package com.hungnn.du_an_1.Model;

public class ThongKe {
    private int maThongKe;
    private String ngay;
    private int tongDonHang;
    private double tongDoanhThu;

    public ThongKe(int maThongKe, String ngay, int tongDonHang, double tongDoanhThu) {
        this.maThongKe = maThongKe;
        this.ngay = ngay;
        this.tongDonHang = tongDonHang;
        this.tongDoanhThu = tongDoanhThu;
    }

    public ThongKe() {
    }

    public int getMaThongKe() {
        return maThongKe;
    }

    public void setMaThongKe(int maThongKe) {
        this.maThongKe = maThongKe;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTongDonHang() {
        return tongDonHang;
    }

    public void setTongDonHang(int tongDonHang) {
        this.tongDonHang = tongDonHang;
    }

    public double getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(double tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }
}
