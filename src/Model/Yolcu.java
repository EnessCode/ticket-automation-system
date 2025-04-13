//Enes Öner 220610026
//Yusuf Ilıca 220610025
//Mehmet Salih Demirci 220610007
//Göktuğ Çakıroğlu 220611008
//Cafer Aydın 220611035
package Model;

import javax.swing.JOptionPane;

import View.YolcuIslemleri;

public class Yolcu extends Kullanici{

	public Yolcu(int id, String tc, String sifre, String isim) {
		super(id, tc, sifre, isim);
	}
	
	public Yolcu() {}
	
    @Override
    public void girisYap() {
        JOptionPane.showMessageDialog(null, "Passenger login successful.");
        // Yolcu işlemleri ekranını burada çağırabilirsiniz.
        YolcuIslemleri yolcuIslem = new YolcuIslemleri();
        yolcuIslem.setVisible(true);
    }
}
