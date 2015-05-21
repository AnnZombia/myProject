import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.Runnable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class postCARD extends JFrame {
	
	postCARD () {
		super ();
		setSize (500, 455);
		setLocationRelativeTo (null);
		setLayout (null);
		setDefaultCloseOperation (EXIT_ON_CLOSE);
		setResizable (false); }
	
	public static void main(String[] args) { 
		postCARD postCARD = new postCARD ();
		Panel myPanel = new Panel();
		Label1 myLabel1 = new Label1();
		Push myPush = new Push();
		Label2 myLabel2 = new Label2();
		postCARD.setContentPane(myPanel);
		postCARD.add(myLabel1);
		postCARD.add(myLabel2);
		postCARD.add(myPush);
		postCARD.setVisible(true); } } 

class Panel extends JPanel implements Runnable {
	MediaTracker track = new MediaTracker(this);
	Image[] image = new Image[8];
	int i;
		
	Panel() {
		super();
		Thread stream = new Thread(this);
	   	stream.start();
		setLayout(null);}
		
	public void run() {
		for ( i = 0; i < image.length; i++ )
			{image[i] = getToolkit().getImage(ResourceLoader.getImage("res/"+i+".png")); 
			track.addImage(image[i], 0); }
		i = 0;
		try {
			track.waitForAll();}
		catch(InterruptedException e) {}
		go(); }

	protected void go() {
			while(true) {
				i = 0;
				while( i < image.length ) {
				repaint();
				try {
					Thread.sleep(350);
					i++; }
				catch (InterruptedException e) {} } } }
			
	protected void paintComponent(Graphics g) {
		g.drawImage(image[i], 0, 0, this); } }


class Label1 extends JLabel implements Runnable  {
	ImageIcon ico = new ImageIcon(ResourceLoader.getImage("res/button80.png"));
	action Action = new action();

	Label1() {
		super();
		Thread stream = new Thread(this);
	   	stream.start();
		setVisible(false);
		setLocation(220, 320);
		setSize(80, 80);
		setIcon(ico);
		addMouseListener(Action); }
		
	public void run() {
		waitMe();
		while(true) {
			waitMe();
			setVisible(false);
			waitMe();
			setVisible(true);} }
	
	protected void startThread() {
		Thread stream = new Thread(this);
	   	stream.start(); }
	
	protected void waitMe() {
		try {
			Thread.sleep(1000);}
		catch (InterruptedException e) {} } }

class Label2 extends JLabel implements Runnable  {
	ImageIcon ico = new ImageIcon(ResourceLoader.getImage("res/button60.png"));
	action Action= new action();
	
	Label2() {
		super();
		Thread stream = new Thread(this);
	   	stream.start();
		setVisible(false);
		setLocation(230, 330);
		setSize(60, 60);
		setIcon(ico);
		addMouseListener(Action);}
		
	public void run() {
		waitBeforeStart();
		setVisible(true); }
	
	protected void startThread() {
		Thread stream3 = new Thread(this);
	   	stream3.start(); }
	
	protected void waitBeforeStart() {
		try {
			Thread.sleep(2000);}
		catch (InterruptedException e) {} } }

class Push extends JLabel implements Runnable  {
	ImageIcon ico = new ImageIcon(ResourceLoader.getImage("res/push.png"));

	Push() {
		super();
		Thread stream = new Thread(this);
	   	stream.start();
		setVisible(false);
		setLocation(31, 286);
		setSize(169, 122);
		setIcon(ico); }
	
	public void run() {
		waitBeforeStart();
		setVisible(true); }
	
	protected void startThread() {
		Thread stream3 = new Thread(this);
	   	stream3.start(); }
	
	protected void waitBeforeStart() {
		try {
			Thread.sleep(3500);}
		catch (InterruptedException e) {} } }

class action implements MouseListener {
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { } 	

	public void mousePressed(MouseEvent e) {
		String s = "http://yandex.ru/";
		URL url=null;
		try {
			url = new URL(s); }
		catch (MalformedURLException e1) {
			e1.printStackTrace(); }
		Object eventSource = e.getSource();
		
		if (eventSource instanceof Label1 | eventSource instanceof Label2) {
			openWebpage(url); } }

	protected void openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
			} catch (Exception e) {
				e.printStackTrace(); } } }
 
	protected void openWebpage(URL url) {
		try {
			openWebpage(url.toURI()); }
		catch (URISyntaxException e) {
			e.printStackTrace(); } } } 

class ResourceLoader {
	static ResourceLoader rl = new ResourceLoader();
	ResourceLoader() {}
	public static URL getImage(String fileName) {
		return (rl.getClass().getResource(fileName)); } }