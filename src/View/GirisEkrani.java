package View;
import java.awt.EventQueue;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.*;
import Model.Kullanici;
import Model.Yolcu;
import Model.Yonetici;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class GirisEkrani extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtYolcuTC;
	private JTextField txtYoneticiTC;
	private JPasswordField passwordFieldYonetici;
	private JPasswordField passwordFieldYolcu;
	private DBConnection conn=new DBConnection();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GirisEkrani frame = new GirisEkrani();
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
	public GirisEkrani() {
		setResizable(false);
		setTitle("Ticket Application");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome to Ticket Automation System");
		lblNewLabel.setFont(new Font("Sylfaen", Font.BOLD, 20));
		lblNewLabel.setBounds(55, 54, 405, 69);
		contentPane.add(lblNewLabel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 133, 466, 270);
		contentPane.add(tabbedPane);
		
		JPanel panelYolcuGiris = new JPanel();
		panelYolcuGiris.setBackground(Color.WHITE);
		tabbedPane.addTab("Passenger Login", null, panelYolcuGiris, null);
		panelYolcuGiris.setLayout(null);
		
		JLabel lblTcNumara = new JLabel("TC number:");
		lblTcNumara.setFont(new Font("Sylfaen", Font.BOLD, 20));
		lblTcNumara.setBounds(40, 35, 140, 69);
		panelYolcuGiris.add(lblTcNumara);
		
		JLabel lblifre = new JLabel("Password:");
		lblifre.setFont(new Font("Sylfaen", Font.BOLD, 20));
		lblifre.setBounds(61, 88, 104, 69);
		panelYolcuGiris.add(lblifre);
		
		txtYolcuTC = new JTextField();
		txtYolcuTC.setFont(new Font("Sylfaen", Font.PLAIN, 16));
		txtYolcuTC.setBounds(175, 49, 241, 42);
		panelYolcuGiris.add(txtYolcuTC);
		txtYolcuTC.setColumns(10);
		
		JButton btnYolcuKayıt = new JButton("Register");
		btnYolcuKayıt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				YolcuKayit yk=new YolcuKayit();
				yk.setVisible(true);
			}
		});
		btnYolcuKayıt.setFont(new Font("Sylfaen", Font.PLAIN, 18));
		btnYolcuKayıt.setBounds(175, 153, 115, 42);
		panelYolcuGiris.add(btnYolcuKayıt);
		
		JButton btnYolcuGiris = new JButton("Login"); 
		btnYolcuGiris.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) { 
		        
		        // Kullanıcının TC kimlik numarası veya şifre alanlarından biri boşsa:
		        if (txtYolcuTC.getText().length() == 0 || passwordFieldYolcu.getText().length() == 0) {
		            Helper.showMsg("fill"); // Helper sınıfında yer alan ve kullanıcıyı bilgilendiren bir mesaj gösteriliyor.

		            
		            // Kullanıcının girdiği TC kimlik numarası 11 haneli değilse:
		        } else if (txtYolcuTC.getText().length() != 11) {
		            // Hata mesajı gösteriliyor.
		            JOptionPane.showMessageDialog(null, "Incorrect entry! Please enter your TC number.", "ERROR", JOptionPane.ERROR_MESSAGE);
		            
		            // Eğer yukarıdaki kontroller geçildiyse, veritabanı bağlantısı kurularak işlemlere geçiliyor.
		        } else {
		            // DBConnection sınıfından conn nesnesiyle veritabanı bağlantısı açılıyor.
		            Connection con = conn.connDB();
		            
		            try {
		                // SQL komutlarını çalıştırmak için bir Statement nesnesi oluşturuluyor.
		                Statement st2 = con.createStatement(); 
		                
		                // "tblyolcu" tablosundaki tüm kayıtlar çekiliyor.
		                ResultSet rs2 = st2.executeQuery("Select * from tblyolcu");
		                
		                // Kullanıcının veritabanında bulunup bulunmadığını kontrol etmek için bir değişken.
		                boolean isUserFound = false; 

	                    // Veritabanı üzerinde her bir yolcu kaydını kontrol eden döngü.
		                while (rs2.next()) { 
	                        
		                	// Eğer girilen TC ve şifre, veritabanındaki bir kayıtla eşleşiyorsa:
		                    if (txtYolcuTC.getText().equals(rs2.getString("YOLCUTC")) 
		                        && passwordFieldYolcu.getText().equals(rs2.getString("YOLCUSIFRE"))) {
		                        
		                    	// Kullanıcı bulundu olarak işaretleniyor.
		                        isUserFound = true; 
		                        
		                        // Polymorphism ile Yolcu nesnesi oluşturuluyor.
		                        // Kullanici tipinde bir yolcu nesnesi oluşturuluyor. Yolcu sınıfı, Kullanici sınıfından türetilmiştir.
		                        Kullanici yolcu = new Yolcu();
		                        
		                        // Yolcu nesnesine, veritabanından gelen TC ve şifre bilgileri atanıyor.
		                        yolcu.setTc(rs2.getString("YOLCUTC"));
		                        yolcu.setSifre(rs2.getString("YOLCUSIFRE"));
		                        TC.yolcuTC = txtYolcuTC.getText();
		                        
		                        // Yolcu sınıfındaki girisYap() metodunu çağırarak giriş işlemi gerçekleştirilir.
		                        yolcu.girisYap(); 
		                        
		                        // Bu penceriyi kapatır, çünkü kullanıcı başarılı şekilde giriş yaptı.
		                        dispose(); 
		                        
		                        // Döngüden çıkılır çünkü kullanıcı bulundu.
		                        break; 
		                    }
		                }
	                    
		                // Eğer hiç bir kayıt eşleşmediyse, kullanıcıya yanlış bilgi verildiği bildirilir.
		                if (!isUserFound) {
		                    // Hata mesajı gösterilir.
		                    JOptionPane.showMessageDialog(null, "TC number or password is incorrect!", "ERROR", JOptionPane.ERROR_MESSAGE);
		                }
		                
		                // Veritabanı ile ilgili kaynaklar kapatılır.
		                rs2.close();
		                st2.close();
		                
		            } catch (SQLException e1) {
		                e1.printStackTrace(); // SQL hatası oluşursa hata mesajı yazdırılır.

		            }
		        }
		    }
		});

		btnYolcuGiris.setFont(new Font("Sylfaen", Font.PLAIN, 18));
		btnYolcuGiris.setBounds(300, 153, 115, 42);
		panelYolcuGiris.add(btnYolcuGiris);
		
		passwordFieldYolcu = new JPasswordField();
		passwordFieldYolcu.setBounds(175, 100, 241, 42);
		panelYolcuGiris.add(passwordFieldYolcu);
		
		JPanel panelYoneticiGiris = new JPanel();
		panelYoneticiGiris.setBackground(Color.WHITE);
		tabbedPane.addTab("Admin Login", null, panelYoneticiGiris, null);
		panelYoneticiGiris.setLayout(null);
		
		JLabel lblTcNumara_1 = new JLabel("Username:");
		lblTcNumara_1.setFont(new Font("Sylfaen", Font.BOLD, 20));
		lblTcNumara_1.setBounds(48, 35, 121, 69);
		panelYoneticiGiris.add(lblTcNumara_1);
		
		txtYoneticiTC = new JTextField();
		txtYoneticiTC.setFont(new Font("Sylfaen", Font.PLAIN, 16));
		txtYoneticiTC.setColumns(10);
		txtYoneticiTC.setBounds(175, 49, 241, 42);
		panelYoneticiGiris.add(txtYoneticiTC);
		
		JLabel lblifre_1 = new JLabel("Password:");
		lblifre_1.setFont(new Font("Sylfaen", Font.BOLD, 20));
		lblifre_1.setBounds(61, 89, 90, 69);
		panelYoneticiGiris.add(lblifre_1);
		
		JButton btnYoneticiGiris = new JButton("Login");
		btnYoneticiGiris.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
	            // Eğer kullanıcı TC veya şifre alanlarını boş bırakmışsa:
		        if (txtYoneticiTC.getText().length() == 0 || passwordFieldYonetici.getText().length() == 0) {
		            Helper.showMsg("fill"); // Helper sınıfında yer alan ve kullanıcıyı bilgilendiren bir mesaj gösteriliyor.
		            
		        // Eğer alanlar boş değilse, veritabanı bağlantısı kurarak işlemlere geçiliyor.
		        } else {
		            // DBConnection sınıfından conn nesnesiyle veritabanı bağlantısı açılıyor.
		            Connection con = conn.connDB();
		            try {
		                // SQL komutlarını çalıştırmak için bir Statement nesnesi oluşturuluyor.
		                Statement st = con.createStatement();
		                // Kullanıcının girdiği TC ve şifreyi veritabanında sorgulayan SQL sorgusu.
		                ResultSet rs = st.executeQuery("SELECT * FROM tblyonetici WHERE TC = '" + txtYoneticiTC.getText() +
		                                               "' AND SIFRE = '" + passwordFieldYonetici.getText() + "'");
	                    // Eğer SQL sorgusundan dönen veri varsa kullanıcı bulunduysa:
		                if (rs.next()) {
		                    // Polymorphism ile Kullanici tipinde bir yinetici nesnes, oluşturuluyor. Yonetici sınıfı, Kullanici sınıfından türetilmiştir.
		                    Kullanici yonetici = new Yonetici();
		                    // Yonetici nesnesine, veritabanından gelen TC ve şifre bilgileri atanıyor.
		                    yonetici.setTc(rs.getString("TC"));
		                    yonetici.setSifre(rs.getString("SIFRE"));
		                    // Yonetici sınıfındaki girisYap() metodunu çağırarak giriş işlemi gerçekleştirilir.
		                    yonetici.girisYap();
		                    // Bu penceriyi kapatır çünkü yönetici başarılı şekilde giriş yaptı.
		                    dispose();
		                // Eğer kullanıcı veritabanında bulunmazsa:
		                } else {
		                    // Hata mesajı gösterilir.
		                    JOptionPane.showMessageDialog(null, "Username or password is incorrect!", "ERROR", JOptionPane.ERROR_MESSAGE);
		                }
		            } catch (SQLException e1) {
		                e1.printStackTrace(); // SQL hatası oluşursa, hata mesajı yazdırılır.
		            }
		        }
		    }
		});



		btnYoneticiGiris.setFont(new Font("Sylfaen", Font.PLAIN, 18));
		btnYoneticiGiris.setBounds(175, 153, 240, 42);
		panelYoneticiGiris.add(btnYoneticiGiris);
		
		passwordFieldYonetici = new JPasswordField();
		passwordFieldYonetici.setBounds(175, 101, 241, 42);
		panelYoneticiGiris.add(passwordFieldYonetici);
	}
}
