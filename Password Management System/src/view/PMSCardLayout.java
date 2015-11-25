package view;

import java.awt.CardLayout;

import control.Operation;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PMSCardLayout {
	
	private JPanel contentPane;
	private LoginPanel loginPanel;
	
	public PMSCardLayout(){

		JFrame frame = new JFrame("Password Management System");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new CardLayout());
        loginPanel = new LoginPanel(contentPane);
        
        contentPane.add(loginPanel, "Login"); 
        
        frame.setContentPane(contentPane);      
        frame.pack();   
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        if (!PMS.operation.init()){
        	PMS.alertBox("Log file is damaged.\nPlease remove the log.txt file", "Error!");
        	System.exit(0);
        }
	}
	
}
