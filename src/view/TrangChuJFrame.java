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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.room;
import model.thietBi;
import service.deviceService;
import service.roomService;

/**
 *
 * @author Lenovo
 */
public class TrangChuJFrame extends javax.swing.JFrame {

    deviceService deviceService;
    roomService roomService;

    DefaultTableModel defaultTableModel;
    DefaultTableModel defaultTableModelRoom;
    DefaultTableModel defaultTableModelPhongKho;
    DefaultTableModel defaultTableModelPhong;

    List<String> listCate = new ArrayList<>();
    List<String> listTT = new ArrayList<>();

    public TrangChuJFrame(int home) {
        initComponents();
        deviceService = new deviceService();
        roomService = new roomService();

        defaultTableModel = new DefaultTableModel();
        defaultTableModelRoom = new DefaultTableModel();
        defaultTableModelPhong = new DefaultTableModel();
        jTabbedPane1.setSelectedIndex(home);

        jtableDevice.setModel(defaultTableModel);
        defaultTableModel.addColumn("Mã thiết bị");
        defaultTableModel.addColumn("Tên thiết bị");
        defaultTableModel.addColumn("Số lượng");
        defaultTableModel.addColumn("Đơn giá");
        defaultTableModel.addColumn("Loại thiết bị");
        defaultTableModel.addColumn("Nhà sản xuất");
        //defaultTableModel.addColumn("Ngày sản xuất");
        defaultTableModel.addColumn("Phòng");
        defaultTableModel.addColumn("Trạng thái");

        jTableRoom.setModel(defaultTableModelRoom);
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
        jLabelTongThietBi.setText("" + sumTB);

        int sumTBDangSD = deviceService.sumTBDangSD();
        jLabelTBDangSD.setText("" + sumTBDangSD);

        int sumTBCanTL = deviceService.sumTBCanTL();
        jLabelTBCanTL.setText("" + sumTBCanTL);

        int sumTBHuHong = deviceService.sumTBHuHong();
        jLabelTBHuHong.setText("" + sumTBHuHong);

        int sumTBSS = deviceService.sumTBDangRanh();
        jLabelTBSS.setText("" + sumTBSS);

        int sumTBQH = deviceService.sumTBQuaHan();
        jLabelTBQuaHan.setText("" + sumTBQH);

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
        jComboBoxCate.removeAllItems();
        jComboBoxCate.addItem("Danh mục");
        for (String l : listCate) {
            jComboBoxCate.addItem(l);
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
        jComboBoxTrangThai.removeAllItems();
        jComboBoxTrangThai.addItem("Trạng thái");
        for (String tt : listTT) {
            jComboBoxTrangThai.addItem(tt);
        }

        //Danh sách phòng
        jTablePhong.setModel(defaultTableModelPhong);
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

    }

    private void setTableData(List<thietBi> devices) {

        DefaultTableModel defaultTableModel = new DefaultTableModel();
        jtableDevice.setModel(defaultTableModel);
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
        jTableDeviceRoom.setModel(defaultTableModelPhong);
        defaultTableModelPhong.addColumn("Mã thiết bị");
        defaultTableModelPhong.addColumn("Tên thiết bị");
        defaultTableModelPhong.addColumn("Số lượng");
        defaultTableModelPhong.addColumn("Loại thiết bị");
        defaultTableModelPhong.addColumn("Nhà sản xuất");
        //defaultTableModel.addColumn("Ngày sản xuất");
        defaultTableModelPhong.addColumn("Trạng thái");

        for (thietBi thietBi : devices) {
            defaultTableModelPhong.addRow(new Object[]{thietBi.getMaTB(), thietBi.getTenTB(),
                thietBi.getSoLuong(),
                deviceService.viewLTB(thietBi.getLoaiTB()),
                deviceService.viewNSX(thietBi.getNSX()),
                deviceService.viewTT(thietBi.getTrangThai())
            }
            );
        }
    }

    private void setTableDeviceKho(List<thietBi> devices) {

        DefaultTableModel defaultTableModelPhongKho = new DefaultTableModel();
        jTablePhongKho.setModel(defaultTableModelPhongKho);
        defaultTableModelPhongKho.addColumn("Mã thiết bị");
        defaultTableModelPhongKho.addColumn("Tên thiết bị");
        defaultTableModelPhongKho.addColumn("Số lượng");
        defaultTableModelPhongKho.addColumn("Số lượng");
        defaultTableModelPhongKho.addColumn("Đơn giá");
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
        jButton27 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtableDevice = new javax.swing.JTable();
        jPanel33 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabelTongThietBi = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabelTBDangSD = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabelTBSS = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabelTBCanTL = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabelTBHuHong = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabelTBQuaHan = new javax.swing.JLabel();
        jButton28 = new javax.swing.JButton();
        jComboBoxCate = new javax.swing.JComboBox<>();
        jComboBoxTrangThai = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableRoom = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePhong = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableDeviceRoom = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTablePhongKho = new javax.swing.JTable();
        jButton12 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabelPhong = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jPanel49 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        textField14 = new java.awt.TextField();
        textField15 = new java.awt.TextField();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        textField16 = new java.awt.TextField();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jLabel49 = new javax.swing.JLabel();
        textField21 = new java.awt.TextField();
        jLabel59 = new javax.swing.JLabel();
        textField22 = new java.awt.TextField();
        textField23 = new java.awt.TextField();
        jLabel60 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        textField17 = new java.awt.TextField();
        jLabel51 = new javax.swing.JLabel();
        textField18 = new java.awt.TextField();
        textField19 = new java.awt.TextField();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        textField20 = new java.awt.TextField();
        jButton6 = new javax.swing.JButton();
        jPanel55 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jPanel56 = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jPasswordField3 = new javax.swing.JPasswordField();
        jButton26 = new javax.swing.JButton();
        jPanel58 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();

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

        jButton27.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton27.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_add_to_queue_black_24dp.png")); // NOI18N
        jButton27.setText("Nhập thiết bị");
        jButton27.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jtableDevice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtableDevice.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jtableDevice.setFocusTraversalPolicyProvider(true);
        jtableDevice.setRowHeight(30);
        jtableDevice.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(jtableDevice);

        jPanel33.setBackground(new java.awt.Color(204, 204, 204));
        jPanel33.setLayout(new java.awt.GridLayout(2, 3, 20, 20));

        jPanel1.setBackground(new java.awt.Color(0, 123, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(255, 255, 255)));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TỔNG SỐ THIẾT BỊ:");

        jLabelTongThietBi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTongThietBi.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTongThietBi.setText("20");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                .addComponent(jLabelTongThietBi)
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTongThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(38, 38, 38))
        );

