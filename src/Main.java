//���E�B_107403552_��ޤTB
import java.awt.Color;

import javax.swing.*;
public class Main {
	static JFrame frame;
	public static void main(String args[]) {
		frame = new JFrame();
		
		int option=JOptionPane.showConfirmDialog(frame, "�O�_���o����","�n�J", JOptionPane.YES_NO_CANCEL_OPTION);
		Contactbook contactbook= new Contactbook(option);
		if (option == 2) {System.exit(0);}
		contactbook.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contactbook.setSize(800, 600); 
		contactbook.setVisible(true); 
	}

}
