/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.deviceDao;
import java.sql.SQLException;
import java.util.List;
import model.thietBi;

/**
 *
 * @author Lenovo
 */
public class deviceService {

    private final deviceDao deviceDao;

    public deviceService() {
        deviceDao = new deviceDao();
    }

    public List<thietBi> getAllthietbi() {
        return deviceDao.getAllthietbi();
    }

    public List<thietBi> getDeviceRoom(int maPhong) {
        return deviceDao.getDeviceRoom(maPhong);
    }

    public List<thietBi> getFilterTB(int id_trangThai, int id_loaiTB) {
        return deviceDao.getFilterTB(id_trangThai, id_loaiTB);
    }

    public List<thietBi> getFilterTTTB(int id_trangThai) {
        return deviceDao.getFilterTTTB(id_trangThai);
    }

    public List<thietBi> getFilterLTB(int id_loaiTB) {
        return deviceDao.getFilterLTB(id_loaiTB);
    }

    public String viewTT(int id) {
        return deviceDao.viewTT(id);
    }

    public String viewLTB(int id) {
        return deviceDao.viewLTB(id);
    }

    public String viewNSX(int id) {
        return deviceDao.viewNSX(id);
    }

    public String viewPhong(int id) {
        return deviceDao.viewPhong(id);
    }

    public void adddevice(thietBi device) {
        deviceDao.addthietbi(device);
    }

    public void deletedevice(String ma) {
        deviceDao.deletethietbi(ma);
    }
    
    public void returnKho2(String ma) {
        deviceDao.returnKho2(ma);
    }
    
    public void transferPhong(String ma, int phong) {
        deviceDao.transferPhong(ma, phong);
    }

    public int sumTB() {
        return deviceDao.sumTB();

    }

    public int sumTBDangSD() {
        return deviceDao.sumTBDangSD();
    }

    public int sumTBCanTL() {
        return deviceDao.sumTBCanTL();
    }

    public int sumTBHuHong() {
        return deviceDao.sumTBHuHong();
    }

    public int sumTBQuaHan() {
        return deviceDao.sumTBQuaHan();
    }

    public int sumTBDangRanh() {
        return deviceDao.sumTBDangRanh();
    }

    public List<thietBi> timTB2(String kitu) {
        return deviceDao.timTB2(kitu);
    }

}
