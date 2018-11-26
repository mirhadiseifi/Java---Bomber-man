package GameListeners;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GameObjects.Map;
import Graphic.Graphic;

public class ServerListener extends StartListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2389985020853250050L;
	private ServerSocket server;
	private clientInfo[] clients;;
	private JTextField port;
	private JLabel[] connectView;

	public ServerListener(Graphic graphic, JComboBox comboBox, JTextField time,
			JPanel gamePanel, JPanel startPanel, Map map, JTextField port,
			JLabel[] connectView) {
		super(graphic, comboBox, time, gamePanel, startPanel, map);
		this.port = port;
		this.connectView = connectView;
	}

	public void mouseClicked(final MouseEvent e) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				comboBox.setEnabled(false);
				port.setEnabled(false);
				time.setEnabled(false);
				serverInit();
				waitForClient();
				mapInit();
				map.addFeatures();
				JLayeredPane mainPane = new JLayeredPane();
				JLayeredPane pane = new JLayeredPane();
				createFrame(graphic, mainPane, pane, gamePanel, 0,clients,null,null);
				addBlocks(pane);
				addPlayers(pane, false);
				createScorePanel(mainPane);
				Random r = new Random();
				int seed = r.nextInt(50);
				addTimer(mainPane,seed);
				startPanel.setVisible(false);
				try {
					sendToClient(seed);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}).start();
	}

	private void waitForClient() {
		while (true) {
			int connected = 0;
			for (int i = 0; i < clients.length; i++) {
				if (clients[i] != null)
					connected++;
			}
			if (connected == comboBox.getSelectedIndex() + 1)
				break;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void sendToClient(int seed) throws IOException {
		for (int i = 0; i < clients.length; i++) {
			clients[i].out.writeObject(map);
			clients[i].out.writeObject(comboBox.getSelectedItem());
			clients[i].out.writeObject(time.getText());
			clients[i].out.writeObject(i+1);
			clients[i].out.writeObject(seed);
			clients[i].out.flush();
		}
	}

	private void serverInit() {
		for (int j = 0; j < Integer.parseInt((String) comboBox
				.getSelectedItem()) - 1; j++) {
			connectView[j].setBackground(Color.RED);
		}
		class GetConnection implements Runnable {
			private int index;

			public GetConnection(int i) {
				index = i;
			}

			public void run() {
				try {
					Socket socket = server.accept();
					clients[index] = new clientInfo(socket);
					connectView[index].setBackground(Color.GREEN);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			server = new ServerSocket(Integer.parseInt(port.getText()));
			int player = comboBox.getSelectedIndex() + 1;
			clients = new clientInfo[player];
			for (int i = 0; i < player; i++) {
				new Thread(new GetConnection(i)).start();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
