/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import dao.JDBCConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.room;
import model.staff;
import model.thietBi;
import service.deviceService;
import service.roomService;
import service.staffService;

/**
 *
 * @author Lenovo
 */
public class TrangChuJFrame extends javax.swing.JFrame {

    staff staff;
    deviceService deviceService;
    roomService roomService;
    staffService staffService;

    DefaultTableModel defaultTableModel;
    DefaultTableModel defaultTableModelRoom;
    DefaultTableModel defaultTableModelPhongKho;
    DefaultTableModel defaultTableModelPhong;
    DefaultComboBoxModel cmbModel;

    String taikhoan;

    List<String> listCate = new ArrayList<>();
    List<String> listTT = new ArrayList<>();

    public TrangChuJFrame(int home, String id) {

        this.taikhoan = id;
        initComponents();
        deviceService = new deviceService();
        roomService = new roomService();

        defaultTableModel = new DefaultTableModel();
        defaultTableModelRoom = new DefaultTableModel();
        defaultTableModelPhong = new DefaultTableModel();
        jTabbedPane1.setSelectedIndex(home);

        jtableDevice41.setModel(defaultTableModel);
        defaultTableModel.addColumn("Mã thiết bị");
        defaultTableModel.addColumn("Tên thiết bị");
        defaultTableModel.addColumn("Số lượng");
        defaultTableModel.addColumn("Đơn giá");
        defaultTableModel.addColumn("Loại thiết bị");
        defaultTableModel.addColumn("Nhà sản xuất");
        //defaultTableModel.addColumn("Ngày sản xuất");
        defaultTableModel.addColumn("Phòng");
        defaultTableModel.addColumn("Trạng thái");

        jTableRoom41.setModel(defaultTableModelRoom);
        defaultTableModelRoom.addColumn("Mã phòng");
        defaultTableModelRoom.addColumn("Tên phòng");
        defaultTableModelRoom.addColumn("Vị trí");
        defaultTableModelRoom.addColumn("Số lượng TB");

        List<thietBi> devices = deviceService.getAllthietbi();
        List<room> room = roomService.getAllroom();

        for (thietBi thietBi : devices) {
            defaultTableModel.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                thietBi.getSoLuong(), thietBi.getGia() + " VNĐ",
                deviceService.viewLTB(thietBi.getLoaiTB()),
                deviceService.viewNSX(thietBi.getNSX()),
                deviceService.viewPhong(thietBi.getId_phong()),
                deviceService.viewTT(thietBi.getTrangThai())
            }
            );
        }
        for (room r : room) {
            defaultTableModelRoom.addRow(new Object[]{r.getMaPhong(), r.getTenPhong(),
                roomService.showNameKhu(r.getMaViTri()), roomService.countTb(r.getMaPhong())});
        }

        int sumTB = deviceService.sumTB();
        jLabelTongThietBi41.setText("" + sumTB);

        int sumTBDangSD = deviceService.sumTBDangSD();
        jLabelTBDangSD41.setText("" + sumTBDangSD);

        int sumTBCanTL = deviceService.sumTBCanTL();
        jLabelTBCanTL41.setText("" + sumTBCanTL);

        int sumTBHuHong = deviceService.sumTBHuHong();
        jLabelTBHuHong41.setText("" + sumTBHuHong);

        int sumTBSS = deviceService.sumTBDangRanh();
        jLabelTBSS41.setText("" + sumTBSS);

        int sumTBQH = deviceService.sumTBQuaHan();
        jLabelTBQuaHan41.setText("" + sumTBQH);

        //Tạo combobox danh mục sản phẩm
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM loaitb";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            List<String> listCate = new ArrayList<>();
            while (rs.next()) {
                this.listCate.add(rs.getString("loaiTB"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jComboBoxCate41.removeAllItems();
        jComboBoxCate41.addItem("Danh mục");
        for (String l : listCate) {
            jComboBoxCate41.addItem(l);
        }

        //Tạo combobox trạng thái sản phẩm
        String sql1 = "SELECT * FROM trangthai";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql1);
            ResultSet rs = preparedStatement.executeQuery();
            List<String> listTT = new ArrayList<>();
            while (rs.next()) {
                this.listTT.add(rs.getString("trangThai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jComboBoxTrangThai41.removeAllItems();
        jComboBoxTrangThai41.addItem("Trạng thái");
        for (String tt : listTT) {
            jComboBoxTrangThai41.addItem(tt);
        }

        //Danh sách phòng
        jTablePhong_41.setModel(defaultTableModelPhong);
        defaultTableModelPhong.addColumn("Mã phòng");
        defaultTableModelPhong.addColumn("Tên phòng");
        defaultTableModelPhong.addColumn("Vị trí");
        defaultTableModelPhong.addColumn("Số lượng TB");

        for (room r : room) {
            if (r.getMaPhong() != 0) {
                defaultTableModelPhong.addRow(new Object[]{r.getMaPhong(), r.getTenPhong(),
                    roomService.showNameKhu(r.getMaViTri()), roomService.countTb(r.getMaPhong())});
            }
        }

        //Danh sách thiết bị phòng kho
        setTableDeviceKho(deviceService.getDeviceRoom(0));

        staff nhanvien;
        staffService = new staffService();
        nhanvien = staffService.getById(id);
        textFieldTenNV.setText(nhanvien.getTenNV());
        textFieldSDT.setText(nhanvien.getSoDienThoai());
        textFieldNgaySinh.setText(nhanvien.getNgaySinh());
        textFieldDiaChi.setText(nhanvien.getDiaChi());

    }

    private void setTableData(List<thietBi> devices) {

        DefaultTableModel defaultTableModel = new DefaultTableModel();
        jtableDevice41.setModel(defaultTableModel);
        defaultTableModel.addColumn("Mã thiết bị");
        defaultTableModel.addColumn("Tên thiết bị");
        defaultTableModel.addColumn("Số lượng");
        defaultTableModel.addColumn("Đơn giá");
        defaultTableModel.addColumn("Loại thiết bị");
        defaultTableModel.addColumn("Nhà sản xuất");
        //defaultTableModel.addColumn("Ngày sản xuất");
        defaultTableModel.addColumn("Phòng");
        defaultTableModel.addColumn("Trạng thái");

        for (thietBi thietBi : devices) {
            defaultTableModel.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                thietBi.getSoLuong(), thietBi.getGia() + " VNĐ",
                deviceService.viewLTB(thietBi.getLoaiTB()),
                deviceService.viewNSX(thietBi.getNSX()),
                deviceService.viewPhong(thietBi.getId_phong()),
                deviceService.viewTT(thietBi.getTrangThai())
            }
            );
        }
    }

    private void setTableDeviceRoom(List<thietBi> devices) {

        DefaultTableModel defaultTableModelPhong = new DefaultTableModel();
        jTableDeviceRoom41.setModel(defaultTableModelPhong);
        defaultTableModelPhong.addColumn("Mã thiết bị");
        defaultTableModelPhong.addColumn("Tên thiết bị");
        defaultTableModelPhong.addColumn("Số lượng");
        defaultTableModelPhong.addColumn("Loại thiết bị");
        defaultTableModelPhong.addColumn("Nhà sản xuất");
        //defaultTableModel.addColumn("Ngày sản xuất");
        defaultTableModelPhong.addColumn("Trạng thái");
        defaultTableModelPhong.addColumn("Phòng");

        for (thietBi thietBi : devices) {
            defaultTableModelPhong.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                thietBi.getSoLuong(),
                deviceService.viewLTB(thietBi.getLoaiTB()),
                deviceService.viewNSX(thietBi.getNSX()),
                deviceService.viewTT(thietBi.getTrangThai()),
                thietBi.getId_phong()
            }
            );
        }
    }

    private void setTableRoom(List<room> room) {

        DefaultTableModel defaultTableModelPhong = new DefaultTableModel();

        jTablePhong_41.setModel(defaultTableModelPhong);
        defaultTableModelPhong.addColumn("Mã phòng");
        defaultTableModelPhong.addColumn("Tên phòng");
        defaultTableModelPhong.addColumn("Vị trí");
        defaultTableModelPhong.addColumn("Số lượng TB");
        for (room r : room) {
            if (r.getMaPhong() != 0) {
                defaultTableModelPhong.addRow(new Object[]{r.getMaPhong(), r.getTenPhong(),
                    roomService.showNameKhu(r.getMaViTri()), roomService.countTb(r.getMaPhong())});
            }
        }
    }

    private void setTableDeviceKho(List<thietBi> devices) {

        DefaultTableModel defaultTableModelPhongKho = new DefaultTableModel();
        jTablePhongKho41.setModel(defaultTableModelPhongKho);
        defaultTableModelPhongKho.addColumn("Mã thiết bị");
        defaultTableModelPhongKho.addColumn("Tên thiết bị");
        defaultTableModelPhongKho.addColumn("Số lượng");
        defaultTableModelPhongKho.addColumn("Đơn giá");
        defaultTableModelPhongKho.addColumn("Loại thiết bị");

        defaultTableModelPhongKho.addColumn("Nhà sản xuất");
        //defaultTableModel.addColumn("Ngày sản xuất");
        defaultTableModelPhongKho.addColumn("Trạng thái");

        for (thietBi thietBi : devices) {
            defaultTableModelPhongKho.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                thietBi.getSoLuong(), thietBi.getGia() + " VNĐ",
                deviceService.viewLTB(thietBi.getLoaiTB()),
                deviceService.viewNSX(thietBi.getNSX()),
                deviceService.viewTT(thietBi.getTrangThai())
            }
            );
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdlCapNhatTaiKhoan = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        textField1 = new java.awt.TextField();
        jLabel4 = new javax.swing.JLabel();
        textField2 = new java.awt.TextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        textField3 = new java.awt.TextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jdlChiTietTaiKhoan = new javax.swing.JDialog();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        textField4 = new java.awt.TextField();
        jLabel11 = new javax.swing.JLabel();
        textField5 = new java.awt.TextField();
        textField6 = new java.awt.TextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        textField7 = new java.awt.TextField();
        jButton3 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jButton1_41 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtableDevice41 = new javax.swing.JTable();
        jPanel33 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabelTongThietBi41 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabelTBDangSD41 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabelTBSS41 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabelTBCanTL41 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabelTBHuHong41 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabelTBQuaHan41 = new javax.swing.JLabel();
        jButton2_41 = new javax.swing.JButton();
        jComboBoxCate41 = new javax.swing.JComboBox<>();
        jComboBoxTrangThai41 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableRoom41 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldSearch41 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePhong_41 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableDeviceRoom41 = new javax.swing.JTable();
        jButton7_41 = new javax.swing.JButton();
        jButton11_41 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTablePhongKho41 = new javax.swing.JTable();
        jButton12_41 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabelPhong41 = new javax.swing.JLabel();
        jLabelPhong1 = new javax.swing.JLabel();
        jLabelTenPhong41 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jButton13_41 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        textFieldTenNV = new java.awt.TextField();
        jButtonLuuTK = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jLabel49 = new javax.swing.JLabel();
        textFieldSDT = new java.awt.TextField();
        jLabel59 = new javax.swing.JLabel();
        textFieldDiaChi = new java.awt.TextField();
        jLabel50 = new javax.swing.JLabel();
        textFieldNgaySinh = new java.awt.TextField();
        jLabel23 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        jdlCapNhatTaiKhoan.setMinimumSize(new java.awt.Dimension(560, 432));

        jPanel10.setBackground(new java.awt.Color(0, 153, 204));

        jPanel9.setBackground(new java.awt.Color(0, 153, 153));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cập Nhật Tài Khoản", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 18))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("Tài Khoản:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("Loại Tài Khoản:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("SDT:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setText("Email:");

        jCheckBox1.setBackground(new java.awt.Color(0, 153, 153));
        jCheckBox1.setText("User ");

        jCheckBox2.setBackground(new java.awt.Color(0, 153, 153));
        jCheckBox2.setText("Admin");

        jButton1.setText("Cập Nhật");

        jButton2.setText("Thoát");
        jButton2.setMinimumSize(new java.awt.Dimension(541, 450));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8))
                        .addGap(69, 69, 69)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addGap(29, 29, 29)
                                .addComponent(jCheckBox2))
                            .addComponent(textField2, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(textField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(334, 334, 334)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2))
                .addGap(26, 26, 26)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(textField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(textField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jPanel11.setBackground(new java.awt.Color(0, 153, 204));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel9.setText("Quản Lý Tài Khoản");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel9)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jdlCapNhatTaiKhoanLayout = new javax.swing.GroupLayout(jdlCapNhatTaiKhoan.getContentPane());
        jdlCapNhatTaiKhoan.getContentPane().setLayout(jdlCapNhatTaiKhoanLayout);
        jdlCapNhatTaiKhoanLayout.setHorizontalGroup(
            jdlCapNhatTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jdlCapNhatTaiKhoanLayout.setVerticalGroup(
            jdlCapNhatTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jdlChiTietTaiKhoan.setMinimumSize(new java.awt.Dimension(450, 450));

        jPanel12.setBackground(new java.awt.Color(0, 153, 204));

        jPanel13.setBackground(new java.awt.Color(0, 153, 153));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi Tiết Tài Khoản", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 18))); // NOI18N

        jLabel10.setText("Tài Khoản:");

        jLabel11.setText("SDT:");

        jLabel12.setText("Email:");

        jLabel13.setText("Loại Tài Khoản:");

        jButton3.setText("Thoát");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel10)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel13))
                        .addGap(66, 66, 66)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textField7, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(textField5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textField4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textField6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(189, 189, 189))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textField4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10))
                .addGap(23, 23, 23)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(textField5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(textField7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textField6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12))
                .addGap(47, 47, 47)
                .addComponent(jButton3)
                .addGap(32, 32, 32))
        );

        jPanel14.setBackground(new java.awt.Color(0, 153, 204));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel14.setText("Quản Lý Tài Khoản");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(jLabel14)
                .addContainerGap(114, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jdlChiTietTaiKhoanLayout = new javax.swing.GroupLayout(jdlChiTietTaiKhoan.getContentPane());
        jdlChiTietTaiKhoan.getContentPane().setLayout(jdlChiTietTaiKhoanLayout);
        jdlChiTietTaiKhoanLayout.setHorizontalGroup(
            jdlChiTietTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jdlChiTietTaiKhoanLayout.setVerticalGroup(
            jdlChiTietTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lí thiết bị UTE - Trang chủ");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFont(new java.awt.Font("VNtimes New Roman", 0, 10)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setToolTipText("");

        jTabbedPane1.setBackground(java.awt.Color.darkGray);
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFocusable(false);
        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTabbedPane1.setOpaque(true);

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));

        jButton1_41.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton1_41.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_add_to_queue_black_24dp.png")); // NOI18N
        jButton1_41.setText("Nhập thiết bị");
        jButton1_41.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButton1_41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_41ActionPerformed(evt);
            }
        });

        jtableDevice41.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtableDevice41.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jtableDevice41.setFocusTraversalPolicyProvider(true);
        jtableDevice41.setRowHeight(30);
        jtableDevice41.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(jtableDevice41);

        jPanel33.setBackground(new java.awt.Color(204, 204, 204));
        jPanel33.setLayout(new java.awt.GridLayout(2, 3, 20, 20));

        jPanel1.setBackground(new java.awt.Color(0, 123, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(255, 255, 255)));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TỔNG SỐ THIẾT BỊ:");

        jLabelTongThietBi41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTongThietBi41.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTongThietBi41.setText("20");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                .addComponent(jLabelTongThietBi41)
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTongThietBi41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(38, 38, 38))
        );

        jPanel33.add(jPanel1);

        jPanel15.setBackground(new java.awt.Color(40, 167, 69));
        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(255, 255, 255)));
        jPanel15.setForeground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("THIẾT BỊ ĐANG SỬ DỤNG:");

        jLabelTBDangSD41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTBDangSD41.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTBDangSD41.setText("10");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addGap(77, 77, 77)
                .addComponent(jLabelTBDangSD41)
                .addGap(25, 25, 25))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTBDangSD41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36))
        );

        jPanel33.add(jPanel15);

        jPanel4.setBackground(new java.awt.Color(23, 162, 184));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("THIẾT BỊ SẴN SÀNG:");

        jLabelTBSS41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTBSS41.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTBSS41.setText("10");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addGap(73, 73, 73)
                .addComponent(jLabelTBSS41)
                .addGap(27, 27, 27))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTBSS41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(37, 37, 37))
        );

        jPanel33.add(jPanel4);

        jPanel16.setBackground(new java.awt.Color(255, 193, 7));
        jPanel16.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(255, 255, 255)));
        jPanel16.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("THIẾT BỊ CẦN THANH LÍ:");

        jLabelTBCanTL41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTBCanTL41.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTBCanTL41.setText("8");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(114, 114, 114)
                .addComponent(jLabelTBCanTL41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(29, 29, 29))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTBCanTL41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(33, 33, 33))
        );

        jPanel33.add(jPanel16);

        jPanel18.setBackground(new java.awt.Color(220, 53, 69));
        jPanel18.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(255, 255, 255)));
        jPanel18.setForeground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("THIẾT BỊ HƯ HỎNG:");

        jLabelTBHuHong41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTBHuHong41.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTBHuHong41.setText("2");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addGap(127, 127, 127)
                .addComponent(jLabelTBHuHong41)
                .addGap(29, 29, 29))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTBHuHong41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );

        jPanel33.add(jPanel18);

        jPanel17.setBackground(new java.awt.Color(52, 58, 64));
        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("THIẾT BỊ CẦN QUÁ HẠN:");

        jLabelTBQuaHan41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTBQuaHan41.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTBQuaHan41.setText("8");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(113, 113, 113)
                .addComponent(jLabelTBQuaHan41)
                .addGap(30, 30, 30))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTBQuaHan41))
                .addGap(38, 38, 38))
        );

        jPanel33.add(jPanel17);

        jButton2_41.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton2_41.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_sync_black_24dp.png")); // NOI18N
        jButton2_41.setText("Làm mới");
        jButton2_41.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButton2_41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2_41ActionPerformed(evt);
            }
        });

        jComboBoxCate41.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jComboBoxCate41.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxCate41.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxCate41ItemStateChanged(evt);
            }
        });

        jComboBoxTrangThai41.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jComboBoxTrangThai41.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxTrangThai41.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTrangThai41ItemStateChanged(evt);
            }
        });

        jTableRoom41.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTableRoom41.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableRoom41.setRowHeight(30);
        jTableRoom41.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jTableRoom41);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("THỐNG KÊ THIẾT BỊ TỪNG PHÒNG");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_filter_alt_black_24dp.png")); // NOI18N
        jLabel2.setText("BỘ LỌC");

        jTextFieldSearch41.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearch41KeyReleased1(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(0, 123, 255));
        jButton8.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_search_white_24dp.png")); // NOI18N

        jButton41.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton41.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_delete_black_24dp.png")); // NOI18N
        jButton41.setText("Xóa");
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_edit_note_black_24dp.png")); // NOI18N
        jButton10.setText("Sửa");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 944, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jButton1_41, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2_41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton41))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldSearch41, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxCate41, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxTrangThai41, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jLabel1))))
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 1321, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBoxCate41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldSearch41, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxTrangThai41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1_41)
                            .addComponent(jButton2_41)
                            .addComponent(jButton41)
                            .addComponent(jButton10)))
                    .addComponent(jScrollPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản Lý Thiết Bị", new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_devices_black_24dp.png"), jPanel6); // NOI18N

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        jTablePhong_41.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTablePhong_41.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTablePhong_41.setRowHeight(30);
        jTablePhong_41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePhong_41MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablePhong_41);

        jTableDeviceRoom41.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTableDeviceRoom41.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableDeviceRoom41.setRowHeight(30);
        jScrollPane3.setViewportView(jTableDeviceRoom41);

        jButton7_41.setBackground(new java.awt.Color(220, 53, 69));
        jButton7_41.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton7_41.setForeground(new java.awt.Color(255, 255, 255));
        jButton7_41.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_delete_outline_white_24dp.png")); // NOI18N
        jButton7_41.setText("Xóa phòng");
        jButton7_41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7_41ActionPerformed(evt);
            }
        });

        jButton11_41.setBackground(new java.awt.Color(255, 193, 7));
        jButton11_41.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton11_41.setForeground(new java.awt.Color(255, 255, 255));
        jButton11_41.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_file_download_white_24dp.png")); // NOI18N
        jButton11_41.setText("Chuyển về kho");
        jButton11_41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11_41ActionPerformed(evt);
            }
        });

        jTablePhongKho41.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTablePhongKho41.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTablePhongKho41.setRowHeight(30);
        jTablePhongKho41.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(jTablePhongKho41);

        jButton12_41.setBackground(new java.awt.Color(0, 123, 255));
        jButton12_41.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton12_41.setForeground(new java.awt.Color(255, 255, 255));
        jButton12_41.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_add_circle_white_24dp.png")); // NOI18N
        jButton12_41.setText("Thêm phòng");
        jButton12_41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12_41ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 123, 255));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("DANH SÁCH PHÒNG");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(183, 183, 183))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        jPanel8.setBackground(new java.awt.Color(40, 167, 69));

        jLabelPhong41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelPhong41.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhong41.setText("?");

        jLabelPhong1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelPhong1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhong1.setText("Mã phòng:");

        jLabelTenPhong41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTenPhong41.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTenPhong41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTenPhong41.setText("Tên phòng:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabelPhong1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPhong41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTenPhong41, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPhong41)
                    .addComponent(jLabelPhong1)
                    .addComponent(jLabelTenPhong41))
                .addContainerGap())
        );

        jPanel19.setBackground(new java.awt.Color(255, 193, 7));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("KHO");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(634, 634, 634)
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(639, 639, 639))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        jButton13_41.setBackground(new java.awt.Color(40, 167, 69));
        jButton13_41.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton13_41.setForeground(new java.awt.Color(255, 255, 255));
        jButton13_41.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_file_upload_white_24dp.png")); // NOI18N
        jButton13_41.setText("Chuyển đến phòng đã chọn");
        jButton13_41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13_41ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 3, 15)); // NOI18N
        jLabel19.setText("(Chọn thiết bị để di chuyển)");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 3, 15)); // NOI18N
        jLabel22.setText("(Chọn phòng để xem, xóa)");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jButton12_41, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7_41, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel22)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton11_41, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton13_41, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3))))
                .addGap(35, 35, 35))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12_41)
                    .addComponent(jButton7_41)
                    .addComponent(jButton11_41)
                    .addComponent(jButton13_41)
                    .addComponent(jLabel19)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jTabbedPane1.addTab("Quản Lý Phòng", new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_meeting_room_black_24dp.png"), jPanel5); // NOI18N

        jPanel7.setBackground(new java.awt.Color(0, 153, 204));

        jPanel41.setBackground(java.awt.Color.lightGray);

        jPanel50.setBackground(new java.awt.Color(204, 204, 204));

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel45.setText("Tên nhân viên:");

        textFieldTenNV.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textFieldTenNV.setEnabled(false);
        textFieldTenNV.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButtonLuuTK.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonLuuTK.setText("Lưu");
        jButtonLuuTK.setEnabled(false);
        jButtonLuuTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLuuTKActionPerformed(evt);
            }
        });

        jButton25.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton25.setText("Chỉnh sửa");
        jButton25.setMinimumSize(new java.awt.Dimension(541, 450));
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel49.setText("Số điện thoại:");

        textFieldSDT.setEnabled(false);
        textFieldSDT.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel59.setText("Địa chỉ:");

        textFieldDiaChi.setEnabled(false);
        textFieldDiaChi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel50.setText("Ngày sinh:");

        textFieldNgaySinh.setEnabled(false);
        textFieldNgaySinh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel23.setText("THÔNG TIN TÀI KHOẢN");

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setText("Đổi mật khẩu");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(369, 369, 369)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45)
                    .addComponent(jLabel49)
                    .addComponent(jLabel59)
                    .addComponent(jLabel50))
                .addGap(62, 62, 62)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGap(0, 57, Short.MAX_VALUE)
                        .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jButtonLuuTK, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80))
                    .addComponent(textFieldDiaChi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldSDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldTenNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(406, 406, 406))
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(510, 510, 510)
                .addComponent(jLabel23)
                .addGap(53, 53, 53)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jButton4))
                .addGap(25, 25, 25)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel45)
                    .addComponent(textFieldTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel49)
                    .addComponent(textFieldSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel50)
                    .addComponent(textFieldNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel59)
                    .addComponent(textFieldDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLuuTK)
                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(309, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Tài Khoản", new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_person_black_24dp.png"), jPanel7); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1385, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1_41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_41ActionPerformed
        new addThietBiJFrame(taikhoan).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1_41ActionPerformed

    private void jButton2_41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2_41ActionPerformed
        defaultTableModel.setRowCount(0);
        setTableData(deviceService.getAllthietbi());
        jComboBoxCate41.setSelectedIndex(0);
        jComboBoxTrangThai41.setSelectedIndex(0);
        jTextFieldSearch41.setText("");
    }//GEN-LAST:event_jButton2_41ActionPerformed

    private void jComboBoxCate41ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxCate41ItemStateChanged
        // TODO add your handling code here:
        int tt = jComboBoxTrangThai41.getSelectedIndex();
        int id_loai = jComboBoxCate41.getSelectedIndex();

        DefaultTableModel defaultTableModel = new DefaultTableModel();

        if (tt == 0 && id_loai == 0) {
            jtableDevice41.setModel(defaultTableModel);
            defaultTableModel.addColumn("Mã thiết bị");
            defaultTableModel.addColumn("Tên thiết bị");
            defaultTableModel.addColumn("Số lượng");
            defaultTableModel.addColumn("Đơn giá");
            defaultTableModel.addColumn("Loại thiết bị");
            defaultTableModel.addColumn("Nhà sản xuất");
            //defaultTableModel.addColumn("Ngày sản xuất");
            defaultTableModel.addColumn("Phòng");
            defaultTableModel.addColumn("Trạng thái");

            List<thietBi> devices = deviceService.getAllthietbi();

            for (thietBi thietBi : devices) {
                defaultTableModel.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                    thietBi.getSoLuong(), thietBi.getGia() + " VNĐ",
                    deviceService.viewLTB(thietBi.getLoaiTB()),
                    deviceService.viewNSX(thietBi.getNSX()),
                    deviceService.viewPhong(thietBi.getId_phong()),
                    deviceService.viewTT(thietBi.getTrangThai())
                }
                );
            }
        } else {

            if (tt == 0 && id_loai != 0) {
                jtableDevice41.setModel(defaultTableModel);
                defaultTableModel.addColumn("Mã thiết bị");
                defaultTableModel.addColumn("Tên thiết bị");
                defaultTableModel.addColumn("Số lượng");
                defaultTableModel.addColumn("Đơn giá");
                defaultTableModel.addColumn("Loại thiết bị");
                defaultTableModel.addColumn("Nhà sản xuất");
                //defaultTableModel.addColumn("Ngày sản xuất");
                defaultTableModel.addColumn("Phòng");
                defaultTableModel.addColumn("Trạng thái");

                List<thietBi> devices = deviceService.getFilterLTB(id_loai);

                for (thietBi thietBi : devices) {
                    defaultTableModel.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                        thietBi.getSoLuong(), thietBi.getGia() + " VNĐ",
                        deviceService.viewLTB(thietBi.getLoaiTB()),
                        deviceService.viewNSX(thietBi.getNSX()),
                        deviceService.viewPhong(thietBi.getId_phong()),
                        deviceService.viewTT(thietBi.getTrangThai())
                    }
                    );
                }
            } else {
                if (tt != 0 && id_loai == 0) {
                    jtableDevice41.setModel(defaultTableModel);
                    defaultTableModel.addColumn("Mã thiết bị");
                    defaultTableModel.addColumn("Tên thiết bị");
                    defaultTableModel.addColumn("Số lượng");
                    defaultTableModel.addColumn("Đơn giá");
                    defaultTableModel.addColumn("Loại thiết bị");
                    defaultTableModel.addColumn("Nhà sản xuất");
                    //defaultTableModel.addColumn("Ngày sản xuất");
                    defaultTableModel.addColumn("Phòng");
                    defaultTableModel.addColumn("Trạng thái");

                    List<thietBi> devices = deviceService.getFilterTTTB(tt);

                    for (thietBi thietBi : devices) {
                        defaultTableModel.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                            thietBi.getSoLuong(), thietBi.getGia() + " VNĐ",
                            deviceService.viewLTB(thietBi.getLoaiTB()),
                            deviceService.viewNSX(thietBi.getNSX()),
                            deviceService.viewPhong(thietBi.getId_phong()),
                            deviceService.viewTT(thietBi.getTrangThai())});
                    }
                } else {
                    jtableDevice41.setModel(defaultTableModel);
                    defaultTableModel.addColumn("Mã thiết bị");
                    defaultTableModel.addColumn("Tên thiết bị");
                    defaultTableModel.addColumn("Số lượng");
                    defaultTableModel.addColumn("Đơn giá");
                    defaultTableModel.addColumn("Loại thiết bị");
                    defaultTableModel.addColumn("Nhà sản xuất");
                    //defaultTableModel.addColumn("Ngày sản xuất");
                    defaultTableModel.addColumn("Phòng");
                    defaultTableModel.addColumn("Trạng thái");

                    List<thietBi> devices = deviceService.getFilterTB(tt, id_loai);

                    for (thietBi thietBi : devices) {
                        defaultTableModel.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                            thietBi.getSoLuong(), thietBi.getGia() + " VNĐ",
                            deviceService.viewLTB(thietBi.getLoaiTB()),
                            deviceService.viewNSX(thietBi.getNSX()),
                            deviceService.viewPhong(thietBi.getId_phong()),
                            deviceService.viewTT(thietBi.getTrangThai())
                        }
                        );
                    }
                }
            }
        }
    }//GEN-LAST:event_jComboBoxCate41ItemStateChanged

    private void jComboBoxTrangThai41ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTrangThai41ItemStateChanged
        // TODO add your handling code here:
        int tt = jComboBoxTrangThai41.getSelectedIndex();
        int id_loai = jComboBoxCate41.getSelectedIndex();

        DefaultTableModel defaultTableModel = new DefaultTableModel();

        if (tt == 0 && id_loai == 0) {
            jtableDevice41.setModel(defaultTableModel);
            defaultTableModel.addColumn("Mã thiết bị");
            defaultTableModel.addColumn("Tên thiết bị");
            defaultTableModel.addColumn("Số lượng");
            defaultTableModel.addColumn("Đơn giá");
            defaultTableModel.addColumn("Loại thiết bị");
            defaultTableModel.addColumn("Nhà sản xuất");
            //defaultTableModel.addColumn("Ngày sản xuất");
            defaultTableModel.addColumn("Phòng");
            defaultTableModel.addColumn("Trạng thái");

            List<thietBi> devices = deviceService.getAllthietbi();

            for (thietBi thietBi : devices) {
                defaultTableModel.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                    thietBi.getSoLuong(), thietBi.getGia() + " vnđ",
                    deviceService.viewLTB(thietBi.getLoaiTB()),
                    deviceService.viewNSX(thietBi.getNSX()),
                    deviceService.viewPhong(thietBi.getId_phong()),
                    deviceService.viewTT(thietBi.getTrangThai())
                }
                );
            }
        } else {

            if (tt == 0 && id_loai != 0) {
                jtableDevice41.setModel(defaultTableModel);
                defaultTableModel.addColumn("Mã thiết bị");
                defaultTableModel.addColumn("Tên thiết bị");
                defaultTableModel.addColumn("Số lượng");
                defaultTableModel.addColumn("Đơn giá");
                defaultTableModel.addColumn("Loại thiết bị");
                defaultTableModel.addColumn("Nhà sản xuất");
                //defaultTableModel.addColumn("Ngày sản xuất");
                defaultTableModel.addColumn("Phòng");
                defaultTableModel.addColumn("Trạng thái");

                List<thietBi> devices = deviceService.getFilterLTB(id_loai);

                for (thietBi thietBi : devices) {
                    defaultTableModel.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                        thietBi.getSoLuong(), thietBi.getGia() + " VNĐ",
                        deviceService.viewLTB(thietBi.getLoaiTB()),
                        deviceService.viewNSX(thietBi.getNSX()),
                        deviceService.viewPhong(thietBi.getId_phong()),
                        deviceService.viewTT(thietBi.getTrangThai())
                    }
                    );
                }
            } else {
                if (tt != 0 && id_loai == 0) {
                    jtableDevice41.setModel(defaultTableModel);
                    defaultTableModel.addColumn("Mã thiết bị");
                    defaultTableModel.addColumn("Tên thiết bị");
                    defaultTableModel.addColumn("Số lượng");
                    defaultTableModel.addColumn("Đơn giá");
                    defaultTableModel.addColumn("Loại thiết bị");
                    defaultTableModel.addColumn("Nhà sản xuất");
                    //defaultTableModel.addColumn("Ngày sản xuất");
                    defaultTableModel.addColumn("Phòng");
                    defaultTableModel.addColumn("Trạng thái");

                    List<thietBi> devices = deviceService.getFilterTTTB(tt);

                    for (thietBi thietBi : devices) {
                        defaultTableModel.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                            thietBi.getSoLuong(), thietBi.getGia() + " VNĐ",
                            deviceService.viewLTB(thietBi.getLoaiTB()),
                            deviceService.viewNSX(thietBi.getNSX()),
                            deviceService.viewPhong(thietBi.getId_phong()),
                            deviceService.viewTT(thietBi.getTrangThai())
                        }
                        );
                    }
                } else {
                    jtableDevice41.setModel(defaultTableModel);
                    defaultTableModel.addColumn("Mã thiết bị");
                    defaultTableModel.addColumn("Tên thiết bị");
                    defaultTableModel.addColumn("Số lượng");
                    defaultTableModel.addColumn("Đơn giá");
                    defaultTableModel.addColumn("Loại thiết bị");
                    defaultTableModel.addColumn("Nhà sản xuất");
                    //defaultTableModel.addColumn("Ngày sản xuất");
                    defaultTableModel.addColumn("Phòng");
                    defaultTableModel.addColumn("Trạng thái");

                    List<thietBi> devices = deviceService.getFilterTB(tt, id_loai);

                    for (thietBi thietBi : devices) {
                        defaultTableModel.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                            thietBi.getSoLuong(), thietBi.getGia() + " VNĐ",
                            deviceService.viewLTB(thietBi.getLoaiTB()),
                            deviceService.viewNSX(thietBi.getNSX()),
                            deviceService.viewPhong(thietBi.getId_phong()),
                            deviceService.viewTT(thietBi.getTrangThai())
                        }
                        );
                    }
                }
            }
        }
    }//GEN-LAST:event_jComboBoxTrangThai41ItemStateChanged

    private void jTextFieldSearch41KeyReleased1(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearch41KeyReleased1
        // TODO add your handling code here:
        DefaultTableModel defaultTableModel = new DefaultTableModel();

        String kitu = jTextFieldSearch41.getText();

        if (kitu == null) {
            defaultTableModel.addColumn("Mã thiết bị");
            defaultTableModel.addColumn("Tên thiết bị");
            defaultTableModel.addColumn("Số lượng");
            defaultTableModel.addColumn("Đơn giá");
            defaultTableModel.addColumn("Loại thiết bị");
            defaultTableModel.addColumn("Nhà sản xuất");
            //defaultTableModel.addColumn("Ngày sản xuất");
            defaultTableModel.addColumn("Phòng");
            defaultTableModel.addColumn("Trạng thái");

            List<thietBi> devices = deviceService.getAllthietbi();

            for (thietBi thietBi : devices) {
                defaultTableModel.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                    thietBi.getSoLuong(), thietBi.getGia() + " VNĐ",
                    deviceService.viewLTB(thietBi.getLoaiTB()),
                    deviceService.viewNSX(thietBi.getNSX()),
                    deviceService.viewPhong(thietBi.getId_phong()),
                    deviceService.viewTT(thietBi.getTrangThai())
                }
                );
            }
        } else {

            jtableDevice41.setModel(defaultTableModel);
            defaultTableModel.addColumn("Mã thiết bị");
            defaultTableModel.addColumn("Tên thiết bị");
            defaultTableModel.addColumn("Số lượng");
            defaultTableModel.addColumn("Đơn giá");
            defaultTableModel.addColumn("Loại thiết bị");
            defaultTableModel.addColumn("Nhà sản xuất");
            //defaultTableModel.addColumn("Ngày sản xuất");
            defaultTableModel.addColumn("Phòng");
            defaultTableModel.addColumn("Trạng thái");

            List<thietBi> devices = deviceService.timTB2(kitu);
            for (thietBi thietBi : devices) {
                defaultTableModel.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                    thietBi.getSoLuong(), thietBi.getGia() + " VNĐ",
                    deviceService.viewLTB(thietBi.getLoaiTB()),
                    deviceService.viewNSX(thietBi.getNSX()),
                    deviceService.viewPhong(thietBi.getId_phong()),
                    deviceService.viewTT(thietBi.getTrangThai())
                }
                );
            }
        }
    }//GEN-LAST:event_jTextFieldSearch41KeyReleased1

    //Xóa thiết bị
    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        // TODO add your handling code here:
        int row = jtableDevice41.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(TrangChuJFrame.this, "Vui lòng chọn phòng muốn xóa!", "Thông báo", JOptionPane.ERROR_MESSAGE);

        } else {
            int cf = JOptionPane.showConfirmDialog(TrangChuJFrame.this, "Bạn có chắc chăn muốn xóa không ?");
            if (cf == JOptionPane.YES_OPTION) {
                String maphong = String.valueOf(jtableDevice41.getValueAt(row, 0));
                deviceService.deletedevice(maphong);

                defaultTableModelPhong.setRowCount(0);
                setTableData(deviceService.getAllthietbi());
            }
        }
    }//GEN-LAST:event_jButton41ActionPerformed

    //Chuyển TB về Kho
    private void jButton11_41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11_41ActionPerformed

        int row = jTableDeviceRoom41.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(TrangChuJFrame.this, "Chọn thiết bị cần đưa về KHO", "Thông báo", JOptionPane.ERROR_MESSAGE);

        } else {
            String tenTB = String.valueOf(jTableDeviceRoom41.getValueAt(row, 1));

            int cf = JOptionPane.showConfirmDialog(TrangChuJFrame.this, "Bạn có chắc chăn muốn chuyển " + tenTB + " về KHO");
            if (cf == JOptionPane.YES_OPTION) {
                String maTB = String.valueOf(jTableDeviceRoom41.getValueAt(row, 0));
                deviceService.returnKho2(maTB);
                int maPhong = (int) jTableDeviceRoom41.getValueAt(row, 6);
                setTableDeviceRoom(deviceService.getDeviceRoom(maPhong));
            }
        }
        setTableDeviceKho(deviceService.getDeviceRoom(0));
        setTableRoom(roomService.getAllroom());
    }//GEN-LAST:event_jButton11_41ActionPerformed

    //Thêm phòng
    private void jButton12_41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12_41ActionPerformed
        // TODO add your handling code here:
        new addRoom(taikhoan).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton12_41ActionPerformed

    //Chuyển thiết bị từ kho đến phòng
    private void jButton13_41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13_41ActionPerformed
        int row = jTablePhongKho41.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(TrangChuJFrame.this, "Chọn thiết bị cần chuyển", "Thông báo", JOptionPane.ERROR_MESSAGE);
        } else {
            String tenTB = String.valueOf(jTablePhongKho41.getValueAt(row, 1));

            int cf = JOptionPane.showConfirmDialog(TrangChuJFrame.this, "Bạn có muốn chuyển " + tenTB + " đến phòng");
            if (cf == JOptionPane.YES_OPTION) {
                String maTB = String.valueOf(jTablePhongKho41.getValueAt(row, 0));
                int maphong = Integer.valueOf(jLabelPhong41.getText());
                deviceService.transferPhong(maTB, maphong);
                //int maPhong = (int) jTablePhong.getValueAt(row1, 0);
                setTableDeviceRoom(deviceService.getDeviceRoom(maphong));
            }
        }
        setTableDeviceKho(deviceService.getDeviceRoom(0));
        setTableRoom(roomService.getAllroom());
    }//GEN-LAST:event_jButton13_41ActionPerformed

    //Xem TB từng phòng
    private void jTablePhong_41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePhong_41MouseClicked
        int row = jTablePhong_41.getSelectedRow();
        int maphong = (int) jTablePhong_41.getValueAt(row, 0);

        if (row == -1) {
            JOptionPane.showMessageDialog(TrangChuJFrame.this, "Chọn phòng bạn muốn xem!", "Thông báo", JOptionPane.ERROR_MESSAGE);

        } else {//ch
            setTableDeviceRoom(deviceService.getDeviceRoom(maphong));
        }
        String tenphong = String.valueOf(jTablePhong_41.getValueAt(row, 1));
        jLabelPhong41.setText("" + maphong);
        jLabelTenPhong41.setText("Tên phòng: " + tenphong);
    }//GEN-LAST:event_jTablePhong_41MouseClicked

    //Xóa phòng
    private void jButton7_41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7_41ActionPerformed
        int row = jTablePhong_41.getSelectedRow();
        int maphong = (int) jTablePhong_41.getValueAt(row, 0);
        int sl = (int) jTablePhong_41.getValueAt(row, 3);
        if (row == -1) {
            JOptionPane.showMessageDialog(TrangChuJFrame.this, "Chọn phòng bạn muốn xem!", "Thông báo", JOptionPane.ERROR_MESSAGE);

        } else {
            if (sl != 0) {
                JOptionPane.showMessageDialog(TrangChuJFrame.this, "Phòng vẫn còn thiết bị, không thể xóa ! ", "Thông báo", JOptionPane.ERROR_MESSAGE);
            } else {
                int cf = JOptionPane.showConfirmDialog(TrangChuJFrame.this, "Bạn có chắc chăn muốn xóa không ?");
                if (cf == JOptionPane.YES_OPTION) {
                    roomService.deleteRoom(maphong);
                }
            }
            setTableRoom(roomService.getAllroom());
        }
    }//GEN-LAST:event_jButton7_41ActionPerformed

    //Chỉnh sửa NV
    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton25ActionPerformed

    //Chỉnh sửa thông tin nhân viên
    private void jButtonLuuTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLuuTKActionPerformed
        // TODO add your handling code here:
        //JOptionPane.showConfirmDialog(rootPane, taikhoan);

    }//GEN-LAST:event_jButtonLuuTKActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11_41;
    private javax.swing.JButton jButton12_41;
    private javax.swing.JButton jButton13_41;
    private javax.swing.JButton jButton1_41;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton2_41;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton7_41;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButtonLuuTK;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBoxCate41;
    private javax.swing.JComboBox<String> jComboBoxTrangThai41;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelPhong1;
    private javax.swing.JLabel jLabelPhong41;
    private javax.swing.JLabel jLabelTBCanTL41;
    private javax.swing.JLabel jLabelTBDangSD41;
    private javax.swing.JLabel jLabelTBHuHong41;
    private javax.swing.JLabel jLabelTBQuaHan41;
    private javax.swing.JLabel jLabelTBSS41;
    private javax.swing.JLabel jLabelTenPhong41;
    private javax.swing.JLabel jLabelTongThietBi41;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableDeviceRoom41;
    private javax.swing.JTable jTablePhongKho41;
    private javax.swing.JTable jTablePhong_41;
    private javax.swing.JTable jTableRoom41;
    private javax.swing.JTextField jTextFieldSearch41;
    private javax.swing.JDialog jdlCapNhatTaiKhoan;
    private javax.swing.JDialog jdlChiTietTaiKhoan;
    private javax.swing.JTable jtableDevice41;
    private java.awt.TextField textField1;
    private java.awt.TextField textField2;
    private java.awt.TextField textField3;
    private java.awt.TextField textField4;
    private java.awt.TextField textField5;
    private java.awt.TextField textField6;
    private java.awt.TextField textField7;
    private java.awt.TextField textFieldDiaChi;
    private java.awt.TextField textFieldNgaySinh;
    private java.awt.TextField textFieldSDT;
    private java.awt.TextField textFieldTenNV;
    // End of variables declaration//GEN-END:variables
}
