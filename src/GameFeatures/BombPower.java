package GameFeatures;

import java.io.Serializable;

import GameObjects.Map;
import GameObjects.Player;
import Tools.Resources;

public class BombPower extends Features implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 959873228629765531L;

	public BombPower(Map map) {
		super(map);
		setIcon(Resources.boostPower);
	}

	@Override
	public void doPower(Player player) {
		player.setBombPower(player.getBombPower() + 1);
		if (player.getBombs().size() != 0) {
			for (int i = 0; i < player.getBombs().size(); i++) {
				player.getBombs().get(i).setPower(player.getBombPower());
			}
		}
		player.getLastPower().add(this);
		player.getBombPowerLabel().setText(
				"Bomb Power:  " + player.getBombPower());

	}

	@Override
	public void loseLastPower(Player player) {
		player.getLastPower().remove(this);
		player.setBombPower(player.getBombPower() - 1);
		if (player.getBombs().size() != 0) {
			for (int i = 0; i < player.getBombs().size(); i++) {
				player.getBombs().get(i).setPower(player.getBombPower());
			}
		}
		player.getBombPowerLabel().setText(
				"Bomb Power:  " + player.getBombPower());
	}

}
