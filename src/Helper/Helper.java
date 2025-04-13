//Enes Öner 220610026
//Yusuf Ilıca 220610025
//Mehmet Salih Demirci 220610007
//Göktuğ Çakıroğlu 220611008
//Cafer Aydın 220611035

package Helper;
import javax.swing.JOptionPane;

public class Helper {
	public static void showMsg(String str)
	{
		String msg;
		
		switch(str) {
		case "fill":
			msg = "Please fill out all fields completely..";
			break;
			default:
				msg = str;
		}
		JOptionPane.showMessageDialog(null, msg,"Message", JOptionPane.INFORMATION_MESSAGE);
	}
}
