package GameListeners;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PlayerInfoListener extends MouseAdapter {
	private JComboBox comboBox;

	public PlayerInfoListener(JComboBox comboBox) {
		this.comboBox = comboBox;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JFrame info = new JFrame("Player Info");
		info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		int number = Integer.parseInt((String) comboBox.getSelectedItem());
		info.setBounds(200, 100, number * 200, 400);
		info.setLayout(new GridLayout(0, number));
		for (int i = 1; i < number + 1; i++) {
			info.add(new JLabel(new ImageIcon("Resource/Player" + i + ".png")));
		}
		String[] upButton = { "Up Arrow", "W", "Y", "O" };
		String[] downButton = { "Down Arrow", "S", "H", "L" };
		String[] leftButton = { "Left Arrow", "A", "G", "K" };
		String[] rightButton = { "Right Arrow", "D", "J", ";" };
		String[] bombButton = { "Enter", "Q", "T", "I" };
		for (int i = 0; i < number; i++) {
			info.add(new JLabel("Up: " + upButton[i], JLabel.CENTER));
		}
		for (int i = 0; i < number; i++) {
			info.add(new JLabel("Down: " + downButton[i], JLabel.CENTER));
		}
		for (int i = 0; i < number; i++) {
			info.add(new JLabel("Left: " + leftButton[i], JLabel.CENTER));
		}
		for (int i = 0; i < number; i++) {
			info.add(new JLabel("Right: " + rightButton[i], JLabel.CENTER));
		}
		for (int i = 0; i < number; i++) {
			info.add(new JLabel("Bomb: " + bombButton[i], JLabel.CENTER));
		}
		info.setVisible(true);
		info.repaint();

	}
}
