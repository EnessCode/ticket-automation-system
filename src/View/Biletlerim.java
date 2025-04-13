//Enes Öner 220610026
//Yusuf Ilıca 220610025
//Mehmet Salih Demirci 220610007
//Göktuğ Çakıroğlu 220611008
//Cafer Aydın 220611035
package View;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import Helper.DBConnection;
import Helper.TC;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Biletlerim extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    DefaultTableModel modelim = new DefaultTableModel();
    Object[] kolonlar = {"Voyage No", "Departure", "Arrival", "Date", "Time", "Seat No","Price"};
    Object[] satırlar = new Object[7];
    private DBConnection conn = new DBConnection();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Biletlerim frame = new Biletlerim();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Biletlerim() {
        setTitle("Biletlerim");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Sadece bu pencereyi kapat
        setBounds(100, 100, 551, 320);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        table = new JTable();
        table.setBounds(10, 10, 517, 204);
        contentPane.add(table);

        // Bağlantı nesnesini oluşturuyoruz ve veritabanı bağlantısını sağlıyoruz
        Connection con = conn.connDB();
        try {
            // JTable modelini temizliyoruz, böylece eski veriler varsa temizlenip yenisi eklenebilir
            modelim.setColumnCount(0);  // Sütun sayısını sıfırlıyoruz
            modelim.setRowCount(0);     // Satır sayısını sıfırlıyoruz
            modelim.setColumnIdentifiers(kolonlar); // Yeni sütun başlıklarını set ediyoruz
            
            // Yolcunun TC bilgisini statik bir değişken üzerinden alıyoruz
            String yolcuTC = TC.yolcuTC; // 'TC' sınıfından statik olarak yolcunun TC bilgisini alıyoruz
            // Yolcuya ait sefer bilgilerini almak için SQL sorgusunu oluşturuyoruz
            
            String query = "SELECT * FROM tblsefer "
                         + "INNER JOIN tblseferdetay ON tblsefer.SEFERNO = tblseferdetay.SEFERNO "
                         + "WHERE tblseferdetay.YOLCUTC = ?"; // YOLCUTC'yi bağlamak için parametreyi kullanıyoruz
            // PreparedStatement ile parametreli SQL sorgusu hazırlanıyor
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, yolcuTC); // Yolcu TC'sini sorguya parametre olarak bağlıyoruz
            // Sorguyu çalıştırıp sonuçları ResultSet ile alıyoruz
            ResultSet rs = pstmt.executeQuery();
            // Sonuçları tabloya ekliyoruz
            while (rs.next()) {
                // Veritabanından alınan her satır için, ilgili bilgileri satırlara atıyoruz
                satırlar[0] = rs.getString("SEFERNO");  
                satırlar[1] = rs.getString("KALKIS");   
                satırlar[2] = rs.getString("VARIS");    
                satırlar[3] = rs.getString("TARIH");    
                satırlar[4] = rs.getString("SAAT");     
                satırlar[5] = rs.getString("KOLTUK");   
                satırlar[6] = rs.getString("FIYAT");    
                // Her bir satırı tabloya ekliyoruz
                modelim.addRow(satırlar);
            }
            // Son olarak modelimizi table'a atıyoruz, böylece yeni veriler görünür hale gelir
            table.setModel(modelim);
        } catch (SQLException e1) {
            e1.printStackTrace(); // Eğer SQL sorgusunda bir hata olursa, hata mesajını yazdırıyoruz
        }


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 517, 204);
        contentPane.add(scrollPane);

        JButton btnIptal = new JButton("Cancel");
        btnIptal.setBounds(180, 224, 159, 49);
        contentPane.add(btnIptal);

        // İptal Et butonu
        btnIptal.addActionListener(e -> {
            // JTable'den seçilen satırın indeksini alıyoruz
            int selectedRow = table.getSelectedRow();
            
            // Eğer hiçbir satır seçilmediyse, hata mesajı gösteriyoruz
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select the ticket you wish to cancel.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return; // Seçili satır olmadığı için işlemi sonlandırıyoruz
            }

            // Seçilen satırdaki Sefer No ve Koltuk No'yu alıyoruz
            String seferNo = (String) modelim.getValueAt(selectedRow, 0);  // Sefer No'yu alıyoruz (0. sütun)
            String koltukNo = (String) modelim.getValueAt(selectedRow, 5); // Koltuk No'yu alıyoruz (5. sütun)

            try {
                // İlgili kaydı silmek için SQL sorgusu hazırlıyoruz
                String delete = "DELETE FROM tblseferdetay WHERE SEFERNO = ? AND KOLTUK = ?";
                PreparedStatement ps = con.prepareStatement(delete);
                ps.setString(1, seferNo); // Sefer No'yu parametre olarak bağlıyoruz
                ps.setString(2, koltukNo); // Koltuk No'yu parametre olarak bağlıyoruz

                // SQL sorgusunu çalıştırıyoruz ve etkilenen satır sayısını alıyoruz
                int rowsAffected = ps.executeUpdate();

                // Eğer silme işlemi başarılı olduysa, kullanıcıya başarılı mesajı gösteriyoruz ve tabloyu güncelliyoruz
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "The ticket was cancelled.", "SUCCESSFUL", JOptionPane.INFORMATION_MESSAGE);
                    modelim.removeRow(selectedRow); // Tablodan silinen satırı kaldırıyoruz
                } else {
                    // Eğer herhangi bir satır silinmediyse, hata mesajı gösteriyoruz
                    JOptionPane.showMessageDialog(null, "There was a problem canceling the ticket.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                
                // Bu formu gizliyoruz, diğer form açık kalır
                setVisible(false); 

            } catch (SQLException ex) {
                ex.printStackTrace(); // Veritabanı hatası oluşursa, hata mesajını ekrana yazdırıyoruz
            }
        });

    }
}
