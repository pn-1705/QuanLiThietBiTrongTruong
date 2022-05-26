/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.staffDao;
import model.staff;


/**
 *
 * @author Lenovo
 */
public class staffService {
    
    private final staffDao staffDao;
    
    public staffService() {
        staffDao = new staffDao();
    }
    
    public staff viewAccount(String tdn) {
        return staffDao.getNhanVien(tdn);
    }
//    public List<thietBi> getAllthietbi() {
//        return staffDao.getAllthietbi();
//    }
//    
//    public List<thietBi> getFilterTB(int id_trangThai, int id_loaiTB) {
//        return staffDao.getFilterTB(id_trangThai, id_loaiTB);
//    }
//    
//    public List<thietBi> getFilterTTTB(int id_trangThai) {
//        return staffDao.getFilterTTTB(id_trangThai);
//    }
//    
//    public List<thietBi> getFilterLTB(int id_loaiTB) {
//        return staffDao.getFilterLTB(id_loaiTB);
//    }
//    
//    public String viewTT(int id){
//        return staffDao.viewTT(id);
//    }
//    
//    public String viewLTB(int id){
//        return staffDao.viewLTB(id);
//    }
//    
//    public String viewNSX(int id){
//        return staffDao.viewNSX(id);
//    }
//
//    public void addstaff(thietBi staff) {
//        staffDao.addthietbi(staff);
//    }
//
//    public void deletestaff(int ma) {
//        staffDao.deletethietbi(ma);
//    }
//
//    public int sumTB() throws SQLException {
//        return staffDao.sumTB();
//
//    }
//
//    public int sumTBDangSD() throws SQLException {
//        return staffDao.sumTBDangSD();
//    }
//
//    public int sumTBCanTL() throws SQLException {
//        return staffDao.sumTBCanTL();
//    }
//
//    public int sumTBHuHong() throws SQLException {
//        return staffDao.sumTBHuHong();
//    }
//
//    public int sumTBQuaHan() throws SQLException {
//        return staffDao.sumTBQuaHan();
//    }
//
//    public int sumTBDangRanh() throws SQLException {
//        return staffDao.sumTBDangRanh();
//    }
    public staff getById(String tenDangNhap){
        return staffDao.getNhanVien(tenDangNhap);
    }
}
