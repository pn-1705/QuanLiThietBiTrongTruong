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

    public void adddevice(thietBi device) {
        deviceDao.addthietbi(device);
    }
    public void deletedevice(int ma) {
        deviceDao.deletethietbi(ma);
    }

    public int sumTB() throws SQLException {
        return deviceDao.sumTB();

    }

    public int sumTBDangSD() throws SQLException {
        return deviceDao.sumTBDangSD();
    }

    public int sumTBCanTL() throws SQLException {
        return deviceDao.sumTBCanTL();
    }

    public int sumTBHuHong() throws SQLException {
        return deviceDao.sumTBHuHong();
    }
}
