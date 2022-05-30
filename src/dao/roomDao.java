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
import model.room;

/**
 *
 * @author Lenovo
 */
public class roomDao {

    public List<room> getAllRoom() {
        List<room> room = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM phong";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                room r = new room();

                r.setMaPhong(rs.getInt("maPhong"));
                r.setTenPhong(rs.getString("tenPhong"));
                r.setMaViTri(rs.getInt("maViTri"));

                room.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    public int countTB(int maPhong) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT count(maTB) FROM thietbi where id_phong = ?";

        int sl = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, maPhong);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                sl = rs.getInt("count(maTB)");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sl;
    }

//    public List<thietBi> getFilterTB(int id_trangThai, int id_loaiTB) {
//        List<thietBi> thietbis = new ArrayList<>();
//        Connection connection = JDBCConnection.getJDBCConnection();
//        String sql = "SELECT * FROM thietbi where id_trangThai = ? and id_loaiTB =?";
//
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, id_trangThai);
//            preparedStatement.setInt(2, id_loaiTB);
//
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                thietBi thietbi = new thietBi();
//
//                thietbi.setMaTB(rs.getString("maTB"));
//                thietbi.setLoaiTB(rs.getInt("id_loaiTB"));
//                thietbi.setTenTB(rs.getString("tenTB"));
//                thietbi.setNSX(rs.getInt("id_NSX"));
//                //thietbi.setNgaySX(rs.getDate("ngaySX"));
//                thietbi.setSoLuong(rs.getInt("soLuong"));
//                thietbi.setGia(rs.getInt("gia"));
//                thietbi.setId_phong(rs.getInt("id_phong"));
//                thietbi.setTrangThai(rs.getInt("id_trangThai"));
//
//                thietbis.add(thietbi);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return thietbis;
//    }
//
//    public List<thietBi> getFilterTTTB(int id_trangThai) {
//        List<thietBi> thietbis = new ArrayList<>();
//        Connection connection = JDBCConnection.getJDBCConnection();
//        String sql = "SELECT * FROM thietbi where id_trangThai = ?";
//
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, id_trangThai);
//
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                thietBi thietbi = new thietBi();
//
//                thietbi.setMaTB(rs.getString("maTB"));
//                thietbi.setLoaiTB(rs.getInt("id_loaiTB"));
//                thietbi.setTenTB(rs.getString("tenTB"));
//                thietbi.setNSX(rs.getInt("id_NSX"));
//                //thietbi.setNgaySX(rs.getDate("ngaySX"));
//                thietbi.setSoLuong(rs.getInt("soLuong"));
//                thietbi.setGia(rs.getInt("gia"));
//                thietbi.setId_phong(rs.getInt("id_phong"));
//                thietbi.setTrangThai(rs.getInt("id_trangThai"));
//
//                thietbis.add(thietbi);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return thietbis;
//    }
//
//    public List<thietBi> getFilterLTB(int id_loaiTB) {
//        List<thietBi> thietbis = new ArrayList<>();
//        Connection connection = JDBCConnection.getJDBCConnection();
//        String sql = "SELECT * FROM thietbi where id_loaiTB =?";
//
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, id_loaiTB);
//
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                thietBi thietbi = new thietBi();
//
//                thietbi.setMaTB(rs.getString("maTB"));
//                thietbi.setLoaiTB(rs.getInt("id_loaiTB"));
//                thietbi.setTenTB(rs.getString("tenTB"));
//                thietbi.setNSX(rs.getInt("id_NSX"));
//                //thietbi.setNgaySX(rs.getDate("ngaySX"));
//                thietbi.setSoLuong(rs.getInt("soLuong"));
//                thietbi.setGia(rs.getInt("gia"));
//                thietbi.setId_phong(rs.getInt("id_phong"));
//                thietbi.setTrangThai(rs.getInt("id_trangThai"));
//
//                thietbis.add(thietbi);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return thietbis;
//    }
//
//    public int sumTB() throws SQLException {
//        Connection connection = JDBCConnection.getJDBCConnection();
//        String sql = "SELECT count(maTB) FROM thietbi";
//
//        int num = 0;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                num = (rs.getInt(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return num;
//
//    }
//
//    public int sumTBDangSD() throws SQLException {
//        Connection connection = JDBCConnection.getJDBCConnection();
//
//        String sql = "SELECT count(maTB) FROM thietbi where id_trangThai = 1";
//
//        int num = 0;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                num = (rs.getInt(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return num;
//    }
//
//    public int sumTBCanTL() throws SQLException {
//        Connection connection = JDBCConnection.getJDBCConnection();
//        String sql = "SELECT count(maTB) FROM thietbi where id_trangThai =2";
//
//        int num = 0;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                num = (rs.getInt(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return num;
//    }
//
//    public int sumTBDangRanh() throws SQLException {
//        Connection connection = JDBCConnection.getJDBCConnection();
//        String sql = "SELECT count(maTB) FROM thietbi where id_trangThai =0";
//
//        int num = 0;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                num = (rs.getInt(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return num;
//    }
//
//    public int sumTBHuHong() throws SQLException {
//        Connection connection = JDBCConnection.getJDBCConnection();
//        String sql = "SELECT count(maTB) FROM thietbi where id_trangThai = 4";
//
//        int num = 0;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                num = (rs.getInt(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return num;
//    }
//
//    public int sumTBQuaHan() throws SQLException {
//        Connection connection = JDBCConnection.getJDBCConnection();
//        String sql = "SELECT count(maTB) FROM thietbi where id_trangThai = 3";
//
//        int num = 0;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                num = (rs.getInt(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return num;
//    }
//
    public String showNameKhu(int id) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT tenVT FROM vitri where maViTri = ?";

        String tt = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                tt = rs.getString("tenVT");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tt;
    }
