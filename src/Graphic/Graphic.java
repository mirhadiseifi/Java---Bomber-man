package Graphic;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GameListeners.ClientListener;
import GameListeners.EditMapListener;
import GameListeners.PlayerInfoListener;
import GameListeners.ServerListener;
import GameListeners.StartListener;
import GameObjects.Map;

public class Graphic extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6164321948611650270L;
	JPanel startPanel;
	JPanel gamePanel;
	//JPanel editPanel;
	Map map;
	JLayeredPane pane;
	StartListener listener;

	public Graphic() {
		super("Bomber Man");
		setFrame();
		setButtons();
		repaint();
	}

	private void setButtons() {
		JLabel numberOfPlayer = new JLabel("Number Of Player:");
		numberOfPlayer.setBounds(60, 60, 130, 30);
		startPanel.add(numberOfPlayer);
		String[] number = { "2", "3", "4" };
		JComboBox comboBox = new JComboBox(number);
		comboBox.setBounds(200, 60, 100, 30);
		startPanel.add(comboBox);
		JButton playerInfor = new JButton("Player Info");
		playerInfor.setBounds(60, 120, 100, 30);
		startPanel.add(playerInfor);
		playerInfor.addMouseListener(new PlayerInfoListener(comboBox));
		JButton start = new JButton("Start Game");
		start.setBounds(60, 180, 100, 30);
		startPanel.add(start);
		JLabel timeLabel = new JLabel("Time:(Seconds)");
		timeLabel.setBounds(360, 60, 100, 30);
		startPanel.add(timeLabel);
		JTextField time = new JTextField("120");
		time.setBounds(460, 60, 100, 30);
		startPanel.add(time);
		JLabel row = new JLabel("Row: ");
		row.setBounds(360, 130, 100, 30);
		startPanel.add(row);
		JTextField rowTextField = new JTextField("10");
		rowTextField.setBounds(460, 130, 100, 30);
		startPanel.add(rowTextField);
		JLabel col = new JLabel("Column: ");
		col.setBounds(360, 170, 100, 30);
		startPanel.add(col);
		JTextField colTextField = new JTextField("15");
		colTextField.setBounds(460, 170, 100, 30);
		startPanel.add(colTextField);
		JButton editMap = new JButton("Edit Map");
		editMap.setBounds(400, 230, 100, 30);
		startPanel.add(editMap);
		map = new Map(10, 15);
		listener = new StartListener(this, comboBox, time, gamePanel,
				startPanel, map);
		editMap.addMouseListener(new EditMapListener(this, map,
				rowTextField, colTextField, listener));
		start.addMouseListener(listener);
		serverClient(comboBox, time, map);
		startPanel.validate();
		startPanel.repaint();

	}

	private void serverClient(JComboBox comboBox, JTextField time, Map map) {
		JButton server = new JButton("Server");
		JButton client = new JButton("Client");
		JTextField port = new JTextField("12345");
		JTextField ip = new JTextField("127.0.0.1");
		server.setBounds(30, 250, 100, 30);
		client.setBounds(30, 285, 100, 30);
		port.setBounds(140, 285, 60, 30);
		port.setBounds(140, 285, 60, 30);
		ip.setBounds(140, 250, 80, 30);
		startPanel.add(server);
		startPanel.add(client);
		startPanel.add(port);
		startPanel.add(ip);
		JLabel[] connectView = new JLabel[3];
		for (int i = 0; i < connectView.length; i++) {
			connectView[i] = new JLabel();
			connectView[i].setOpaque(true);
			connectView[i].setBounds(230, 250+22*i, 70, 20);
			startPanel.add(connectView[i]);
		}
		server.addMouseListener(new ServerListener(this, comboBox, time,
				gamePanel, startPanel, map, port, connectView));
		client.addMouseListener(new ClientListener(this, comboBox, time, gamePanel, startPanel, map, port, ip));
	}

	private void setFrame() {
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 600, 400);
		setVisible(true);
		startPanel = new JPanel();
		startPanel.setLayout(null);
		startPanel.setBounds(0, 0, 600, 400);
		add(startPanel);
		gamePanel = new JPanel();
		gamePanel.setLayout(null);
		add(gamePanel);

	}


}
