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
