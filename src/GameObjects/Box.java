package GameObjects;

import java.io.Serializable;

import javax.swing.JButton;

import Tools.Resources;

public class Box extends JButton implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7758804060820038545L;

	public Box() {
		setIcon(Resources.box);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusable(false);
	}
}
