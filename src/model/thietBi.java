/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Lenovo
 */
public class thietBi {

    private String maTB;
    private String tenTB;
    private int loaiTB;
    private int NSX;
    private Date ngaySX;
    private int soLuong;
    private int gia;
    private int trangThai;
    private int id_phong;

    public int getId_phong() {
        return id_phong;
    }

    public void setId_phong(int id_phong) {
        this.id_phong = id_phong;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getTenTB() {
        return tenTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }

    public int getLoaiTB() {
        return loaiTB;
    }

    public void setLoaiTB(int loaiTB) {
        this.loaiTB = loaiTB;
    }

    public int getNSX() {
        return NSX;
    }

    public void setNSX(int NSX) {
        this.NSX = NSX;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgaySX() {
        return ngaySX;
    }

    public void setNgaySX(Date ngaySX) {
        this.ngaySX = ngaySX;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

}
