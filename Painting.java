import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Painting extends JFrame {
	int width = 700; int height = 500; 
	Color COLOR=new Color(221,125,75);
	MainPanel MainPan = new MainPanel(width, height, COLOR);
	Button Button=new Button();
	ButtonAction ButAction=new ButtonAction(MainPan);

	Painting() {
		super();
		setTitle("Paint");
		setLayout(null);
		getContentPane().setBackground(COLOR);
		setSize(width, height);
		setLocation (220, 180);
		setResizable(true);
		setDefaultCloseOperation (EXIT_ON_CLOSE);
		Button.addActionListener(ButAction);
		add(Button);
		add(MainPan);
		setVisible(true); }
	
	public static void main(String[] args) {
		Painting MyPaint = new Painting(); }	}



class MainPanel extends JPanel {
	mousePoint clickIn = new mousePoint(this);
	boolean Pressed = false;
	int OldX=0; int OldY=0;
	boolean Flag = false;
	int width; int height; Color COLOR;
	
	MainPanel(int w, int h, Color COL) {
		super();
		width = w-80;
		height = h;
		COLOR = COL;
		setSize(w, h);
		setLayout(null);
		setLocation (80, 0);
 		addMouseListener(clickIn); 
		addMouseMotionListener(clickIn); }
	
	public void paintComponent(Graphics g) {

			Graphics2D g2 = (Graphics2D) g;
			
		if (Pressed) {
			g2.setStroke(new BasicStroke(4.0f)); 
			g2.drawLine(OldX, OldY, clickIn.x, clickIn.y);
			OldX=clickIn.x;
			OldY=clickIn.y;
			g2.fillOval(clickIn.x-2, clickIn.y-2, 4, 4); }
		if (Flag) {
			g2.setColor(COLOR);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			Flag=false; } } }

class mousePoint implements MouseListener, MouseMotionListener {
	MainPanel Screen;
		int x=0; int y=0;
	
		mousePoint(MainPanel copy) {
			Screen=copy;	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) { }

	@Override
	public void mouseEntered(MouseEvent arg0) {	}

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) {
		clickedPoint(arg0.getX(),arg0.getY());
		Screen.OldX=x;
		Screen.OldY=y;
		Screen.Pressed = true;
		Screen.repaint(); }

	@Override
	public void mouseReleased(MouseEvent arg0) {
		clickedPoint(arg0.getX(),arg0.getY());
		Screen.Pressed = false;
		Screen.repaint(); } 
	
	void clickedPoint (int x, int y) {
		this.x=x;
		this.y=y; }

	@Override
	public void mouseDragged(MouseEvent arg0) {		
		if (Screen.Pressed)
		{clickedPoint(arg0.getX(),arg0.getY());
		Screen.repaint();}	}

	@Override
	public void mouseMoved(MouseEvent arg0) { } }
	
class ButtonAction implements ActionListener {
	MainPanel Panelext;
	
	ButtonAction(MainPanel Panelext) {
		this.Panelext=Panelext;	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Panelext.Flag = true;
		Panelext.repaint();
		
	} }

class Button extends JButton {
	
	Button() {
	setLocation(0, 0);
	setSize(80,20);
	setText("CLEAN"); }

}