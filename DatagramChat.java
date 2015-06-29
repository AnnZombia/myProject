import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
 
public class DatagramChat
{
  public static void main(String args[])
  {
    FrameWindow frame;
    frame =  new FrameWindow(
      "Datagram chat");
    frame.setVisible(true);
  }
}

// =======================================
// Class FrameWindow
// =======================================
class FrameWindow extends Frame
  implements ActionListener, WindowListener
{
  TextArea taConsole;
  TextField tfCommand;
  Button btExit;
  Button btSend;
  
  ServerThread sThread = null;
  static DatagramSocket s;
  static DatagramPacket dp;
  
  // ============================================
  // FrameWindow
  // ============================================
  public FrameWindow(String szTitle)
  {
    super(szTitle);
    setSize(400, 300);
    
    taConsole = new TextArea(10, 30);
    tfCommand = new TextField(40);
    btExit = new Button("Exit");
    btSend = new Button("Send");
    
    btExit.addActionListener(this);
    btSend.addActionListener(this);
    this.addWindowListener(this);
    
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints c = 
      new GridBagConstraints();
    
    setLayout(gbl);
    
// -----------------------
// taConsole
// -----------------------
    c.anchor = GridBagConstraints.NORTHWEST; 
    c.fill   = GridBagConstraints.BOTH;  
    c.gridheight = 1;
    c.gridwidth  = 1;
    c.gridx = GridBagConstraints.RELATIVE; 
    c.gridy = GridBagConstraints.RELATIVE; 
    c.insets = new Insets(10, 10, 10, 10);
    
    c.weightx = 1.0;
    c.weighty = 1.0;
    gbl.setConstraints(taConsole, c);
    add(taConsole);
    
// -----------------------
// btStartServer
// -----------------------
    c.weightx = 0;
    c.weighty = 0;
    c.fill   = GridBagConstraints.NONE;  
    c.gridwidth = GridBagConstraints.REMAINDER; 
    c.gridheight = 1;
    c.ipadx = 10;
    c.gridx = GridBagConstraints.RELATIVE; 
    c.gridy = GridBagConstraints.RELATIVE; 
    
    gbl.setConstraints(btExit, c);
    add(btExit);
    
// -----------------------
// tfCommand
// -----------------------
    c.fill  = GridBagConstraints.HORIZONTAL;  
    c.gridx = GridBagConstraints.RELATIVE; 
    c.gridwidth = 1;
      
    gbl.setConstraints(tfCommand, c);
    add(tfCommand);
    
// -----------------------
// btSend
// -----------------------
    c.fill   = GridBagConstraints.NONE;  
    c.gridwidth = GridBagConstraints.REMAINDER; 
    
    gbl.setConstraints(btSend, c);
    add(btSend);
    btSend.setEnabled(false);
    
    sThread = new ServerThread(this);
    sThread.start();
    
    try
    {
      s = new DatagramSocket();
    }  
    catch(Exception ex)
    {
      System.out.println(ex.toString());
    }
  }

  // ============================================
  // actionPerformed
  // ============================================
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource().equals(btSend))
    {
       String szSended = tfCommand.getText(); 
       consolePrint("\n:" + szSended);
       
       try
       {
	 sendString(szSended);
       }  
       catch(Exception ex)
       {
	 System.out.println(ex.toString());
       }
    }
      
    else if(e.getSource().equals(btExit))
    {
      setVisible(false);
      System.exit(0);
    }
  }

  // ============================================
  // windowClosing
  // ============================================
  public void windowClosing(WindowEvent e)
  {
    setVisible(false);
    System.exit(0);
  }
  
  public void windowOpened(WindowEvent e) {}
  public void windowClosed(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowActivated(WindowEvent e) {}
  public void windowDeactivated(WindowEvent e) {}
  
  // ============================================
  // consolePrint
  // ============================================
  void consolePrint(String s)
  {
    taConsole.append(s);
  }
  
  // ============================================
  // sendString
  // ============================================
  static void sendString(String str)
    throws IOException
  {
    byte[] buf = str.getBytes();
    
    dp = new DatagramPacket(buf, buf.length,
      InetAddress.getByName("255.255.255.255"),
      9997);
            
    s.send(dp);  
  }
}

// ============================================
// Class ServerThread
// ============================================
class ServerThread extends Thread
{
  FrameWindow frame;
  DatagramSocket s;
  
  // ============================================
  // ServerThread
  // ============================================
  public ServerThread(FrameWindow fr)
  {
    frame = fr;
  }
  
  // ============================================
  // run
  // ============================================
  public void run()
  {
    try
    {
      frame.btSend.setEnabled(true);
      s = new DatagramSocket(9997);
      
      frame.consolePrint(
	"!Server socket opend\n");
      while(true)
      {
	String szStr = recvString(s);
        frame.consolePrint("\n--->" + szStr);
      }
    }
    catch(SocketException se)
    {
      frame.consolePrint(
	"!Server socket could not be opened\n");
      stop(); 
      s.close();
    }
    catch(Exception ex)
    {
      stop(); 
      s.close();
    }
  }
  
  // ============================================
  // recvString
  // ============================================
  static String recvString(DatagramSocket s)
    throws IOException
  {
    DatagramPacket dp;
    byte buf[] = new byte[512];
    
    dp = new DatagramPacket(buf, 512);
    s.receive(dp);
    
    String szBuf = new String(buf);
    return szBuf;
  }
}