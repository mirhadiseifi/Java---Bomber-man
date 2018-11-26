package GameListeners;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import GameObjects.Map;
import GameObjects.StoneBlock;
import Graphic.Graphic;

public class EditMapListener extends MouseAdapter {
	private Map map;
	private JTextField rowTextField;
	private JTextField colTextField;
	private Graphic graphic;
	private StartListener listener;
	private JLayeredPane pane;

	public EditMapListener(Graphic graphic, Map map, JTextField rowTextField,
			JTextField colTextField, StartListener listener) {
		super();
		this.map = map;
		this.graphic = graphic;
		// this.editPanel = editPanel;
		this.rowTextField = rowTextField;
		this.colTextField = colTextField;
		this.listener = listener;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			int row = Integer.parseInt(rowTextField.getText());
			int col = Integer.parseInt(colTextField.getText());
			map = new Map(row, col);
			graphic.setVisible(false);
			createFrame();
			addInitialBlocks();

		} catch (Exception e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null, "Wrong Input");
		}
	}

	public void addInitialBlocks() {
		for (int i = 0; i < map.getRow(); i++) {
			for (int j = 0; j < map.getCol(); j++) {
				map.getBlocks(i, j).setBounds(50 * j, 50 * i, 50, 50);
				pane.add(map.getBlocks(i, j), new Integer(0));
				
				if (map.getBlocks(i, j).getBox() != null) {
					map.getBlocks(i, j).getBox()
							.setBounds(50 * j, 50 * i, 50, 50);
					pane.add(map.getBlocks(i, j).getBox(), new Integer(2));
					map.getBlocks(i, j).getBox()
							.addMouseListener(new DeleteListener(map, pane));
				}
				if (i != 0 && j != 0 && i != map.getRow() - 1
						&& j != map.getCol() - 1) {
					if (map.getBlocks(i, j) instanceof StoneBlock) {
						map.getBlocks(i, j).addMouseListener(new DeleteListener(map, pane));
					}else
						map.getBlocks(i, j).addMouseListener(
								new BlockListener(map, pane));
						
				}
			}
		}
		pane.repaint();

	}

	public void createFrame() {
		final JFrame graphic = new JFrame();
		graphic.setVisible(true);
		graphic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel editPanel = new JPanel();
		editPanel.setLayout(null);
		graphic.add(editPanel);
		pane = new JLayeredPane();
		graphic.setBounds(50, 50, 50 * map.getCol() + 30,
				50 * map.getRow() + 100);
		editPanel.setBounds(0, 0, 50 * map.getCol() + 30,
				50 * map.getRow() + 100);
		pane.setLayout(null);
		pane.setBounds(10, 10, 50 * map.getCol(), 50 * map.getRow());
		editPanel.add(pane);
		editPanel.setBackground(new Color(46, 139, 87));
		JButton save = new JButton("Save");
		save.setBounds(25 * map.getCol() - 120, 50 * map.getRow() + 20, 80, 30);
		editPanel.add(save);
		save.addMouseListener(new SaveListener(map));
		JButton load = new JButton("Load");
		load.setBounds(25 * map.getCol() - 30, 50 * map.getRow() + 20, 80, 30);
		editPanel.add(load);
		load.addMouseListener(new LoadListener(graphic));
		JButton back = new JButton("Back");
		back.setBounds(25 * map.getCol() + 60, 50 * map.getRow() + 20, 80, 30);
		editPanel.add(back);
		graphic.repaint();
		editPanel.repaint();
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				graphic.setVisible(false);
				graphic.dispose();
				EditMapListener.this.graphic.setVisible(true);
				listener.setMap(map);
			}
		});

	}

	class LoadListener extends MouseAdapter {
		JFrame graphic;

		public LoadListener(JFrame graphic) {
			this.graphic = graphic;
		}

		public void mouseClicked(MouseEvent e) {
			JFileChooser load = new JFileChooser(new File("map save"));
			load.setFileFilter(new FileFilter() {

				@Override
				public String getDescription() {
					return "Map File   (*.map)";
				}

				@Override
				public boolean accept(File f) {
					if (f.isDirectory())
						return false;
					return f.getName().endsWith(".map");
				}
			});
			int returnVal = load.showOpenDialog(null);
			File file = load.getSelectedFile();
			try {
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					ObjectInputStream loadMapFile = new ObjectInputStream(
							new FileInputStream(file));
					map = (Map) loadMapFile.readObject();
					graphic.dispose();
					JOptionPane.showMessageDialog(null, "Loaded successfully");
					loadMapFile.close();
				} else if (returnVal == JFileChooser.CANCEL_OPTION)
					return;
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "File not found");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "IO Exception");
			} catch (ClassNotFoundException e1) {
			}
			// showMap(map);
			createFrame();
			addInitialBlocks();

		}

	}

}
