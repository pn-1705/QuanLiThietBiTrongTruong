/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.thietBi;

/**
 *
 * @author Lenovo
 */
public class deviceDao {

    public List<thietBi> getAllthietbi() {
        List<thietBi> thietbis = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM thietbi";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                thietBi thietbi = new thietBi();

                thietbi.setMaTB(rs.getString("maTB"));
                thietbi.setLoaiTB(rs.getString("loaiTB"));
                thietbi.setTenTB(rs.getString("tenTB"));
                thietbi.setNSX(rs.getString("NSX"));
//                thietbi.setNgaySX(rs.getDate("ngaySX"));
                thietbi.setSoLuong(rs.getInt("soLuong"));
                thietbi.setGia(rs.getInt("gia"));
                thietbi.setTrangThai(rs.getString("trangThai"));

                thietbis.add(thietbi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thietbis;
    }

    public int sumTB() throws SQLException {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT count(maTB) FROM thietbi";

        int num = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                num = (rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;

    }

    public int sumTBDangSD() throws SQLException {
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "SELECT count(maTB) FROM thietbi where trangThai = 1";

        int num = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                num = (rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    public int sumTBCanTL() throws SQLException {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT count(maTB) FROM thietbi where trangThai =2";

        int num = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                num = (rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    public int sumTBHuHong() throws SQLException {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT count(maTB) FROM thietbi where trangThai = 3";

        int num = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                num = (rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

//    public thietbi getthietbiID(int mathietbi) {
//        Connection connection = JDBCConnection.getJDBCConnection();
//        String sql = "SELECT * FROM thietbi where mathietbi=?";
//
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, mathietbi);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                thietbi thietbi = new thietbi();
//
//                thietbi.setMathietbi(rs.getInt("mathietbi"));
//                thietbi.setLoaithietbi(rs.getString("loaithietbi"));
//                thietbi.setCpu(rs.getString("cpu"));
//                thietbi.setRam(rs.getInt("ram"));
//                thietbi.setVga(rs.getString("vga"));
//                thietbi.setMonitor(rs.getString("monitor"));
//                thietbi.setTrangthai(rs.getInt("trangthai"));
//                thietbi.setVitri(rs.getInt("vitri"));
//                thietbi.setPhongid(rs.getInt("phongid"));
//
//                return thietbi;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
    public void addthietbi(thietBi thietbi) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "INSERT INTO thietbi(maTB,loaiTB,tenTB, NSX, soLuong, gia, trangThai) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, thietbi.getMaTB());
            preparedStatement.setString(2, thietbi.getLoaiTB());
            preparedStatement.setString(3, thietbi.getTenTB());
            preparedStatement.setString(4, thietbi.getNSX());
            //preparedStatement.setDate(5, thietbi.getNgaySX());
            preparedStatement.setInt(5, thietbi.getSoLuong());
            preparedStatement.setInt(6, thietbi.getGia());
            preparedStatement.setString(7, thietbi.getTrangThai());

            int rs = preparedStatement.executeUpdate();
            System.out.println(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void updatethietbi(thietBi thietbi) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "UPDATE thietbi SET loaiTB = ?,tenTB = ?, NSX = ?, soLuong = ?, gia = ?, trangThai = ? where ma =?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, thietbi.getLoaiTB());
            preparedStatement.setString(2, thietbi.getTenTB());
            preparedStatement.setString(3, thietbi.getNSX());
            //preparedStatement.setDate(4, thietbi.getNgaySX());
            preparedStatement.setInt(4, thietbi.getSoLuong());
            preparedStatement.setInt(5, thietbi.getGia());
            preparedStatement.setString(6, thietbi.getTrangThai());

            int rs = preparedStatement.executeUpdate();
            System.out.println(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deletethietbi(int mathietbi) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "delete from thietbi where mathietbi= ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, mathietbi);
            int rs = preparedStatement.executeUpdate();
            System.out.println(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
