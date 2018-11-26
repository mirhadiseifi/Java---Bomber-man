package GameListeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPopupMenu;

import GameObjects.Map;

public class BlockListener extends MouseAdapter {
	private Map map;
	private JLayeredPane pane;

	public BlockListener(Map map, JLayeredPane pane) {
		super();
		this.map = map;
		this.pane = pane;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JPopupMenu menu = new JPopupMenu();
		JButton addBox = new JButton("Add Box");
		addBox.setContentAreaFilled(false);
		menu.add(addBox);
		JButton addStone = new JButton("Add Stone");
		addStone.setContentAreaFilled(false);
		menu.add(addStone);
		addBox.addMouseListener(new AddBoxORStoneListener(e, map, menu, pane,
				"box"));
		addStone.addMouseListener(new AddBoxORStoneListener(e, map, menu, pane,
				"stone"));
		menu.show(e.getComponent(), e.getX(), e.getY());
	}
}
