package Tools;

import java.io.Serializable;
import java.util.Random;

import javax.swing.JLabel;

import GameObjects.Map;
import GameObjects.StoneBlock;
import GameObjects.Well;

public class Time implements Runnable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1930022893151568927L;
	public int time;
	public JLabel timelable;
	private Map map;
	private int gameTime;
	private int seed;

	public Time(int time, JLabel timer, Map map, int gameTime,int seed) {
		super();
		this.time = time;
		this.timelable = timer;
		this.gameTime = gameTime;
		this.map = map;
		this.seed = seed;
	}

	@Override
	public void run() {
		try {
			while (time <= gameTime) {
				Thread.sleep(1000);
				time++;
				timelable.setText("Timer:  " + time);
			}
			Random random = new Random(seed);
			while (true) {
				int r = random.nextInt(map.getRow());
				int c = random.nextInt(map.getCol());
				while (map.getBlocks(r, c) instanceof StoneBlock
						|| map.getBlocks(r, c).getBox() != null
						|| map.getBlocks(r, c).getWell() != null) {
					r = random.nextInt(map.getRow());
					c = random.nextInt(map.getCol());
				}
				Well w = new Well();
				map.getBlocks(r, c).setWell(w);
				w.setBounds(c * 50 + 5, r * 50 + 5, 40, 40);
				map.getPane().add(w, new Integer(4));
				map.getPane().repaint();
				Thread.sleep(2000);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
