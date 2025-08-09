/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kclass;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;



/**
 *
 * @author Aji Putra Pamungkas
 */
public class LayoutKuis extends JFrame {
    JLabel labelDiberikanKepala, labelDiberikan, labelJawabanKepala, labelNilai;
    JLabel[] labelJawaban;
    JTextField teksJawaban;
    JTextArea teksKomentar;
    JProgressBar progressBar;
    JButton tombolLanjut, tombolMulai;
    Container container;
    ButtonGroup grup1, grup2;
    JMenuBar menuUtama;
    JMenu menuOpsi;
    JRadioButtonMenuItem itemMenuKepala1, itemMenuKepala2, itemMenuPG, itemMenuIsian;
    String judulUjian, kepala1, kepala2, persentaseBenar;
    int banyakSuku, banyakDicoba, banyakTepat, idxTepat, letakJawabanTepat, maxPertanyaan;
    ArrayList<String> suku1,suku2;
    String idCSV;

    LayoutKuis(String idCSV, String url){    
        initComponents();
        //Atur sesi frame ini
        this.idCSV = idCSV;
        initBukaFile(url,10);   
    }
    
    private void initComponents(){
        container = getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints konstrainGrid;
        
        Font f1 = new Font("Arial", Font.BOLD, 18);
        Font f2 = new Font("Arial", Font.BOLD, 16);

        Dimension dim = new Dimension(370,30);
        Color cKomentar = new Color(255,255,192);
        
        labelDiberikanKepala = new JLabel();
        labelDiberikanKepala.setFont(f1);
        labelDiberikanKepala.setPreferredSize(dim);

        konstrainGrid = new GridBagConstraints();
        konstrainGrid.gridx = 0; konstrainGrid.gridy = 0;
        konstrainGrid.insets = new Insets(10,10,0,10);
        container.add(labelDiberikanKepala,konstrainGrid);
        
        labelDiberikan = new JLabel();
        labelDiberikan.setFont(f2);
        labelDiberikan.setPreferredSize(dim);
        labelDiberikan.setForeground(Color.BLUE);
        labelDiberikan.setBackground(Color.WHITE);
        labelDiberikan.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        labelDiberikan.setOpaque(true);
        labelDiberikan.setHorizontalAlignment(SwingConstants.CENTER);
        konstrainGrid = new GridBagConstraints();
        konstrainGrid.gridx = 0; konstrainGrid.gridy = 1;
        konstrainGrid.insets = new Insets(0,10,0,10);
        container.add(labelDiberikan,konstrainGrid);
        
        labelJawabanKepala = new JLabel();
        labelJawabanKepala.setFont(f1);
        labelJawabanKepala.setPreferredSize(dim);
        konstrainGrid = new GridBagConstraints();
        konstrainGrid.gridx = 0; konstrainGrid.gridy = 2;
        konstrainGrid.insets = new Insets(10,10,0,10);
        container.add(labelJawabanKepala,konstrainGrid);
        
        labelJawaban = new JLabel[4];
        for(int i = 0; i < 4; i++){
            labelJawaban[i] = new JLabel();
            labelJawaban[i].setFont(f2);
            labelJawaban[i].setPreferredSize(dim);
            labelJawaban[i].setForeground(Color.BLUE);
            labelJawaban[i].setBackground(Color.WHITE);
            labelJawaban[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            labelJawaban[i].setOpaque(true);
            labelJawaban[i].setHorizontalAlignment(SwingConstants.CENTER);
            konstrainGrid = new GridBagConstraints();
            konstrainGrid.gridx = 0; konstrainGrid.gridy = i+3;
            konstrainGrid.insets = new Insets(0,10,10,10);
            container.add(labelJawaban[i],konstrainGrid);
            labelJawaban[i].addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e){
                    eventLabel(e);
                }
            });
        }
        
        teksJawaban = new JTextField();
        teksJawaban.setFont(f2);
        teksJawaban.setPreferredSize(dim);
        teksJawaban.setForeground(Color.BLUE);
        teksJawaban.setBackground(Color.WHITE);
        teksJawaban.setHorizontalAlignment(SwingConstants.CENTER);
        teksJawaban.setVisible(false);
        konstrainGrid = new GridBagConstraints();
        konstrainGrid.gridx = 0; konstrainGrid.gridy = 3;
        konstrainGrid.insets = new Insets(0,10,10,10);
        container.add(teksJawaban,konstrainGrid);
        teksJawaban.addActionListener((ActionEvent e) -> eventTeksJawaban(e));
        
        teksKomentar = new JTextArea();
        teksKomentar.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, 18));
        teksKomentar.setPreferredSize(new Dimension(370,80));
        teksKomentar.setForeground(Color.RED);
        teksKomentar.setBackground(cKomentar);
        teksKomentar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        teksKomentar.setEditable(false);
        konstrainGrid = new GridBagConstraints();
        konstrainGrid.gridx = 0; konstrainGrid.gridy = 7;
        konstrainGrid.insets = new Insets(0,10,10,10);
        container.add(teksKomentar,konstrainGrid);
        
        labelNilai = new JLabel("Nilai saat ini: 0.00/100.00");
        konstrainGrid = new GridBagConstraints();
        konstrainGrid.gridx = 0; konstrainGrid.gridy = 8;
        konstrainGrid.insets = new Insets(0,0,10,0);
        container.add(labelNilai,konstrainGrid);
        
        
        progressBar = new JProgressBar(0,100);
        progressBar.setBounds(50,50,350,30);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        konstrainGrid = new GridBagConstraints();
        konstrainGrid.gridx = 0; konstrainGrid.gridy = 9;
        konstrainGrid.insets = new Insets(0,0,10,0);
        container.add(progressBar,konstrainGrid);
        
        tombolLanjut = new JButton("Pertanyaan Selanjutnya");
        konstrainGrid = new GridBagConstraints();
        konstrainGrid.gridx = 0; konstrainGrid.gridy = 10;
        konstrainGrid.insets = new Insets(0,0,10,0);
        container.add(tombolLanjut,konstrainGrid);
        tombolLanjut.addActionListener((ActionEvent e) -> eventTombolLanjut(e));
        
        tombolMulai = new JButton("Mulai Ujian");
        konstrainGrid = new GridBagConstraints();
        konstrainGrid.gridx = 0; konstrainGrid.gridy = 11;
        konstrainGrid.insets = new Insets(0,0,10,0);
        container.add(tombolMulai,konstrainGrid);
        tombolMulai.addActionListener((ActionEvent e) -> eventTombolMulai(e));
        
        menuUtama = new JMenuBar();

        menuOpsi = new JMenu("Opsi");
        itemMenuKepala1 = new JRadioButtonMenuItem("Kepala 1", true);
        itemMenuKepala2 = new JRadioButtonMenuItem("Kepala 2", false);
        itemMenuPG = new JRadioButtonMenuItem("Pilihan Ganda", true);
        itemMenuIsian = new JRadioButtonMenuItem("Isian", false);
        grup1 = new ButtonGroup();
        grup2 = new ButtonGroup();
        
        this.setJMenuBar(menuUtama);

        menuUtama.add(menuOpsi);
        menuOpsi.add(itemMenuKepala1);
        menuOpsi.add(itemMenuKepala2);
        menuOpsi.addSeparator();
        menuOpsi.add(itemMenuPG);
        menuOpsi.add(itemMenuIsian);
        
        grup1.add(itemMenuKepala1);
        grup1.add(itemMenuKepala2);
        grup2.add(itemMenuPG);
        grup2.add(itemMenuIsian);

        itemMenuKepala1.addActionListener((ActionEvent e) -> eventItemMenuKepala1(e));
        itemMenuKepala2.addActionListener((ActionEvent e) -> eventItemMenuKepala2(e));
        itemMenuPG.addActionListener((ActionEvent e) -> eventItemMenuPG(e));
        itemMenuIsian.addActionListener((ActionEvent e) -> eventItemMenuIsian(e));
        
        pack();
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    
    private void initBukaFile(String url, int maxPertanyaan){
        String baris; int indeksKoma;
        suku1 = new ArrayList<>();
        suku2 = new ArrayList<>();
        
        File file = new File(url);
        try (BufferedReader fileKu = new BufferedReader(new FileReader(file))){
            baris = fileKu.readLine();
            indeksKoma = baris.indexOf(",");
            judulUjian = baris.substring(0,indeksKoma);

            baris = fileKu.readLine();
            indeksKoma = baris.indexOf(",");
            kepala1 = baris.substring(0,indeksKoma);
            kepala2 = baris.substring(indeksKoma+1);
            
            do{
                baris = fileKu.readLine();
                indeksKoma = baris.indexOf(",");
                suku1.add(baris.substring(0,indeksKoma));
                suku2.add(baris.substring(indeksKoma+1));
            }while(fileKu.ready());
            JOptionPane.showConfirmDialog(null, file.getName()+ " dibuka, selamat mengerjakan",
                        "Berhasil", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
            
        } catch(Exception ex){
            JOptionPane.showConfirmDialog(null, 
                "Pastikan entri mengikuti format yang benar",
                "Error File Ujian",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        this.setTitle("Kuis PG - " + judulUjian);
        itemMenuKepala1.setText(kepala1 + ", jika diberikan " + kepala2 );
        itemMenuKepala2.setText(kepala2 + ", jika diberikan " + kepala1 );
        
        if (itemMenuKepala1.isSelected()){
            labelDiberikanKepala.setText(kepala2);
            labelJawabanKepala.setText(kepala1);
        }
        else{
            labelDiberikanKepala.setText(kepala1);
            labelJawabanKepala.setText(kepala2);
        }
        
        banyakSuku = suku1.size();
        this.maxPertanyaan = maxPertanyaan;
        tombolMulai.setEnabled(true);
        tombolLanjut.setEnabled(false);
        menuOpsi.setEnabled(true);
        teksJawaban.setEditable(false);
        teksKomentar.setText(areaTeksTengah("File telah dimuat\nPilih opsi dan klik mulai ujian"));
        
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowOpened(WindowEvent e){
                tombolMulai.requestFocusInWindow();
            }
        });
    }
    
    private void eventTombolMulai(ActionEvent e){
        if(tombolMulai.getText().equals("Mulai Ujian")){     
            tombolLanjut.setEnabled(false);
            tombolMulai.setText("Hentikan Ujian");
            teksKomentar.setText("");
            menuOpsi.setEnabled(false);
            banyakDicoba = 0;
            banyakTepat = 0;
            pertanyaanSelanjutnya(new Random());
        }
        else{
            eventBerhenti();
        }
    }
    
    private void eventTombolLanjut(ActionEvent e){
        if(banyakDicoba >= maxPertanyaan){
            eventBerhenti();
        }
        else{
            pertanyaanSelanjutnya(new Random());
        }
    }
    
    private void pertanyaanSelanjutnya(Random r){
        teksKomentar.setText("");
        idxTepat = r.nextInt(banyakSuku);
        if (itemMenuKepala1.isSelected()){
            labelDiberikan.setText(suku2.get(idxTepat));
        }
        else{
            labelDiberikan.setText(suku1.get(idxTepat));
        }
        if (itemMenuPG.isSelected()){     
            boolean[] sukuDigunakan = new boolean[banyakSuku];
            for(int i = 0; i < banyakSuku ; i++){
                sukuDigunakan[i] = false;
            }
            
            int[] idxDigunakan = new int[4];     
            for(int i = 0; i < 4; i++){
                labelJawaban[i].setBackground(Color.WHITE);
                int j;
                do{
                    j = r.nextInt(banyakSuku);
                }while(sukuDigunakan[j] || j == idxTepat );
                sukuDigunakan[j] = true;
                idxDigunakan[i] = j;
            }
            letakJawabanTepat = r.nextInt(4);
            idxDigunakan[letakJawabanTepat] = idxTepat;
            
            if (itemMenuKepala1.isSelected()){
                for(int i = 0; i < 4; i++){
                    labelJawaban[i].setText(suku1.get(idxDigunakan[i]));
                }
            }
            else{
                for(int i = 0; i < 4; i++){
                    labelJawaban[i].setText(suku2.get(idxDigunakan[i]));
                }
            }
        }
        else{
            teksJawaban.setEditable(true);
            teksJawaban.setText("");
            teksJawaban.requestFocus();
        }
        tombolLanjut.setEnabled(false);
    }
    
    private void updateProgress(){
        int hasil = (banyakDicoba * 100) / maxPertanyaan;
        progressBar.setValue(hasil);
        
        if(banyakDicoba == 0) persentaseBenar = "0";
        else{
            Double d = (banyakTepat*1.0/maxPertanyaan)*100;
            persentaseBenar = new DecimalFormat("0.00").format(d);
        }
        labelNilai.setText("Nilai saat ini: " + persentaseBenar + "/100.00");
    }
    
    private void eventLabel(MouseEvent e){
        if (tombolMulai.getText().equals("Mulai Ujian") || tombolLanjut.isEnabled()){
            return;
        }
        JLabel labelKamu = (JLabel) e.getSource(); //ambil jlabel dari klikan mouse
        
        banyakDicoba += 1;
        String jawabanTepat = labelJawaban[letakJawabanTepat].getText();      
        String jawabanKamu = labelKamu.getText();
        
        //Atur skor dan warna background label
        labelJawaban[letakJawabanTepat].setBackground(Color.GREEN);
        if (jawabanKamu.equals(jawabanTepat)){          
            banyakTepat += 1;
            teksKomentar.setText(areaTeksTengah("Tepat!"));
        }
        else{
            labelKamu.setBackground(Color.RED);
            teksKomentar.setText(areaTeksTengah("Maaf.. Jawabanmu kurang tepat\nJawaban yang benar ditampilkan"));
        }

        tombolLanjut.setEnabled(true);        
        updateProgress();
    }
    
    private void eventTeksJawaban(ActionEvent e){
        banyakDicoba += 1;
        if(itemMenuKepala1.isSelected()){
            if(teksJawaban.getText().toUpperCase().equals(suku1.get(idxTepat).toUpperCase())){
                banyakTepat += 1;
                teksKomentar.setText(areaTeksTengah("Tepat!"));
            }
            else{
                teksKomentar.setText(areaTeksTengah("Maaf.. Jawabanmu kurang tepat\nJawaban yang benar ditampilkan"));
            }
        }
        else{
            if(teksJawaban.getText().toUpperCase().equals(suku2.get(idxTepat).toUpperCase())){
                banyakTepat += 1;
                teksKomentar.setText(areaTeksTengah("Tepat!"));
            }
            else{
                teksKomentar.setText(areaTeksTengah("Maaf.. Jawabanmu kurang tepat\nJawaban yang benar ditampilkan"));
            }
        }
        teksJawaban.setEditable(false);
        tombolLanjut.setEnabled(true);
        updateProgress();        
    }
    
    private void eventBerhenti(){
        tombolMulai.setText("Mulai Ujian");
        teksKomentar.setText(areaTeksTengah("File telah dimuat\nPilih opsi dan klik mulai ujian"));
        labelDiberikan.setText("");
        for(int i = 0; i < 4; i++){
            labelJawaban[i].setText("");
            labelJawaban[i].setBackground(Color.WHITE);
        }
        teksJawaban.setText("");
        tombolLanjut.setEnabled(false);
        menuOpsi.setEnabled(true);        

        String sOut = "Pertanyaan dicoba: " + banyakDicoba + " dari "+ maxPertanyaan +" pertanyaan"
                + "\nJawaban tepat: " + banyakTepat + "\nSkor anda: " + persentaseBenar + "%";
        JOptionPane.showConfirmDialog(null, sOut, judulUjian, 
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
        String skor = persentaseBenar.substring(0, persentaseBenar.length() - 3); //100.00 -> 100
        LayoutPengguna.instanceLP.handleFromKuis(skor, idCSV);        
    }
    
    private String areaTeksTengah(String s){
        int j = s.indexOf("\n");
        int totalKarakter = 33;
        String out;
        if(j == -1){
            out = "\n" + spasi((int) ((totalKarakter - s.length())/2)) + s;
        }
        else{
            String baris1 = s.substring(0,j);
            String baris2 = s.substring(j+1);
            out = "\n" + spasi((int) ((totalKarakter - baris1.length())/2)) + baris1;
            out += "\n" + spasi((int) ((totalKarakter - baris2.length())/2)) + baris2;
        }
        return out;
    }
    
    private String spasi(int n){    //untuk areaTeksTengah
        String banyakSpasi = "";
        while (n >0){
            banyakSpasi += " ";
            n--;
        }
        return banyakSpasi;
    }
    
    private void eventItemMenuKepala1(ActionEvent e){
        labelDiberikanKepala.setText(kepala2);
        labelJawabanKepala.setText(kepala1);
    }
    private void eventItemMenuKepala2(ActionEvent e){
        labelDiberikanKepala.setText(kepala1);
        labelJawabanKepala.setText(kepala2);
    }
    private void eventItemMenuPG(ActionEvent e){
        for (int i = 0; i < 4; i++){
            labelJawaban[i].setVisible(true);
        }
        teksJawaban.setVisible(false);
    }
    private void eventItemMenuIsian(ActionEvent e){
        for (int i = 0; i < 4; i++){
            labelJawaban[i].setVisible(false);
        }
        teksJawaban.setVisible(true);
    }
}
