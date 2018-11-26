package GameObjects;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import GameFeatures.Features;

public class Player extends JButton implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1663355184878964306L;
	private int xPosition;
	private int yPosition;
	private int speed = 5;
	private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	private int health = 2;
	private JLabel healthLabel;
	private JLabel bombNumberLabel;
	private JLabel bombPowerLabel;
	private JLabel speedLabel;
	private JButton transparentLabel;
	private JButton replaceLable;
	private JButton loseBombPowerLable;
	private boolean isAlive = true;
	private boolean transparent = false;
	private boolean buttonReplaced = false;
	private ArrayList<Features> lastPower = new ArrayList<Features>();
	private int bombPower = 1;

	public Player(ImageIcon icon) {
		setIcon(icon);
		setContentAreaFilled(false);
		setBorderPainted(false);
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public ArrayList<Bomb> getBombs() {
		return bombs;
	}

	public void setBombs(ArrayList<Bomb> bombs) {
		this.bombs = bombs;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	public void setHealthLabel(JLabel healthLabel) {
		this.healthLabel = healthLabel;
	}

	public JLabel getHealthLabel() {
		return healthLabel;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean isTransparent() {
		return transparent;
	}

	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

	public boolean isButtonReplaced() {
		return buttonReplaced;
	}

	public void setButtonReplaced(boolean buttonReplaced) {
		this.buttonReplaced = buttonReplaced;
	}

	public ArrayList<Features> getLastPower() {
		return lastPower;
	}

	public void setLastPower(ArrayList<Features> lastPower) {
		this.lastPower = lastPower;
	}

	public int getBombPower() {
		return bombPower;
	}

	public void setBombPower(int bombPower) {
		this.bombPower = bombPower;
	}

	public JLabel getBombNumberLabel() {
		return bombNumberLabel;
	}

	public void setBombNumberLabel(JLabel bombNumberLabel) {
		this.bombNumberLabel = bombNumberLabel;
	}

	public void setBombPowerLabel(JLabel bombPowerLabel) {
		this.bombPowerLabel = bombPowerLabel;
	}

	public JLabel getBombPowerLabel() {
		return bombPowerLabel;
	}

	public void setSpeedLabel(JLabel speedLabel) {
		this.speedLabel = speedLabel;
	}

	public JLabel getSpeedLabel() {
		return speedLabel;
	}

	public JButton getTransparentLabel() {
		return transparentLabel;
	}

	public void setTransparentLabel(JButton transparentLabel) {
		this.transparentLabel = transparentLabel;
	}

	public JButton getReplaceLable() {
		return replaceLable;
	}

	public void setReplaceLable(JButton replaceLable) {
		this.replaceLable = replaceLable;
	}

	public JButton getLoseBombPowerLable() {
		return loseBombPowerLable;
	}

	public void setLoseBombPowerLable(JButton loseBombPowerLable) {
		this.loseBombPowerLable = loseBombPowerLable;
	}

}
