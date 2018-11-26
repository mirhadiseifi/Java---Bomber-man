package GameFeatures;

import java.io.Serializable;

import javax.swing.JButton;

import GameObjects.Map;
import GameObjects.Player;

public class Features extends JButton implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3892114882507302747L;
	private Map map;

	public Features(Map map) {
		super();
		this.map = map;
		setContentAreaFilled(false);
		setBorderPainted(false);
	}

	public void loseLastPower(Player player) {

	}

	public void doPower(Player player) {

	}

}
