package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class LoginPanel extends JPanel {

	private JPanel contentPane;
	private JTextField textField_username;
	private JPasswordField passwordField;
	private JTextArea textArea;
	
	/**
	 * Create the panel.
	 */
	public LoginPanel(JPanel panel) {
		setLayout(new BorderLayout());
		contentPane = panel;
		
		Font bold24 = new Font("Times New Roman", Font.BOLD, 24);
		
		JPanel topJPanel = new JPanel();
		
		JTextArea title = new JTextArea();
		title.setEditable(false);
		title.setBackground(SystemColor.control);
		title.setFont(bold24);
		title.setText("Login");
		topJPanel.add(title);
		
		JPanel keyPadJPanel = new JPanel();
		keyPadJPanel.setLayout(new GridLayout(4, 3, 2, 2));
		keyPadJPanel.setPreferredSize(new Dimension(300, 300));
		
		ArrayList<JButton> keys = new ArrayList<JButton>();
		for (int i = 0; i < 10; i++)
			keys.add(new JButton(String.valueOf(i)));
		Collections.shuffle(keys);
		for (int i = 0; i < 10; i++){
			JButton btn = keys.get(i);
			btn.setFont(bold24);
			keyPadJPanel.add(btn);
		}
		
		JButton clear = new JButton("C");
		clear.setFont(bold24);
		keyPadJPanel.add(clear);
		
		JPanel leftJPanel = new JPanel();
		leftJPanel.setLayout(new BoxLayout(leftJPanel, BoxLayout.Y_AXIS));

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBackground(new Color(240,240,240));
		textArea.setFont(bold24);
		textArea.setLineWrap(true); 
		textArea.setWrapStyleWord(true);
		setText("Welcome to Password Management System!");
		
		JPanel usernamePanel = new JPanel();
		
		JLabel lblUsername = new JLabel("Username");
		usernamePanel.add(lblUsername);
		
		textField_username = new JTextField();
		textField_username.setColumns(20);
		usernamePanel.add(textField_username);
		
		JPanel passwordPanel = new JPanel();
		
		JLabel lblPassword = new JLabel("Password");
		passwordPanel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setEditable(false);
		passwordField.setColumns(20);
		passwordPanel.add(passwordField);
		
		JPanel btnPanel = new JPanel();
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(bold24);
		btnPanel.add(btnLogin);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setFont(bold24);
		btnPanel.add(btnRegister);
		
		leftJPanel.add(textArea);
		leftJPanel.add(usernamePanel);
		leftJPanel.add(passwordPanel);
		leftJPanel.add(btnPanel);
		
		add(topJPanel, BorderLayout.NORTH);
		add(keyPadJPanel, BorderLayout.EAST);
		add(leftJPanel, BorderLayout.CENTER);
		
		LoginButtonListener loginHandler = new LoginButtonListener();
		btnLogin.addActionListener(loginHandler);
		RegisterButtonListener registerHandler = new RegisterButtonListener();
		btnRegister.addActionListener(registerHandler);
		KeypadButtonHandler keypadHandler = new KeypadButtonHandler();
		for (int i = 0; i <= 9; i++)
			keys.get(i).addActionListener(keypadHandler);
		ClearButtonListener handler3 = new ClearButtonListener();
		clear.addActionListener(handler3);
	}
	
	private void setText(String message){
		textArea.setText(message);
	}
	
	private class LoginButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String username = textField_username.getText();
			String password = String.valueOf(passwordField.getPassword());
			if(username.isEmpty() || password.isEmpty())
				setText("Please enter username or password.");

		} 
	} 
	
	private class RegisterButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			RegisterPanel registerPanel = new RegisterPanel(contentPane);
			contentPane.add(registerPanel, "Register");
			CardLayout cardLayout = (CardLayout) contentPane.getLayout();
			cardLayout.show(contentPane, "Register");
			contentPane.remove(0);
		} 
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
}
