package GameObjects;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import Tools.Resources;

public class Bomb extends JButton implements Runnable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1555394304175773565L;
	private Map map;
	private int iPosition;
	private int jPosition;
	private int power = 1;
	private Player player;
	private ArrayList<Explosion> explosionsList = new ArrayList<Explosion>();

	public Bomb(Map map, Player player) {
		super();
		this.map = map;
		this.player = player;
		this.power = player.getBombPower();
		setFocusable(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setIcon(Resources.bomb);
	}

	@Override
	public void run() {
		try {
			int i = 0;
			while (i <= 6) {
				Thread.sleep(500);
				i++;
				if (isEnabled()) {
					setEnabled(false);
				} else
					setEnabled(true);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.getPane().remove(this);
		Explosion explosion = new Explosion(map.getPane(), map);
		explosion.setBounds(jPosition * 50 + 5, iPosition * 50 + 5, 40, 40);
		map.getPane().add(explosion, new Integer(3));
		new Thread(explosion).start();
		explosionsList.add(explosion);
		addUpExplosion(power);
		addDownExplosion(power);
		addLeftExplosion(power);
		addRightExplosion(power);
		player.getBombs().add(new Bomb(map, player));
		player.getBombNumberLabel().setText(
				"Bombs:  " + player.getBombs().size());
		checkHealth();
		map.getPane().repaint();

	}

	public void checkHealth() {
		for (int i = 0; i < map.getPlayers().size(); i++) {
			for (int j = 0; j < explosionsList.size(); j++) {
				if (map.getPlayers().get(i).getBounds()
						.intersects(explosionsList.get(j).getBounds())) {
					changeStatus(i);
					if (map.getPlayers().get(i).getHealth() == 0) {
						map.getPane().remove(map.getPlayers().get(i));
						map.getPlayers().get(i).setAlive(false);
						int counter = 0;
						Player p = null;
						for (int k = 0; k < map.getPlayers().size(); k++) {
							if (map.getPlayers().get(k).isAlive()) {
								p = map.getPlayers().get(k);
								counter++;
							}
						}
						if (counter == 1) {
							ImageIcon icon = (ImageIcon) p.getIcon();
							JOptionPane.showMessageDialog(null, " is winner!",
									"Winner", JOptionPane.INFORMATION_MESSAGE,
									icon);
							System.exit(0);

						} else if (counter == 0) {
							JOptionPane.showMessageDialog(null, "No Winner");
						}
					}
					break;

				}

			}

		}
		return;
	}

	public void changeStatus(int i) {
		map.getPlayers().get(i)
				.setHealth(map.getPlayers().get(i).getHealth() - 1);
		map.getPlayers().get(i).getHealthLabel()
				.setText("Health: " + map.getPlayers().get(i).getHealth());
		map.getPlayers().get(i).getLastPower().clear();
		map.getPlayers().get(i).setSpeed(5);
		map.getPlayers().get(i).getSpeedLabel()
				.setText("Speed:  " + map.getPlayers().get(i).getSpeed());
		map.getPlayers().get(i).setBombPower(1);
		map.getPlayers()
				.get(i)
				.getBombPowerLabel()
				.setText(
						"Bomb Power:  "
								+ map.getPlayers().get(i).getBombPower());
		map.getPlayers().get(i).getBombs().clear();
		map.getPlayers().get(i).getBombs()
				.add(new Bomb(map, map.getPlayers().get(i)));
		map.getPlayers()
				.get(i)
				.getBombNumberLabel()
				.setText("Bombs:  " + map.getPlayers().get(i).getBombs().size());
		map.getPlayers().get(i).setTransparent(false);
		map.getPlayers().get(i).getTransparentLabel().setEnabled(false);
		map.getPlayers().get(i).setButtonReplaced(false);
		map.getPlayers().get(i).getReplaceLable().setEnabled(false);
	}

	private void addRightExplosion(int power) {
		for (int i = 1; i <= power; i++) {
			if (map.getBlocks(iPosition, jPosition + i) instanceof StoneBlock) {
				return;
			} else {
				if (map.getBlocks(iPosition, jPosition + i).getBox() != null) {
					map.getPane().remove(
							map.getBlocks(iPosition, jPosition + i).getBox());
					map.getBlocks(iPosition, jPosition + i).setBox(null);
				}
				Explosion explosion = new Explosion(map.getPane(), map);
				explosion.setBounds((jPosition + i) * 50 + 5,
						iPosition * 50 + 5, 40, 40);
				map.getPane().add(explosion, new Integer(3));
				new Thread(explosion).start();
				explosionsList.add(explosion);
			}
		}
	}

	private void addLeftExplosion(int power) {
		for (int i = 1; i <= power; i++) {
			if (map.getBlocks(iPosition, jPosition - i) instanceof StoneBlock) {
				return;
			} else {
				if (map.getBlocks(iPosition, jPosition - i).getBox() != null) {
					map.getPane().remove(
							map.getBlocks(iPosition, jPosition - i).getBox());
					map.getBlocks(iPosition, jPosition - i).setBox(null);
				}
				Explosion explosion = new Explosion(map.getPane(), map);
				explosion.setBounds((jPosition - i) * 50 + 5,
						iPosition * 50 + 5, 40, 40);
				map.getPane().add(explosion, new Integer(3));
				new Thread(explosion).start();
				explosionsList.add(explosion);
			}
		}
	}

	private void addDownExplosion(int power) {
		for (int i = 1; i <= power; i++) {
			if (map.getBlocks(iPosition + i, jPosition) instanceof StoneBlock) {
				return;
			} else {
				if (map.getBlocks(iPosition + i, jPosition).getBox() != null) {
					map.getPane().remove(
							map.getBlocks(iPosition + i, jPosition).getBox());
					map.getBlocks(iPosition + i, jPosition).setBox(null);
				}
				Explosion explosion = new Explosion(map.getPane(), map);
				explosion.setBounds((jPosition) * 50 + 5,
						(iPosition + i) * 50 + 5, 40, 40);
				map.getPane().add(explosion, new Integer(3));
				new Thread(explosion).start();
				explosionsList.add(explosion);
			}
		}
	}

	private void addUpExplosion(int power) {
		for (int i = 1; i <= power; i++) {
			if (map.getBlocks(iPosition - i, jPosition) instanceof StoneBlock) {
				return;
			} else {
				if (map.getBlocks(iPosition - i, jPosition).getBox() != null) {
					map.getPane().remove(
							map.getBlocks(iPosition - i, jPosition).getBox());
					map.getBlocks(iPosition - i, jPosition).setBox(null);
				}
				Explosion explosion = new Explosion(map.getPane(), map);
				explosion.setBounds((jPosition) * 50 + 5,
						(iPosition - i) * 50 + 5, 40, 40);
				map.getPane().add(explosion, new Integer(3));
				new Thread(explosion).start();
				explosionsList.add(explosion);
			}
		}
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getiPosition() {
		return iPosition;
	}

	public int getjPosition() {
		return jPosition;
	}

	public void setiPosition(int iPosition) {
		this.iPosition = iPosition;
	}

	public void setjPosition(int jPosition) {
		this.jPosition = jPosition;
	}
}
