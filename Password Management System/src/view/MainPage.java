package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import model.SubAccount;

public class MainPage extends JPanel {

	private JPanel contentPane;
	private JTable table;
	private JPasswordField keyText;
	private JLabel message;
	private DefaultTableModel model = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row,int column){
			if(column == 2) 
				return false;//the 4th column is not editable
			return true;
		}
	};
	

	public MainPage(JPanel panelPass) { 
		contentPane = panelPass;
		
		Font bold24 = new Font("Times New Roman", Font.BOLD, 24);
		//JPanel panel = new JPanel();
		setBounds(0, 0, 700, 500);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JLabel lblRegistration = new JLabel("Welcome, "+PMS.operation.getUsername());
		lblRegistration.setBounds(161, 2, 438, 28);
		add(lblRegistration);
		lblRegistration.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistration.setFont(bold24);
		
		JLabel keyLabel = new JLabel("Master Key");
		keyLabel.setBounds(14, 40, 80, 25);
		add(keyLabel);
		
		keyText = new JPasswordField();
		keyText.setBounds(104, 40, 344, 25);
		add(keyText);
		
		JButton addButton = new JButton("Add");
		addButton.setBounds(475, 83, 117, 29);
		add(addButton);
		
		JButton updateButton = new JButton("Update");
		updateButton.setBounds(475, 122, 117, 29);
		add(updateButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(475, 161, 117, 29);
		add(deleteButton);
		
		message = new JLabel("");
		message.setForeground(Color.RED);
		message.setBounds(14, 202, 424, 16);
		add(message);
		
        String[] columnsHeader = new String[] {
                "Domain","Username","Passphrase"
            };
 
        model.setColumnIdentifiers(columnsHeader);
        
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane(table);	
		scrollPane.setBounds(14, 79, 438, 227);
		add(scrollPane);
		
		JButton generateButton = new JButton("Generate");
		generateButton.setBounds(475, 38, 117, 29);
		add(generateButton);
		
		JButton saveButton = new JButton("Save Changes");
		saveButton.setBounds(475, 234, 117, 72);
		add(saveButton);

        
        AddButtonListener addListener = new AddButtonListener();
        addButton.addActionListener(addListener);
        
        UpdateButtonListener updateListener = new UpdateButtonListener();

        updateButton.addActionListener(updateListener);
        
        DelButtonListener delListener = new DelButtonListener();
        deleteButton.addActionListener(delListener);
        
        saveButtonListener saveListener = new saveButtonListener();
        saveButton.addActionListener(saveListener);
        
        generateButtonListener generateListener = new generateButtonListener();
        generateButton.addActionListener(generateListener);
   
        RowClickListener rowClickListener = new RowClickListener();
        table.addMouseListener(rowClickListener);
	}
	private class saveButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			message.setText("");
			model = (DefaultTableModel) table.getModel();
			model.addRow(new Object[] {"","",""});
			int row = 0;
			List<SubAccount> subAccount = new ArrayList<SubAccount>();
			while(row < table.getRowCount()){
					SubAccount temp = new SubAccount();
					temp.setDomain(model.getValueAt(row, 0).toString());
					temp.setUsername(model.getValueAt(row, 1).toString());
					subAccount.add(temp);
				row++;
			}
			try {
				PMS.operation.saveChanges(subAccount);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
	}
	private class generateButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			message.setText("");
			model = (DefaultTableModel) table.getModel();
			int row = 0;
			List<SubAccount> subAccount = new ArrayList<SubAccount>();
			while(row < table.getRowCount()){
					SubAccount temp = new SubAccount();
					temp.setDomain(model.getValueAt(row, 0).toString());
					temp.setUsername(model.getValueAt(row, 1).toString());
					subAccount.add(temp);
				row++;
			}
			List<SubAccount> temp = new ArrayList<SubAccount>();
			temp = PMS.operation.keyGen(subAccount,keyText.getPassword().toString(),8);
			row = 0;
			while(row < table.getRowCount()){
				model.setValueAt(temp.get(row).getPassword(), row, 2);
			row++;
		}
		} 
	}
	public void displayPw(){
		
	}
	private class AddButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			message.setText("");
			model = (DefaultTableModel) table.getModel();
			model.addRow(new Object[] {"","",""});
			
		} 
	}
	
	private class UpdateButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			message.setText("");
			model = (DefaultTableModel) table.getModel();
			
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
