import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

//*************************************************************************
// EnigmaDriver.java       Author: Huff                     5/6/2016
//
// Sets up GUI with correct size and in the middle of the screen
//*************************************************************************
public class EnigmaDriver {

	//********************************************************************
	// Creates a JFrame and adds the GUI
	//********************************************************************
	public static void main (String[] args){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame frame = new JFrame ("EnigmaMachine");
		frame.setSize(500, 400);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new EnigmaGUI());
		frame.pack();
		int x = (int) ((screenSize.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((screenSize.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
		frame.setVisible(true);
	}
}
