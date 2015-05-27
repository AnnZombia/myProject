import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class Painting extends JFrame {
	Panel Panel = new Panel();

	
	Painting() {
		super();
		setSize(600, 400);
		setLocation (300, 200);
		setDefaultCloseOperation (EXIT_ON_CLOSE); }
	
	public static void main(String[] args) {
		Painting MyPaint = new Painting();
		Panel Panel = new Panel();
		mousePoint mousePoint = new mousePoint();
		MyPaint.setContentPane(Panel);
		MyPaint.setVisible(true); }	} 
	

class Panel extends JPanel implements Runnable {
	mousePoint clickIn = new mousePoint(this);
	volatile boolean Flag = false;
	volatile boolean Pressed = false;
	int k=0; int flagLAST; int miniK;
	int OldX=0; int OldY=0;	int miniFlag=3;
	
	Panel() {
		super();
		Thread stream = new Thread(this);
	   	stream.start();
		setBackground(Color.RED);
		addMouseListener(clickIn); 
		addMouseMotionListener(clickIn); } 
	
	public void paintComponent(Graphics g) {
		if (Flag) {
			module();
			System.out.println("k="+k);
			System.out.println("k="+k);
			while ( OldX != clickIn.x && OldY != clickIn.y) {
					flagLAST=0;
					System.out.println("FLAG="+miniFlag);
					for (k=miniK; k > 0; k--) {
						Calculate();
						flagLAST=1;
						g.fillRect(OldX-2, OldY-2, 4, 4); 
						System.out.println("OLDX="+OldX+" OLDY="+OldY); } }
			OldX=clickIn.x;
			OldY=clickIn.y;
			g.fillRect(clickIn.x-2, clickIn.y-2, 4, 4); } }

	void Calculate() {
		if (miniFlag == 1) {
			if (OldX < clickIn.x ) {
				if (flagLAST == 0) {
					if (OldY < clickIn.y )
						OldY++; 
					if (OldY > clickIn.y )
						OldY--; }
				OldX++; }
			if (OldX > clickIn.x) {
				if (flagLAST == 0) {
					if (OldY < clickIn.y )
						OldY++; 
					if (OldY > clickIn.y )
						OldY--; }
				OldX--; } }
		
		if (miniFlag == 2) {
			if (OldY < clickIn.y ) {
				if (flagLAST == 0) {
					if (OldX < clickIn.x )
						OldX++; 
					if (OldX > clickIn.x )
						OldX--; }
				OldY++; }
			if (OldY > clickIn.y) {
				if (flagLAST == 0) {
					if (OldX < clickIn.x )
						OldX++; 
					if (OldX > clickIn.x )
						OldX--; }
				OldY--; } }
		
		if (miniFlag == 3) {
			if (OldY < clickIn.y )
				OldY++; 
			if (OldY > clickIn.y )
				OldY--;
			if (OldX < clickIn.x ) 
				OldX++; 
			if (OldX > clickIn.x )
				OldX--; }
	
		if (miniFlag == 4) {
			if (OldX < clickIn.x ) 
				OldX++; 
			if (OldX > clickIn.x )
				OldX--; } 

		if (miniFlag == 4) {
			if (OldY < clickIn.y ) 
				OldY++; 
			if (OldY > clickIn.y )
				OldY--; } }
	
	void module() {
		int Dy= OldY - clickIn.y;
		int Dx= OldX - clickIn.x;
		if (Dx < 0)
			Dx=-Dx;
		if (Dy < 0)
			Dy=-Dy;
		if (Dy != 0 && Dx != 0) {
			if (Dx/Dy != 0) {
					k=Dx/Dy;
					miniFlag=1; }
			else 	{k=Dy/Dx; 
					miniFlag=2; } }	
		if (Dy == Dx && Dy !=0) {
			miniFlag=3; 
			k=Dy; }
		if (Dy == 0) {
			k = Dx;
			miniFlag=4; }
		if (Dx == 0) {
			k = Dy;
			miniFlag=5; }
		miniK=k;}
	
	@Override
	public void run() {	} }

class mousePoint implements MouseListener, Runnable, MouseMotionListener {
	Panel Screen;
	int x=0; int y=0;
	
	mousePoint(Panel copy) {
		this.Screen=copy;	}
	
	mousePoint() {
	Thread stream = new Thread(this);
   	stream.start(); }
	
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
	public void run() {	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {		
		if (Screen.Pressed)
		{clickedPoint(arg0.getX(),arg0.getY());
		Screen.repaint();}	}

	@Override
	public void mouseMoved(MouseEvent arg0) { } }
	