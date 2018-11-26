package GameFeatures;

import java.io.Serializable;

import GameObjects.Map;
import GameObjects.Player;
import Tools.Resources;

public class SpeedPower extends Features implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6024971803260169698L;

	public SpeedPower(Map map) {
		super(map);
		setIcon(Resources.speed);
	}

	@Override
	public void doPower(Player player) {
		player.setSpeed(player.getSpeed() + 5);
		player.getSpeedLabel().setText("Speed:  " + player.getSpeed());
		player.getLastPower().add(this);
	}

	@Override
	public void loseLastPower(Player player) {
		player.getLastPower().remove(this);
		player.setSpeed(player.getSpeed() - 5);
		player.getSpeedLabel().setText("Speed:  " + player.getSpeed());
	}
}