//

    public String viewTenPhong1(int id) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT tenPhong FROM phong where maPhong = ?";

        String tt = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                tt = rs.getString("tenPhong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tt;
    }
//
//    public String viewLTB(int id) {
//        Connection connection = JDBCConnection.getJDBCConnection();
//        String sql = "SELECT loaiTB FROM loaitb where id = ?";
//
//        String tt = null;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, id);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                tt = rs.getString("loaiTB");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return tt;
//    }
//
////    public thietbi getthietbiID(int mathietbi) {
////        Connection connection = JDBCConnection.getJDBCConnection();
////        String sql = "SELECT * FROM thietbi where mathietbi=?";
////
////        try {
////            PreparedStatement preparedStatement = connection.prepareStatement(sql);
////            preparedStatement.setInt(1, mathietbi);
////            ResultSet rs = preparedStatement.executeQuery();
////
////            while (rs.next()) {
////                thietbi thietbi = new thietbi();
////
////                thietbi.setMathietbi(rs.getInt("mathietbi"));
////                thietbi.setLoaithietbi(rs.getString("loaithietbi"));
////                thietbi.setCpu(rs.getString("cpu"));
////                thietbi.setRam(rs.getInt("ram"));
////                thietbi.setVga(rs.getString("vga"));
////                thietbi.setMonitor(rs.getString("monitor"));
////                thietbi.setTrangthai(rs.getInt("trangthai"));
////                thietbi.setVitri(rs.getInt("vitri"));
////                thietbi.setPhongid(rs.getInt("phongid"));
////
////                return thietbi;
////            }
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        return null;
////    }
////

    public void addRoom(room room) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "INSERT INTO phong(maPhong, tenPhong, maViTri) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, room.getMaPhong());
            preparedStatement.setString(2, room.getTenPhong());
            preparedStatement.setInt(3, room.getMaViTri());

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
    public void deleteRoom(int ma) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "delete from phong where maPhong= ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, ma);
            int rs = preparedStatement.executeUpdate();
            System.out.println(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
