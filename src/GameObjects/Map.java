package GameObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLayeredPane;

import GameFeatures.Features;
import Tools.Probibility;

public class Map implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6727466732087035266L;
	private Block[][] blocks;
	private int row;
	private int col;
	private ArrayList<Player> players = new ArrayList<Player>();
	private JLayeredPane pane;
	private ArrayList<Features> features = new ArrayList<Features>();

	public Map(int row, int col) {
		super();
		this.row = row;
		this.col = col;

		blocks = new Block[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (i == 0 || j == 0 || i == row - 1 || j == col - 1) {
					blocks[i][j] = new StoneBlock(i, j);

				} else {
					blocks[i][j] = new GrassBlock(i, j);

				}

			}
		}
		// addFeatures();

	}

	public void addRandomBoxes() {
		Random random = new Random();
		for (int i = 0; i < 30; i++) {
			int r = random.nextInt(row);
			int c = random.nextInt(col);
			while (blocks[r][c] instanceof StoneBlock
					|| blocks[r][c].getBox() != null) {
				r = random.nextInt(row);
				c = random.nextInt(col);
			}
			blocks[r][c].setBox(new Box());
		}
	}

	public void addFeatures() {
		int counter = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (getBlocks(i, j).getBox() != null) {
					counter++;
				}
			}
		}
		Random r = new Random();
		for (int i = 0; i < counter / 2; i++) {
			Features f = Probibility.canDo(r.nextDouble(), this);
			features.add(f);
			int x = r.nextInt(row);
			int y = r.nextInt(col);
			while (blocks[x][y].getFeatures() != null
					|| blocks[x][y].getBox() == null) {
				x = r.nextInt(row);
				y = r.nextInt(col);
			}
			blocks[x][y].setFeatures(f);
		}

	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Block getBlocks(int i, int j) {
		return blocks[i][j];
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public void setPane(JLayeredPane pane) {
		this.pane = pane;
	}

	public JLayeredPane getPane() {
		return pane;
	}

	public ArrayList<Features> getFeatures() {
		return features;
	}

}
