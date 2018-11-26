package GameObjects;

import java.io.Serializable;

import javax.swing.JButton;

import GameFeatures.Features;

public class Block extends JButton implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1620331057141710455L;
	private int i;
	private int j;

	Block(int i, int j) {
		super();
		this.i = i;
		this.j = j;
		setFocusable(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public void setBox(Box box) {

	}

	public Box getBox() {
		return null;

	}

	public Well getWell() {
		return null;
	}

	public void setWell(Well well) {
	}

	public Features getFeatures() {
		return null;
	}

	public void setFeatures(Features features) {
	}

}
