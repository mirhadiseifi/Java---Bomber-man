package GameFeatures;

import java.io.Serializable;
import GameObjects.Bomb;
import GameObjects.Map;
import GameObjects.Player;
import Tools.Resources;

public class loseBombPower extends Features implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8432926800704282868L;
	private Map map;

	public loseBombPower(Map map) {
		super(map);
		this.map = map;
		setIcon(Resources.loseBomb);
	}

	@Override
	public void doPower(final Player player) {
		final int bombs = player.getBombs().size();
		player.getLoseBombPowerLable().setEnabled(true);
		player.getBombs().clear();
		player.getBombNumberLabel().setText(
				"Bombs:  " + player.getBombs().size());
		Thread t = new Thread() {
			public void run() {
				try {
					sleep(5000);

					for (int i = 0; i < bombs; i++) {
						player.getBombs().add(new Bomb(map, player));
					}
					player.getBombNumberLabel().setText(
							"Bombs:  " + player.getBombs().size());
					player.getLoseBombPowerLable().setEnabled(false);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};
		t.start();
	}
}
