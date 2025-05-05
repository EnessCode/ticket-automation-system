package View;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import Helper.DBConnection;
import Helper.Helper;
import Helper.TC;
import Model.Yolcu;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class YolcuIslemleri extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtSeferNo;
    private JTextField txtKoltukNo;
    private JTable table;
    private JTextField txtYolcuTC;
    DefaultTableModel modelim = new DefaultTableModel();
    Object[] kolonlar = {"Voyage No", "Departure", "Arrival", "Date", "Time", "Price"};
    Object[] satırlar = new Object[6];
    private DBConnection conn = new DBConnection();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    YolcuIslemleri frame = new YolcuIslemleri();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public YolcuIslemleri() {
        setTitle("Passenger Procedures");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 919, 355);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(10, 10, 175, 290);
        contentPane.add(panel);

        JButton btnNewButton = new JButton("C");
        btnNewButton.setEnabled(false);
        btnNewButton.setBounds(23, 24, 40, 40);
        panel.add(btnNewButton);

        JButton btn1 = new JButton("1");
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtKoltukNo.setText("1");
            }
        });
        btn1.setBounds(23, 70, 40, 40);
        panel.add(btn1);

        JButton btn2 = new JButton("2");
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtKoltukNo.setText("2");
            }
        });
        btn2.setBounds(68, 70, 40, 40);
        panel.add(btn2);

        JButton btn3 = new JButton("3");
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtKoltukNo.setText("3");
            }
        });
        btn3.setBounds(23, 118, 40, 40);
        panel.add(btn3);

        JButton btn4 = new JButton("4");
        btn4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtKoltukNo.setText("4");
            }
        });
        btn4.setBounds(68, 118, 40, 40);
        panel.add(btn4);

        JButton btn5 = new JButton("5");
        btn5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtKoltukNo.setText("5");
            }
        });
        btn5.setBounds(23, 168, 40, 40);
        panel.add(btn5);

        JButton btn6 = new JButton("6");
        btn6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtKoltukNo.setText("6");
            }
        });
        btn6.setBounds(68, 168, 40, 40);
        panel.add(btn6);

        JButton btn7 = new JButton("7");
        btn7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtKoltukNo.setText("7");
            }
        });
        btn7.setBounds(23, 218, 40, 40);
        panel.add(btn7);

        JButton btn8 = new JButton("8");
        btn8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtKoltukNo.setText("8");
            }
        });
        btn8.setBounds(68, 218, 40, 40);
        panel.add(btn8);

        JButton btn9 = new JButton("9");
        btn9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtKoltukNo.setText("9");
            }
        });
        btn9.setBounds(114, 218, 40, 40);
        panel.add(btn9);

        JPanel panel_1_1_1 = new JPanel();
        panel_1_1_1.setLayout(null);
        panel_1_1_1.setBounds(195, 10, 235, 156);
        contentPane.add(panel_1_1_1);

        JLabel lblNewLabel_1_5 = new JLabel("Voyage Number:");
        lblNewLabel_1_5.setBounds(26, 9, 107, 26);
        panel_1_1_1.add(lblNewLabel_1_5);

        JLabel lblSoyad_1_1 = new JLabel("Passenger TC:");
        lblSoyad_1_1.setBounds(31, 45, 102, 26);
        panel_1_1_1.add(lblSoyad_1_1);

        JLabel lblNewLabel_1_1_1_1 = new JLabel("Seat No:");
        lblNewLabel_1_1_1_1.setBounds(63, 81, 56, 26);
        panel_1_1_1.add(lblNewLabel_1_1_1_1);

        txtSeferNo = new JTextField();
        txtSeferNo.setColumns(10);
        txtSeferNo.setBounds(125, 13, 100, 19);
        panel_1_1_1.add(txtSeferNo);

        JButton btnRezervasyon = new JButton("BUY TICKET");
        btnRezervasyon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Eğer koltuk numarası, sefer numarası veya yolcu TC kimlik numarası alanlarından biri boşsa:
                if (txtKoltukNo.getText().length() == 0 || txtSeferNo.getText().length() == 0 || txtYolcuTC.getText().length() == 0) {
                    Helper.showMsg("fill"); // Helper sınıfındaki "fill" mesajı gösteriliyor.
                } 
                // Eğer tüm alanlar dolu ise:
                else {
                    // Veritabanı bağlantısı yapılır.
                    Connection con = conn.connDB();
                    try {
                        // Sefer numarasına ve koltuk numarasına göre daha önce alınmış olup olmadığını kontrol et
                        String kontrolKomut = "SELECT COUNT(*) FROM tblseferdetay WHERE SEFERNO = ? AND KOLTUK = ?";
                        PreparedStatement pStatement = con.prepareStatement(kontrolKomut);
                        // SQL sorgusunda kullanılan parametreler ayarlanır (sefer numarası ve koltuk numarası).
                        pStatement.setString(1, txtSeferNo.getText());
                        pStatement.setString(2, txtKoltukNo.getText());

                        // SQL sorgusu çalıştırılır ve sonuç alınır.
                        ResultSet rs = pStatement.executeQuery();
                        
                        // Sonuç varsa, koltuğun durumu kontrol edilir.
                        if (rs.next()) {
                            // COUNT(*) değeri alınıp koltuğun daha önce alınıp alınmadığına bakılır.
                            int count = rs.getInt(1);
                            if (count > 0) {
                                // Kullanıcıya "Bu koltuk zaten alınmış" hatası gösterilir.
                                JOptionPane.showMessageDialog(null, "This seat has already been taken.", "ERROR", JOptionPane.ERROR_MESSAGE);
                            } else {
                                // Eğer count == 0 ise, koltuk alınmamış demektir:
                                String komut = "INSERT INTO tblseferdetay (SEFERNO, YOLCUTC, KOLTUK) VALUES (?, ?, ?)";
                                // Yeni rezervasyon işlemi için SQL sorgusu hazırlanır.
                                pStatement = con.prepareStatement(komut);
                                pStatement.setString(1, txtSeferNo.getText());
                                pStatement.setString(2, txtYolcuTC.getText());
                                pStatement.setString(3, txtKoltukNo.getText());
                                // Rezervasyon işlemi veritabanına kaydedilir.
                                pStatement.executeUpdate();

                                // Rezervasyon başarılı olduğunda kullanıcıya bilgilendirme mesajı gösterilir.
                                JOptionPane.showMessageDialog(null, "The voyage has been recorded.", "Successful", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace(); // Eğer bir hata oluşursa, hata mesajı konsola yazdırılır.

                    }
                }
            }
        });
        btnRezervasyon.setBounds(10, 118, 215, 30);
        panel_1_1_1.add(btnRezervasyon);

        txtKoltukNo = new JTextField();
        txtKoltukNo.setColumns(10);
        txtKoltukNo.setBounds(123, 85, 102, 19);
        panel_1_1_1.add(txtKoltukNo);

        Yolcu yolcu = new Yolcu();

        txtYolcuTC = new JTextField();
        txtYolcuTC.setColumns(10);
        txtYolcuTC.setBounds(123, 49, 102, 19);

        // TC bilgisini Helper sınıfından al ve TextField'a yaz
        txtYolcuTC.setText(TC.yolcuTC);
        // Kullanıcı değiştiremesin
        txtYolcuTC.setEditable(false); 
        panel_1_1_1.add(txtYolcuTC);

     // Yeni bir JTable (tablo) nesnesi oluşturuluyor.
        table = new JTable();
        table.setBounds(411, 10, 428, 298);
     // Oluşturulan tablo, contentPane (panel) üzerine ekleniyor.
        contentPane.add(table);
        Connection con = conn.connDB();
        try {
            modelim.setColumnCount(0);
            modelim.setRowCount(0);
            modelim.setColumnIdentifiers(kolonlar);
            // SQL sorgusu çalıştırmak için bir Statement nesnesi oluşturuluyor.
            Statement st = con.createStatement();
            // Veritabanında bulunan "tblsefer" tablosundan tüm veriler seçiliyor.
            ResultSet rs = st.executeQuery("SELECT * FROM tblsefer");
            // Sonuç kümesindeki (ResultSet) her satır için işlem yapılacak.
            while (rs.next()) {
                // "SEFERNO" sütunundan gelen değer satırlar dizisinin ilk elemanına atanıyor.
                satırlar[0] = rs.getString("SEFERNO");
                // "KALKIS" sütunundan gelen değer satırlar dizisinin ikinci elemanına atanıyor.
                satırlar[1] = rs.getString("KALKIS");
                // "VARIS" sütunundan gelen değer satırlar dizisinin üçüncü elemanına atanıyor.
                satırlar[2] = rs.getString("VARIS");
                // "TARIH" sütunundan gelen değer satırlar dizisinin dördüncü elemanına atanıyor.
                satırlar[3] = rs.getString("TARIH");
                // "SAAT" sütunundan gelen değer satırlar dizisinin beşinci elemanına atanıyor.
                satırlar[4] = rs.getString("SAAT");
                // "SAAT" sütunundan gelen değer satırlar dizisinin beşinci elemanına atanıyor.
                satırlar[5] = rs.getString("FIYAT");
                // Yukarıda alınan satır verileri, modelim adlı tablo modeline ekleniyor.
                modelim.addRow(satırlar);
            }
            // Tabloya yeni model (yani sütun başlıkları ve satır verileri) atanıyor.
            table.setModel(modelim);
        } catch (SQLException e1) {
            e1.printStackTrace();    // Eğer bir hata oluşursa, hata mesajı konsola yazdırılır.
        }

        // JTable'i bir JScrollPane içerisine ekleyerek başlıkları görünür hale getiriyoruz.
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(467, 10, 428, 298);
        contentPane.add(scrollPane);
        
        JButton btnBiletlerim = new JButton("MY TICKETS");
        btnBiletlerim.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TC.yolcuTC=txtYolcuTC.getText();
        		Biletlerim biletlerim=new Biletlerim();
        		biletlerim.setVisible(true);
        	}
        });
        btnBiletlerim.setBounds(205, 176, 215, 30);
        contentPane.add(btnBiletlerim);

        // JTable'a MouseListener ekliyoruz
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Tıklanan satırın indeksini alıyoruz
                int row = table.getSelectedRow();

                // Seçilen satırdaki 'Sefer No'yu alıyoruz (ilk sütun)
                String seferNo = table.getValueAt(row, 0).toString();

                // 'txtSeferNo' alanına seçilen sefer numarasını yazdırıyoruz
                txtSeferNo.setText(seferNo);
            }
        });
        
    }
}
