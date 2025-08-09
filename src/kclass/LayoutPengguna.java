/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package kclass;


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Aji Putra Pamungkas
 */
public class LayoutPengguna extends javax.swing.JFrame {

    /**
     * Creates new form LayoutPengguna
     */
    JLabel[] arrLabel, arrUserLb, arrPointLb;
    JButton[] arrButton;
    JTextField[] arrTF;
    Connection conn;
    String idSession;
    PreparedStatement ps;
    public static LayoutPengguna instanceLP;


    public LayoutPengguna(String idSession, String username) {
        initComponents();
        initDB();
        initSession(idSession, username);
        instanceLP = this;
    }
    
    private void initDB() {
        String url = "jdbc:mysql://localhost:3306/kclassDB?useSSL=true";
        String username = "root";
        String password = "adminkurukuru_2";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            ps = conn.prepareStatement("SELECT score FROM kScore WHERE idUser = ? AND idCSV = ?");//untuk ambil nilai
            System.out.println("Koneksi sukses Layout Pengguna");
        } catch (Exception e){
            System.out.println("Exception: " + e);
        }
    }
    
    private void initSession(String idSession, String username){
        namaUser.setText(username);
        this.idSession = idSession;   
        this.setTitle("K Class - " + username);
        
        int iconWidth = 100;
        int iconHeight = 100;
        String pathSrc = "/images/Profile/" + Character.toUpperCase(username.charAt(0)) + ".png";
        Image originalIcon = (new ImageIcon(getClass().getResource(pathSrc))).getImage();
        Image scaledImg = originalIcon.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        profilePicture.setIcon(new ImageIcon(scaledImg));
        
        refreshList();  
        
        this.setLocationRelativeTo(null);
        this.setSize(800, 500);
        this.setResizable(false);
        this.setVisible(true);
        JOptionPane.showConfirmDialog(null, "Halo " + username + ". Selamat datang di K Class :)", 
            "Pesan hangat", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void refreshList(){
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM kCSV");
            
            ps.setString(1,idSession);
            int totalSkor = 0;
            int totalPengerjaan = 0;
            int totalKuis = 0;
            int i = 0;
            while(rs.next()){
                int idCSV = rs.getInt("idCSV");
                String nama = rs.getString("nama");
                arrLabel[i].setText("Kuis " + idCSV + " : " + nama);
                arrButton[i].setEnabled(true);
                
                //ps = SELECT score FROM kScore WHERE idUser = ? AND idCSV = ?
                ps.setString(2,String.valueOf(idCSV));  //ps.setString(1,idSession) 
                ResultSet rsLoop = ps.executeQuery();   //SELECT score FROM kScore WHERE idUser = idSession AND idCSV = idCSV
                if(rsLoop.next()){
                    arrButton[i].setText("Reattempt");
                    arrTF[i].setText("Nilai: " + rsLoop.getInt("score"));
                    totalSkor += rsLoop.getInt("score");
                    totalPengerjaan++;
                }
                else{
                    arrButton[i].setText("Attempt");
                    arrTF[i].setText("Nilai: - ");
                }
                totalKuis++;
                rsLoop.close();
                
                i++;
            }
            for(int j = i; j <10; j++){
                arrLabel[j].setText("Kuis X : - ");
                arrButton[j].setEnabled(false);
                arrTF[j].setText("Nilai: - ");
            }
            rs.close();
            
            if(totalPengerjaan == 0){
                nilaiRata.setForeground(Color.RED);
                nilaiRata.setText("0.0");
            }else{
                Double rata = totalSkor*1.0/totalPengerjaan;
                if (rata > 80) nilaiRata.setForeground(Color.GREEN);
                else if(rata > 45) nilaiRata.setForeground(Color.YELLOW);
                else nilaiRata.setForeground(Color.RED);
                String formatRata = String.format("%.2f", rata);
                nilaiRata.setText(formatRata);
            }
            jProgressBar1.setValue(totalPengerjaan*100/totalKuis);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(LayoutPengguna.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void handleFromKuis(String score, String idCSV){
        try {
            ps.setString(1,idSession); ps.setString(2,idCSV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){ //ada
                int skorDB = rs.getInt("score");
                if(Integer.parseInt(score) > skorDB){
                    PreparedStatement ps2 = conn.prepareStatement("UPDATE kScore SET score = ? WHERE idUser = ? AND idCSV = ?");
                    ps2.setString(1, score); ps2.setString(2, idSession); ps2.setString(3, idCSV);
                    ps2.executeUpdate();
                    ps2.close();
                }       
            } else{           //tidak ada 
                PreparedStatement ps2 = conn.prepareStatement("INSERT INTO kScore VALUES (?,?,?)");
                ps2.setString(1, idSession); ps2.setString(2, idCSV); ps2.setString(3, score);
                ps2.executeUpdate();
                ps2.close();
            }
            rs.close();
            refreshList();
        } catch (SQLException ex) {
            Logger.getLogger(LayoutPengguna.class.getName()).log(Level.SEVERE, null, ex);
        }
    };
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupOpsi = new javax.swing.ButtonGroup();
        grupPGIsian = new javax.swing.ButtonGroup();
        StatusUser = new javax.swing.JPanel();
        profilePicture = new javax.swing.JLabel();
        profilePicture.setHorizontalAlignment(JLabel.CENTER);
        namaUser = new javax.swing.JLabel();
        namaUser.setHorizontalAlignment(JLabel.CENTER);
        labelStatus = new javax.swing.JLabel();
        labelRata = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        buttonLogout = new javax.swing.JButton();
        nilaiRata = new javax.swing.JLabel();
        labelRata1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        buttonLeaderboard = new javax.swing.JButton();
        buttonLeaderboard.setAlignmentX(Component.CENTER_ALIGNMENT);
        parentPanel = new javax.swing.JPanel();
        dashboardTugas = new javax.swing.JPanel();
        labelList = new javax.swing.JLabel();
        labelK0 = new javax.swing.JLabel();
        labelK1 = new javax.swing.JLabel();
        labelK2 = new javax.swing.JLabel();
        labelK3 = new javax.swing.JLabel();
        labelK4 = new javax.swing.JLabel();
        labelK5 = new javax.swing.JLabel();
        labelK6 = new javax.swing.JLabel();
        labelK7 = new javax.swing.JLabel();
        labelK8 = new javax.swing.JLabel();
        labelK9 = new javax.swing.JLabel();
        arrLabel = new JLabel[10];
        arrLabel[0] = labelK0;
        arrLabel[1] = labelK1;
        arrLabel[2] = labelK2;
        arrLabel[3] = labelK3;
        arrLabel[4] = labelK4;
        arrLabel[5] = labelK5;
        arrLabel[6] = labelK6;
        arrLabel[7] = labelK7;
        arrLabel[8] = labelK8;
        arrLabel[9] = labelK9;
        attemptK0 = new javax.swing.JButton();
        nilaiK0 = new javax.swing.JTextField();
        attemptK1 = new javax.swing.JButton();
        attemptK2 = new javax.swing.JButton();
        attemptK3 = new javax.swing.JButton();
        attemptK4 = new javax.swing.JButton();
        attemptK5 = new javax.swing.JButton();
        attemptK6 = new javax.swing.JButton();
        attemptK7 = new javax.swing.JButton();
        attemptK8 = new javax.swing.JButton();
        attemptK9 = new javax.swing.JButton();
        arrButton = new JButton[10];
        arrButton[0] = attemptK0;
        arrButton[1] = attemptK1;
        arrButton[2] = attemptK2;
        arrButton[3] = attemptK3;
        arrButton[4] = attemptK4;
        arrButton[5] = attemptK5;
        arrButton[6] = attemptK6;
        arrButton[7] = attemptK7;
        arrButton[8] = attemptK8;
        arrButton[9] = attemptK9;
        nilaiK1 = new javax.swing.JTextField();
        nilaiK2 = new javax.swing.JTextField();
        nilaiK3 = new javax.swing.JTextField();
        nilaiK4 = new javax.swing.JTextField();
        nilaiK5 = new javax.swing.JTextField();
        nilaiK6 = new javax.swing.JTextField();
        nilaiK7 = new javax.swing.JTextField();
        nilaiK8 = new javax.swing.JTextField();
        nilaiK9 = new javax.swing.JTextField();
        arrTF = new JTextField[10];
        arrTF[0] = nilaiK0;
        arrTF[1] = nilaiK1;
        arrTF[2] = nilaiK2;
        arrTF[3] = nilaiK3;
        arrTF[4] = nilaiK4;
        arrTF[5] = nilaiK5;
        arrTF[6] = nilaiK6;
        arrTF[7] = nilaiK7;
        arrTF[8] = nilaiK8;
        arrTF[9] = nilaiK9;
        panelLeaderboard = new javax.swing.JPanel();
        labelLb = new javax.swing.JLabel();
        comboBoxLb = new javax.swing.JComboBox<>();
        userLb0 = new javax.swing.JLabel();
        userLb1 = new javax.swing.JLabel();
        userLb2 = new javax.swing.JLabel();
        userLb3 = new javax.swing.JLabel();
        userLb4 = new javax.swing.JLabel();
        userLb5 = new javax.swing.JLabel();
        userLb6 = new javax.swing.JLabel();
        userLb7 = new javax.swing.JLabel();
        userLb8 = new javax.swing.JLabel();
        userLb9 = new javax.swing.JLabel();
        arrUserLb = new JLabel[10];
        arrUserLb[0] = userLb0;
        arrUserLb[1] = userLb1;
        arrUserLb[2] = userLb2;
        arrUserLb[3] = userLb3;
        arrUserLb[4] = userLb4;
        arrUserLb[5] = userLb5;
        arrUserLb[6] = userLb6;
        arrUserLb[7] = userLb7;
        arrUserLb[8] = userLb8;
        arrUserLb[9] = userLb9;
        userLbTitle = new javax.swing.JLabel();
        pointLbTitle = new javax.swing.JLabel();
        pointLb0 = new javax.swing.JLabel();
        pointLb1 = new javax.swing.JLabel();
        pointLb2 = new javax.swing.JLabel();
        pointLb3 = new javax.swing.JLabel();
        pointLb4 = new javax.swing.JLabel();
        pointLb5 = new javax.swing.JLabel();
        pointLb6 = new javax.swing.JLabel();
        pointLb7 = new javax.swing.JLabel();
        pointLb8 = new javax.swing.JLabel();
        pointLb9 = new javax.swing.JLabel();
        arrPointLb = new JLabel[10];
        arrPointLb[0] = pointLb0;
        arrPointLb[1] = pointLb1;
        arrPointLb[2] = pointLb2;
        arrPointLb[3] = pointLb3;
        arrPointLb[4] = pointLb4;
        arrPointLb[5] = pointLb5;
        arrPointLb[6] = pointLb6;
        arrPointLb[7] = pointLb7;
        arrPointLb[8] = pointLb8;
        arrPointLb[9] = pointLb9;

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("K Class - Pengguna");

        StatusUser.setBackground(new java.awt.Color(0, 51, 51));
        StatusUser.setBorder(new javax.swing.border.MatteBorder(null));
        StatusUser.setPreferredSize(new java.awt.Dimension(202, 100));

        profilePicture.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profilePicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/blankpp.png"))); // NOI18N
        profilePicture.setMaximumSize(new java.awt.Dimension(162, 162));
        profilePicture.setMinimumSize(new java.awt.Dimension(162, 162));

        namaUser.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        namaUser.setForeground(new java.awt.Color(153, 255, 153));
        namaUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        namaUser.setText("MANUSIA");
        namaUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labelStatus.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        labelStatus.setForeground(new java.awt.Color(153, 255, 153));
        labelStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelStatus.setText("STATUS PENGERJAAN");
        labelStatus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelStatus.setVerifyInputWhenFocusTarget(false);

        labelRata.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        labelRata.setForeground(new java.awt.Color(255, 255, 255));
        labelRata.setText("Rata-rata Nilai:");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setMinimumSize(new java.awt.Dimension(50, 50));
        jSeparator1.setPreferredSize(new java.awt.Dimension(50, 50));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setMinimumSize(new java.awt.Dimension(50, 50));
        jSeparator2.setPreferredSize(new java.awt.Dimension(50, 50));

        buttonLogout.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        buttonLogout.setText("LOGOUT");
        buttonLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        buttonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLogoutActionPerformed(evt);
            }
        });

        nilaiRata.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        nilaiRata.setForeground(new java.awt.Color(0, 255, 0));
        nilaiRata.setText("0");

        labelRata1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        labelRata1.setForeground(new java.awt.Color(255, 255, 255));
        labelRata1.setText("Progress:");

        jProgressBar1.setBackground(new java.awt.Color(255, 255, 255));
        jProgressBar1.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jProgressBar1.setForeground(new java.awt.Color(0, 0, 0));
        jProgressBar1.setStringPainted(true);

        buttonLeaderboard.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        buttonLeaderboard.setText("LEADERBOARD");
        buttonLeaderboard.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        buttonLeaderboard.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonLeaderboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLeaderboardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout StatusUserLayout = new javax.swing.GroupLayout(StatusUser);
        StatusUser.setLayout(StatusUserLayout);
        StatusUserLayout.setHorizontalGroup(
            StatusUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(StatusUserLayout.createSequentialGroup()
                .addGroup(StatusUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(namaUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(StatusUserLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(profilePicture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(labelStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(StatusUserLayout.createSequentialGroup()
                        .addGroup(StatusUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(StatusUserLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(StatusUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(StatusUserLayout.createSequentialGroup()
                                        .addComponent(labelRata)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(nilaiRata))
                                    .addGroup(StatusUserLayout.createSequentialGroup()
                                        .addComponent(labelRata1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(StatusUserLayout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(buttonLogout)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(StatusUserLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(buttonLeaderboard, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        StatusUserLayout.setVerticalGroup(
            StatusUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StatusUserLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(profilePicture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaUser)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(StatusUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRata)
                    .addComponent(nilaiRata))
                .addGap(10, 10, 10)
                .addGroup(StatusUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelRata1)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonLeaderboard)
                .addGap(18, 18, 18)
                .addComponent(buttonLogout)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        parentPanel.setLayout(new java.awt.CardLayout());

        dashboardTugas.setBackground(new java.awt.Color(0, 102, 102));

        labelList.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        labelList.setForeground(new java.awt.Color(255, 255, 255));
        labelList.setText("LIST KUIS");

        labelK0.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        labelK0.setForeground(new java.awt.Color(255, 255, 255));
        labelK0.setText("Kuis 1 :  Provinsi - Ibukota");

        labelK1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        labelK1.setForeground(new java.awt.Color(255, 255, 255));
        labelK1.setText("Kuis 2 : ");

        labelK2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        labelK2.setForeground(new java.awt.Color(255, 255, 255));
        labelK2.setText("Kuis 3 : ");

        labelK3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        labelK3.setForeground(new java.awt.Color(255, 255, 255));
        labelK3.setText("Kuis 4 : ");

        labelK4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        labelK4.setForeground(new java.awt.Color(255, 255, 255));
        labelK4.setText("Kuis 5 : ");

        labelK5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        labelK5.setForeground(new java.awt.Color(255, 255, 255));
        labelK5.setText("Kuis 6 : ");

        labelK6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        labelK6.setForeground(new java.awt.Color(255, 255, 255));
        labelK6.setText("Kuis 7 : ");

        labelK7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        labelK7.setForeground(new java.awt.Color(255, 255, 255));
        labelK7.setText("Kuis 8 : ");

        labelK8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        labelK8.setForeground(new java.awt.Color(255, 255, 255));
        labelK8.setText("Kuis 9 : ");

        labelK9.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        labelK9.setForeground(new java.awt.Color(255, 255, 255));
        labelK9.setText("Kuis 10 : ");

        attemptK0.setBackground(new java.awt.Color(204, 204, 255));
        attemptK0.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        attemptK0.setText("Attempt");
        attemptK0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attemptButtonPerformed(evt);
            }
        });

        nilaiK0.setText("Nilai: -");
        nilaiK0.setEnabled(false);
        nilaiK0.setFocusable(false);

        attemptK1.setBackground(new java.awt.Color(204, 204, 255));
        attemptK1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        attemptK1.setText("Attempt");
        attemptK1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attemptButtonPerformed(evt);
            }
        });

        attemptK2.setBackground(new java.awt.Color(204, 204, 255));
        attemptK2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        attemptK2.setText("Attempt");
        attemptK2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attemptButtonPerformed(evt);
            }
        });

        attemptK3.setBackground(new java.awt.Color(204, 204, 255));
        attemptK3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        attemptK3.setText("Attempt");
        attemptK3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attemptButtonPerformed(evt);
            }
        });

        attemptK4.setBackground(new java.awt.Color(204, 204, 255));
        attemptK4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        attemptK4.setText("Attempt");
        attemptK4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attemptButtonPerformed(evt);
            }
        });

        attemptK5.setBackground(new java.awt.Color(204, 204, 255));
        attemptK5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        attemptK5.setText("Attempt");
        attemptK5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attemptButtonPerformed(evt);
            }
        });

        attemptK6.setBackground(new java.awt.Color(204, 204, 255));
        attemptK6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        attemptK6.setText("Attempt");
        attemptK6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attemptButtonPerformed(evt);
            }
        });

        attemptK7.setBackground(new java.awt.Color(204, 204, 255));
        attemptK7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        attemptK7.setText("Attempt");
        attemptK7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attemptButtonPerformed(evt);
            }
        });

        attemptK8.setBackground(new java.awt.Color(204, 204, 255));
        attemptK8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        attemptK8.setText("Attempt");
        attemptK8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attemptButtonPerformed(evt);
            }
        });

        attemptK9.setBackground(new java.awt.Color(204, 204, 255));
        attemptK9.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        attemptK9.setText("Attempt");
        attemptK9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attemptButtonPerformed(evt);
            }
        });

        nilaiK1.setText("Nilai: -");
        nilaiK1.setEnabled(false);
        nilaiK1.setFocusable(false);

        nilaiK2.setText("Nilai: -");
        nilaiK2.setEnabled(false);
        nilaiK2.setFocusable(false);

        nilaiK3.setText("Nilai: -");
        nilaiK3.setEnabled(false);
        nilaiK3.setFocusable(false);

        nilaiK4.setText("Nilai: -");
        nilaiK4.setEnabled(false);
        nilaiK4.setFocusable(false);

        nilaiK5.setText("Nilai: -");
        nilaiK5.setEnabled(false);
        nilaiK5.setFocusable(false);

        nilaiK6.setText("Nilai: -");
        nilaiK6.setEnabled(false);
        nilaiK6.setFocusable(false);

        nilaiK7.setText("Nilai: -");
        nilaiK7.setEnabled(false);
        nilaiK7.setFocusable(false);

        nilaiK8.setText("Nilai: -");
        nilaiK8.setEnabled(false);
        nilaiK8.setFocusable(false);

        nilaiK9.setText("Nilai: -");
        nilaiK9.setEnabled(false);
        nilaiK9.setFocusable(false);

        javax.swing.GroupLayout dashboardTugasLayout = new javax.swing.GroupLayout(dashboardTugas);
        dashboardTugas.setLayout(dashboardTugasLayout);
        dashboardTugasLayout.setHorizontalGroup(
            dashboardTugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardTugasLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(dashboardTugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(dashboardTugasLayout.createSequentialGroup()
                        .addComponent(labelK2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nilaiK2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(attemptK2))
                    .addGroup(dashboardTugasLayout.createSequentialGroup()
                        .addComponent(labelK3, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nilaiK3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(attemptK3))
                    .addGroup(dashboardTugasLayout.createSequentialGroup()
                        .addComponent(labelK4, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nilaiK4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(attemptK4))
                    .addGroup(dashboardTugasLayout.createSequentialGroup()
                        .addComponent(labelK5, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nilaiK5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(attemptK5))
                    .addGroup(dashboardTugasLayout.createSequentialGroup()
                        .addComponent(labelK6, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nilaiK6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(attemptK6))
                    .addGroup(dashboardTugasLayout.createSequentialGroup()
                        .addComponent(labelK7, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nilaiK7, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(attemptK7))
                    .addGroup(dashboardTugasLayout.createSequentialGroup()
                        .addComponent(labelK8, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nilaiK8, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(attemptK8))
                    .addGroup(dashboardTugasLayout.createSequentialGroup()
                        .addComponent(labelK9, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nilaiK9, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(attemptK9))
                    .addGroup(dashboardTugasLayout.createSequentialGroup()
                        .addComponent(labelK1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nilaiK1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(attemptK1))
                    .addGroup(dashboardTugasLayout.createSequentialGroup()
                        .addGroup(dashboardTugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelList, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(dashboardTugasLayout.createSequentialGroup()
                                .addComponent(labelK0, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nilaiK0, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(attemptK0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(485, Short.MAX_VALUE))
        );
        dashboardTugasLayout.setVerticalGroup(
            dashboardTugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardTugasLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(labelList)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardTugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelK0, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attemptK0)
                    .addComponent(nilaiK0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardTugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelK1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attemptK1)
                    .addComponent(nilaiK1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardTugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelK2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attemptK2)
                    .addComponent(nilaiK2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardTugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelK3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attemptK3)
                    .addComponent(nilaiK3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardTugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelK4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attemptK4)
                    .addComponent(nilaiK4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardTugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelK5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attemptK5)
                    .addComponent(nilaiK5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardTugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelK6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attemptK6)
                    .addComponent(nilaiK6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardTugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelK7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attemptK7)
                    .addComponent(nilaiK7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardTugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelK8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attemptK8)
                    .addComponent(nilaiK8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardTugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelK9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attemptK9)
                    .addComponent(nilaiK9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        parentPanel.add(dashboardTugas, "dashboardTugas");

        panelLeaderboard.setBackground(new java.awt.Color(0, 102, 102));

        labelLb.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        labelLb.setForeground(new java.awt.Color(255, 255, 255));
        labelLb.setText("LEADERBOARD");

        comboBoxLb.setFont(new java.awt.Font("Century Gothic", 2, 12)); // NOI18N
        comboBoxLb.setForeground(new java.awt.Color(0, 102, 102));
        comboBoxLb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Total Skor", "Rata-rata", "Total Pengerjaan" }));
        comboBoxLb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxLbActionPerformed(evt);
            }
        });

        userLb0.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        userLb0.setForeground(new java.awt.Color(255, 255, 255));
        userLb0.setText("1. Goku");

        userLb1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        userLb1.setForeground(new java.awt.Color(255, 255, 255));
        userLb1.setText("2. User 1");

        userLb2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        userLb2.setForeground(new java.awt.Color(255, 255, 255));
        userLb2.setText("3. User2");

        userLb3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        userLb3.setForeground(new java.awt.Color(255, 255, 255));
        userLb3.setText("4.");

        userLb4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        userLb4.setForeground(new java.awt.Color(255, 255, 255));
        userLb4.setText("5.");

        userLb5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        userLb5.setForeground(new java.awt.Color(255, 255, 255));
        userLb5.setText("6.");

        userLb6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        userLb6.setForeground(new java.awt.Color(255, 255, 255));
        userLb6.setText("7.");

        userLb7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        userLb7.setForeground(new java.awt.Color(255, 255, 255));
        userLb7.setText("8.");

        userLb8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        userLb8.setForeground(new java.awt.Color(255, 255, 255));
        userLb8.setText("9.");

        userLb9.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        userLb9.setForeground(new java.awt.Color(255, 255, 255));
        userLb9.setText("10.");

        userLbTitle.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        userLbTitle.setForeground(new java.awt.Color(255, 255, 255));
        userLbTitle.setText("USERNAME");

        pointLbTitle.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        pointLbTitle.setForeground(new java.awt.Color(255, 255, 255));
        pointLbTitle.setText("TOTAL SKOR");

        pointLb0.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        pointLb0.setForeground(new java.awt.Color(255, 255, 255));
        pointLb0.setText("-");

        pointLb1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        pointLb1.setForeground(new java.awt.Color(255, 255, 255));
        pointLb1.setText("-");

        pointLb2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        pointLb2.setForeground(new java.awt.Color(255, 255, 255));
        pointLb2.setText("-");

        pointLb3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        pointLb3.setForeground(new java.awt.Color(255, 255, 255));
        pointLb3.setText("-");

        pointLb4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        pointLb4.setForeground(new java.awt.Color(255, 255, 255));
        pointLb4.setText("-");

        pointLb5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        pointLb5.setForeground(new java.awt.Color(255, 255, 255));
        pointLb5.setText("-");

        pointLb6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        pointLb6.setForeground(new java.awt.Color(255, 255, 255));
        pointLb6.setText("-");

        pointLb7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        pointLb7.setForeground(new java.awt.Color(255, 255, 255));
        pointLb7.setText("-");

        pointLb8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        pointLb8.setForeground(new java.awt.Color(255, 255, 255));
        pointLb8.setText("-");

        pointLb9.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        pointLb9.setForeground(new java.awt.Color(255, 255, 255));
        pointLb9.setText("-");

        javax.swing.GroupLayout panelLeaderboardLayout = new javax.swing.GroupLayout(panelLeaderboard);
        panelLeaderboard.setLayout(panelLeaderboardLayout);
        panelLeaderboardLayout.setHorizontalGroup(
            panelLeaderboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLeaderboardLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelLeaderboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLeaderboardLayout.createSequentialGroup()
                        .addComponent(labelLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(86, 86, 86)
                        .addComponent(comboBoxLb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLeaderboardLayout.createSequentialGroup()
                        .addGroup(panelLeaderboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLeaderboardLayout.createSequentialGroup()
                                .addGroup(panelLeaderboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(userLb9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(userLb8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(userLb7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(userLb6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(userLb5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(userLb4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(userLb3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(userLb0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(userLb1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(userLb2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(panelLeaderboardLayout.createSequentialGroup()
                                .addComponent(userLbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(9, 9, 9)))
                        .addGroup(panelLeaderboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pointLbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelLeaderboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(pointLb9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pointLb8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pointLb7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pointLb6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pointLb5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pointLb4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pointLb3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pointLb0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pointLb1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pointLb2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(497, 497, 497))
        );
        panelLeaderboardLayout.setVerticalGroup(
            panelLeaderboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLeaderboardLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panelLeaderboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLb)
                    .addComponent(comboBoxLb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelLeaderboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelLeaderboardLayout.createSequentialGroup()
                        .addComponent(userLbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userLb0, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userLb1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userLb2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userLb3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userLb4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userLb5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userLb6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userLb7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userLb8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userLb9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLeaderboardLayout.createSequentialGroup()
                        .addComponent(pointLbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pointLb0, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pointLb1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pointLb2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pointLb3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pointLb4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pointLb5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pointLb6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pointLb7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pointLb8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pointLb9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(88, Short.MAX_VALUE))
        );

        parentPanel.add(panelLeaderboard, "panelLeaderboard");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(StatusUser, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(parentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StatusUser, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
            .addComponent(parentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(770, 478));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
 
    private void comboBoxLbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxLbActionPerformed
        // TODO add your handling code here:
        int pilihan = comboBoxLb.getSelectedIndex(); //total skor = 0; rata rata = 1; total pengerjaan = 2
        eventLeaderboard(pilihan);
    }//GEN-LAST:event_comboBoxLbActionPerformed

    private void buttonLeaderboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLeaderboardActionPerformed
        // TODO add your handling code here:
        if(buttonLeaderboard.getText().equals("LEADERBOARD")){
            buttonLeaderboard.setText("LIST KUIS");
            CardLayout cards = (CardLayout) parentPanel.getLayout();
            cards.show(parentPanel,"panelLeaderboard");//ke leaderboard
            comboBoxLb.setSelectedIndex(0);
            eventLeaderboard(0);
        } else{
            buttonLeaderboard.setText("LEADERBOARD");
            CardLayout cards = (CardLayout) parentPanel.getLayout();
            cards.show(parentPanel,"dashboardTugas");//ke dashboard kuis
        }
    }//GEN-LAST:event_buttonLeaderboardActionPerformed

    private void buttonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLogoutActionPerformed
        try {
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LayoutPengguna.class.getName()).log(Level.SEVERE, null, ex);
        }
        new LayoutLogin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_buttonLogoutActionPerformed

    private void attemptButtonPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attemptButtonPerformed
        JButton tombolAttempt = (JButton) evt.getSource();
        String teksLabelKuis = "";
        for(int i = 0; i < 10; i++){
            if(arrButton[i].equals(tombolAttempt)) {
                teksLabelKuis = arrLabel[i].getText();
            }
        }
        int idxAkhir = teksLabelKuis.indexOf(":");
        String idxCSV = teksLabelKuis.substring(4, idxAkhir  - 1);
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT url FROM kCSV WHERE idCSV = " + idxCSV);
            if(rs != null && rs.next()){
                String urlKuis = rs.getString("url");
                new LayoutKuis(idxCSV, urlKuis);
            }
            st.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(LayoutPengguna.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_attemptButtonPerformed
  
    private void eventLeaderboard(int pilihan){
        try {
            Statement st = conn.createStatement();
            String statementPilihan = "";
            switch (pilihan) {
                case 0 -> {
                    pointLbTitle.setText("TOTAL SKOR");
                    statementPilihan = "SELECT idUser, SUM(score) FROM kScore GROUP BY idUser ORDER BY SUM(score) DESC";
                }
                case 1 -> {
                    pointLbTitle.setText("RATA-RATA");
                    statementPilihan = "SELECT idUser, AVG(score) FROM kScore GROUP BY idUser ORDER BY AVG(score) DESC";
                }
                case 2 -> {
                    pointLbTitle.setText("TOTAL PENGERJAAN");
                    statementPilihan = "SELECT idUser, COUNT(*) FROM kScore GROUP BY idUser ORDER BY COUNT(*) DESC";
                }
                default -> {
                }
            }
            ResultSet rs = st.executeQuery(statementPilihan);   //ambil data
            
            PreparedStatement ps2 = conn.prepareStatement("SELECT username FROM kUser WHERE idUser = ?"); //untuk ambil username           
            //loop dari yang paling besar
            int i = 0;
            while(rs.next()){
                //ambil username dulu
                ps2.setString(1, rs.getString("idUser"));
                ResultSet rs2 = ps2.executeQuery();
                String username = "";
                if(rs2.next()) username = rs2.getString("username");

                //set label
                arrUserLb[i].setText((i+1) + ". " + username);
                switch (pilihan) {
                    case 0 -> arrPointLb[i].setText(rs.getString("SUM(score)"));
                    case 1 -> {
                        String avg = rs.getString("AVG(score)");
                        double avgDouble = Double.parseDouble(avg);
                        arrPointLb[i].setText(String.format("%.2f", avgDouble));
                    }
                    case 2 -> arrPointLb[i].setText(rs.getString("COUNT(*)"));
                    default -> {
                    }
                }
                rs2.close();
                i++;

                //jika sudah ada 10, keluar loop
                if(i >= 10) break;
            }
            while(i < 10){
                //kosongkan label jika database sudah selesai loop, namun belum ada 10 peringkat
                arrUserLb[i].setText((i+1) + ". ");
                arrPointLb[i].setText("-");
                i++;
            }
            rs.close();
            st.close();
            ps2.close();
        } catch (SQLException ex) {
            Logger.getLogger(LayoutPengguna.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel StatusUser;
    private javax.swing.JButton attemptK0;
    private javax.swing.JButton attemptK1;
    private javax.swing.JButton attemptK2;
    private javax.swing.JButton attemptK3;
    private javax.swing.JButton attemptK4;
    private javax.swing.JButton attemptK5;
    private javax.swing.JButton attemptK6;
    private javax.swing.JButton attemptK7;
    private javax.swing.JButton attemptK8;
    private javax.swing.JButton attemptK9;
    private javax.swing.JButton buttonLeaderboard;
    private javax.swing.JButton buttonLogout;
    private javax.swing.JComboBox<String> comboBoxLb;
    private javax.swing.JPanel dashboardTugas;
    private javax.swing.ButtonGroup grupOpsi;
    private javax.swing.ButtonGroup grupPGIsian;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel labelK0;
    private javax.swing.JLabel labelK1;
    private javax.swing.JLabel labelK2;
    private javax.swing.JLabel labelK3;
    private javax.swing.JLabel labelK4;
    private javax.swing.JLabel labelK5;
    private javax.swing.JLabel labelK6;
    private javax.swing.JLabel labelK7;
    private javax.swing.JLabel labelK8;
    private javax.swing.JLabel labelK9;
    private javax.swing.JLabel labelLb;
    private javax.swing.JLabel labelList;
    private javax.swing.JLabel labelRata;
    private javax.swing.JLabel labelRata1;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel namaUser;
    private javax.swing.JTextField nilaiK0;
    private javax.swing.JTextField nilaiK1;
    private javax.swing.JTextField nilaiK2;
    private javax.swing.JTextField nilaiK3;
    private javax.swing.JTextField nilaiK4;
    private javax.swing.JTextField nilaiK5;
    private javax.swing.JTextField nilaiK6;
    private javax.swing.JTextField nilaiK7;
    private javax.swing.JTextField nilaiK8;
    private javax.swing.JTextField nilaiK9;
    private javax.swing.JLabel nilaiRata;
    private javax.swing.JPanel panelLeaderboard;
    private javax.swing.JPanel parentPanel;
    private javax.swing.JLabel pointLb0;
    private javax.swing.JLabel pointLb1;
    private javax.swing.JLabel pointLb2;
    private javax.swing.JLabel pointLb3;
    private javax.swing.JLabel pointLb4;
    private javax.swing.JLabel pointLb5;
    private javax.swing.JLabel pointLb6;
    private javax.swing.JLabel pointLb7;
    private javax.swing.JLabel pointLb8;
    private javax.swing.JLabel pointLb9;
    private javax.swing.JLabel pointLbTitle;
    private javax.swing.JLabel profilePicture;
    private javax.swing.JLabel userLb0;
    private javax.swing.JLabel userLb1;
    private javax.swing.JLabel userLb2;
    private javax.swing.JLabel userLb3;
    private javax.swing.JLabel userLb4;
    private javax.swing.JLabel userLb5;
    private javax.swing.JLabel userLb6;
    private javax.swing.JLabel userLb7;
    private javax.swing.JLabel userLb8;
    private javax.swing.JLabel userLb9;
    private javax.swing.JLabel userLbTitle;
    // End of variables declaration//GEN-END:variables
}
