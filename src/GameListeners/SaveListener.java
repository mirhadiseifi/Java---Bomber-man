package GameListeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import GameObjects.Map;

public class SaveListener extends MouseAdapter {
	private ArrayList<String> names = new ArrayList<String>();
	private String mapName;
	private Map map;

	public SaveListener(Map map) {
		super();
		this.map = map;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		File file = new File("map save\\");
		file.mkdir();
		String[] temp = file.list();
		if (temp != null) {
			for (int i = 0; i < temp.length; i++) {
				names.add(temp[i]);
			}
		}
		mapName = JOptionPane.showInputDialog("Enter map name");
		ObjectOutputStream saveMap = null;
		if (mapName != null && !mapName.equals("")) {
			mapName += ".map";
			if (!names.contains(mapName)) {
				try {
					saveMap = new ObjectOutputStream(new FileOutputStream(
							"map save\\" + mapName));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "We can not save now");
				}
				try {
					saveMap.writeObject(map);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "We can not save now");
					e1.printStackTrace();
				}
				try {
					saveMap.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null,
							"We can not close file now");
				}
				JOptionPane.showMessageDialog(null, "Save successfully");
			} else {
				int i = JOptionPane.showConfirmDialog(null, "overwrite?");
				if (i == 0) {
					try {
						saveMap = new ObjectOutputStream(new FileOutputStream(
								"map save\\" + mapName));
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null,
								"We can not save now");
					}
					try {
						saveMap.writeObject(map);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null,
								"We can not save now");
					}
					try {
						saveMap.close();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null,
								"We can not close file now");
					}
					JOptionPane.showMessageDialog(null, "Save successfully");
				}
			}
		}
		names.clear();
	}
}