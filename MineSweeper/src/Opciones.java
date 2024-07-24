import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;

public class Opciones extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private int[] rangeHeigh = {1,99};
	private int[] rangeWidth = {8,99};
	private int[] rangeMines = {0,99};
	private int[] rangeSize = {10,50};
	private String dif="";
	
	private final JPanel panel = new JPanel();
	private JPanel buttonPane;
	
	private ButtonGroup btnsDificultades;
	private MyRadioButton btnHard;
	private MyRadioButton btnNormal;
	private MyRadioButton btnEasy;
	private MyRadioButton btnCustom;
	private JLabel menuTitle;
	private JLabel menuIndex;
	private JFormattedTextField txtHeigh;
	private JFormattedTextField txtWidth;
	private JFormattedTextField txtMines;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JFormattedTextField txtSize;
	private JSlider slider;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Opciones dialog = new Opciones();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Opciones() {
		setBounds(10, 10, 300, 240);
		getContentPane().setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		ImageIcon imgIcon = new ImageIcon(MineSweeper.class.getResource("/Images/Options.jpg")); 
		Image img = imgIcon.getImage();
		img = img.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH) ;  
		this.setIconImage(img);
		this.setTitle("Opciones");
		this.addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {}
            public void windowLostFocus(WindowEvent e) {
                Opciones.this.setVisible(false);
            }
        });
		
		startMenu();
		startButtons();
	}
	
	public void startMenu() {
		startRadioButtons();
		startNumberFields();
		startSlider();
		
		menuTitle = new JLabel("Game");
		menuTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		menuTitle.setHorizontalAlignment(SwingConstants.CENTER);
		menuTitle.setBounds(10, 11, 109, 14);
		panel.add(menuTitle);
		
		menuIndex = new JLabel("  Heigh - Width - Mines");
		menuIndex.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuIndex.setBounds(139, 0, 145, 28);
		panel.add(menuIndex);
		
		JLabel lblNewLabel_1 = new JLabel("  10  -  10  -  10");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(139, 34, 145, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("  16  -  16  -  40");
		lblNewLabel_2.setBackground(SystemColor.scrollbar);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(139, 60, 145, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("  16  -  30  -  99");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(139, 86, 145, 14);
		panel.add(lblNewLabel_3);
	}

	public void startSlider() {
		
		slider = new JSlider();
		slider.setMinimum(rangeSize[0]);
		slider.setMaximum(rangeSize[1]);
		slider.setBounds(136, 142, 145, 26);
		slider.setValue(MineSweeper.btnSize);
		panel.add(slider);
		slider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
            	txtSize.setValue(slider.getValue());
            }
        });
		
		JLabel lblSize = new JLabel("Size");
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSize.setBounds(10, 146, 45, 14);
		panel.add(lblSize);
		
		NumberFormatter nf = new NumberFormatter();
		txtSize = new JFormattedTextField(nf);
		txtSize.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSize.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtSize.setValue(MineSweeper.btnSize);
		txtSize.setBounds(65, 143, 36, 20);
		panel.add(txtSize);
		txtSize.setColumns(10);
		txtSize.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				double value = Double.parseDouble(txtSize.getText());
				if (value > rangeSize[1]) {
					txtSize.setValue(rangeSize[1]);
				}
				else if (value < rangeSize[0]) {
					txtSize.setValue(rangeSize[0]);
				}
				slider.setValue((int)value);
			}
		});
	}
	
	public void startNumberFields() {
		NumberFormatter nf = new NumberFormatter();
		
		txtHeigh = new JFormattedTextField(nf);
		txtHeigh.setHorizontalAlignment(SwingConstants.RIGHT);
		txtHeigh.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtHeigh.setValue(20);
		txtHeigh.setBounds(126, 111, 50, 20);
		panel.add(txtHeigh);
		txtHeigh.setColumns(10);
		txtHeigh.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if (Double.parseDouble(txtHeigh.getText()) > rangeHeigh[1]) {
					txtHeigh.setValue(rangeHeigh[1]);
				}
				else if (Double.parseDouble(txtHeigh.getText()) < rangeHeigh[0]) {
					txtHeigh.setValue(rangeHeigh[0]);
				}
				btnCustom.setSelected(true);
			}
		});
		
		txtWidth = new JFormattedTextField();
		txtWidth.setHorizontalAlignment(SwingConstants.RIGHT);
		txtWidth.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtWidth.setValue(30);
		txtWidth.setBounds(177, 111, 50, 20);
		panel.add(txtWidth);
		txtWidth.setColumns(10);
		txtWidth.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if (Double.parseDouble(txtWidth.getText()) > rangeWidth[1]) {
					txtWidth.setValue(rangeWidth[1]);
				}
				else if (Double.parseDouble(txtWidth.getText()) < rangeWidth[0]) {
					txtWidth.setValue(rangeWidth[0]);
				}
				btnCustom.setSelected(true);
			}
		});
		
		txtMines = new JFormattedTextField();
		txtMines.setHorizontalAlignment(SwingConstants.RIGHT);
		txtMines.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtMines.setValue(145);
		txtMines.setColumns(10);
		txtMines.setBounds(228, 111, 50, 20);
		panel.add(txtMines);
		txtMines.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				rangeMines[1] = Integer.parseInt(txtWidth.getText())*
						Integer.parseInt(txtHeigh.getText())-1;
				if (Double.parseDouble(txtMines.getText()) > rangeMines[1]) {
					txtMines.setValue(rangeMines[1]);
				}
				else if (Double.parseDouble(txtMines.getText()) < rangeMines[0]) {
					txtMines.setValue(rangeMines[0]);
				}
				btnCustom.setSelected(true);
			}
		});
	}
	
	public void startRadioButtons(){
		btnsDificultades = new ButtonGroup();
		
		btnHard = new MyRadioButton("Hard", panel, btnsDificultades);
		btnHard.setBackground(SystemColor.scrollbar);
		btnHard.setBounds(10, 84, 109, 23);
		
		btnNormal = new MyRadioButton("Normal", panel, btnsDificultades);
		btnNormal.setBounds(10, 58, 109, 23);
		btnNormal.setSelected(true);
		
		btnEasy = new MyRadioButton("Easy", panel, btnsDificultades);
		btnEasy.setBackground(SystemColor.scrollbar);
		btnEasy.setBounds(10, 32, 109, 23);
		
		btnCustom = new MyRadioButton("Custom", panel, btnsDificultades);
		btnCustom.setBounds(10, 115, 109, 23);
	}
	
	public void startButtons() {
		buttonPane = new JPanel();
		buttonPane.setBackground(SystemColor.scrollbar);
		buttonPane.setBounds(0, 171, 284, 30);
		panel.add(buttonPane);
		buttonPane.setLayout(null);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancelar.setBackground(SystemColor.menu);
		btnCancelar.setBounds(147, 5, 129, 20);
		btnCancelar.setFocusPainted(false);
		buttonPane.add(btnCancelar);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				MineSweeper.btnReset.doClick();
			}
		});
		btnAceptar.setBackground(SystemColor.menu);
		btnAceptar.setBounds(10, 5, 127, 20);
		btnAceptar.setFocusPainted(false);
		buttonPane.add(btnAceptar);
	}
	
	public int[] getValues() {
		int[] values = new int[4];
		if (dif.equals("Easy")) {
			values[0]=10;
			values[1]=10;
			values[2]=10;
		}
		else if (dif.equals("Normal")) {
			values[0]=16;
			values[1]=16;
			values[2]=40;
		}
		else if (dif.equals("Hard")) {
			values[0]=30;
			values[1]=16;
			values[2]=99;
		}
		else if (dif.equals("Custom")){
			values[0]=Integer.parseInt(txtWidth.getText());
			values[1]=Integer.parseInt(txtHeigh.getText());
			values[2]=Integer.parseInt(txtMines.getValue().toString());
		}
		else {
			values[0]=5;
			values[1]=5;
			values[2]=5;
		}
		values[3]=Integer.parseInt(txtSize.getText());
		return values;
	}
	
	private class MyRadioButton extends JRadioButton 
    implements ItemListener {
		private static final long serialVersionUID = 1L;

		public MyRadioButton(String text, JPanel panel,ButtonGroup bg) {
			super(text);
			panel.add(this);
			bg.add(this);
			this.setFocusPainted(false);
			this.setFont(new Font("Tahoma", Font.PLAIN, 16));
			addItemListener(this);
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange()==1) {
				dif = this.getText().toString();
			}
		}
		
	}
}
