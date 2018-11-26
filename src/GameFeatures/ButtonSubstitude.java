package GameFeatures;

import java.io.Serializable;

import GameObjects.Map;
import GameObjects.Player;
import Tools.Resources;

public class ButtonSubstitude extends Features implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6403898859046844296L;

	public ButtonSubstitude(Map map) {
		super(map);
		setIcon(Resources.replace);
	}

	@Override
	public void doPower(final Player player) {
		player.setButtonReplaced(true);
		player.getReplaceLable().setEnabled(true);
		Thread t = new Thread() {
			public void run() {
				try {
					sleep(5000);
					player.setButtonReplaced(false);
					player.getReplaceLable().setEnabled(false);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};
		t.start();
	}
}
