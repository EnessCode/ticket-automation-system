//Enes Öner 220610026
//Yusuf Ilıca 220610025
//Mehmet Salih Demirci 220610007
//Göktuğ Çakıroğlu 220611008
//Cafer Aydın 220611035
package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.DBConnection;
import Helper.Helper;
import Model.Yonetici;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class YolcuKayit extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel YolcuKayitEkrani;
	private JTextField txtYolcuKayitIsim;
	private JTextField txtYolcuKayitTC;
	private JTextField txtYolcuKayitSifre;
	private DBConnection conn=new DBConnection();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YolcuKayit frame = new YolcuKayit();
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
	public YolcuKayit() {
		setTitle("Passenger Registration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 370);
		YolcuKayitEkrani = new JPanel();
		YolcuKayitEkrani.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(YolcuKayitEkrani);
		YolcuKayitEkrani.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NAME SURNAME:");
		lblNewLabel.setFont(new Font("Sylfaen", Font.PLAIN, 20));
		lblNewLabel.setBounds(27, 80, 181, 49);
		YolcuKayitEkrani.add(lblNewLabel);
		
		JLabel lblTc = new JLabel("TC:");
		lblTc.setFont(new Font("Sylfaen", Font.PLAIN, 20));
		lblTc.setBounds(156, 122, 40, 49);
		YolcuKayitEkrani.add(lblTc);
		
		JLabel lblifre = new JLabel("PASSWORD:");
		lblifre.setFont(new Font("Sylfaen", Font.PLAIN, 20));
		lblifre.setBounds(79, 164, 139, 49);
		YolcuKayitEkrani.add(lblifre);
		
		txtYolcuKayitIsim = new JTextField();
		txtYolcuKayitIsim.setBounds(214, 80, 215, 32);
		YolcuKayitEkrani.add(txtYolcuKayitIsim);
		txtYolcuKayitIsim.setColumns(10);
		
		txtYolcuKayitTC = new JTextField();
		txtYolcuKayitTC.setColumns(10);
		txtYolcuKayitTC.setBounds(214, 122, 215, 32);
		YolcuKayitEkrani.add(txtYolcuKayitTC);
		
		txtYolcuKayitSifre = new JTextField();
		txtYolcuKayitSifre.setColumns(10);
		txtYolcuKayitSifre.setBounds(214, 164, 215, 32);
		YolcuKayitEkrani.add(txtYolcuKayitSifre);
		
		JButton btnKayıt = new JButton("Register");
		btnKayıt.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
	            // Eğer isim, şifre veya TC kimlik numarası alanları boşsa:
		        if (txtYolcuKayitIsim.getText().length()==0||txtYolcuKayitSifre.getText().length()==0||txtYolcuKayitTC.getText().length()==0) {
		            Helper.showMsg("fill"); // Helper sınıfındaki "fill" mesajı gösteriliyor.
		        } 
	            // Eğer TC kimlik numarası 11 haneli değilse:
		        else if (txtYolcuKayitTC.getText().length() != 11) 
		        {
		            // Kullanıcıya 11 haneli TC numarası girmesi gerektiği belirtiliyor.
		            JOptionPane.showMessageDialog(null, "The TC number must be 11 digits.", "ERROR", JOptionPane.ERROR_MESSAGE);
		        } 
	            // Eğer tüm alanlar dolu ve TC 11 haneli ise:
		        else {
		            Connection con = conn.connDB();
		            try {
		                // Yolcu bilgilerini "tblyolcu" tablosuna eklemek için SQL komutu oluşturuluyor.
		                String komut = "insert into tblyolcu (YOLCUISIM,YOLCUTC,YOLCUSIFRE) values (?,?,?)";
		                // SQL komutunu çalıştırmak için PreparedStatement nesnesi oluşturuluyor.
		                PreparedStatement ps = con.prepareStatement(komut);
		                // Yolcu ismi "YOLCUISIM" alanına ekleniyor.
		                ps.setString(1, txtYolcuKayitIsim.getText());
		                // Yolcu TC kimlik numarası "YOLCUTC" alanına ekleniyor.
		                ps.setString(2, txtYolcuKayitTC.getText());
		                // Yolcu şifresi "YOLCUSIFRE" alanına ekleniyor.
		                ps.setString(3, txtYolcuKayitSifre.getText());
		                // Veritabanına kayıt işlemi yapılıyor.
		                ps.executeUpdate();
		                // Kayıt işlemi başarılı olduğunda bir bilgilendirme mesajı gösteriliyor.
		                JOptionPane.showMessageDialog(null, "Passenger registration has been made.", "Successful", JOptionPane.INFORMATION_MESSAGE);
		                // Mevcut ekran kapanıyor.
		                setVisible(false);
		                // Yeni bir giriş ekranı nesnesi oluşturuluyor.
		                GirisEkrani ge=new GirisEkrani();
		                // Giriş ekranı görünür hale getiriliyor.
		                ge.setVisible(true);
		                } catch (SQLException e1) {
		                e1.printStackTrace();// SQL hatası oluşursa hata mesajı konsola yazdırılıyor.
		            }
		        }
		    }
		});

		btnKayıt.setFont(new Font("Sylfaen", Font.PLAIN, 18));
		btnKayıt.setBounds(214, 206, 215, 42);
		YolcuKayitEkrani.add(btnKayıt);
	}
}
