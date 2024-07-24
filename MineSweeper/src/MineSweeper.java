import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MineSweeper extends JFrame implements MouseListener{
	private static final long serialVersionUID = -7537051181899724144L;
	
	static int btnSize = 30;
	private int numMines = 40;
	private int[] boardSize = {16,16};
	
	private int menuSize;
	private int faceSize;
	private int minesNotMined;
	private int tilesNotTilted;
	private boolean firstClick = true;
	
	static private MineButton[][] tiles;
	private MineGrid mines;
	private Timer timer;
	private Clock task;
	private JPanel panel;
	private JPanel contentPane;
	private JTextField minesLeft;
	private JTextField timePlayed;
	private JButton reset;
	
	private ImageIcon iconBomb;
	private ImageIcon iconBombCrossed;
	private ImageIcon iconFlag;
	private ImageIcon iconFace0;
	private ImageIcon iconFace1;
	private ImageIcon iconFace2;
	
	private Opciones menu;
	public static JButton btnReset;
	
	private boolean mouse1 = false;
	private boolean mouse3 = false;
	private MineButton focusedButton;
	private MineButton focusedAux;

	//*******JDialog*****************
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MineSweeper frame = new MineSweeper();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setTitle("MineSweeper");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MineSweeper() {
		menuSize = (btnSize*4)/3;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, boardSize[0]*btnSize+36, boardSize[1]*btnSize+menuSize+60);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 10, boardSize[0]*btnSize, boardSize[1]*btnSize+menuSize);
		contentPane.add(panel);
		panel.setLayout(null);
		
		focusedAux = new MineButton(-10, -10);
		focusedAux.setDisabled(true);
		
		if (numMines>=(boardSize[1]*boardSize[0])) {
			setNumMines((boardSize[1]*boardSize[0])-1);
		}
		minesNotMined = numMines;
		faceSize = menuSize-6;
		tilesNotTilted = boardSize[0]*boardSize[1]-numMines;
		tiles = new MineButton[boardSize[1]][boardSize[0]];
		mines = new MineGrid(boardSize[1], boardSize[0], numMines);
		
		startImages();
		startButtons(tiles, panel);
		startHeader(panel);
		startOptions();
		
		timer = new Timer();
		task = new Clock(timePlayed);
	}
	
