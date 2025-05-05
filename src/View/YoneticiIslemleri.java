package View;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.text.MaskFormatter;

import Helper.DBConnection;
import Helper.Helper;

import java.text.ParseException;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class YoneticiIslemleri extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtKaptanIsim;
    private JTextField txtKalkis;
    private JTextField txtVaris;
    private JTextField txtFiyat;
    private DBConnection conn = new DBConnection();
    
    private JFormattedTextField mskTarih;
    private JFormattedTextField mskSaat;
    private JFormattedTextField mskKaptanNo;

    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    YoneticiIslemleri frame = new YoneticiIslemleri();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public YoneticiIslemleri() {
        setTitle("Admin Procedures");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 518, 322);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel_1_1 = new JPanel();
        panel_1_1.setLayout(null);
        panel_1_1.setBounds(258, 10, 236, 114);
        contentPane.add(panel_1_1);

        JLabel lblKaptanNo = new JLabel("Captain No:");
        lblKaptanNo.setBounds(31, 6, 85, 26);
        panel_1_1.add(lblKaptanNo);

        JLabel lblAdSoyad = new JLabel("Name Surname:");
        lblAdSoyad.setBounds(10, 46, 96, 26);
        panel_1_1.add(lblAdSoyad);

        txtKaptanIsim = new JTextField();
        txtKaptanIsim.setColumns(10);
        txtKaptanIsim.setBounds(116, 46, 110, 19);
        panel_1_1.add(txtKaptanIsim);

        // Kaptan No maskelemesi (mskKaptanNo2 - 4 haneli)
        final JFormattedTextField mskKaptanNoKayit = new JFormattedTextField();
        try {
            // MaskFormatter, metin kutusundaki girişe bir maske ekler.
            // "####" ifadesi, 4 haneli bir sayı veya metin formatını belirtir.
            MaskFormatter kaptanNoMask2 = new MaskFormatter("####");
            // Maske ile girilmesi gereken her boş karakter için "_" (alt çizgi) karakteri kullanılacak.
            kaptanNoMask2.setPlaceholderCharacter('_');
            // MaskFormatter'ın formatını, mskKaptanNoKayit adlı "JFormattedTextField" alanına uygular.
            // Yani, kullanıcının 4 haneli bir değer girmesi sağlanır.
            mskKaptanNoKayit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(kaptanNoMask2));
            mskKaptanNoKayit.setBounds(116, 10, 110, 19);
            // Son olarak, mskKaptanNoKayit alanı, panel_1_1 adlı panel üzerine eklenir.
            panel_1_1.add(mskKaptanNoKayit);
        } catch (ParseException e) {
            e.printStackTrace();    // Eğer MaskFormatter ile ilgili bir hata oluşursa, hata mesajı konsola yazdırılır.
        }

        JButton btnKaptan = new JButton("ADD CAPTAIN");
        btnKaptan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Eğer kaptan ismi veya kaptan numarası eksikse veya kaptan numarası maskesinde boş yer varsa
                if (txtKaptanIsim.getText().length() == 0 || mskKaptanNoKayit.getText().trim().contains("_")) {
                    // Kullanıcıya eksik bilgi olduğunu belirten bir mesaj gösteriyoruz
                    Helper.showMsg("Lütfen tüm alanları eksiksiz doldurun!");
                } else {
                    // Veritabanı bağlantısı kuruyoruz
                    Connection con = conn.connDB();
                    try {
                        // Veritabanına kaptan bilgilerini eklemek için SQL komutunu oluşturuyoruz
                        String komut = "INSERT INTO tblkaptan (KAPTANNO, ADSOYAD) VALUES (?, ?)";
                        // Hazırlanan SQL komutunu PreparedStatement ile çalıştırmak için bağlantı açıyoruz
                        PreparedStatement ps = con.prepareStatement(komut);
                        // Kaptan numarasını ve ismimi parametre olarak bağlıyoruz
                        ps.setString(1, mskKaptanNoKayit.getText());
                        ps.setString(2, txtKaptanIsim.getText());
                        // SQL komutunu çalıştırıyoruz ve veritabanına kayıt ekliyoruz
                        ps.executeUpdate();
                        // Başarı mesajı gösteriyoruz
                        JOptionPane.showMessageDialog(null, "Captain registration has been made.", "Successful", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e1) {
                        e1.printStackTrace(); // Eğer veritabanı işlemi sırasında bir hata olursa hata mesajını yazdırıyoruz
                    }
                }
            }
        });
        btnKaptan.setBounds(116, 77, 110, 25);
        panel_1_1.add(btnKaptan);

        JPanel panel_2 = new JPanel();
        panel_2.setLayout(null);
        panel_2.setBounds(10, 10, 238, 264);
        contentPane.add(panel_2);

        JLabel lblKalk = new JLabel("Departure:");
        lblKalk.setBounds(40, 10, 75, 26);
        panel_2.add(lblKalk);

        JLabel lblVar = new JLabel("Arrival:");
        lblVar.setBounds(60, 44, 55, 26);
        panel_2.add(lblVar);

        JLabel lblTarih = new JLabel("Date:");
        lblTarih.setBounds(70, 80, 45, 26);
        panel_2.add(lblTarih);

        JLabel lblSaat = new JLabel("Time:");
        lblSaat.setBounds(69, 116, 46, 26);
        panel_2.add(lblSaat);

        JLabel lblKaptan = new JLabel("Captain No:");
        lblKaptan.setBounds(34, 152, 95, 26);
        panel_2.add(lblKaptan);

        JLabel lblFiyat = new JLabel("Price:");
        lblFiyat.setBounds(70, 188, 45, 26);
        panel_2.add(lblFiyat);

        txtKalkis = new JTextField();
        txtKalkis.setColumns(10);
        txtKalkis.setBounds(105, 12, 123, 19);
        panel_2.add(txtKalkis);

        txtVaris = new JTextField();
        txtVaris.setColumns(10);
        txtVaris.setBounds(105, 48, 123, 19);
        panel_2.add(txtVaris);

        // Tarih maskelemesi
        try {
            // MaskFormatter ile tarih için özel bir format belirleniyor.
            // "##/##/####" biçimi, iki rakamlı gün (01-31), iki rakamlı ay (01-12) ve dört rakamlı yıl (0000-9999) girişine olanak tanır.
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            // Boşlukları yerine geçecek karakter belirleniyor. "_" karakteri, kullanıcı boş alanları doldurmadığında görünecek.
            dateMask.setPlaceholderCharacter('_'); 
            // mskTarih üye değişkenine MaskFormatter ile oluşturduğumuz maskeyi uyguluyoruz.
            // Bu sayede, kullanıcı yalnızca doğru formatta tarih bilgisi girebilecek.
            mskTarih = new JFormattedTextField(dateMask);         
            // Tarih alanının konumunu ve boyutunu belirliyoruz (x, y, width, height).
            mskTarih.setBounds(105, 84, 123, 19);           
            // Tarih alanını belirtilen panel'e ekliyoruz.
            panel_2.add(mskTarih);
        } catch (ParseException e) {
            // Eğer maskede bir hata oluşursa, hata mesajı yazdırılıyor.
            e.printStackTrace();
        }


        // Saat maskelemesi
        try {
            // MaskFormatter ile saat için özel bir format belirleniyor. 
            // "##:##" biçimi, iki rakamlı saat ve iki rakamlı dakika girişi sağlar
            MaskFormatter timeMask = new MaskFormatter("##:##");           
            // Boşlukları yerine geçecek karakter belirleniyor. "_" karakteri, kullanıcı boş alanları doldurmadığında görünecek.
            timeMask.setPlaceholderCharacter('_');
            // mskSaat üye değişkenine MaskFormatter ile oluşturduğumuz maskeyi uyguluyoruz.
            // Bu sayede, kullanıcı yalnızca doğru formatta saat bilgisi girebilecek
            mskSaat = new JFormattedTextField(timeMask);
            // Saat alanının konumunu ve boyutunu belirliyoruz (x, y, width, height)
            mskSaat.setBounds(105, 120, 123, 19);
            // Saat alanını belirtilen panel'e ekliyoruz
            panel_2.add(mskSaat);
        } catch (ParseException e) {
            e.printStackTrace(); // Eğer maskede bir hata oluşursa, hata mesajı yazdırılıyor
        }


        txtFiyat = new JTextField();
        txtFiyat.setColumns(10);
        txtFiyat.setBounds(105, 192, 123, 19);
        panel_2.add(txtFiyat);

        // Kaptan No maskelemesi (mskKaptanNo - 4 haneli)
        try {
            MaskFormatter kaptanNoMask = new MaskFormatter("####");
            kaptanNoMask.setPlaceholderCharacter('_');
            mskKaptanNo = new JFormattedTextField(kaptanNoMask);  // Bu satırda mskKaptanNo üye değişkenine atandı
            mskKaptanNo.setBounds(105, 156, 123, 19);
            panel_2.add(mskKaptanNo);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JButton btnSeferOlustur = new JButton("CREATE A VOYAGE");
        btnSeferOlustur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Eğer herhangi bir alan boş bırakılmışsa veya maskelenmiş alanlar eksikse
                if (txtFiyat.getText().length() == 0 || mskSaat.getText().trim().contains("_") || mskTarih.getText().trim().contains("_") || mskKaptanNo.getText().trim().contains("_") || txtKalkis.getText().length() == 0 || txtVaris.getText().length() == 0) {
                    // Kullanıcıya tüm alanların doldurulması gerektiğini belirten bir mesaj gösteriyoruz
                    Helper.showMsg("Please fill out all fields completely!");
                } else {
                    // Veritabanı bağlantısı kuruyoruz
                    Connection con = conn.connDB();
                    try {
                        // Seferi veritabanına eklemek için SQL komutunu oluşturuyoruz
                        String komut = "INSERT INTO tblsefer (KALKIS, VARIS, TARIH, SAAT, KAPTANNO, FIYAT) VALUES (?, ?, ?, ?, ?, ?)";
                        // Hazırlanan SQL komutunu PreparedStatement ile çalıştırmak için bağlantı açıyoruz
                        PreparedStatement ps = con.prepareStatement(komut);
                        // Kullanıcının girdiği verileri parametre olarak bağlıyoruz
                        ps.setString(1, txtKalkis.getText());
                        ps.setString(2, txtVaris.getText());
                        ps.setString(3, mskTarih.getText());
                        ps.setString(4, mskSaat.getText());
                        ps.setString(5, mskKaptanNo.getText());
                        ps.setString(6, txtFiyat.getText());
                        // SQL komutunu çalıştırarak veritabanına yeni seferi ekliyoruz
                        ps.executeUpdate();
                        
                        // Kullanıcıya başarılı bir şekilde sefer oluşturulduğunu belirten bir mesaj gösteriyoruz
                        JOptionPane.showMessageDialog(null, "The voyage has been created.", "Successful", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e1) {
                        // Eğer veritabanı işlemi sırasında bir hata olursa hata mesajını yazdırıyoruz
                        e1.printStackTrace();
                    }
                }
            }
        });
        btnSeferOlustur.setBounds(103, 224, 125, 25);
        panel_2.add(btnSeferOlustur);
        
        JButton btnGirisEkraniDonus = new JButton("Return to home screen");
        btnGirisEkraniDonus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mevcut ekranı gizliyoruz
                setVisible(false);
                // Yeni bir giriş ekranı nesnesi oluşturuluyor
                GirisEkrani ge = new GirisEkrani();
                // Giriş ekranını görünür hale getiriyoruz
                ge.setVisible(true);
            }
        });

        btnGirisEkraniDonus.setBounds(268, 134, 225, 30);
        contentPane.add(btnGirisEkraniDonus);
    }
}
