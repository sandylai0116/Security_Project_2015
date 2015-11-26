package view;

import java.awt.CardLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PMSCardLayout extends WindowAdapter{
	
	private JPanel contentPane;
	private LoginPanel loginPanel;
	
	public PMSCardLayout(){

		JFrame frame = new JFrame("Password Management System");
        frame.setSize(700, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new CardLayout());
        loginPanel = new LoginPanel(contentPane);
        
        contentPane.add(loginPanel, "Login"); 
        
        frame.setContentPane(contentPane);      
        frame.pack();   
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.addWindowListener(this);
        if (!PMS.operation.init()){
        	PMS.alertBox("Log file is damaged.\nPlease remove the log.txt file", "Error!");
        	System.exit(0);
        }
	}
	private Timer timer;
	public void windowActivated(WindowEvent e) {
		if (timer != null){
			timer.cancel();
			timer=null;
		}
	}
	public void windowDeactivated(WindowEvent e) {
		timer = new Timer();
		timer.schedule(new TimerTask(){
			public void run(){
				PMS.alertBox("PMS Losing focus too long\nForce Exit", "Away From Keyboard");
				System.exit(0);
			}
		}, 20000);
    }
	
}
