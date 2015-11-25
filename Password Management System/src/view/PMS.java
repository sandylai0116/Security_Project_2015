package view;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import control.Operation;

public class PMS {
	private final static String path = "Log.txt";
	public static Operation operation = new Operation(path);
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new PMSCardLayout();
            }
        });
	}
	public static void alertBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
	
}
