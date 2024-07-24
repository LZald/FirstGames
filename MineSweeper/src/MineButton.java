import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

class MineButton extends JLabel{
	private static final long serialVersionUID = 1717009307797938654L;
	
	private int posI;
	private int posJ;
	private boolean mined;
	private boolean clicked;
	private boolean disabled;
	
	private Border border;
	
	public MineButton(int i, int j) {
		super();
		this.posI = i;
		this.posJ = j;
		this.mined = false;
		this.clicked = false;
		this.disabled = false;
		this.setFont(new Font("Tahoma", Font.PLAIN, (MineSweeper.btnSize*2)/3));
		this.setBackground(Color.lightGray);
		this.setOpaque(true);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		setBorder();
	}

	//**************Getters***********************************
	public int getPosI() {
		return posI;
	}

	public int getPosJ() {
		return posJ;
	}

	public boolean isMined() {
		return mined;
	}

	public boolean isDisabled() {
		return disabled;
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
	//***************Setters***********************************
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	//****************Functions**********************************
	public void setBorder() {
		if (posI==0&&posJ==0) {
			border = new MatteBorder(1, 1, 1, 1,SystemColor.controlShadow);
		}
		else if (posI==0) {
			border = new MatteBorder(1, 0, 1, 1,SystemColor.controlShadow);
		}
		else if (posJ==0) {
			border = new MatteBorder(0, 1, 1, 1,SystemColor.controlShadow);
		}
		else {
			border = new MatteBorder(0, 0, 1, 1,SystemColor.controlShadow);
		}
		this.setBorder(border);
	}
	
	public boolean rightClick(ImageIcon iconFlag) {
		if (clicked) {
			return false;
		}
		if (mined) {
			this.setIcon(null);
			mined = false;
			return false;
		}
		this.setIcon(iconFlag);
		mined = true;
		return true;
	}
	
	public boolean leftClick(int v, ImageIcon iconBomb) {
		if (mined||clicked) {
			return false;
		}
		this.clicked = true;
		this.setBackground(Color.GRAY);
		switch(v) {
			case 0:{
				return true;
			}
			case 1:{
				this.setForeground(new Color(0, 102, 255));
				break;
			}
			case 2:{
				this.setForeground(Color.green);
				break;
			}
			case 3:{
				this.setForeground(Color.red);
				break;
			}
			case 4:{
				this.setForeground(new Color(0, 0, 128));
				break;
			}
			case 5:{
				this.setForeground(new Color(128, 0, 0));
				break;
			}
			case 6:{
				this.setForeground(Color.cyan);
				break;
			}
			case 7:{
				this.setForeground(Color.black);
				break;
			}
			case 8:{
				this.setForeground(SystemColor.controlHighlight);
				break;
			}
			case 9:{
				this.setIcon(iconBomb);
				this.setBackground(Color.red);
				return true;
			}
		}
		this.setText(String.valueOf(v));
		return true;
	}

	public void endMine(ImageIcon iconBomb) {
		this.setIcon(iconBomb);
	}

	public void reset() {
		this.mined = false;
		this.clicked = false;
		this.disabled = false;
		this.setBackground(Color.lightGray);
		this.setText(null);
		this.setIcon(null);
	}

	public void hover(boolean b) {
		if (clicked||disabled||mined) {
			return;
		}
		if (b) {
			this.setBackground(Color.gray);
		}
		else {
			this.setBackground(Color.lightGray);
		}
	}
}