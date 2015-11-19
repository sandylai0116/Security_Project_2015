package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;

public class Login extends JFrame {

	private JPanel contentPane;
	private ArrayList<JButton> keys;
	private JTextField textField_username;
	private JPasswordField passwordField;
	private JTextArea textArea;
	private JButton clear;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Password Management System");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel keyPadJPanel = new JPanel();
		keyPadJPanel.setBounds(237, 78, 187, 173);
		contentPane.add(keyPadJPanel);
		keyPadJPanel.setLayout(new GridLayout(4, 3, 2, 2));
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 216, 227, 35);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 181, 227, 35);
		contentPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 145, 227, 35);
		contentPane.add(panel_2);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblLogin.setBounds(186, 22, 69, 35);
		contentPane.add(lblLogin);
		
		JButton btnLogin = new JButton("Login");
		panel.add(btnLogin);
		
		JLabel lblUsername = new JLabel("Username");
		panel_2.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		panel_1.add(lblPassword);
		
		JButton btnRegister = new JButton("Register");
		panel.add(btnRegister);
		
		textField_username = new JTextField();
		panel_2.add(textField_username);
		textField_username.setColumns(13);
		
		passwordField = new JPasswordField();
		passwordField.setEditable(false);
		panel_1.add(passwordField);
		passwordField.setColumns(13);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBackground(SystemColor.control);
		textArea.setBounds(10, 78, 227, 67);
		textArea.setFont(new Font("Times New Roma", Font.PLAIN, 14));
		textArea.setLineWrap(true); 
		textArea.setWrapStyleWord(true);
		contentPane.add(textArea);
		setText("Welcome to Password Management System!");
		
		keys = new ArrayList<JButton>();
		for (int i = 0; i < 10; i++)
			keys.add(new JButton(String.valueOf(i)));
		Collections.shuffle(keys);
		for (int i = 0; i < 10; i++){
			JButton btn = keys.get(i);
			btn.setFont(new Font("Times New Roman", Font.PLAIN, 24));
			keyPadJPanel.add(btn);
		}
		
		clear = new JButton("C");
		clear.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		keyPadJPanel.add(clear);
		
		RegisterButtonListener registerHandler = new RegisterButtonListener();
		btnRegister.addActionListener(registerHandler);
		LoginButtonListener loginHandler = new LoginButtonListener();
		btnLogin.addActionListener(loginHandler);
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
	
	private class RegisterButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			dispose();
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Register frame = new Register();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} 
	} 
	
	private class LoginButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String username = textField_username.getText();
			String password = String.valueOf(passwordField.getPassword());
			if(username.isEmpty() || password.isEmpty())
				setText("Please enter username or password.");

		} 
	} 
	
	private class ClearButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			passwordField.setText(""); 
		} 
	}
}
