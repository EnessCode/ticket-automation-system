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