//************************Starters*************************
	public void startButtons(MineButton[][] tab, JPanel panel) {
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				tab[i][j] = new MineButton(i,j);
				tab[i][j].setBounds(btnSize*j, menuSize+btnSize*i, btnSize, btnSize);
				panel.add(tab[i][j]);
				tab[i][j].addMouseListener(this);
//				addClickers(tab[i][j]);
			}
		}
	}
	
	public void startHeader(JPanel panel) {
		startFace(panel);
		startMinesLeft(panel);
		startTimerRight(panel);
	}
	
	public void startFace(JPanel panel) {
		reset = new JButton();
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		reset.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton()==3){
					if (menu.isVisible()) {
						menu.setVisible(false);
					}
					else {
						menu.setVisible(true);
						menu.setLocationRelativeTo(panel);
					}
				}
			}
			@Override public void mouseClicked(MouseEvent arg0) {}
			@Override public void mouseEntered(MouseEvent arg0) {}
			@Override public void mouseExited(MouseEvent arg0) {}
			@Override public void mouseReleased(MouseEvent arg0) {}
		});
		reset.setBounds((panel.getWidth()-faceSize)/2, 0, faceSize, faceSize);
		panel.add(reset);
		reset.setIcon(iconFace0);
	}
	
	public void startMinesLeft(JPanel panel) {
		minesLeft = new JTextField();
		minesLeft.setEditable(false);
		
		minesLeft.setBounds(0, 0, 2*btnSize+2, menuSize-6);
		panel.add(minesLeft);
		minesLeft.setCaretColor(Color.black);
		minesLeft.setColumns(3);
		minesLeft.setBackground(Color.black);
		minesLeft.setHorizontalAlignment(SwingConstants.RIGHT);
		minesLeft.setFont(new Font("Tahoma", Font.PLAIN, btnSize));
		minesLeft.setForeground(Color.red);
		minesLeft.setBorder(new EmptyBorder(btnSize/6, btnSize/6, btnSize/6, btnSize/6));
		if (minesNotMined<=999) {
			minesLeft.setText(String.valueOf(minesNotMined));
		}
		else {
			minesLeft.setText(String.valueOf(999));
		}
	}
	
	public void startTimerRight(JPanel panel) {
		timePlayed = new JTextField();
		timePlayed.setEditable(false);
		timePlayed.setBounds(panel.getWidth()-2*btnSize+2, 0, 2*btnSize+2, menuSize-6);
		panel.add(timePlayed);
		timePlayed.setColumns(3);
		timePlayed.setBackground(Color.black);
		timePlayed.setHorizontalAlignment(SwingConstants.RIGHT);
		timePlayed.setFont(new Font("Tahoma", Font.PLAIN, btnSize));
		timePlayed.setForeground(Color.red);
		timePlayed.setBorder(new EmptyBorder(btnSize/6, btnSize/6, btnSize/6, 1+btnSize/6));
		timePlayed.setText(String.valueOf(0));
	}
	
	public void startImages() {
		iconBomb = new ImageIcon(MineSweeper.class.getResource("/Images/bomb.png"));
		iconFlag = new ImageIcon(MineSweeper.class.getResource("/Images/flag.png"));
		iconBombCrossed = new ImageIcon(MineSweeper.class.getResource("/Images/BombCrossed.png"));
		iconFace0 = new ImageIcon(MineSweeper.class.getResource("/Images/face0.png"));
		iconFace1 = new ImageIcon(MineSweeper.class.getResource("/Images/face1.png"));
		iconFace2 = new ImageIcon(MineSweeper.class.getResource("/Images/face2.png"));
		
		Image img = iconBomb.getImage();  
		img = img.getScaledInstance(btnSize-2, btnSize-2,  java.awt.Image.SCALE_SMOOTH) ;  
		iconBomb = new ImageIcon(img);
		
		img = iconBombCrossed.getImage();  
		img = img.getScaledInstance(btnSize-2, btnSize-2,  java.awt.Image.SCALE_SMOOTH) ;  
		iconBombCrossed = new ImageIcon(img);
		
		img = iconFlag.getImage();  
		img = img.getScaledInstance((btnSize*2)/3,(btnSize*2)/3 , java.awt.Image.SCALE_SMOOTH) ;  
		iconFlag = new ImageIcon(img);
		
		img = iconFace0.getImage();
		img = img.getScaledInstance(faceSize, faceSize,  java.awt.Image.SCALE_SMOOTH) ;  
		iconFace0 = new ImageIcon(img);
		
		img = iconFace1.getImage();
		img = img.getScaledInstance(faceSize, faceSize,  java.awt.Image.SCALE_SMOOTH) ;  
		iconFace1 = new ImageIcon(img);
		
		img = iconFace2.getImage();
		img = img.getScaledInstance(faceSize, faceSize,  java.awt.Image.SCALE_SMOOTH) ;  
		iconFace2 = new ImageIcon(img);
	}
	
	public void startOptions() {
		menu = new Opciones();
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hardReset();
			}
		});
	}
	
