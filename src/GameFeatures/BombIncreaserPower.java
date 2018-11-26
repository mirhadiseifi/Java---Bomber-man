package GameFeatures;

import java.io.Serializable;

import GameObjects.Bomb;
import GameObjects.Map;
import GameObjects.Player;
import Tools.Resources;

public class BombIncreaserPower extends Features implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3733487117547804747L;
	private Map map;

	public BombIncreaserPower(Map map) {
		super(map);
		this.map = map;
		setIcon(Resources.extraBomb);
	}

	@Override
	public void doPower(Player player) {
		player.getBombs().add(new Bomb(map, player));
		player.getBombNumberLabel().setText(
				"Bombs:  " + player.getBombs().size());
		player.getLastPower().add(this);
	}

	@Override
	public void loseLastPower(Player player) {
		player.getLastPower().remove(this);
		if (player.getBombs().size() != 0) {
			player.getBombs().remove(0);
			player.getBombNumberLabel().setText(
					"Bombs:  " + player.getBombs().size());
		}
	}
}
