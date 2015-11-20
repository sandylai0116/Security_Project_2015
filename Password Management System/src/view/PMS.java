package view;

import javax.swing.SwingUtilities;

public class PMS {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new PMSCardLayout();
            }
        });
	}
}
