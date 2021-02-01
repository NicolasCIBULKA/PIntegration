package gui;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class ShapeGroup {
	private Shape mainShape;
	private Shape headShape;
	private String groupName;
	private ArrayList<String> alEntries = new ArrayList<String>();

	private float x;
	private float y;
	private float width = 200.0f;
	private float height = 100.0f;

	private boolean isAnEntity;

	public ShapeGroup(float x, float y, boolean entityType, String groupName) {
		this.x = x;
		this.y = y;
		this.isAnEntity = entityType;
		this.groupName = groupName;

		if (isAnEntity) {

			mainShape = new Rectangle2D.Float(x, y, width, height);
			headShape = new Rectangle2D.Float(x, y, width, height / 2);

		} else {

			mainShape = new RoundRectangle2D.Float(x, y, width, height, 20.0f, 20.0f);
			headShape = new RoundRectangle2D.Float(x, y, width, height / 2, 20.0f, 20.0f);
		}

	}

	// TODO : update height operations, only suitable for empty entities
	public void setGroupAbscissa(float newX) {
		setX(newX);

		if (isAnEntity) {

			((Rectangle2D) mainShape).setRect(newX, y, width, height);
			((Rectangle2D) headShape).setRect(newX, y, width, height / 2);

		} else {

			((RoundRectangle2D) mainShape).setRoundRect(newX, y, width, height, 20.0f, 20.0f);
			((RoundRectangle2D) headShape).setRoundRect(newX, y, width, height / 2, 20.0f, 20.0f);

		}

	}

	public void setGroupOrdinate(float newY) {
		setY(newY);

		if (isAnEntity) {

			((Rectangle2D) mainShape).setRect(x, newY, width, height);
			((Rectangle2D) headShape).setRect(x, newY, width, height / 2);

		} else {

			((RoundRectangle2D) mainShape).setRoundRect(x, newY, width, height, 20.0f, 20.0f);
			((RoundRectangle2D) headShape).setRoundRect(x, newY, width, height / 2, 20.0f, 20.0f);

		}
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * @return the isAnEntity
	 */
	public boolean isAnEntity() {
		return isAnEntity;
	}

	/**
	 * @param isAnEntity the isAnEntity to set
	 */
	public void setAnEntity(boolean isAnEntity) {
		this.isAnEntity = isAnEntity;
	}

	/**
	 * @return the mainShape
	 */
	public Shape getMainShape() {
		return mainShape;
	}

	/**
	 * @return the bodyShape
	 */
	public Shape getHeadShape() {
		return headShape;
	}

	/**
	 * @return the alEntries
	 */
	public ArrayList<String> getAlEntries() {
		return alEntries;
	}

}