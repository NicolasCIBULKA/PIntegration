package test;

import java.util.ArrayList;

import data.Association;
import data.Attribute;
import data.Cardinality;
import data.Entity;
import exceptions.ExistingEdgeException;
import exceptions.InvalidNodeLinkException;
import exceptions.NullNodeException;
import process.MCDManaging;

public class TestCommit {
	
	public static void main(String[] args) throws InvalidNodeLinkException {
		
		MCDManaging manager = new MCDManaging();
//		MLDManaging m = new MLDManaging();
//		MLD mld = new MLD();

		
		
		// Entities
		Attribute at1 = new Attribute("Attribute 1","String",false,true,false);
		Attribute at2 = new Attribute("Attribute 2","int",false,false,false);
		Attribute at3 = new Attribute("Attribute 3","String",false,false,false);
		Attribute at4 = new Attribute("Attribute 4","int",false,true,false);
		Attribute at5 = new Attribute("Attribute 5","float",false,false,false);
		Attribute at6 = new Attribute("Attribute 6","int",false,true,false);
		Attribute at7 = new Attribute("Attribute 7","float",false,false,false);
		Attribute at8 = new Attribute("Attribute 8","int",false,true,false);
		Attribute at9 = new Attribute("Attribute 9","float",false,true,false);
		Attribute at10 = new Attribute("Attribute 10","int",false,false,false);
		
		ArrayList<Attribute> liste1 = new ArrayList<Attribute>();
		liste1.add(at1);
		liste1.add(at2);
		ArrayList<Attribute> liste2 = new ArrayList<Attribute>();
		liste2.add(at3);
		liste2.add(at4);
		ArrayList<Attribute> liste3 = new ArrayList<Attribute>();
		liste3.add(at5);
		liste3.add(at6);
		ArrayList<Attribute> liste4 = new ArrayList<Attribute>();
		liste4.add(at7);
		liste4.add(at8);
		ArrayList<Attribute> liste5 = new ArrayList<Attribute>();
		liste5.add(at9);
		liste5.add(at10);
		
		
		Entity e1 = new Entity("Entité 1", liste1);
		Entity e2 = new Entity("Entité 2", liste2);
		Entity e3 = new Entity("Entité 3", liste3);
		Entity e4 = new Entity("Entité 4", liste4);
		Entity e5 = new Entity("Entité 5", liste5);
		
		
		Cardinality ce1_a1= new Cardinality("0","N",e1.getName());
		Cardinality ce2_a1= new Cardinality("2","2",e2.getName());
		Cardinality ce3_a1= new Cardinality("2","N",e3.getName());
		
		Cardinality ce1_a2= new Cardinality("0","N",e1.getName());
		Cardinality ce4_a2= new Cardinality("1","N",e4.getName());
		
		
		Cardinality ce1_a3= new Cardinality("0","1",e1.getName());
		Cardinality ce5_a3= new Cardinality("1","1",e5.getName());
		
		
		
		ArrayList< Cardinality> card = new ArrayList< Cardinality>();
		card.add(ce1_a1);
		card.add(ce2_a1);
		card.add(ce2_a1);
		
		ArrayList< Cardinality> card2 = new ArrayList< Cardinality>();
		card2.add(ce1_a2);
		card2.add(ce4_a2);
		
		ArrayList< Cardinality> card3 = new ArrayList< Cardinality>();
		card3.add(ce1_a3);
		card2.add(ce5_a3);
		
		
		Association a1 = new Association("Association1", new ArrayList<Attribute>(), card);
		Association a2 = new Association("Association2", new ArrayList<Attribute>(), card2);
		Association a3 = new Association("Association3", new ArrayList<Attribute>(), card3);
		
		
		
		manager.addNode(e1);
		manager.addNode(e2);
		manager.addNode(e3);
		manager.addNode(a1);
		manager.addNode(e4);
		manager.addNode(a2);
		manager.addNode(a3);
		manager.addNode(e5);
		//m.addkeytoAssociation(manager);
		//System.out.println(mld.getEntityList().isEmpty());
		try {
			manager.connectNodes(e1, a1,ce1_a1);
			manager.connectNodes(e2, a1,ce2_a1);
			manager.connectNodes(e3, a1,ce3_a1);
			
			manager.connectNodes(e1, a2,ce1_a2);
			manager.connectNodes(e4, a2,ce4_a2);
			
			manager.connectNodes(e1, a3,ce1_a3);
			manager.connectNodes(e5, a3,ce5_a3);
		} catch (NullNodeException | ExistingEdgeException e) {
			
			e.printStackTrace();
		}
		
		
		
		
//How to use MLD Managing		
/**
		System.out.println(manager.isCorrectlyBuild());
		ArrayList<Entity> ListOfAllEntities= new ArrayList<Entity>();
		m.newMld(manager);
		mld=m.getMLD();
		ListOfAllEntities=mld.getEntityList();
		
		for (Entity e:ListOfAllEntities) {
			System.out.println(e.getName());
			ArrayList<Attribute> att= e.getListAttribute();
			for(int i=0;i<att.size();i++) {
				MLDAttribute cat = new MLDAttribute(null, null, false, false, false, false, null, null);
				
				if (att.get(i) instanceof MLDAttribute) {
					System.out.println("--"+att.get(i).getName());
					System.out.println("--"+((MLDAttribute) att.get(i)).isForeignKey());
				}
				else {
					System.out.println(att.get(i).getName());
				}

			}
			
		}**/
		
		
		
		
		

		
	}
}
