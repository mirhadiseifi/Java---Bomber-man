package GameObjects;

import java.io.Serializable;

import Tools.Resources;

public class StoneBlock extends Block implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 140322948208779461L;

	public StoneBlock(int i, int j) {
		super(i, j);
		setIcon(Resources.stone);
	}

}