//*********************Funciones*****************************
	public void clickAdjacents(int i, int j) {
		boolean fail = false;
		for (int a = -1; a < 2; a++) {
			for (int b = -1; b < 2; b++) {
				if((i+a>=0)&&(j+b>=0)&&(i+a<tiles.length)&&(j+b<tiles[0].length)) {
					int num = mines.getNumber(i+a,j+b);
					if(tiles[i+a][j+b].leftClick(num, iconBomb)&&!fail){
						if (num==9) {
							fail=true;
						}
						else {
							tilesNotTilted--;
							if (tilesNotTilted==0) {
								win();
							}
							else if (num==0) {
								clickAdjacents(i+a,j+b);
							}
						}
					}
				}
			}
		}
		if (fail) {
			died();
		}
	}
	
	public void died() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j].setDisabled(true);
				tiles[i][j].setClicked(true);
				if (mines.getNumber(i,j)==9&&!tiles[i][j].isMined()) {
					tiles[i][j].endMine(iconBomb);
				}
				else if (mines.getNumber(i,j)!=9&&tiles[i][j].isMined()) {
					tiles[i][j].endMine(iconBombCrossed);
				}
			}
		}
		reset.setIcon(iconFace2);
		task.cancel();
	}

	public void reset() {
		mines.reset();
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j].reset();
			}
		}
		mouse1 = false;
		mouse3 = false;
		minesNotMined = numMines;
		firstClick = true;
		tilesNotTilted = boardSize[0]*boardSize[1]-numMines;
		if (minesNotMined<=999) {
			minesLeft.setText(String.valueOf(minesNotMined));
		}
		else {
			minesLeft.setText(String.valueOf(999));
		}
		reset.setIcon(iconFace0);
		task.cancel();
		task = new Clock(timePlayed);
		timePlayed.setText("0");
	}

	public void win() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j].setDisabled(true);
				tiles[i][j].setClicked(true);
				if (mines.getNumber(i,j)==9) {
					tiles[i][j].endMine(iconFlag);
				}
			}
		}
		minesLeft.setText("0");
		reset.setIcon(iconFace1);
		task.cancel();
	}

	public void hardReset() {
		int[] values = menu.getValues();
		boardSize[0]=values[0];
		boardSize[1]=values[1];
		numMines = values[2];
		btnSize = values[3];
		
		panel.removeAll();
		
		menuSize = (btnSize*4)/3;
		this.setBounds(0, 0, boardSize[0]*btnSize+36, boardSize[1]*btnSize+menuSize+60);
		this.setLocationRelativeTo(null);
		panel.setBounds(10, 10, boardSize[0]*btnSize, boardSize[1]*btnSize+menuSize);
		
		if (numMines>=(boardSize[1]*boardSize[0])) {
			setNumMines((boardSize[1]*boardSize[0])-1);
		}
		
		faceSize = menuSize-6;
		tilesNotTilted = boardSize[0]*boardSize[1]-numMines;
		tiles = new MineButton[boardSize[1]][boardSize[0]];
		mines = new MineGrid(boardSize[1], boardSize[0], numMines);
		
		startImages();
		startButtons(tiles, panel);
		startHeader(panel);
		startOptions();
		reset();
	}
	
	public void test() {
		System.out.println("EstoVa");
	}
	
	public void doubleClick(int i, int j) {
		int num = mines.getNumber(i,j);
		int mines = 0;
		for (int a = -1; a < 2; a++) {
			for (int b = -1; b < 2; b++) {
				if((i+a>=0)&&(j+b>=0)&&(i+a<tiles.length)&&(j+b<tiles[0].length)) {
					if(tiles[i+a][j+b].isMined()){
						mines++;
					}
				}
			}
		}
		if (num==mines) {
			clickAdjacents(i, j);
		}
	}
	
	public void setNumMines(int numMines) {
		this.numMines = numMines;
	}

	public void hover9(boolean bool) {
		int i = focusedButton.getPosI();
		int j = focusedButton.getPosJ();
		for (int a = -1; a < 2; a++) {
			for (int b = -1; b < 2; b++) {
				if((i+a>=0)&&(j+b>=0)&&(i+a<tiles.length)&&(j+b<tiles[0].length)) {
					tiles[i+a][j+b].hover(bool);
				}
			}
		}
	}
	
	//******************Listeners****************************
	@Override public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		focusedButton = (MineButton) e.getComponent();
		if (mouse1&&mouse3) {
			hover9(true);
		}
		else if (mouse1) {
			focusedButton.hover(true);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (mouse1&&mouse3) {
			hover9(false);
		}
		else if (mouse1) {
			focusedButton.hover(false);
		}
		focusedButton = focusedAux;
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton()==1) {
			mouse1=true;
			if (mouse3) {
				hover9(true);
			}
			else {
				focusedButton.hover(true);
			}
		}
		else if (e.getButton()==3) {
			mouse3=true;
			if (mouse1) {
				hover9(true);
			}
			else if (!focusedButton.isClicked()) {
				if (focusedButton.rightClick(iconFlag)) {
					minesNotMined--;
				}
				else {
					minesNotMined++;
				}
				if (minesNotMined<=999) {
					minesLeft.setText(String.valueOf(minesNotMined));
				}
				else {
					minesLeft.setText(String.valueOf(999));
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (focusedButton.isDisabled()) {
			mouse1=false;
			mouse3=false;
			return;
		}
		int num = mines.getNumber(focusedButton.getPosI(), focusedButton.getPosJ());
		if(mouse1&&mouse3){
			if (num!=0&&focusedButton.isClicked()&&!focusedButton.isMined()) {
				doubleClick(focusedButton.getPosI(), focusedButton.getPosJ());
			}
			hover9(false);
			mouse1=false;
			mouse3=false;
		}
		else if (e.getButton() == 1) {
			if (!mouse1) {
				return;
			}
			if (firstClick&&focusedButton!=focusedAux) {
				mines.setToBlanc(focusedButton.getPosI(), focusedButton.getPosJ());
				mines.setNumbers();
				timer.schedule(task, 1000, 1000);
				firstClick = false;
				num = mines.getNumber(focusedButton.getPosI(), focusedButton.getPosJ());
			}
			if(focusedButton.leftClick(num, iconBomb)) {
				if (num==9) {
					died();
				}
				else {
					tilesNotTilted--;
					if (tilesNotTilted==0) {
						win();
					}
					else if(num==0) {
						clickAdjacents(focusedButton.getPosI(),focusedButton.getPosJ());
					}
				}
			}
			mouse1=false;
		}
		else if (e.getButton() == 3) {
			mouse3=false;
		}
	}
}
