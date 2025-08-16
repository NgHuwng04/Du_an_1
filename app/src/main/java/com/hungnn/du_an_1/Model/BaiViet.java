package com.hungnn.du_an_1.Model;

import java.io.Serializable;

public class BaiViet implements Serializable {
    private int maBaiViet;
    private String tieuDe;
    private String noiDung;
    private String ngayDang;
    private int maTacGia;
    private String tenTacGia;
    private String hinhAnh;

    public BaiViet(int maBaiViet, String tieuDe, String noiDung, String ngayDang, int maTacGia, String tenTacGia, String hinhAnh) {
        this.maBaiViet = maBaiViet;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.ngayDang = ngayDang;
        this.maTacGia = maTacGia;
        this.tenTacGia = tenTacGia;
        this.hinhAnh = hinhAnh;
    }

    public BaiViet() {
    }

    public int getMaBaiViet() {
        return maBaiViet;
    }

    public void setMaBaiViet(int maBaiViet) {
        this.maBaiViet = maBaiViet;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }

    public int getMaTacGia() {
        return maTacGia;
    }

    public void setMaTacGia(int maTacGia) {
        this.maTacGia = maTacGia;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
