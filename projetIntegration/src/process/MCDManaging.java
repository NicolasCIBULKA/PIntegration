package process;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.AbstractGraphIterator;
import org.jgrapht.traverse.BreadthFirstIterator;

import data.Association;
import data.Attribute;
import data.Cardinality;
import data.Entity;
import data.MCD;
import data.Node;
import exceptions.EdgesNotLinkedException;
import exceptions.ExistingEdgeException;
import exceptions.InvalidNodeLinkException;
import exceptions.NullNodeException;

public class MCDManaging {

	/*
	 * This class will regroup all methods to manage the MCD
	 */

	// Data

	private MCD mcd;

	// Methods

	public MCDManaging() {
		this.mcd = new MCD();
	}

	// Method to add a Node to the MCD
	public void addNode(Node node) {
		if (!mcd.getMCDGraph().containsVertex(node));
		mcd.getMCDGraph().addVertex(node);
	}


	// Connect two Nodes
	public void connectNodes(Node firstNode, Node secondNode, Cardinality cardinality)
			throws NullNodeException, ExistingEdgeException, InvalidNodeLinkException {
		if (mcd.getMCDGraph().containsVertex(firstNode) && mcd.getMCDGraph().containsVertex(secondNode)) {
			if (/*mcd.getMCDGraph().containsEdge(firstNode, secondNode)*/ mcd.getMCDGraph().getAllEdges(firstNode,secondNode).size() > 2) {
				// Error - Edge already exists
				throw new ExistingEdgeException("Error - maximum number of Edges already exists between those two Nodes");
			}
			if ((firstNode instanceof Entity && secondNode instanceof Association)
					|| (firstNode instanceof Association && secondNode instanceof Entity)) {
				mcd.getMCDGraph().addEdge(firstNode, secondNode);
				if (secondNode instanceof Association) {
					((Association) secondNode).getCardinalityList().add(cardinality);
				} else {
					((Association) firstNode).getCardinalityList().add(cardinality);
				}
			} else {
				throw new InvalidNodeLinkException("Error - Tried to link 2 Entities or 2 Associations together !");
			}

		} else {
			// Error - nodes aren't in the MCD Graph
			throw new NullNodeException("Error - One of the Nodes called for connecting does not exists");
		}

	}

	// Destroy Edge between two nodes
	public void disconnectNodes(Node firstNode, Node secondNode) throws NullNodeException, EdgesNotLinkedException {
		if (mcd.getMCDGraph().containsVertex(firstNode) && mcd.getMCDGraph().containsVertex(secondNode)) {
			if (!mcd.getMCDGraph().containsEdge(firstNode, secondNode)) {
				throw new EdgesNotLinkedException("Error - No Edge between those two Nodes");
			}
			mcd.getMCDGraph().removeAllEdges(firstNode, secondNode);
			Cardinality card = null;
			if (secondNode instanceof Association) {
				for (Cardinality cardinality : ((Association) secondNode).getCardinalityList()) {
					if (cardinality.getNomEntity() == firstNode.getName()) {
						card = cardinality;
					}
				}
				((Association) secondNode).getCardinalityList().remove(card);
			} else {
				for (Cardinality cardinality : ((Association) firstNode).getCardinalityList()) {
					if (cardinality.getNomEntity() == secondNode.getName()) {
						card = cardinality;
					}
				}
				((Association) firstNode).getCardinalityList().remove(card);
			}
		} else {
			// Error - nodes aren't in the MCD Graph
			throw new NullNodeException("Error - One of the Nodes called for disconnecting does not exists");
		}
	}
	
	

	// get a Node from the name
	public Node getNodeFromName(String name) {
		AbstractGraphIterator<Node, DefaultEdge> iterator = new BreadthFirstIterator<>(mcd.getMCDGraph());
		while (iterator.hasNext()) {
			Node currentNode = iterator.next();
			if (currentNode.getName().equals(name)) {
				return currentNode;
			}
		}
		return null;
	}

