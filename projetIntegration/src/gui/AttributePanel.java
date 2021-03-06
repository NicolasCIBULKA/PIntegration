package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import data.Attribute;

/**
 * AttributePanel is a JPanel containing the dynamic JTable which reprensents
 * all attributes of an entity or association
 * 
 * @author Yann Barrachina
 *
 */
public class AttributePanel extends JPanel {
	private JPanel mainPanel;

	private JButton addButton = new JButton("+");
	private JButton deleteButton = new JButton("-");

	private ButtonGroup radioGroupPK;

	private JTable mainTable;
	private DefaultTableModel model;
	private JScrollPane jsp;
	private Dimension d = new Dimension(500, 350);

	private int index = 0;

	private ArrayList<Attribute> attributeList;

	public AttributePanel() {
		mainPanel = this;
		attributeList = new ArrayList<Attribute>();

		initEntityTable();

		initActions();
		
	}

	private void initEntityTable() {
		String columnHeader[] = { "Nom", "Type", "is Null", "Primary Key", "is Unique" };
		Object[][] data = {};
		model = new DefaultTableModel(data, columnHeader);

		mainTable = new JTable(model) {
			@Override
			public Class<? extends Object> getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return Boolean.class;
				case 3:
					return JRadioButton.class;
				case 4:
					return Boolean.class;
				default:
					return null;
				}

			}
		};

		TableColumn typeColumn = mainTable.getColumnModel().getColumn(3);
		typeColumn.setCellRenderer(new RadioButtonRenderer());
		typeColumn.setCellEditor(new RadioButtonEditor(new JCheckBox()));
		
		JPanel jpTable = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		mainPanel.removeAll();
		mainPanel.validate();
		
		jsp = new JScrollPane(mainTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setPreferredSize(d);
		jpTable.add(jsp);
		mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jpTable.setBorder(BorderFactory.createTitledBorder("Attributs"));
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(jpTable, BorderLayout.CENTER);

		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);

		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		
		mainPanel.revalidate();
		mainPanel.repaint();
		
	}

	private void initAssociationTable() {
		String columnHeader[] = { "Nom", "Type", "is Null", "is Unique" };
		Object[][] data = {};
		model = new DefaultTableModel(data, columnHeader);

		mainTable = new JTable(model) {
			@Override
			public Class<? extends Object> getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return Boolean.class;
				case 3:
					return Boolean.class;
				default:
					return null;
				}

			}
		};
		
		JPanel jpTable = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		mainPanel.removeAll();
		mainPanel.validate();
		
		jsp = new JScrollPane(mainTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setPreferredSize(d);
		jpTable.add(jsp);
		mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jpTable.setBorder(BorderFactory.createTitledBorder("Attributs"));
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(jpTable, BorderLayout.CENTER);

		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);

		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		
		mainPanel.revalidate();
		mainPanel.repaint();
		
	}

	private void initActions() {
		addButton.addActionListener(new AddAttributeAction());
		deleteButton.addActionListener(new DeleteAttributeAction());
	}

	public void setPanelState(Boolean isEntity) {
		if (isEntity) {
			initEntityTable();
		} else {
			initAssociationTable();
		}
	}

	/**
	 * Updates the attributeList by reading all the rows. All user's modifications
	 * are registered into the list of attributes by retrieving the rows datas.
	 */
	public void updateAttributeList() {
		int rowCount = model.getRowCount();
		ArrayList<Attribute> newAttributeList = new ArrayList<Attribute>();
		if (model.getColumnCount() == 4) {
			for (int index = 0; index < rowCount; index++) {
				newAttributeList.add(new Attribute((String) model.getValueAt(index, 0),
						(String) model.getValueAt(index, 1), (Boolean) model.getValueAt(index, 2), (Boolean) false,
						(Boolean) model.getValueAt(index, 3)));
			}
		} else {
			for (int index = 0; index < rowCount; index++) {
				newAttributeList.add(new Attribute((String) model.getValueAt(index, 0),
						(String) model.getValueAt(index, 1), (Boolean) model.getValueAt(index, 2),
						(Boolean) ((JRadioButton) model.getValueAt(index, 3)).isSelected(),
						(Boolean) model.getValueAt(index, 4)));
			}
		}

		attributeList = newAttributeList;
	}

	/**
	 * @param attributeList the attributeList to set
	 */
	public void setAttributeList(ArrayList<Attribute> attributeList) {
		this.attributeList = attributeList;
		model.setRowCount(0);

		if (model.getColumnCount() == 4) {
			for (Attribute attribute : attributeList) {
				Vector<Object> attributeVector = new Vector<Object>();
				attributeVector.add(attribute.getName());
				attributeVector.add(attribute.getType());
				attributeVector.add(attribute.isNullable());
				attributeVector.add(attribute.isUnique());

				model.addRow(attributeVector);
			}
			
		} else {
			radioGroupPK = new ButtonGroup();

			for (Attribute attribute : attributeList) {
				Vector<Object> attributeVector = new Vector<Object>();
				attributeVector.add(attribute.getName());
				attributeVector.add(attribute.getType());
				attributeVector.add(attribute.isNullable());

				JRadioButton rb = new JRadioButton();
				if (attribute.isPrimaryKey()) {
					rb.setSelected(true);
				} else {
					rb.setSelected(false);
				}
				radioGroupPK.add(rb);
				attributeVector.add(rb);
				attributeVector.add(attribute.isUnique());

				model.addRow(attributeVector);
			}
		}

		// Notifying table that datas are uploaded into the table's model
		model.fireTableDataChanged();
	}

	/**
	 * @return the addButton
	 */
	public JButton getAddButton() {
		return addButton;
	}

	/**
	 * @return the deleteButton
	 */
	public JButton getDeleteButton() {
		return deleteButton;
	}

	/**
	 * @return the attributeList
	 */
	public ArrayList<Attribute> getAttributeList() {
		return attributeList;
	}

	/**
	 * Creates then adds a blank attribute to the attribute list and the mainTable.
	 */
	public class AddAttributeAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			String newName = "" + index++;
			Attribute newAttribute;
			Vector<Object> attributeVector = new Vector<Object>();

			if (model.getColumnCount() == 4) {
				attributeVector.add(newName);
				attributeVector.add("int");
				attributeVector.add(false);
				attributeVector.add(false);
				
				newAttribute = new Attribute(newName, "int", false, false, false);
				
			}else {
				attributeVector.add(newName);
				attributeVector.add("int");
				attributeVector.add(false);

				JRadioButton rb = new JRadioButton();
				if (attributeList.size() == 0) {
					newAttribute = new Attribute(newName, "int", false, true, false);
					rb.setSelected(true);
				} else {
					newAttribute = new Attribute(newName, "int", false, false, false);
					rb.setSelected(false);
				}
				radioGroupPK.add(rb);
				attributeVector.add(rb);
				attributeVector.add(false);
			}
		

			attributeList.add(newAttribute);

			model.addRow(attributeVector);
			model.fireTableDataChanged();
			repaint();
		}
	}

	/**
	 * Deleting selected attribute from attribute list and mainTable.
	 */
	public class DeleteAttributeAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = mainTable.getSelectedRow();
			attributeList.remove(selectedRow);
			model.removeRow(selectedRow);
			model.fireTableDataChanged();
			repaint();
		}
	}

	class RadioButtonRenderer implements TableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (value == null)
				return null;
			return (Component) value;
		}
	}

	class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
		private JRadioButton button;

		public RadioButtonEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (value == null)
				return null;
			button = (JRadioButton) value;
			button.addItemListener(this);
			return (Component) value;
		}

		public Object getCellEditorValue() {
			button.removeItemListener(this);
			return button;
		}

		public void itemStateChanged(ItemEvent e) {
			super.fireEditingStopped();
		}
	}

}
