package data;

import java.util.ArrayList;

public class MLD {
	/*
	 * 
	 */
	
	// Data
	
	private ArrayList<Entity> entityList;

	// Methods
	
	public MLD(ArrayList<Entity> entityList) {
		super();
		this.entityList = entityList;
	}

	public ArrayList<Entity> getEntityList() {
		return entityList;
	}

	public void setEntityList(ArrayList<Entity> entityList) {
		this.entityList = entityList;
	}
	
	
	
}
