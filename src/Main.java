//賴鴻運_107403552_資管三B
import java.awt.Color;

import javax.swing.*;
public class Main {
	static JFrame frame;
	public static void main(String args[]) {
		frame = new JFrame();
		
		int option=JOptionPane.showConfirmDialog(frame, "是否為發布者","登入", JOptionPane.YES_NO_CANCEL_OPTION);
		Contactbook contactbook= new Contactbook(option);
		if (option == 2) {System.exit(0);}
		contactbook.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contactbook.setSize(800, 600); 
		contactbook.setVisible(true); 
	}

}
