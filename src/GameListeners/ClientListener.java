package GameListeners;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GameObjects.Map;
import Graphic.Graphic;

public class ClientListener extends StartListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7930408380918515822L;
	private Socket socket;
	private JTextField port;
	private JTextField ip;

	public ClientListener(Graphic graphic, JComboBox comboBox, JTextField time,
			JPanel gamePanel, JPanel startPanel, Map map, JTextField port,
			JTextField ip) {
		super(graphic, comboBox, time, gamePanel, startPanel, map);
		this.port = port;
		this.ip = ip;
	}

	public void mouseClicked(MouseEvent e) {
			JOptionPane.showMessageDialog(graphic, "Connecting to server...");
			try {
				socket = new Socket(ip.getText(), Integer.parseInt(port.getText()));
			JOptionPane.showMessageDialog(graphic,
					"Connected successfully. Pleas wait for game starting");
			ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());
			out.flush();
			ObjectInputStream in = new ObjectInputStream(
					socket.getInputStream());
			map = (Map) in.readObject();
			String selected = (String) in.readObject();
			String time = (String) in.readObject();
			int number = (Integer)in.readObject();
			int seed = (Integer) in.readObject();
			comboBox.setSelectedItem(selected);
			this.time.setText(time);
			gameTime = Integer.parseInt(time);
			JLayeredPane mainPane = new JLayeredPane();
			JLayeredPane pane = new JLayeredPane();
			createFrame(graphic, mainPane, pane, gamePanel, number,null,in,out);
			addPlayers(pane, true);
			addBlocks(pane);
			addTimer(mainPane,seed);
			createScorePanel(mainPane);
			startPanel.setVisible(false);
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
	}
}
