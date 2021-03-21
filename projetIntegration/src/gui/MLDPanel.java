package gui;


import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import data.Attribute;
import data.Entity;
import data.MLD;
import data.MLDAttribute;


public class MLDPanel extends JPanel{
	
	
    MLDPanel(MLD mld) throws BadLocationException{
    	init(mld);
    	setBackground(Color.white);
    }
    
    
   
    
    
    public void init(MLD mld) throws BadLocationException {
    	JTextPane textPane = new JTextPane();
       
    	textPane.setOpaque(false);
          SimpleAttributeSet attributeSet = new SimpleAttributeSet();
          textPane.setCharacterAttributes(attributeSet, true);
          Font font = new Font("Times New Roman", Font.PLAIN, 18);
          textPane.setFont(font);
          StyledDocument doc = textPane.getStyledDocument();
          
          //JScrollPane scrollPane = new JScrollPane(textPane);
          textPane.setEditable(false);
          add(textPane);
          ArrayList<Entity> ListOfAllEntities= new ArrayList<Entity>();
          ListOfAllEntities=mld.getEntityList();
          int a=0;
          for (Entity e:ListOfAllEntities) {
        	  //mldLabel.setText(mldLabel.getText()+e.getName());
        	  StyleConstants.setBold(attributeSet, true);
        	  doc.insertString(doc.getLength(), e.getName(), attributeSet);
        	  StyleConstants.setBold(attributeSet, false);
        	  doc.insertString(doc.getLength(), "(", attributeSet);
        	ArrayList<Attribute> att= e.getListAttribute();
			for(int i=0;i<att.size();i++) {
				boolean containsNonpk=false;
				a=0;
				
				if (att.get(i) instanceof MLDAttribute) {
					if(((MLDAttribute) att.get(i)).isForeignKey()){
						if(((MLDAttribute) att.get(i)).isPrimaryKey()){
							StyleConstants.setUnderline(attributeSet, true);
							doc.insertString(doc.getLength(), "#"+att.get(i).getName(), attributeSet);
							StyleConstants.setUnderline(attributeSet, false);
							a++;
						}
					}
					else {
						if(((MLDAttribute) att.get(i)).isPrimaryKey()){
							StyleConstants.setUnderline(attributeSet, true);
							doc.insertString(doc.getLength(), att.get(i).getName(), attributeSet);
							StyleConstants.setUnderline(attributeSet, false);
							a++;
							
						}
					}
				}
				else {
					if ((att.get(i))!=null) {
						if((att.get(i)).isPrimaryKey()){
							//StyleConstants.setUnderline(attributeSet, true);
							//doc.insertString(doc.getLength(), att.get(i).getName(), attributeSet);
							//StyleConstants.setUnderline(attributeSet, false);
							//a++;
						}
						else {}
					}
					else {}
				}
				
				for(int k=0;k<att.size();k++) {
					if((att.get(k))!=null) {
						if(!(att.get(k)).isPrimaryKey()){
							containsNonpk=true;
						}
					}

				}
				
				
				if ((a!=0)&&(containsNonpk==true)) {
					doc.insertString(doc.getLength(), ",", attributeSet);
				}
				else if(((a!=0)&&((i+1)<att.size()))) {
					doc.insertString(doc.getLength(), ",", attributeSet);
				}
			}
			for(int j=0;j<att.size();j++) {
				a=0;
				if (att.get(j) instanceof MLDAttribute) {
					if(((MLDAttribute) att.get(j)).isForeignKey()){
						if(!((MLDAttribute) att.get(j)).isPrimaryKey()){
							doc.insertString(doc.getLength(), "#"+att.get(j).getName(), attributeSet);
							a++;
						}
					}
					else {
						if(!((MLDAttribute) att.get(j)).isPrimaryKey()){
							doc.insertString(doc.getLength(), att.get(j).getName(), attributeSet);	
							a++;
						}
					}
				}
				else {
					if ((att.get(j))!=null) {
						if(!(att.get(j)).isPrimaryKey()){
							doc.insertString(doc.getLength(), att.get(j).getName(), attributeSet);
							a++;
						}
					}	
				}
				if(((j+1)<att.size())&&(a!=0)&&(!(att.get(j+1)).isPrimaryKey())) {
					doc.insertString(doc.getLength(), ",", attributeSet);
				}
			}
			doc.insertString(doc.getLength(), ")\n", attributeSet);
          }
    }
    
/**    
	public void init(MLD mld) {
		
		
		FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
		Font font = new Font(Font.MONOSPACED, Font.ITALIC, 15);
		JLabel mldLabel = new JLabel();
		mldLabel.setOpaque(false);
		mldLabel.setFont(font);
		mldLabel.setForeground(Color.BLACK);
		mldLabel.setText("<HTML>");
		add(mldLabel);
		ArrayList<Entity> ListOfAllEntities= new ArrayList<Entity>();
		ListOfAllEntities=mld.getEntityList();
		for (Entity e:ListOfAllEntities) {
			mldLabel.setText(mldLabel.getText()+"<P>");
			mldLabel.setText(mldLabel.getText()+"<B>");
			mldLabel.setText(mldLabel.getText()+e.getName());
			mldLabel.setText(mldLabel.getText()+"</B>");
			mldLabel.setText(mldLabel.getText()+"(");
			ArrayList<Attribute> att= e.getListAttribute();
			for(int i=0;i<att.size();i++) {
				
				if (att.get(i) instanceof MLDAttribute) {
					if(((MLDAttribute) att.get(i)).isForeignKey()){
						if(((MLDAttribute) att.get(i)).isPrimaryKey()){
							mldLabel.setText(mldLabel.getText()+"<U>");
							mldLabel.setText(mldLabel.getText()+"#");
							mldLabel.setText(mldLabel.getText()+att.get(i).getName());
							mldLabel.setText(mldLabel.getText()+"</U>");	
						}
						else {
							mldLabel.setText(mldLabel.getText()+"#");
							mldLabel.setText(mldLabel.getText()+att.get(i).getName());
						}
						//mldLabel.setText(mldLabel.getText()+att.get(i).getName());
					}
					else {
						if(((MLDAttribute) att.get(i)).isPrimaryKey()){
							mldLabel.setText(mldLabel.getText()+"<U>");
							mldLabel.setText(mldLabel.getText()+att.get(i).getName());
							mldLabel.setText(mldLabel.getText()+"</U>");	
							
						}
						else {
							mldLabel.setText(mldLabel.getText()+att.get(i).getName());
						}
					}
				}
				else {
					if((att.get(i)).isPrimaryKey()){
						mldLabel.setText(mldLabel.getText()+"<U>");
						mldLabel.setText(mldLabel.getText()+att.get(i).getName());
						mldLabel.setText(mldLabel.getText()+"</U>");	
					}
					else {
						mldLabel.setText(mldLabel.getText()+att.get(i).getName());
					}
				}
				if((i+1)<att.size()) {
					mldLabel.setText(mldLabel.getText()+",");
				}
				else {}
				
				
				
			}
			mldLabel.setText(mldLabel.getText()+")");
			mldLabel.setText(mldLabel.getText()+"</P>");
			
		}
		mldLabel.setText(mldLabel.getText()+"</HTML>");
		
	}*/




}