	// Remove a Node of the MCD
	public void removeNode(Node node) throws NullNodeException {
		List<DefaultEdge> toBeRemovedEdge = new ArrayList<>();
		List<Node> toBeRemovedNode = new ArrayList<>();
		if (mcd.getMCDGraph().containsVertex(node)) {
			Set<DefaultEdge> edgeSet = mcd.getMCDGraph().edgesOf(node);
			for (DefaultEdge edge : edgeSet) {
				toBeRemovedEdge.add(edge);
				//mcd.getMCDGraph().removeEdge(edge);
			}
			toBeRemovedNode.add(node);
			//mcd.getMCDGraph().removeVertex(node);
		} else {
			// Error - Node is not ine the Graph
			throw new NullNodeException("Error - The node is not in the MCD");
		}
		
		// removing elements
		for(DefaultEdge edge1 : toBeRemovedEdge) {
			mcd.getMCDGraph().removeEdge(edge1);
		}
		for(Node node1 : toBeRemovedNode) {
			mcd.getMCDGraph().removeVertex(node1);
		}
	}

	// Testing the existence of all the entities in the list
	public boolean nodeListContainedInMCD(List<Entity> entityList) {
		int i = 0;
		while (i < entityList.size()) {
			if (!mcd.getMCDGraph().containsVertex(entityList.get(i))) {
				return false;
			}
			i++;
		}
		return true;
	}

	// testing if the MCD Graph is well build, ie correctly alternate entities and
	// associations
	// return true if graph is correct, false if not
	public boolean isCorrectlyBuild() {
		AbstractGraphIterator<Node, DefaultEdge> iterator = new BreadthFirstIterator<>(mcd.getMCDGraph());
		while (iterator.hasNext()) {
			Node currentNode = iterator.next();
			List<Node> connectedNodes = Graphs.neighborListOf(mcd.getMCDGraph(), currentNode);
			for (Node connectedNode : connectedNodes) {
				if (connectedNode instanceof Entity) {
					if (currentNode instanceof Entity) {
						return false;
					}
				} else if (connectedNode instanceof Association) {
					if (currentNode instanceof Association) {
						return false;
					}
				}
			}

		}
		return true;
	}

	// This function test the fact if all the Nodes are well formed in term of
	// attributes.
	// It returns a list a non conform Nodes
	public List<Node> correctKeyMCD() {
		AbstractGraphIterator<Node, DefaultEdge> iterator = new BreadthFirstIterator<>(mcd.getMCDGraph());
		List<Node> notWellFormed = new ArrayList<Node>();
		while (iterator.hasNext()) {
			Node currentNode = iterator.next();
			List<Node> connectedNodes = Graphs.neighborListOf(mcd.getMCDGraph(), currentNode);
			for (Node connectedNode : connectedNodes) {
				// Checking the existence of PK in the current node and acting depending of the
				// type
				int nbPK = 0;
				for (Attribute attributVerif : connectedNode.getListAttribute()) {
					if (attributVerif.isPrimaryKey()) {
						nbPK += 1;
					}
				}
				if (connectedNode instanceof Entity) {
					if ((nbPK > 1) && (nbPK == 0)) {
						notWellFormed.add(connectedNode);
					}
				} else if (connectedNode instanceof Association) {
					if (nbPK > 0) {
						notWellFormed.add(connectedNode);
					}
				}
			}

		}
		return notWellFormed;
	}

	/**
	 * When an entity is renamed, this method, only usable with an entity as
	 * target, updates all occurences of its name in its association neighbour's
	 * cardinalityLists. Without this, the software do not find the corresponding
	 * entity anymore and do not display its associated cardinality.
	 * 
	 * @author Yann Barrachina
	 * 
	 * @param entityName    the entity which name has to be changed
	 * @param newEntityName the new name of the entity
	 */
	public void updateEntityNameInCardinalities(String entityName, String newEntityName) {
		// Ultimate precaution
		if (getNodeFromName(entityName) instanceof Entity) {

			List<Node> connectedNodes = Graphs.neighborListOf(mcd.getMCDGraph(), getNodeFromName(entityName));
			for (Node connectedNode : connectedNodes) {

				// For each cardinality in each neighbour (which are associations), updating name
				for (Cardinality cardinality : ((Association) connectedNode).getCardinalityList()) {
					if (cardinality.getNomEntity().equals(entityName)) {
						cardinality.setNomEntity(newEntityName);
					}
				}
			}
		}

	}

	public MCD getMCD() {
		return mcd;
	}

}
