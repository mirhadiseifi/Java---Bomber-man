package GameFeatures;

import java.io.Serializable;

import GameObjects.Map;
import GameObjects.Player;
import Tools.Resources;

public class HealthPower extends Features implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -401105352370753928L;
	private Map map;

	public HealthPower(Map map) {
		super(map);
		this.map = map;
		setIcon(Resources.health);
	}

	@Override
	public void doPower(Player player) {
		player.setHealth(player.getHealth() + 1);
		player.getHealthLabel().setText("Health:  " + player.getHealth());
		player.getLastPower().add(this);
	}

	@Override
	public void loseLastPower(Player player) {
		player.getLastPower().remove(this);
		player.setHealth(player.getHealth() - 1);
		player.getHealthLabel().setText("Health:  " + player.getHealth());
		if (player.getHealth() == 0) {
			map.getPane().remove(player);
			player.setAlive(false);
		}
	}
}
