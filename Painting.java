import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class Painting extends JFrame {
	mousePoint click = new mousePoint(this);
	Panel Panel = new Panel();

	
	Painting() {
		super();
		setSize(300, 200);
		setLocation (300, 200);
		setContentPane(Panel);
		setVisible(true); }
	
	public static void main(String[] args) {
		Painting MyPaint = new Painting(); }
	
	} 
	

class Panel extends JPanel {
	mousePoint clickIn = new mousePoint(this);
	boolean Flag;
	
	Panel() {
		super();
		setBackground(Color.RED);
		addMouseListener(clickIn);  } 
	
	public void paintComponent(Graphics g) {
		if (Flag) 
			{g.fillRect(clickIn.clickPoint.x, clickIn.clickPoint.y, 30, 30);
			System.out.println("4tooo");
			Flag=false;
			System.out.println("4tooo");}
	} 
	
}


class mousePoint implements MouseListener {
	clickedPoint clickPoint;
	Painting copy;
	Panel copy1;
	
	mousePoint(Panel copy) {
		this.copy1=copy;	}
	
	mousePoint(Painting copy) {
		this.copy=copy;	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		clickPoint=new clickedPoint(arg0.getX(),arg0.getY()); 
		copy1.Flag = true; }

	@Override
	public void mouseEntered(MouseEvent arg0) {	}

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) {
		clickPoint=new clickedPoint(arg0.getX(),arg0.getY());
		copy1.Flag = true; }

	@Override
	public void mouseReleased(MouseEvent arg0) { } }

class clickedPoint {
		int x; int y;
		
	clickedPoint (int e, int r) {
		x=e; y=r; } }
	