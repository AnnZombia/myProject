import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
 
public class JavaChat
{
  public static void main(String args[])
  {
    FrameWindow frame;
    frame =  new FrameWindow(
      "Java on-line chat");
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
  Button btStartServer;
  Button btStartClient;
  Button btExit;
  Button btSend;
  
  ServerThread sThread = null;
  DataOutputStream dos;
  
  boolean serverMode = true;
  
  // ============================================
  // FrameWindow
  // ============================================
  public FrameWindow(String szTitle)
  {
    super(szTitle);
    setSize(400, 300);
    
    taConsole = new TextArea(10, 30);
    tfCommand = new TextField(40);
    btStartServer = new Button("Start Server");
    btStartClient = new Button("Start Client");
    btExit = new Button("Exit");
    btSend = new Button("Send");
    
    btStartServer.addActionListener(this);
    btStartClient.addActionListener(this);
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
    c.gridheight = 3;
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
    c.gridwidth  = GridBagConstraints.REMAINDER; 
    c.gridheight = 1;
    c.ipadx = 10;
    c.gridx = GridBagConstraints.RELATIVE; 
    c.gridy = GridBagConstraints.RELATIVE; 
    
    gbl.setConstraints(btStartServer, c);
    add(btStartServer);
    
// -----------------------
// btStartClient
// -----------------------
    c.fill   = GridBagConstraints.HORIZONTAL;  
    c.gridwidth  = GridBagConstraints.REMAINDER; 
    c.gridx = 1; 
    
    gbl.setConstraints(btStartClient, c);
    add(btStartClient);
    
// -----------------------
// btExit
// -----------------------
    c.gridwidth  = GridBagConstraints.REMAINDER; 
    
    gbl.setConstraints(btExit, c);
    add(btExit);
    
// -----------------------
// tfCommand
// -----------------------
    c.gridx = GridBagConstraints.RELATIVE; 
    c.gridwidth = 1;
      
    gbl.setConstraints(tfCommand, c);
    add(tfCommand);
    
// -----------------------
// btSend
// -----------------------
    c.gridwidth  = GridBagConstraints.REMAINDER; 
    
    gbl.setConstraints(btSend, c);
    add(btSend);
    btSend.setEnabled(false);
  }

  // ============================================
  // actionPerformed
  // ============================================
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource().equals(btStartServer))
    {
      btStartClient.setEnabled(false);
      btStartServer.setEnabled(false);
      setTitle("Java on-line chat (SERVER)");
      
      serverMode = true;
      sThread = 
        new ServerThread(this, serverMode);
      sThread.start();
    }
    
    else if(e.getSource().equals(btStartClient))
    {
      btStartClient.setEnabled(false);
      btStartServer.setEnabled(false);
      setTitle("Java on-line chat (CLIENT)");
      
      serverMode = false;
      sThread = 
        new ServerThread(this, serverMode);
      sThread.start();
    }
      
    else if(e.getSource().equals(btSend))
    {
       dos = sThread.getDataOutputStream();
	 
       String szSended = tfCommand.getText(); 
       consolePrint("\n:" + szSended);
       
       try
       {
	 sendString(dos, szSended);
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
  static void sendString(DataOutputStream dos,
     String s)
    throws IOException
  {
    dos.writeChars(s + "\n");
    dos.flush();
  }
}

// ============================================
// Class ServerThread
// ============================================
class ServerThread extends Thread
{
  boolean serverMode;
  ServerSocket ss = null;
  Socket s = null;
  
  InputStream is;
  static DataInputStream dis;
  
  OutputStream os;
  DataOutputStream dos;
  
  FrameWindow frame;
  
  // ============================================
  // ServerThread
  // ============================================
  public ServerThread(FrameWindow fr,
    boolean bMode)
  {
    serverMode = bMode;
    frame = fr;
  }
  
  // ============================================
  // getDataOutputStream
  // ============================================
  public DataOutputStream getDataOutputStream()
  {
    return dos;
  }
  
  // ============================================
  // run
  // ============================================
  public void run()
  {
    if(serverMode)
    {
      frame.consolePrint(
	"Server started. Connecting...");
      
      try
      {
        ss = new ServerSocket(9998);
        s = ss.accept();  
	frame.consolePrint("\nConnected.");
        frame.btSend.setEnabled(true);
      }  
      catch(Exception ex)
      {
        System.out.println(ex.toString());
        stop(); 
      }
    }  
    
    else  
    {
      frame.consolePrint(
	"Client started. Connecting...");
    
      String szServerURL = 
        frame.tfCommand.getText();
    
      try
      {
        s = new Socket(szServerURL, 9998);
	frame.consolePrint("\nConnected.");
        frame.btSend.setEnabled(true);
      }
      catch(Exception ex)
      {
        stop(); 
      }
    }
    
    try
    {
      is = s.getInputStream();
      dis = new DataInputStream(is);
      os = s.getOutputStream();  
      dos = new DataOutputStream(os);
    
      while(true)
      {
	String szStr = recvString(dis);
        frame.consolePrint("\n--->" + szStr);
      }
    }
    catch(Exception ex)
    {
      stop(); 
    }

    try
    {
      s.close();    
      ss.close();
      dis.close();
      dos.close();
    }
    catch(Exception ex)
    {
      stop(); 
    }
  }
  
  // ============================================
  // recvString
  // ============================================
  static String recvString(InputStream is)
    throws IOException
  {
    String szBuf = "";
    char ch = dis.readChar();

    while (ch >= 0 && ch != '\n')
    {
      szBuf += ch;
      ch = dis.readChar();
    }
    return szBuf;
  }
}