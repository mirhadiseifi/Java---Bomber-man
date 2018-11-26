package GameObjects;

import java.io.Serializable;

import GameFeatures.Features;
import Tools.Resources;

public class GrassBlock extends Block implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7113629188206439383L;
	private Box box;
	private Bomb bomb;
	private Features features;
	private Well well;

	public GrassBlock(int i, int j) {
		super(i, j);
		setIcon(Resources.grass);
		// TODO Auto-generated constructor stub
	}

	public void setBox(Box box) {
		this.box = box;
	}

	public Box getBox() {
		return box;
	}

	public Features getFeatures() {
		return features;
	}

	public void setFeatures(Features features) {
		this.features = features;
	}

	public Well getWell() {
		return well;
	}

	public void setWell(Well well) {
		this.well = well;
	}

}
