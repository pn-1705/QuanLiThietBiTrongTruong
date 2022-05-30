/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.JDBCConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.staff;

/**
 *
 * @author anhqu
 */
public class staffDao {

    public staff getNhanVien(String tenDangNhap) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM nhanvien where tenDangNhap =?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tenDangNhap);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                staff staff = new staff();

                staff.setMaNV(rs.getString("maNV"));
                staff.setTenNV(rs.getString("tenNV"));
                staff.setGioiTinh(rs.getInt("gioiTinh"));
                staff.setSoDienThoai(rs.getString("sdt"));
                staff.setNgaySinh(rs.getString("ngaySinh"));
                staff.setDiaChi(rs.getString("diaChi"));
                staff.setTenDangNhap(rs.getString("tenDangNhap"));
                staff.setMatKhau(rs.getString("matKhau"));

                return staff;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateNhanVien(staff staff, String taiKhoan) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "UPDATE nhanvien SET tenNV = ?, ngaySinh = ?, sdt = ?, diaChi = ? WHERE tenDangNhap = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, staff.getTenNV());
            preparedStatement.setString(2, staff.getNgaySinh());
            preparedStatement.setString(3, staff.getSoDienThoai());
            preparedStatement.setString(4, staff.getDiaChi());
            preparedStatement.setString(5, taiKhoan);
            int rs = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void changePass(staff staff, String taiKhoan) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "UPDATE nhanvien SET matKhau = ? WHERE tenDangNhap = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, staff.getMatKhau());
            preparedStatement.setString(2, taiKhoan);
            int rs = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
