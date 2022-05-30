/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.roomDao;
import java.sql.SQLException;
import java.util.List;
import model.room;

/**
 *
 * @author Lenovo
 */
public class roomService {

    private final roomDao roomDao;

    public roomService() {
        roomDao = new roomDao();
    }

    public List<room> getAllroom() {
        return roomDao.getAllRoom();
    }
    
    public int countTb(int maPhong){
        return roomDao.countTB(maPhong);
    }
    
//    public List<room> getFilterTB(int id_trangThai, int id_loaiTB) {
//        return deviceDao.getFilterTB(id_trangThai, id_loaiTB);
//    }
//    
//    public List<room> getFilterTTTB(int id_trangThai) {
//        return deviceDao.getFilterTTTB(id_trangThai);
//    }
//    
//    public List<room> getFilterLTB(int id_loaiTB) {
//        return deviceDao.getFilterLTB(id_loaiTB);
//    }
//    
    public String showNameKhu(int id){
        return roomDao.showNameKhu(id);
    }
//    
//    public String viewLTB(int id){
//        return deviceDao.viewLTB(id);
//    }
//    
    public String viewTenPhong1(int id){
        return roomDao.viewTenPhong1(id);
    }
//
    public void adddRoom(room room) {
        roomDao.addRoom(room);
    }

    public void deleteRoom(int ma) {
        roomDao.deleteRoom(ma);
    }
//
//    public int sumTB() throws SQLException {
//        return deviceDao.sumTB();
//
//    }
//
//    public int sumTBDangSD() throws SQLException {
//        return deviceDao.sumTBDangSD();
//    }
//
//    public int sumTBCanTL() throws SQLException {
//        return deviceDao.sumTBCanTL();
//    }
//
//    public int sumTBHuHong() throws SQLException {
//        return deviceDao.sumTBHuHong();
//    }
//
//    public int sumTBQuaHan() throws SQLException {
//        return deviceDao.sumTBQuaHan();
//    }
//
//    public int sumTBDangRanh() throws SQLException {
//        return deviceDao.sumTBDangRanh();
//    }
}