        jPanel33.add(jPanel1);

        jPanel15.setBackground(new java.awt.Color(40, 167, 69));
        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(255, 255, 255)));
        jPanel15.setForeground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("THIẾT BỊ ĐANG SỬ DỤNG:");

        jLabelTBDangSD.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTBDangSD.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTBDangSD.setText("10");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addGap(77, 77, 77)
                .addComponent(jLabelTBDangSD)
                .addGap(25, 25, 25))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTBDangSD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36))
        );

        jPanel33.add(jPanel15);

        jPanel4.setBackground(new java.awt.Color(23, 162, 184));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("THIẾT BỊ SẴN SÀNG:");

        jLabelTBSS.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTBSS.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTBSS.setText("10");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addGap(73, 73, 73)
                .addComponent(jLabelTBSS)
                .addGap(27, 27, 27))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTBSS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(37, 37, 37))
        );

        jPanel33.add(jPanel4);

        jPanel16.setBackground(new java.awt.Color(255, 193, 7));
        jPanel16.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(255, 255, 255)));
        jPanel16.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("THIẾT BỊ CẦN THANH LÍ:");

        jLabelTBCanTL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTBCanTL.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTBCanTL.setText("8");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(114, 114, 114)
                .addComponent(jLabelTBCanTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(29, 29, 29))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTBCanTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(33, 33, 33))
        );

        jPanel33.add(jPanel16);

        jPanel18.setBackground(new java.awt.Color(220, 53, 69));
        jPanel18.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(255, 255, 255)));
        jPanel18.setForeground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("THIẾT BỊ HƯ HỎNG:");

        jLabelTBHuHong.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTBHuHong.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTBHuHong.setText("2");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addGap(127, 127, 127)
                .addComponent(jLabelTBHuHong)
                .addGap(29, 29, 29))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTBHuHong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );

        jPanel33.add(jPanel18);

        jPanel17.setBackground(new java.awt.Color(52, 58, 64));
        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("THIẾT BỊ CẦN QUÁ HẠN:");

        jLabelTBQuaHan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTBQuaHan.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTBQuaHan.setText("8");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(113, 113, 113)
                .addComponent(jLabelTBQuaHan)
                .addGap(30, 30, 30))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTBQuaHan))
                .addGap(38, 38, 38))
        );

        jPanel33.add(jPanel17);

        jButton28.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton28.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_sync_black_24dp.png")); // NOI18N
        jButton28.setText("Làm mới");
        jButton28.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jComboBoxCate.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jComboBoxCate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxCate.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxCateItemStateChanged(evt);
            }
        });

        jComboBoxTrangThai.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jComboBoxTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxTrangThai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTrangThaiItemStateChanged(evt);
            }
        });

        jTableRoom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableRoom.setRowHeight(30);
        jTableRoom.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jTableRoom);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("THỐNG KÊ THIẾT BỊ TỪNG PHÒNG");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_filter_alt_black_24dp.png")); // NOI18N
        jLabel2.setText("BỘ LỌC");

        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyReleased1(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(0, 123, 255));
        jButton8.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_search_white_24dp.png")); // NOI18N

        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_delete_black_24dp.png")); // NOI18N
        jButton9.setText("Xóa");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
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
                                .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton9))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxCate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addComponent(jComboBoxCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton27)
                            .addComponent(jButton28)
                            .addComponent(jButton9)
                            .addComponent(jButton10)))
                    .addComponent(jScrollPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản Lý Thiết Bị", new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_devices_black_24dp.png"), jPanel6); // NOI18N

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        jTablePhong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTablePhong.setModel(new javax.swing.table.DefaultTableModel(
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
        jTablePhong.setRowHeight(30);
        jScrollPane1.setViewportView(jTablePhong);

        jTableDeviceRoom.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTableDeviceRoom.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableDeviceRoom.setRowHeight(30);
        jScrollPane3.setViewportView(jTableDeviceRoom);

        jButton4.setBackground(new java.awt.Color(40, 167, 69));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_visibility_white_24dp.png")); // NOI18N
        jButton4.setText("Xem thiết bị");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(220, 53, 69));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_delete_outline_white_24dp.png")); // NOI18N
        jButton7.setText("Xóa phòng");

        jButton11.setBackground(new java.awt.Color(255, 193, 7));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_file_download_white_24dp.png")); // NOI18N
        jButton11.setText("Chuyển về kho");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jTablePhongKho.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTablePhongKho.setModel(new javax.swing.table.DefaultTableModel(
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
        jTablePhongKho.setRowHeight(30);
        jScrollPane4.setViewportView(jTablePhongKho);

        jButton12.setBackground(new java.awt.Color(0, 123, 255));
        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_add_circle_white_24dp.png")); // NOI18N
        jButton12.setText("Thêm phòng");

        jPanel2.setBackground(new java.awt.Color(0, 123, 255));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("DANH SÁCH PHÒNG");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(175, Short.MAX_VALUE)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(174, 174, 174))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(40, 167, 69));

        jLabelPhong.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelPhong.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhong.setText("CHỌN PHÒNG ĐỂ XEM THIẾT BỊ");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(241, 241, 241)
                .addComponent(jLabelPhong)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelPhong)
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
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButton12))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jButton11)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane3)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                    .addComponent(jButton12)
                    .addComponent(jButton7)
                    .addComponent(jButton4)
                    .addComponent(jButton11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jTabbedPane1.addTab("Quản Lý Phòng", new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Desktop\\QuanLiThietBiTrongTruong_1_1_1\\src\\img\\outline_meeting_room_black_24dp.png"), jPanel5); // NOI18N

        jPanel7.setBackground(new java.awt.Color(0, 153, 204));

        jPanel41.setBackground(java.awt.Color.lightGray);

        jTabbedPane7.setBackground(java.awt.Color.gray);
        jTabbedPane7.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane7.setAutoscrolls(true);
        jTabbedPane7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTabbedPane7.setVerifyInputWhenFocusTarget(false);

        jPanel49.setBackground(java.awt.Color.white);

        jPanel50.setBackground(java.awt.Color.lightGray);

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel45.setText("Tài Khoản:");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel47.setText("SDT:");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel48.setText("Email:");

        jButton24.setText("Cập Nhật");

        jButton25.setText("Thoát");
        jButton25.setMinimumSize(new java.awt.Dimension(541, 450));
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel49.setText("Tài Khoản:");

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel59.setText("Tài Khoản:");

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel60.setText("Tài Khoản:");

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45)
                    .addComponent(jLabel47)
                    .addComponent(jLabel48)
                    .addComponent(jLabel49)
                    .addComponent(jLabel59)
                    .addComponent(jLabel60))
                .addGap(92, 92, 92)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addComponent(jButton24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 80, Short.MAX_VALUE))
                    .addComponent(textField15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textField14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textField16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textField21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textField22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textField23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(666, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel45)
                    .addComponent(textField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel49)
                    .addComponent(textField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel59)
                    .addComponent(textField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel60)
                    .addComponent(textField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel47)
                    .addComponent(textField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48))
                .addGap(54, 54, 54)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton24)
                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(353, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane7.addTab("Profile", jPanel49);

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1187, Short.MAX_VALUE)
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 789, Short.MAX_VALUE)
        );

        jTabbedPane7.addTab("Xem Chi Tiết", jPanel46);

        jPanel53.setBackground(new java.awt.Color(0, 153, 204));

        jPanel54.setBackground(new java.awt.Color(0, 153, 153));
        jPanel54.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi Tiết Tài Khoản", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 18))); // NOI18N

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel50.setText("Tài Khoản:");

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel51.setText("SDT:");

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel52.setText("Email:");

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel53.setText("Loại Tài Khoản:");

        jButton6.setText("Thoát");

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel54Layout.createSequentialGroup()
                .addContainerGap(647, Short.MAX_VALUE)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel54Layout.createSequentialGroup()
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel50))
                            .addComponent(jLabel51)
                            .addComponent(jLabel52))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textField20, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(textField18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textField17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textField19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(189, 189, 189))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel54Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addGap(5, 5, 5))
                    .addComponent(textField17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textField18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textField20, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textField19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addComponent(jButton6)
                .addGap(32, 32, 32))
        );

        jPanel55.setBackground(new java.awt.Color(0, 153, 204));

        jLabel54.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel54.setText("Quản Lý Tài Khoản");

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                .addContainerGap(731, Short.MAX_VALUE)
                .addComponent(jLabel54)
                .addGap(250, 250, 250))
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel54)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel53Layout.createSequentialGroup()
                .addComponent(jPanel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane7.addTab("Cập Nhật Thông Tin", jPanel52);

        jPanel56.setBackground(new java.awt.Color(0, 153, 204));

        jPanel57.setBackground(new java.awt.Color(0, 153, 153));
        jPanel57.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đổi Mật Khẩu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 18))); // NOI18N
        jPanel57.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel55.setText("Nhập Mật Khẩu Cũ:");

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel56.setText("Nhập Mật Khẩu Mới:");

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel57.setText("Nhập Lại Mật Khẩu Mới:");

        jButton26.setText("Cập Nhật");

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addGap(163, 163, 163)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55)
                    .addComponent(jLabel56)
                    .addComponent(jLabel57))
                .addGap(25, 25, 25)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPasswordField3, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(jPasswordField1)
                    .addComponent(jPasswordField2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel57Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton26)
                .addGap(134, 134, 134))
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(jButton26)
                .addGap(61, 61, 61))
        );

        jPanel58.setBackground(new java.awt.Color(0, 153, 204));

        jLabel58.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel58.setText("Quản Lý Tài Khoản ");

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel58Layout.createSequentialGroup()
                .addContainerGap(727, Short.MAX_VALUE)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(184, 184, 184))
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel56Layout.createSequentialGroup()
                .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane7.addTab("Đổi Mật Khẩu", jPanel56);

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane7))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addComponent(jTabbedPane7)
                .addContainerGap())
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

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
        new loginJFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        new addThietBiJFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        defaultTableModel.setRowCount(0);
        setTableData(deviceService.getAllthietbi());
        jComboBoxCate.setSelectedIndex(0);
        jComboBoxTrangThai.setSelectedIndex(0);
        jTextFieldSearch.setText("");
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jComboBoxCateItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxCateItemStateChanged
        // TODO add your handling code here:
        int tt = jComboBoxTrangThai.getSelectedIndex();
        int id_loai = jComboBoxCate.getSelectedIndex();

        DefaultTableModel defaultTableModel = new DefaultTableModel();

        if (tt == 0 && id_loai == 0) {
            jtableDevice.setModel(defaultTableModel);
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
                jtableDevice.setModel(defaultTableModel);
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
                    jtableDevice.setModel(defaultTableModel);
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
                    jtableDevice.setModel(defaultTableModel);
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
    }//GEN-LAST:event_jComboBoxCateItemStateChanged

    private void jComboBoxTrangThaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTrangThaiItemStateChanged
        // TODO add your handling code here:
        int tt = jComboBoxTrangThai.getSelectedIndex();
        int id_loai = jComboBoxCate.getSelectedIndex();

        DefaultTableModel defaultTableModel = new DefaultTableModel();

        if (tt == 0 && id_loai == 0) {
            jtableDevice.setModel(defaultTableModel);
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
                jtableDevice.setModel(defaultTableModel);
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
                    jtableDevice.setModel(defaultTableModel);
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
                    jtableDevice.setModel(defaultTableModel);
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
    }//GEN-LAST:event_jComboBoxTrangThaiItemStateChanged

    private void jTextFieldSearchKeyReleased1(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased1
        // TODO add your handling code here:
        DefaultTableModel defaultTableModel = new DefaultTableModel();

        String kitu = jTextFieldSearch.getText();

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

            jtableDevice.setModel(defaultTableModel);
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
    }//GEN-LAST:event_jTextFieldSearchKeyReleased1

    //Xóa thiết bị
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        int row = jtableDevice.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(TrangChuJFrame.this, "Vui lòng chọn phòng muốn xóa!", "Thông báo", JOptionPane.ERROR_MESSAGE);

        } else {
            int cf = JOptionPane.showConfirmDialog(TrangChuJFrame.this, "Bạn có chắc chăn muốn xóa không ?");
            if (cf == JOptionPane.YES_OPTION) {
                String maphong = String.valueOf(jtableDevice.getValueAt(row, 0));
                deviceService.deletedevice(maphong);

                defaultTableModelPhong.setRowCount(0);
                setTableData(deviceService.getAllthietbi());
            }
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    //Xem ds thiết bị từng phòng
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int row = jTablePhong.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(TrangChuJFrame.this, "Chọn phòng bạn muốn xem!", "Thông báo", JOptionPane.ERROR_MESSAGE);

        } else {
            int maphong = (int) jTablePhong.getValueAt(row, 0);

            setTableDeviceRoom(deviceService.getDeviceRoom(maphong));
        }
        String tenphong = String.valueOf(jTablePhong.getValueAt(row, 1));
        jLabelPhong.setText(tenphong);
    }//GEN-LAST:event_jButton4ActionPerformed

    //Chuyển TB về Kho
    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed

        int row = jTableDeviceRoom.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(TrangChuJFrame.this, "Chọn thiết bị cần đưa về KHO", "Thông báo", JOptionPane.ERROR_MESSAGE);

        } else {
            String tenTB = String.valueOf(jTableDeviceRoom.getValueAt(row, 1));

            int cf = JOptionPane.showConfirmDialog(TrangChuJFrame.this, "Bạn có chắc chăn muốn chuyển " + tenTB + " về KHO");
            if (cf == JOptionPane.YES_OPTION) {
                String maTB = String.valueOf(jTableDeviceRoom.getValueAt(row, 0));
                deviceService.returnKho(maTB);
            }
        }
        setTableDeviceKho(deviceService.getDeviceRoom(0));
    }//GEN-LAST:event_jButton11ActionPerformed

    public void TableDanhSachTaiKhoan() {
        defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("id");
        defaultTableModel.addColumn("Tài Khoản");
        defaultTableModel.addColumn("Mật Khẩu");
        defaultTableModel.addColumn("Loại Tài Khoản");
        defaultTableModel.addColumn("Số Điện Thoại");
        defaultTableModel.addColumn("Email");
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TrangChuJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrangChuJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrangChuJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrangChuJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TrangChuJFrame(0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBoxCate;
    private javax.swing.JComboBox<String> jComboBoxTrangThai;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelPhong;
    private javax.swing.JLabel jLabelTBCanTL;
    private javax.swing.JLabel jLabelTBDangSD;
    private javax.swing.JLabel jLabelTBHuHong;
    private javax.swing.JLabel jLabelTBQuaHan;
    private javax.swing.JLabel jLabelTBSS;
    private javax.swing.JLabel jLabelTongThietBi;
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
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTable jTableDeviceRoom;
    private javax.swing.JTable jTablePhong;
    private javax.swing.JTable jTablePhongKho;
    private javax.swing.JTable jTableRoom;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JDialog jdlCapNhatTaiKhoan;
    private javax.swing.JDialog jdlChiTietTaiKhoan;
    private javax.swing.JTable jtableDevice;
    private java.awt.TextField textField1;
    private java.awt.TextField textField14;
    private java.awt.TextField textField15;
    private java.awt.TextField textField16;
    private java.awt.TextField textField17;
    private java.awt.TextField textField18;
    private java.awt.TextField textField19;
    private java.awt.TextField textField2;
    private java.awt.TextField textField20;
    private java.awt.TextField textField21;
    private java.awt.TextField textField22;
    private java.awt.TextField textField23;
    private java.awt.TextField textField3;
    private java.awt.TextField textField4;
    private java.awt.TextField textField5;
    private java.awt.TextField textField6;
    private java.awt.TextField textField7;
    // End of variables declaration//GEN-END:variables
}
