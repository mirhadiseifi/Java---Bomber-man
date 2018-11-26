package GameObjects;

import java.io.Serializable;

import javax.swing.JButton;

import Tools.Resources;

public class Well extends JButton implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7318829770624040262L;

	public Well() {
		setIcon(Resources.well);
		setContentAreaFilled(false);
		setBorderPainted(false);
	}
}
