package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

public class MainPage extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField usernameText;
	private JTextField domainText;
	private JTextField passphraseText;
	private JPasswordField keyText;
	private JLabel message;
	private DefaultTableModel model = new DefaultTableModel() {
	    @Override
	    public boolean isCellEditable(int row, int column) {
	       //all cells false
	       return false;
	    }
	};
	
	/**
	 * Create the frame.
	 * @param username 
	 */
	public MainPage(String username) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Password Management System");  
		setBounds(100, 100, 454, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Font bold24 = new Font("Times New Roman", Font.BOLD, 24);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 450, 430);
		panel.setLayout(null);
		contentPane.add(panel);
		
		JLabel lblRegistration = new JLabel("Welcome, "+username);
		lblRegistration.setBounds(0, 0, 438, 28);
		panel.add(lblRegistration);
		lblRegistration.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistration.setFont(bold24);
		
		JLabel keyLabel = new JLabel("Master Key");
		keyLabel.setBounds(14, 40, 80, 25);
		panel.add(keyLabel);
		
		keyText = new JPasswordField();
		keyText.setBounds(104, 40, 300, 25);
		panel.add(keyText);
		
		JLabel domainLabel = new JLabel("Domain");
		domainLabel.setBounds(14, 70, 80, 25);
		panel.add(domainLabel);
		
		domainText = new JTextField();
		domainText.setBounds(104, 70, 300, 25);
		panel.add(domainText);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(14, 100, 80, 25);
		panel.add(usernameLabel);
		
		usernameText = new JTextField();
		usernameText.setBounds(104, 100, 300, 25);
		panel.add(usernameText);
		
		JLabel passphraseLabel = new JLabel("Passphrase");
		passphraseLabel.setBounds(14, 130, 80, 25);
		panel.add(passphraseLabel);
		
		passphraseText = new JTextField();
		passphraseText.setBounds(104, 130, 300, 25);
		panel.add(passphraseText);
		
		JButton addButton = new JButton("Add");
		addButton.setBounds(10, 167, 117, 29);
		panel.add(addButton);
		
		JButton updateButton = new JButton("Update");
		updateButton.setBounds(147, 167, 117, 29);
		panel.add(updateButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(287, 167, 117, 29);
		panel.add(deleteButton);
		
		message = new JLabel("");
		message.setForeground(Color.RED);
		message.setBounds(14, 202, 424, 16);
		panel.add(message);
		
        String[] columnsHeader = new String[] {
                "Domain","Username","Passphrase"
            };
 
        model.setColumnIdentifiers(columnsHeader);
        
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane(table);	
		scrollPane.setBounds(14, 230, 424, 182);
		panel.add(scrollPane);
            
        AddButtonListener addListener = new AddButtonListener();
        addButton.addActionListener(addListener);
        
        UpdateButtonListener updateListener = new UpdateButtonListener();

        updateButton.addActionListener(updateListener);
        
        DelButtonListener delListener = new DelButtonListener();
        deleteButton.addActionListener(delListener);
   
        RowClickListener rowClickListener = new RowClickListener();
        table.addMouseListener(rowClickListener);
	}
	
	private class AddButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			message.setText("");
			model = (DefaultTableModel) table.getModel();
			if (!domainText.getText().isEmpty() && !usernameText.getText().isEmpty() && !passphraseText.getText().isEmpty()) {
				model.addRow(new Object[] {domainText.getText(),usernameText.getText(),passphraseText.getText()});
			} else {
				message.setText("Please enter every field.");
			}
		} 
	}
	
	private class UpdateButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			message.setText("");
			model = (DefaultTableModel) table.getModel();
			if (table.getSelectedRow() == -1) {
				if (table.getRowCount() == 0 ) {
					message.setText("Table is empty.");
				} else {
					message.setText("Please select an row to update.");
				}
			} else {
				if (!domainText.getText().isEmpty() && !usernameText.getText().isEmpty() && !passphraseText.getText().isEmpty()) {
					model.setValueAt(domainText.getText(), table.getSelectedRow(), 0);
					model.setValueAt(usernameText.getText(), table.getSelectedRow(), 1);
					model.setValueAt(passphraseText.getText(), table.getSelectedRow(), 2);
				} else {
					message.setText("Please enter every field.");
				}
			}
		} 
	}
	
	private class RowClickListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent event) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			message.setText("");
			model = (DefaultTableModel) table.getModel();
			if (table.getSelectedRow() == -1) {
				if (table.getRowCount() == 0 ) {
					message.setText("Table is empty.");
				} else {
					message.setText("Please select an row to update.");
				}
			} else {
				domainText.setText(model.getValueAt(table.getSelectedRow(), 0).toString());
				usernameText.setText(model.getValueAt(table.getSelectedRow(), 1).toString());
				passphraseText.setText(model.getValueAt(table.getSelectedRow(), 2).toString());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	} 

	private class DelButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			message.setText("");
			model = (DefaultTableModel) table.getModel();
			if (table.getSelectedRow() == -1) {
				if (table.getRowCount() == 0 ) {
					message.setText("Table is empty.");
				} else {
					message.setText("Please select an row to delete.");
				}
			} else {
				model.removeRow(table.getSelectedRow());
			}
		} 
	}
}
