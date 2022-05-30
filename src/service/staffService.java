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
    public void updateNhanVien1(staff staff, String tk) {
        staffDao.updateNhanVien(staff,tk);
    }
    public staff getById(String tenDangNhap){
        return staffDao.getNhanVien(tenDangNhap);
    }
    public void changePass(staff staff, String tk) {
        staffDao.changePass(staff,tk);
    }
    
}
