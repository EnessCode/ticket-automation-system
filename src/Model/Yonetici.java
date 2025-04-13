//Enes Öner 220610026
//Yusuf Ilıca 220610025
//Mehmet Salih Demirci 220610007
//Göktuğ Çakıroğlu 220611008
//Cafer Aydın 220611035
package Model;

import javax.swing.JOptionPane;

import View.YoneticiIslemleri;

public class Yonetici extends Kullanici{

	public Yonetici(int id, String tc, String sifre, String isim) {
		super(id, tc, sifre, isim);
	}

	public Yonetici() {};
	
    @Override
    public void girisYap() {
        JOptionPane.showMessageDialog(null, "Passenger login successful.");
        // Yönetici işlemleri ekranını burada çağırabilirsiniz.
        YoneticiIslemleri yoneticiIslem = new YoneticiIslemleri();
        yoneticiIslem.setVisible(true);
    }

}
