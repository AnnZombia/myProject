import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Painting extends JFrame {
	Panel Panel = new Panel();

	
	Painting() {
		super();
		setBackground(Color.PINK);
		setSize(700, 500);
		setLocation (220, 180);
		setResizable(false);
		setDefaultCloseOperation (EXIT_ON_CLOSE); }
	
	public static void main(String[] args) {
		Painting MyPaint = new Painting();
		Panel Panel = new Panel();
		MyPaint.setContentPane(Panel);
		MyPaint.setVisible(true); }	} 

class Panel extends JPanel {
	JButton Button=new JButton();
	ButtonAction ButAction=new ButtonAction(this);
	mousePoint clickIn = new mousePoint(this);
	volatile boolean Flag = false;
	volatile boolean Pressed = false;
	int OldX; int OldY;
	
	Panel() {
		super();
		Button.setText("Œ◊»—“»“‹");
		setLayout(new BorderLayout());
		addMouseListener(clickIn); 
		Button.addActionListener(ButAction);
		addMouseMotionListener(clickIn);
		add("North", Button); } 
	
	public void clear() {
		Graphics g=this.getGraphics();
		clearING(g); }
	
	public void clearING(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.PINK); 
		g2.fillRect(0, 0, 700, 500); }
	
	public void paintComponent(Graphics g) {
			if (Flag) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(4.0f)); 
				g2.drawLine(OldX, OldY, clickIn.x, clickIn.y);
				OldX=clickIn.x;
				OldY=clickIn.y;
				g2.fillOval(clickIn.x-2, clickIn.y-2, 4, 4); } } }

	class mousePoint implements MouseListener, MouseMotionListener {
		Panel Screen;
		int x=0; int y=0;
	
		mousePoint(Panel copy) {
			this.Screen=copy;	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		clickedPoint(arg0.getX(),arg0.getY()); 
		Screen.Flag = true;
		Screen.repaint();
		Screen.Flag = false; }

	@Override
	public void mouseEntered(MouseEvent arg0) {	}

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) {
		clickedPoint(arg0.getX(),arg0.getY());
		Screen.OldX=x;
		Screen.OldY=y;
		Screen.Flag = true;
		Screen.Pressed = true;
		Screen.repaint(); }

	@Override
	public void mouseReleased(MouseEvent arg0) {
		clickedPoint(arg0.getX(),arg0.getY());
		Screen.Flag = false;
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
	Panel Main;
	
	ButtonAction(Panel panel) {
		Main=panel;	}
	
	ButtonAction() {
		super();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Main.clear();
		
	} }

	