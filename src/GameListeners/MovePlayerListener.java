package GameListeners;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import GameListeners.StartListener.clientInfo;
import GameObjects.Map;
import GameObjects.Player;
import GameObjects.StoneBlock;

public class MovePlayerListener implements KeyListener, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9204125602304804688L;
	private Map map;
	private Set<Integer> key = new HashSet<Integer>();
	private JLayeredPane pane;
	private int state;
	private clientInfo[] clients;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public MovePlayerListener(Map map, JLayeredPane pane, int state,
			clientInfo[] clients, ObjectOutputStream out, ObjectInputStream in) {
		super();
		this.map = map;
		this.pane = pane;
		this.state = state;
		this.clients = clients;
		this.out = out;
		this.in = in;
		new Thread(new getKey()).start();
	}

	private class SendOther implements Runnable {
		private int i;

		public SendOther(int i) {
			this.i = i;
		}

		@Override
		public void run() {
			while (true) {
				try {
					String info = (String) clients[i].in.readObject();
					sendToOther(info, i);
					String[] split = info.split(" ");
					int temp = state;
					state = -1;
					key.clear();
					keyPressed(new KeyEvent(new JButton(), 0, 1, 0, changeKey(
							Integer.parseInt(split[0]),
							Integer.parseInt(split[1]))));
					state = temp;
					Thread.sleep(10);
				} catch (IOException e) {
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void sendToOther(String info, int i) throws IOException {
			int size = clients.length;
			switch (i) {
			case 0:
				if (size < 2)
					break;
				clients[1].out.writeObject(info);
				clients[1].out.flush();
				if (size < 3)
					break;
				clients[2].out.writeObject(info);
				clients[2].out.flush();
				break;
			case 1:
				if (size < 2)
					break;
				clients[0].out.writeObject(info);
				clients[0].out.flush();
				if (size < 3)
					break;
				clients[2].out.writeObject(info);
				clients[2].out.flush();
				break;
			case 2:
				if (size < 2)
					break;
				clients[0].out.writeObject(info);
				clients[0].out.flush();
				if (size < 3)
					break;
				clients[1].out.writeObject(info);
				clients[1].out.flush();
				break;

			default:
				break;
			}
		}

	}

	private class getKey implements Runnable {
		@Override
		public synchronized void run() {
			if (state != -1)
				if (state == 0) {
					for (int i = 0; i < clients.length; i++) {
						new Thread(new SendOther(i)).start();
					}
				} else {
					while (true) {
						try {
							String info = (String) in.readObject();
							String[] split = info.split(" ");
							int temp = state;
							state = -1;
							key.clear();
							keyPressed(new KeyEvent(new JButton(), 0, 1, 0,
									changeKey(Integer.parseInt(split[0]),
											Integer.parseInt(split[1]))));
							state = temp;
							Thread.sleep(10);
						} catch (IOException e) {
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
		}
	}

	@Override
	public synchronized void keyPressed(KeyEvent a) {
		try {
			if (state != -1) {
				if (!valid(a))
					return;
				if (state == 0) {
					for (int i = 0; i < clients.length; i++) {
						clients[i].out
								.writeObject(a.getKeyCode() + " " + state);
						clients[i].out.flush();
						key.clear();
					}
				} else {
					out.writeObject(a.getKeyCode() + " " + state);
					out.flush();
					a.setKeyCode(changeKey(a.getKeyCode(), state));
					key.clear();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		key.add(a.getKeyCode());
		switch (getPlayerType(a)) {
		case 1:
			if (map.getPlayers().get(0).isAlive())
				setPlayer1Keys(key, map.getPlayers().get(0));

		case 2:
			if (map.getPlayers().get(1).isAlive())
				setPlayer2Keys(key, map.getPlayers().get(1));

		case 3:
			if (map.getPlayers().size() >= 3
					&& map.getPlayers().get(2).isAlive()) {
				setPlayer3Keys(key, map.getPlayers().get(2));
			}
		case 4:
			if (map.getPlayers().size() == 4
					&& map.getPlayers().get(3).isAlive()) {
				setPlayer4Keys(key, map.getPlayers().get(3));
			}
			break;
		default:
			break;
		}
		checkFeatures();
		checkWells();
		pane.repaint();

	}

	private boolean valid(KeyEvent a) {
		if (a.getKeyCode() == KeyEvent.VK_UP
				|| a.getKeyCode() == KeyEvent.VK_DOWN
				|| a.getKeyCode() == KeyEvent.VK_LEFT
				|| a.getKeyCode() == KeyEvent.VK_RIGHT
				|| a.getKeyCode() == KeyEvent.VK_ENTER)
			return true;
		return false;
	}

	private int changeKey(int key, int number) {
		switch (number) {
		case 1:
			switch (key) {
			case KeyEvent.VK_UP:
				return KeyEvent.VK_W;
			case KeyEvent.VK_DOWN:
				return KeyEvent.VK_S;
			case KeyEvent.VK_RIGHT:
				return KeyEvent.VK_D;
			case KeyEvent.VK_LEFT:
				return KeyEvent.VK_A;
			case KeyEvent.VK_ENTER:
				return KeyEvent.VK_Q;
			default:
				return -1;
			}
		case 3:
			switch (key) {
			case KeyEvent.VK_UP:
				return KeyEvent.VK_O;
			case KeyEvent.VK_DOWN:
				return KeyEvent.VK_L;
			case KeyEvent.VK_RIGHT:
				return KeyEvent.VK_SEMICOLON;
			case KeyEvent.VK_LEFT:
				return KeyEvent.VK_K;
			case KeyEvent.VK_ENTER:
				return KeyEvent.VK_I;

			default:
				return -1;
			}
		case 2:
			switch (key) {
			case KeyEvent.VK_UP:
				return KeyEvent.VK_Y;
			case KeyEvent.VK_DOWN:
				return KeyEvent.VK_H;
			case KeyEvent.VK_RIGHT:
				return KeyEvent.VK_J;
			case KeyEvent.VK_LEFT:
				return KeyEvent.VK_G;
			case KeyEvent.VK_ENTER:
				return KeyEvent.VK_T;

			default:
				return -1;
			}
		default:
			return key;
		}
	}

	private void checkWells() {
		for (int i = 0; i < map.getRow(); i++) {
			for (int j = 0; j < map.getRow(); j++) {
				for (int k = 0; k < map.getPlayers().size(); k++) {
					if (map.getBlocks(i, j).getWell() != null) {
						if (map.getBlocks(i, j)
								.getWell()
								.getBounds()
								.intersects(map.getPlayers().get(k).getBounds())) {
							map.getPane().remove(map.getPlayers().get(k));
							map.getPane().repaint();
							map.getPlayers().get(k).setAlive(false);
							map.getPlayers().get(k).setHealth(0);
							map.getPlayers()
									.get(k)
									.getHealthLabel()
									.setText(
											"Health:  "
													+ map.getPlayers().get(k)
															.getHealth());
							int counter = 0;
							Player p = null;
							for (int m = 0; m < map.getPlayers().size(); m++) {
								if (map.getPlayers().get(m).isAlive()) {
									p = map.getPlayers().get(m);
									counter++;
								}
							}
							if (counter == 1) {
								ImageIcon icon = (ImageIcon) p.getIcon();
								JOptionPane.showMessageDialog(null,
										" is winner!", "Winner",
										JOptionPane.INFORMATION_MESSAGE, icon);
								System.exit(0);

							} else if (counter == 0) {
								JOptionPane
										.showMessageDialog(null, "No Winner");
							}
						}
					}
				}
			}
		}

	}

	private void checkFeatures() {
		for (int i = 0; i < map.getPlayers().size(); i++) {
			for (int j = 0; j < map.getFeatures().size(); j++) {
				if (map.getPlayers().get(i).getBounds()
						.intersects(map.getFeatures().get(j).getBounds())) {
					pane.remove(map.getFeatures().get(j));
					map.getFeatures().get(j).doPower(map.getPlayers().get(i));
					map.getFeatures().remove(map.getFeatures().get(j));
				}
			}
		}

	}

	private void setPlayer4Keys(Set<Integer> key, Player player) {
		// key4.add(a.getKeyCode());
		for (Integer i : key) {
			switch (i) {
			case KeyEvent.VK_O:
				if (player.isButtonReplaced()) {
					player.setyPosition(player.getyPosition()
							+ canMove(KeyEvent.VK_L, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setyPosition(player.getyPosition()
							- canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}

				break;
			case KeyEvent.VK_L:
				if (player.isButtonReplaced()) {
					player.setyPosition(player.getyPosition()
							- canMove(KeyEvent.VK_O, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setyPosition(player.getyPosition()
							+ canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}
				break;
			case KeyEvent.VK_K:
				if (player.isButtonReplaced()) {
					player.setxPosition(player.getxPosition()
							+ canMove(KeyEvent.VK_SEMICOLON, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setxPosition(player.getxPosition()
							- canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}
				break;
			case KeyEvent.VK_SEMICOLON:
				if (player.isButtonReplaced()) {
					player.setxPosition(player.getxPosition()
							- canMove(KeyEvent.VK_K, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setxPosition(player.getxPosition()
							+ canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}
				break;
			case KeyEvent.VK_I:
				if (player.getBombs().size() != 0) {
					int x = (player.getxPosition() + 25) / 50;
					int y = (player.getyPosition() + 25) / 50;
					player.getBombs().get(0).setiPosition(y);
					player.getBombs().get(0).setjPosition(x);
					player.getBombs().get(0).setBounds(x * 50, y * 50, 50, 50);
					pane.add(player.getBombs().get(0), new Integer(5));
					new Thread(player.getBombs().get(0)).start();
					player.getBombs().remove(player.getBombs().get(0));
					player.getBombNumberLabel().setText(
							"Bombs:  " + player.getBombs().size());
				}
				break;
			}
		}
	}

	private void setPlayer3Keys(Set<Integer> key, Player player) {
		// key3.add(a.getKeyCode());
		for (Integer i : key) {
			switch (i) {
			case KeyEvent.VK_Y:
				if (player.isButtonReplaced()) {
					player.setyPosition(player.getyPosition()
							+ canMove(KeyEvent.VK_H, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setyPosition(player.getyPosition()
							- canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}

				break;
			case KeyEvent.VK_H:
				if (player.isButtonReplaced()) {
					player.setyPosition(player.getyPosition()
							- canMove(KeyEvent.VK_Y, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setyPosition(player.getyPosition()
							+ canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}
				break;
			case KeyEvent.VK_G:
				if (player.isButtonReplaced()) {
					player.setxPosition(player.getxPosition()
							+ canMove(KeyEvent.VK_J, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setxPosition(player.getxPosition()
							- canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}
				break;
			case KeyEvent.VK_J:
				if (player.isButtonReplaced()) {
					player.setxPosition(player.getxPosition()
							- canMove(KeyEvent.VK_G, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setxPosition(player.getxPosition()
							+ canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}
				break;
			case KeyEvent.VK_T:
				if (player.getBombs().size() != 0) {
					int x = (player.getxPosition() + 25) / 50;
					int y = (player.getyPosition() + 25) / 50;
					player.getBombs().get(0).setiPosition(y);
					player.getBombs().get(0).setjPosition(x);
					player.getBombs().get(0).setBounds(x * 50, y * 50, 50, 50);
					pane.add(player.getBombs().get(0), new Integer(5));
					new Thread(player.getBombs().get(0)).start();
					player.getBombs().remove(player.getBombs().get(0));
					player.getBombNumberLabel().setText(
							"Bombs:  " + player.getBombs().size());
				}
				break;
			}
		}
	}

	private int getPlayerType(KeyEvent a) {
		switch (a.getKeyCode()) {
		case KeyEvent.VK_UP:
			return 1;
		case KeyEvent.VK_DOWN:
			return 1;
		case KeyEvent.VK_RIGHT:
			return 1;
		case KeyEvent.VK_LEFT:
			return 1;
		case KeyEvent.VK_ENTER:
			return 1;
		case KeyEvent.VK_W:
			return 2;
		case KeyEvent.VK_S:
			return 2;
		case KeyEvent.VK_A:
			return 2;
		case KeyEvent.VK_D:
			return 2;
		case KeyEvent.VK_Q:
			return 2;
		case KeyEvent.VK_Y:
			return 3;
		case KeyEvent.VK_H:
			return 3;
		case KeyEvent.VK_G:
			return 3;
		case KeyEvent.VK_J:
			return 3;
		case KeyEvent.VK_T:
			return 3;
		case KeyEvent.VK_O:
			return 4;
		case KeyEvent.VK_K:
			return 4;
		case KeyEvent.VK_I:
			return 4;
		case KeyEvent.VK_L:
			return 4;
		case KeyEvent.VK_SEMICOLON:
			return 4;

		default:
			break;
		}
		return 0;

	}

	private void setPlayer2Keys(Set<Integer> key, Player player) {
		// key2.add(a.getKeyCode());
		for (Integer i : key) {
			switch (i) {
			case KeyEvent.VK_W:
				if (player.isButtonReplaced()) {
					player.setyPosition(player.getyPosition()
							+ canMove(KeyEvent.VK_S, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setyPosition(player.getyPosition()
							- canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}

				break;
			case KeyEvent.VK_S:
				if (player.isButtonReplaced()) {
					player.setyPosition(player.getyPosition()
							- canMove(KeyEvent.VK_W, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setyPosition(player.getyPosition()
							+ canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}
				break;
			case KeyEvent.VK_A:
				if (player.isButtonReplaced()) {
					player.setxPosition(player.getxPosition()
							+ canMove(KeyEvent.VK_D, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setxPosition(player.getxPosition()
							- canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}
				break;
			case KeyEvent.VK_D:
				if (player.isButtonReplaced()) {
					player.setxPosition(player.getxPosition()
							- canMove(KeyEvent.VK_A, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setxPosition(player.getxPosition()
							+ canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}
				break;
			case KeyEvent.VK_Q:
				if (player.getBombs().size() != 0) {
					int x = (player.getxPosition() + 25) / 50;
					int y = (player.getyPosition() + 25) / 50;
					player.getBombs().get(0).setiPosition(y);
					player.getBombs().get(0).setjPosition(x);
					player.getBombs().get(0).setBounds(x * 50, y * 50, 50, 50);
					pane.add(player.getBombs().get(0), new Integer(5));
					new Thread(player.getBombs().get(0)).start();
					player.getBombs().remove(player.getBombs().get(0));
					player.getBombNumberLabel().setText(
							"Bombs:  " + player.getBombs().size());
				}
				break;
			}
		}

	}

	public void setPlayer1Keys(Set<Integer> key, Player player) {
		for (Integer i : key) {
			switch (i) {
			case KeyEvent.VK_UP:
				if (player.isButtonReplaced()) {
					player.setyPosition(player.getyPosition()
							+ canMove(KeyEvent.VK_DOWN, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setyPosition(player.getyPosition()
							- canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}

				break;
			case KeyEvent.VK_DOWN:
				if (player.isButtonReplaced()) {
					player.setyPosition(player.getyPosition()
							- canMove(KeyEvent.VK_UP, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setyPosition(player.getyPosition()
							+ canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}
				break;
			case KeyEvent.VK_LEFT:
				if (player.isButtonReplaced()) {
					player.setxPosition(player.getxPosition()
							+ canMove(KeyEvent.VK_RIGHT, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setxPosition(player.getxPosition()
							- canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (player.isButtonReplaced()) {
					player.setxPosition(player.getxPosition()
							- canMove(KeyEvent.VK_LEFT, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				} else {
					player.setxPosition(player.getxPosition()
							+ canMove(i, player));
					player.setLocation(player.getxPosition(),
							player.getyPosition());
				}
				break;
			case KeyEvent.VK_ENTER:
				if (player.getBombs().size() != 0) {
					int x = (player.getxPosition() + 25) / 50;
					int y = (player.getyPosition() + 25) / 50;
					player.getBombs().get(0).setiPosition(y);
					player.getBombs().get(0).setjPosition(x);
					player.getBombs().get(0).setBounds(x * 50, y * 50, 50, 50);
					pane.add(player.getBombs().get(0), new Integer(5));
					new Thread(player.getBombs().get(0)).start();
					player.getBombs().remove(player.getBombs().get(0));
					player.getBombNumberLabel().setText(
							"Bombs:  " + player.getBombs().size());
				}
				break;

			}
		}
	}

	private int canMove(int keyEvent, Player player) {
		Rectangle r;
		switch (keyEvent) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
		case KeyEvent.VK_Y:
		case KeyEvent.VK_O:
			r = player.getBounds();
			r.setLocation(player.getxPosition(),
					player.getyPosition() - player.getSpeed());
			int speed = player.getSpeed();
			while (!checkAvailablity(r, player) && speed != 0) {
				speed--;
				r.setLocation(player.getxPosition(), player.getyPosition()
						- speed);
				if (speed == 0) {
					return 0;
				}
			}
			if (checkAvailablity(r, player))
				return speed;
			return 0;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
		case KeyEvent.VK_H:
		case KeyEvent.VK_L:
			r = player.getBounds();
			r.setLocation(player.getxPosition(),
					player.getyPosition() + player.getSpeed());
			int speed2 = player.getSpeed();
			while (!checkAvailablity(r, player) && speed2 != 0) {
				speed2--;
				r.setLocation(player.getxPosition(), player.getyPosition()
						+ speed2);
				if (speed2 == 0) {
					return 0;
				}
			}
			if (checkAvailablity(r, player))
				return speed2;
			return 0;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
		case KeyEvent.VK_J:
		case KeyEvent.VK_SEMICOLON:
			r = player.getBounds();
			r.setLocation(player.getxPosition() + player.getSpeed(),
					player.getyPosition());
			int speed3 = player.getSpeed();
			while (!checkAvailablity(r, player) && speed3 != 0) {
				speed3--;
				r.setLocation(player.getxPosition() + speed3,
						player.getyPosition());
				if (speed3 == 0) {
					return 0;
				}
			}
			if (checkAvailablity(r, player))
				return speed3;
			return 0;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
		case KeyEvent.VK_G:
		case KeyEvent.VK_K:
			r = player.getBounds();
			r.setLocation(player.getxPosition() - player.getSpeed(),
					player.getyPosition());
			int speed4 = player.getSpeed();
			while (!checkAvailablity(r, player) && speed4 != 0) {
				speed4--;
				r.setLocation(player.getxPosition() - speed4,
						player.getyPosition());
				if (speed4 == 0) {
					return 0;
				}
			}
			if (checkAvailablity(r, player))
				return speed4;
			return 0;
		default:
			break;
		}

		return 0;
	}

	public boolean checkAvailablity(Rectangle r, Player player) {
		for (int i = 0; i < map.getRow(); i++) {
			for (int j = 0; j < map.getCol(); j++) {
				if (map.getBlocks(i, j) instanceof StoneBlock) {
					if (map.getBlocks(i, j).getBounds().intersects(r)) {
						return false;
					}
				} else if (map.getBlocks(i, j).getBox() != null) {
					if (map.getBlocks(i, j).getBox().getBounds().intersects(r)) {
						if (player.isTransparent()) {
							return true;
						}
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		key.remove(arg0.getKeyCode());

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}