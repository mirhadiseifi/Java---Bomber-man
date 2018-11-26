package GameFeatures;

import java.io.Serializable;

import GameObjects.Map;
import GameObjects.Player;
import Tools.Resources;

public class LoseLastPower extends Features implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1596144652382395571L;

	public LoseLastPower(Map map) {
		super(map);
		setIcon(Resources.loseLastPower);
	}

	@Override
	public void doPower(Player player) {
		if (player.getLastPower().size() != 0) {
			player.getLastPower().get(player.getLastPower().size() - 1)
					.loseLastPower(player);
			player.getLastPower()
					.remove(player.getLastPower().get(
							player.getLastPower().size() - 1));
		}
	}
}
