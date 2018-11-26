package GameObjects;

import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import Tools.Resources;

public class Explosion extends JButton implements Runnable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6723789444760531390L;
	private JLayeredPane pane;
	private Map map;

	public Explosion(JLayeredPane pane, Map map) {
		this.pane = pane;
		this.map = map;
		setIcon(Resources.explosion);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusable(false);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pane.remove(this);
		pane.repaint();

	}

}
