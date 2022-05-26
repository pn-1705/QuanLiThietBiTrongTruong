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


/**
 *
 * @author anhqu
 */
public class staffDao {

    public staff getNhanVien(String tenDangNhap) {
        List<staff> nv = new ArrayList<>();
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
                //staff.setNgaySinh(rs.getString("ngaySinh"));
                staff.setGioiTinh(rs.getInt("gioiTinh"));
                staff.setSoDienThoai(rs.getString("sdt"));
                staff.setDiaChi(rs.getString("diaChi"));
                //staff.setMaChucVu(rs.getString("maChucVu"));
                staff.setTenDangNhap(rs.getString("tenDangNhap"));
                staff.setMatKhau(rs.getString("matKhau"));
                nv.add(staff);
                return staff;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
