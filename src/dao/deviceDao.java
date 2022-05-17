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
                thietbi.setLoaiTB(rs.getInt("id_loaiTB"));
                thietbi.setTenTB(rs.getString("tenTB"));
                thietbi.setNSX(rs.getInt("id_NSX"));
                //thietbi.setNgaySX(rs.getDate("ngaySX"));
                thietbi.setSoLuong(rs.getInt("soLuong"));
                thietbi.setGia(rs.getInt("gia"));
                thietbi.setId_phong(rs.getInt("id_phong"));
                thietbi.setTrangThai(rs.getInt("id_trangThai"));

                thietbis.add(thietbi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thietbis;
    }

    public List<thietBi> getFilterTB(int id_trangThai, int id_loaiTB) {
        List<thietBi> thietbis = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM thietbi where id_trangThai = ? and id_loaiTB =?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_trangThai);
            preparedStatement.setInt(2, id_loaiTB);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                thietBi thietbi = new thietBi();

                thietbi.setMaTB(rs.getString("maTB"));
                thietbi.setLoaiTB(rs.getInt("id_loaiTB"));
                thietbi.setTenTB(rs.getString("tenTB"));
                thietbi.setNSX(rs.getInt("id_NSX"));
                //thietbi.setNgaySX(rs.getDate("ngaySX"));
                thietbi.setSoLuong(rs.getInt("soLuong"));
                thietbi.setGia(rs.getInt("gia"));
                thietbi.setId_phong(rs.getInt("id_phong"));
                thietbi.setTrangThai(rs.getInt("id_trangThai"));

                thietbis.add(thietbi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thietbis;
    }

    public List<thietBi> getFilterTTTB(int id_trangThai) {
        List<thietBi> thietbis = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM thietbi where id_trangThai = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_trangThai);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                thietBi thietbi = new thietBi();

                thietbi.setMaTB(rs.getString("maTB"));
                thietbi.setLoaiTB(rs.getInt("id_loaiTB"));
                thietbi.setTenTB(rs.getString("tenTB"));
                thietbi.setNSX(rs.getInt("id_NSX"));
                //thietbi.setNgaySX(rs.getDate("ngaySX"));
                thietbi.setSoLuong(rs.getInt("soLuong"));
                thietbi.setGia(rs.getInt("gia"));
                thietbi.setId_phong(rs.getInt("id_phong"));
                thietbi.setTrangThai(rs.getInt("id_trangThai"));

                thietbis.add(thietbi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thietbis;
    }

    public List<thietBi> getFilterLTB(int id_loaiTB) {
        List<thietBi> thietbis = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM thietbi where id_loaiTB =?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_loaiTB);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                thietBi thietbi = new thietBi();

                thietbi.setMaTB(rs.getString("maTB"));
                thietbi.setLoaiTB(rs.getInt("id_loaiTB"));
                thietbi.setTenTB(rs.getString("tenTB"));
                thietbi.setNSX(rs.getInt("id_NSX"));
                //thietbi.setNgaySX(rs.getDate("ngaySX"));
                thietbi.setSoLuong(rs.getInt("soLuong"));
                thietbi.setGia(rs.getInt("gia"));
                thietbi.setId_phong(rs.getInt("id_phong"));
                thietbi.setTrangThai(rs.getInt("id_trangThai"));

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

        String sql = "SELECT count(maTB) FROM thietbi where id_trangThai = 1";

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
        String sql = "SELECT count(maTB) FROM thietbi where id_trangThai =2";

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

    public int sumTBDangRanh() throws SQLException {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT count(maTB) FROM thietbi where id_trangThai =0";

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
        String sql = "SELECT count(maTB) FROM thietbi where id_trangThai = 4";

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

    public int sumTBQuaHan() throws SQLException {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT count(maTB) FROM thietbi where id_trangThai = 3";

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

    public String viewTT(int id_trangThai) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT trangThai FROM trangthai where id = ?";

        String tt = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_trangThai);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                tt = rs.getString("trangThai");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tt;
    }

    public String viewNSX(int id) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT tenNSX FROM nhasanxuat where id = ?";

        String tt = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                tt = rs.getString("tenNSX");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tt;
    }

    public String viewLTB(int id) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT loaiTB FROM loaitb where id = ?";

        String tt = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                tt = rs.getString("loaiTB");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tt;
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
        String sql = "INSERT INTO thietbi(maTB, id_loaiTB, tenTB, id_NSX, soLuong, gia, id_phong, id_trangThai) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, thietbi.getMaTB());
            preparedStatement.setInt(2, thietbi.getLoaiTB());
            preparedStatement.setString(3, thietbi.getTenTB());
            preparedStatement.setInt(4, thietbi.getNSX());
            //preparedStatement.setDate(5, thietbi.getNgaySX());
            preparedStatement.setInt(5, thietbi.getSoLuong());
            preparedStatement.setInt(6, thietbi.getGia());
            preparedStatement.setInt(7, thietbi.getId_phong());
            preparedStatement.setInt(8, thietbi.getTrangThai());

            int rs = preparedStatement.executeUpdate();
            System.out.println(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//    public void updatethietbi(thietBi thietbi) {
//        Connection connection = JDBCConnection.getJDBCConnection();
//        String sql = "UPDATE thietbi SET id_loaiTB = ?,tenTB = ?, NSX = ?, soLuong = ?, gia = ?, trangThai = ? where ma =?";
//
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            preparedStatement.setInt(1, thietbi.getLoaiTB());
//            preparedStatement.setString(2, thietbi.getTenTB());
//            preparedStatement.setInt(3, thietbi.getNSX());
//            preparedStatement.setDate(4, thietbi.getNgaySX());
//            preparedStatement.setInt(4, thietbi.getSoLuong());
//            preparedStatement.setInt(5, thietbi.getGia());
//            preparedStatement.setInt(6, thietbi.getTrangThai());
//
//            int rs = preparedStatement.executeUpdate();
//            System.out.println(rs);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
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
