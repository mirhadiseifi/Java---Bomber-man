package GameListeners;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import GameObjects.Bomb;
import GameObjects.Map;
import GameObjects.Player;
import GameObjects.StoneBlock;
import Graphic.Graphic;
import Tools.Resources;

public class StartListener extends MouseAdapter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1368430900383859207L;
	protected JComboBox comboBox;
	protected Map map;
	protected Graphic graphic;
	protected JTextField time;
	protected int gameTime;
	protected JPanel gamePanel;
	protected JPanel startPanel;

	public StartListener(Graphic graphic, JComboBox comboBox, JTextField time,
			JPanel gamePanel, JPanel startPanel, Map map) {
		super();
		this.graphic = graphic;
		this.gamePanel = gamePanel;
		this.comboBox = comboBox;
		this.time = time;
		this.startPanel = startPanel;
		this.map = map;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			mapInit();
			startPanel.setVisible(false);
			JLayeredPane mainPane = new JLayeredPane();
			JLayeredPane pane = new JLayeredPane();
			createFrame(graphic, mainPane, pane, gamePanel, -1,null,null,null);
			map.addFeatures();
			addBlocks(pane);
			addPlayers(pane, false);
			createScorePanel(mainPane);
			addTimer(mainPane,(int)System.currentTimeMillis());
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "Wrong Input");
		}

	}

	protected void mapInit() {
		gameTime = Integer.parseInt(time.getText());
		int type = JOptionPane.showConfirmDialog(null, "Use Saved Map?", null,
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (type == 1) {
			map = new Map(10, 15);
			map.addRandomBoxes();
		}
	}

	public void setMap(Map map) {
		this.map = map;
	}

	protected void addTimer(JLayeredPane mainPane,int seed) {
		JLabel timer = new JLabel("Timer:  " + 0);
		Tools.Time time = new Tools.Time(0, timer, map, gameTime,seed);
		new Thread(time).start();
		timer.setBounds(100, 5, 100, 35);
		mainPane.add(timer);

	}

	protected void createScorePanel(JLayeredPane mainPane) {
		int number = Integer.parseInt((String) comboBox.getSelectedItem());
		JPanel panel = new JPanel();
		panel.setBounds(30, 40, 240, number * 130);
		panel.setLayout(new GridLayout(number, 0));
		JPanel player1 = new JPanel();
		player1.setBorder(new LineBorder(Color.black));
		player1.setBackground(new Color(218, 165, 32));
		createPlayerStatusPanel(player1, map.getPlayers().get(0),
				Resources.player1);
		panel.add(player1);
		JPanel player2 = new JPanel();
		player2.setBorder(new LineBorder(Color.black));
		player2.setBackground(new Color(218, 165, 32));
		createPlayerStatusPanel(player2, map.getPlayers().get(1),
				Resources.player2);
		panel.add(player2);
		if (number == 3) {
			JPanel player3 = new JPanel();
			player3.setBorder(new LineBorder(Color.black));
			player3.setBackground(new Color(218, 165, 32));
			createPlayerStatusPanel(player3, map.getPlayers().get(2),
					Resources.player3);
			panel.add(player3);
		}
		if (number == 4) {
			JPanel player3 = new JPanel();
			player3.setBorder(new LineBorder(Color.black));
			player3.setBackground(new Color(218, 165, 32));
			createPlayerStatusPanel(player3, map.getPlayers().get(2),
					Resources.player3);
			panel.add(player3);
			JPanel player4 = new JPanel();
			player4.setBorder(new LineBorder(Color.black));
			player4.setBackground(new Color(218, 165, 32));
			createPlayerStatusPanel(player4, map.getPlayers().get(3),
					Resources.player4);
			panel.add(player4);
		}
		mainPane.add(panel);
	}

	private void createPlayerStatusPanel(JPanel panel, Player player,
			ImageIcon imageIcon) {
		panel.setLayout(null);
		JLabel image = new JLabel(imageIcon);
		image.setBounds(10, 10, 50, 50);
		panel.add(image);
		JLabel health = new JLabel("Health: " + player.getHealth());
		player.setHealthLabel(health);
		health.setBounds(120, 1, 120, 30);
		panel.add(health);
		JLabel numberOfBombs = new JLabel("Bombs:  " + player.getBombs().size());
		numberOfBombs.setBounds(120, 20, 120, 30);
		player.setBombNumberLabel(numberOfBombs);
		panel.add(numberOfBombs);
		JLabel bombPower = new JLabel("Bomb Power:  " + player.getBombPower());
		player.setBombPowerLabel(bombPower);
		bombPower.setBounds(120, 39, 120, 30);
		panel.add(bombPower);
		JLabel speedLabel = new JLabel("Speed:  " + player.getSpeed());
		player.setSpeedLabel(speedLabel);
		speedLabel.setBounds(120, 58, 120, 30);
		panel.add(speedLabel);
		JButton transparentLabel = new JButton(Resources.transparent);
		transparentLabel.setContentAreaFilled(false);
		transparentLabel.setBorderPainted(false);
		player.setTransparentLabel(transparentLabel);
		transparentLabel.setBounds(10, 85, 40, 40);
		transparentLabel.setEnabled(false);
		panel.add(transparentLabel);
		JButton replaceLable = new JButton(Resources.replace);
		replaceLable.setContentAreaFilled(false);
		replaceLable.setBorderPainted(false);
		player.setReplaceLable(replaceLable);
		replaceLable.setBounds(60, 85, 40, 40);
		replaceLable.setEnabled(false);
		panel.add(replaceLable);
		JButton loseBombPowerLable = new JButton(Resources.loseBomb);
		loseBombPowerLable.setContentAreaFilled(false);
		loseBombPowerLable.setBorderPainted(false);
		player.setLoseBombPowerLable(loseBombPowerLable);
		loseBombPowerLable.setBounds(110, 85, 40, 40);
		loseBombPowerLable.setEnabled(false);
		panel.add(loseBombPowerLable);

	}

	protected void addPlayers(JLayeredPane pane, boolean client) {
		if (!client) {
			int number = Integer.parseInt((String) comboBox.getSelectedItem());
			for (int i = 1; i <= number; i++) {
				map.getPlayers().add(
						new Player(
								new ImageIcon("Resource/Player" + i + ".png")));
			}
		}
		for (int i = 0; i < map.getPlayers().size(); i++) {
			if (!client) {
				int[] randomNumber = new int[2];
				randomNumber = getRandomNumber(map.getRow(), map.getCol());
				map.getPlayers()
						.get(i)
						.setBounds(50 * randomNumber[1], 50 * randomNumber[0],
								50, 50);
				map.getPlayers().get(i).setyPosition(randomNumber[0] * 50);
				map.getPlayers().get(i).setxPosition(randomNumber[1] * 50);
			}
			map.getPlayers().get(i).getBombs()
					.add(new Bomb(map, map.getPlayers().get(i)));
			pane.add(map.getPlayers().get(i), new Integer(2));
		}

	}

	private int[] getRandomNumber(int row, int col) {
		int[] numbers = new int[2];
		Random random = new Random();
		int i = random.nextInt(row);
		int j = random.nextInt(col);
		while (map.getBlocks(i, j) instanceof StoneBlock
				|| map.getBlocks(i, j).getBox() != null) {
			i = random.nextInt(row);
			j = random.nextInt(col);
		}

		numbers[0] = i;
		numbers[1] = j;

		return numbers;
	}

	protected void addBlocks(JLayeredPane pane) {

		for (int i = 0; i < map.getRow(); i++) {
			for (int j = 0; j < map.getCol(); j++) {
				map.getBlocks(i, j).setBounds(50 * j, 50 * i, 50, 50);
				pane.add(map.getBlocks(i, j), new Integer(0));
				if (map.getBlocks(i, j).getBox() != null) {
					map.getBlocks(i, j).getBox()
							.setBounds(50 * j, 50 * i, 50, 50);
					pane.add(map.getBlocks(i, j).getBox(), new Integer(3));
					if (map.getBlocks(i, j).getFeatures() != null) {
						map.getBlocks(i, j).getFeatures()
								.setBounds(50 * j + 5, 50 * i + 5, 40, 40);
						pane.add(map.getBlocks(i, j).getFeatures(),
								new Integer(2));
					}
				}
			}
		}

	}

	protected void createFrame(JFrame gameFrame, JLayeredPane mainPane,
			JLayeredPane pane, JPanel gamePanel2, int state,clientInfo[] clients,ObjectInputStream in,ObjectOutputStream out) {
		// gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// gameFrame.setLayout(null);
		// gameFrame.setVisible(true);

		gameFrame.setBounds(100, 100, map.getCol() * 50 + 400,
				map.getRow() * 50 + 100);
		gamePanel.setBounds(0, 0, map.getCol() * 50 + 400,
				map.getRow() * 50 + 100);
		mainPane.setLayout(null);
		mainPane.setBounds(0, 0, map.getCol() * 50 + 400,
				map.getRow() * 50 + 100);
		gamePanel.add(mainPane);
		pane.setLayout(null);
		pane.setBounds(300, 30, map.getCol() * 50, map.getRow() * 50);
		mainPane.add(pane, new Integer(1));
		map.setPane(pane);
		JButton upLayer = new JButton();
		upLayer.setContentAreaFilled(false);
		upLayer.setBorderPainted(false);
		upLayer.setBounds(0, 0, map.getCol() * 50 + 400,
				map.getRow() * 50 + 100);
		upLayer.requestFocusInWindow();
		upLayer.addKeyListener(new MovePlayerListener(map, pane, state,clients,out,in));
		mainPane.add(upLayer, new Integer(2000));

	}

	protected class clientInfo implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3717939300657147457L;
		public Socket socket;
		public ObjectInputStream in;
		public ObjectOutputStream out;

		public clientInfo(Socket socket) throws IOException {
			this.socket = socket;
			out = new ObjectOutputStream(this.socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(this.socket.getInputStream());
		}
	}
}
