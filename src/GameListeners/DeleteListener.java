package GameListeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;

import GameObjects.Box;
import GameObjects.GrassBlock;
import GameObjects.Map;
import GameObjects.StoneBlock;

public class DeleteListener extends MouseAdapter {
	private Map map;
	private JLayeredPane pane;

	public DeleteListener(Map map, JLayeredPane pane) {
		super();
		this.map = map;
		this.pane = pane;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object object = e.getComponent();
		if (object instanceof StoneBlock) {
			StoneBlock block = (StoneBlock) object;
			pane.remove(map.getBlocks()[block.getI()][block.getJ()]);
			map.getBlocks()[block.getI()][block.getJ()] = new GrassBlock(
					block.getI(), block.getJ());
			map.getBlocks(block.getI(), block.getJ()).setBounds(
					50 * block.getJ(), 50 * block.getI(), 50, 50);
			map.getBlocks(block.getI(), block.getJ()).addMouseListener(
					new BlockListener(map, pane));
			pane.add(map.getBlocks(block.getI(), block.getJ()), new Integer(2));
		} else if (object instanceof Box) {
			Box box = (Box) object;
			for (int i = 0; i < map.getRow(); i++) {
				for (int j = 0; j < map.getCol(); j++) {
					if (map.getBlocks(i, j).getBox() != null
							&& map.getBlocks(i, j).getBox().equals(box)) {
						map.getBlocks(i, j).setBox(null);
						pane.remove(box);
					}
				}
			}
		}
		pane.repaint();
	}
}
