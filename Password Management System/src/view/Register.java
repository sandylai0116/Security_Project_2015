package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JPasswordField;

public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField textField_username;
	private JPasswordField passwordField;
	private JTextArea textArea;
	
	/**
	 * Create the frame.
	 */
	public Register() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Password Management System");  
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 216, 414, 35);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 143, 414, 37);
		contentPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 179, 414, 37);
		contentPane.add(panel_2);
		
		JLabel lblRegistration = new JLabel("Registration");
		lblRegistration.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblRegistration.setBounds(158, 10, 127, 28);
		contentPane.add(lblRegistration);
		
		JLabel lblUsername = new JLabel("Username");
		panel_1.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		panel_2.add(lblPassword);
		
		JButton btnRegister = new JButton("Register");
		panel.add(btnRegister);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel.add(btnBack);
		
		textField_username = new JTextField();
		panel_1.add(textField_username);
		textField_username.setColumns(15);
		
		passwordField = new JPasswordField();
		panel_2.add(passwordField);
		passwordField.setColumns(15);
		
		textArea = new JTextArea();
		textArea.setBackground(SystemColor.control);
		textArea.setForeground(Color.LIGHT_GRAY);
		textArea.setEditable(false);
		textArea.setBounds(10, 72, 414, 74);
		textArea.setFont(new Font("Times New Roma", Font.PLAIN, 14));
		textArea.setLineWrap(true); 
		textArea.setWrapStyleWord(true);
		contentPane.add(textArea);
		setText("Please enter Username and Password(number only)");
		
		RegisterButtonListener registerHandler = new RegisterButtonListener();
		btnRegister.addActionListener(registerHandler);
		BackButtonListener handler = new BackButtonListener();
		btnBack.addActionListener(handler);
	}
	
	private void setText(String message){
		textArea.setText(message);
	}
	
	private class RegisterButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String username = textField_username.getText();
			String password = String.valueOf(passwordField.getPassword());
			if(username.isEmpty() || password.isEmpty())
				setText("Please enter username or password.");
			if(!password.matches("[0-9]"))
				setText("Password can contain only number.");
		} 
	} 
	
	private class BackButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			dispose();
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Login frame = new Login();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});	
		} 
	} 
}
