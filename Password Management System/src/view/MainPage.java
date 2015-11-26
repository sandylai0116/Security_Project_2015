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

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

import model.SubAccount;

import javax.swing.JSpinner;

public class MainPage extends JPanel {

	private JPanel contentPane;
	private JTable table;
	private JPasswordField keyText;
	private JLabel message;
	private JSpinner spinner;
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
		lblRegistration.setBounds(14, 2, 680, 28);
		add(lblRegistration);
		lblRegistration.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistration.setFont(bold24);
		
		JLabel keyLabel = new JLabel("Master Key");
		keyLabel.setBounds(14, 40, 64, 25);
		add(keyLabel);
		
		keyText = new JPasswordField();
		keyText.setBounds(86, 40, 223, 25);
		add(keyText);
		
		JButton addButton = new JButton("Add");
		addButton.setBounds(475, 83, 117, 29);
		add(addButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(475, 122, 117, 29);
		add(deleteButton);
		
		message = new JLabel("");
		message.setForeground(Color.RED);
		message.setBounds(14, 63, 424, 16);
		add(message);
		
        String[] columnsHeader = new String[] {
                "Domain","Username","Password"
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
		
		spinner = new JSpinner();
		spinner.setBounds(417, 40, 35, 28);
		add(spinner);
		SpinnerModel model = new SpinnerNumberModel(8, 8, 20,1);
		spinner.setModel(model);
		
		JLabel lengthlabel = new JLabel("Password length");
		lengthlabel.setBounds(319, 40, 100, 25);
		add(lengthlabel);

        
        AddButtonListener addListener = new AddButtonListener();
        addButton.addActionListener(addListener);
        
        DelButtonListener delListener = new DelButtonListener();
        deleteButton.addActionListener(delListener);
        
        saveButtonListener saveListener = new saveButtonListener();
        saveButton.addActionListener(saveListener);
        
        generateButtonListener generateListener = new generateButtonListener();
        generateButton.addActionListener(generateListener);
   
        RowClickListener rowClickListener = new RowClickListener();
        table.addMouseListener(rowClickListener);
        
        
        DefaultCellEditor singleclick = new DefaultCellEditor(new JTextField());
        singleclick.setClickCountToStart(1);

        //set the editor as default on every column
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.setDefaultEditor(table.getColumnClass(i), singleclick);
        } 
	}
	public boolean checkFieldEmpty(){
		if (table.isEditing())
		    table.getCellEditor().stopCellEditing();
		model = (DefaultTableModel) table.getModel();
		int row = 0;
		boolean empty = false;
		if (table.getRowCount()==0)
			PMS.alertBox("You have no domain list ", "Error");
		else{
			while(row < table.getRowCount()){
				if (model.getValueAt(row, 0).toString()==""){
					empty = true;
				}
					
				if (model.getValueAt(row, 1).toString()==""){
					empty = true;
				}
					
			row++;
			}
		}
		if (empty == true)
			PMS.alertBox("Missing domain information or username", "Missing fields");
		if (String.valueOf(keyText)==null){
			empty = true;
			PMS.alertBox("You don't have a master key", "Missing fields");
		}
			
		return empty;
	}
	private class saveButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (!checkFieldEmpty())
			{
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
				try {
					PMS.operation.saveChanges(subAccount);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} 
	}
	private class generateButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (!checkFieldEmpty())
			{
				message.setText("");
				model = (DefaultTableModel) table.getModel();
				int row = 0;
				List<SubAccount> subAccount = new ArrayList<SubAccount>();
				int length = (int) spinner.getValue();
				while(row < table.getRowCount()){
						SubAccount temp = new SubAccount();
						temp.setDomain(model.getValueAt(row, 0).toString());
						temp.setUsername(model.getValueAt(row, 1).toString());
						subAccount.add(temp);
					row++;
				}
				List<SubAccount> temp = new ArrayList<SubAccount>();
				temp = PMS.operation.keyGen(subAccount,String.valueOf(keyText.getPassword()),length);
				row = 0;
				while(row < table.getRowCount()){
					model.setValueAt(temp.get(row).getPassword(), row, 2);
					row++;
				}
			}
		} 
	}
	private class AddButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			message.setText("");
			model = (DefaultTableModel) table.getModel();
			model.addRow(new Object[] {"","",""});
			
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
