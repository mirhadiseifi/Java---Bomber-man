package GameFeatures;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import GameObjects.Map;
import GameObjects.Player;
import Tools.Resources;

public class TransparentPower extends Features implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5460994691814937138L;
	private Map map;

	public TransparentPower(Map map) {
		super(map);
		this.map = map;
		setIcon(Resources.transparent);
	}

	@Override
	public void doPower(final Player player) {
		player.getLastPower().add(this);
		player.setTransparent(true);
		player.getTransparentLabel().setEnabled(true);
		Thread t = new Thread() {
			public void run() {
				try {
					sleep(5000);
					for (int i = 0; i < map.getRow(); i++) {
						for (int j = 0; j < map.getCol(); j++) {
							if (map.getBlocks(i, j).getBox() != null) {
								if (map.getBlocks(i, j).getBox().getBounds()
										.intersects(player.getBounds())) {
									map.getPane().remove(player);
									map.getPane().repaint();
									player.setAlive(false);
									player.setHealth(0);
									player.getHealthLabel().setText(
											"Health:  " + player.getHealth());
									int counter = 0;
									Player p = null;
									for (int k = 0; k < map.getPlayers().size(); k++) {
										if (map.getPlayers().get(k).isAlive()) {
											p = map.getPlayers().get(k);
											counter++;
										}
									}
									if (counter == 1) {
										ImageIcon icon = (ImageIcon) p
												.getIcon();
										JOptionPane
												.showMessageDialog(
														null,
														" is winner!",
														"Winner",
														JOptionPane.INFORMATION_MESSAGE,
														icon);
										System.exit(0);

									} else if (counter == 0) {
										JOptionPane.showMessageDialog(null,
												"No Winner");
									}
								}
							}
						}
					}
					player.setTransparent(false);
					player.getTransparentLabel().setEnabled(false);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};
		t.start();
	}

}
