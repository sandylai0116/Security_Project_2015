package view;

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
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPasswordField;

public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField textField_username;
	private JPasswordField passwordField;
	private JTextArea textArea;
	private ArrayList<JButton> keys;
	private JButton clear;
	
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
		
		Font bold24 = new Font("Times New Roman", Font.BOLD, 24);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 216, 225, 35);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 143, 225, 37);
		contentPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 179, 225, 37);
		contentPane.add(panel_2);
		
		JPanel keyPadJPanel = new JPanel();
		keyPadJPanel.setBounds(237, 48, 187, 203);
		contentPane.add(keyPadJPanel);
		keyPadJPanel.setLayout(new GridLayout(4, 3, 2, 2));	
		
		JLabel lblRegistration = new JLabel("Registration");
		lblRegistration.setFont(bold24);
		lblRegistration.setBounds(159, 10, 127, 28);
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

		keys = new ArrayList<JButton>();
		for (int i = 0; i < 10; i++)
			keys.add(new JButton(String.valueOf(i)));
		Collections.shuffle(keys);
		for (int i = 0; i < 10; i++){
			JButton btn = keys.get(i);
			btn.setFont(bold24);
			keyPadJPanel.add(btn);
		}
		
		clear = new JButton("C");
		clear.setFont(bold24);
		keyPadJPanel.add(clear);
		
		textField_username = new JTextField();
		panel_1.add(textField_username);
		textField_username.setColumns(13);
		
		passwordField = new JPasswordField();
		passwordField.setEditable(false);
		panel_2.add(passwordField);
		passwordField.setColumns(13);
		
		textArea = new JTextArea();
		textArea.setBackground(SystemColor.control);
		textArea.setForeground(Color.BLACK);
		textArea.setEditable(false);
		textArea.setBounds(10, 48, 225, 98);
		textArea.setFont(new Font("Times New Roma", Font.PLAIN, 14));
		textArea.setLineWrap(true); 
		textArea.setWrapStyleWord(true);
		contentPane.add(textArea);
		setText("Register a new account.");
		
		RegisterButtonListener registerHandler = new RegisterButtonListener();
		btnRegister.addActionListener(registerHandler);
		BackButtonListener handler = new BackButtonListener();
		btnBack.addActionListener(handler);
		KeypadButtonHandler keypadHandler = new KeypadButtonHandler();
		for (int i = 0; i <= 9; i++)
			keys.get(i).addActionListener(keypadHandler);
		ClearButtonListener handler3 = new ClearButtonListener();
		clear.addActionListener(handler3);
	}
	
	private void setText(String message){
		textArea.setText(message);
	}
	
	private class KeypadButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JButton source = (JButton) event.getSource();
			String text = source.getText();
			passwordField.setText(String.valueOf(passwordField.getPassword()) + text);
		} 
	} 
	
	private class ClearButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			passwordField.setText(""); 
		} 
	}
	
	private class RegisterButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String username = textField_username.getText();
			String password = String.valueOf(passwordField.getPassword());
			if(username.isEmpty() || password.isEmpty())
				setText("Please enter username or password.");
			else
				setText("");
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
