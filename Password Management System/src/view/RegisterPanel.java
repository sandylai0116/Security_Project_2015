package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
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
import javax.swing.SwingUtilities;

import control.Operation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class RegisterPanel extends JPanel {
	
	private JPanel contentPane;
	private JTextField textField_username;
	private JPasswordField passwordField;
	private JTextArea textArea;
	/**
	 * Create the panel.
	 */
	public RegisterPanel(JPanel panel) {
		setLayout(new BorderLayout());
		contentPane = panel;
		
		Font bold24 = new Font("Times New Roman", Font.BOLD, 24);
		
		JPanel topJPanel = new JPanel();
		
		JTextArea title = new JTextArea();
		title.setBackground(SystemColor.control);
		title.setEditable(false);
		title.setFont(bold24);
		title.setText("Registration");
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
		setText("Register a new account.");
		
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
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setFont(bold24);
		btnPanel.add(btnRegister);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(bold24);
		btnPanel.add(btnBack);
		
		leftJPanel.add(textArea);
		leftJPanel.add(usernamePanel);
		leftJPanel.add(passwordPanel);
		leftJPanel.add(btnPanel);
		
		add(topJPanel, BorderLayout.NORTH);
		add(keyPadJPanel, BorderLayout.EAST);
		add(leftJPanel, BorderLayout.CENTER);
		
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
	
	private class RegisterButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			final String username = textField_username.getText();
			String password = String.valueOf(passwordField.getPassword());
			if(username.isEmpty() || password.isEmpty())
				PMS.alertBox("Please enter username or password.","Alert!");
			else {
				if (password.length()>=4) {
					if (PMS.operation.isUsernameValid(username)){
							PMS.operation.register(username, password);
							MainPage mainPage = new MainPage(contentPane);
							contentPane.add(mainPage, "mainPage");
							CardLayout cardLayout = (CardLayout) contentPane.getLayout();
							cardLayout.show(contentPane, "mainPage");
							contentPane.remove(0);
							/*SwingUtilities.getWindowAncestor(contentPane).dispose();
							EventQueue.invokeLater(new Runnable() {
								public void run() {
									try {
										MainPage mainPage = new MainPage(username);
										mainPage.setVisible(true);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});	*/
					} else
						PMS.alertBox("Username exists!\nTry another one","Alert!");
				}  else 
					PMS.alertBox("Minimum password length = 4","Invalid Length");
			}
		} 
	} 
	
	private class BackButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			LoginPanel loginPanel = new LoginPanel(contentPane);
			contentPane.add(loginPanel, "Register");
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
