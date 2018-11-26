package GameListeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.JPopupMenu;

import GameObjects.Block;
import GameObjects.Box;
import GameObjects.Map;
import GameObjects.StoneBlock;

public class AddBoxORStoneListener extends MouseAdapter {
	private MouseEvent event;
	private Map map;
	private JPopupMenu menu;
	private JLayeredPane pane;
	private String type;

	public AddBoxORStoneListener(MouseEvent event, Map map, JPopupMenu menu,
			JLayeredPane pane, String type) {
		super();
		this.event = event;
		this.map = map;
		this.menu = menu;
		this.pane = pane;
		this.type = type;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		menu.setVisible(false);
		Block block = (Block) event.getComponent();
		if (type.equals("box")) {
			Box box = new Box();
			box.setBounds(block.getJ() * 50, block.getI() * 50, 50, 50);
			block.setBox(box);
			box.addMouseListener(new DeleteListener(map, pane));
			pane.add(box, new Integer(2));
		} else if (type.equals("stone")) {
			pane.remove(map.getBlocks()[block.getI()][block.getJ()]);
			map.getBlocks()[block.getI()][block.getJ()] = new StoneBlock(
					block.getI(), block.getJ());
			map.getBlocks(block.getI(), block.getJ()).setBounds(
					50 * block.getJ(), 50 * block.getI(), 50, 50);
			map.getBlocks(block.getI(), block.getJ()).addMouseListener(
					new DeleteListener(map, pane));
			pane.add(map.getBlocks(block.getI(), block.getJ()), new Integer(2));
		}
		pane.repaint();
	}
}
