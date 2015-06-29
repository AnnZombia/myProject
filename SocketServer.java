import java.net.*;
import java.util.*;
import java.io.*;

public class SocketServer
{
  public static void main(String args[])
  {
    System.out.println(
      "* Socket Server *");
      
    ServerSocket ss = null;
    try
    {
      ss = new ServerSocket(9999);
    }  
    catch(Exception ex)
    {
      System.out.println(ex.toString());
      System.exit(0);
    }  
     
    int nPort = ss.getLocalPort();
    
    System.out.println(
      "Local Port: " + nPort);
      
    ServerThread sThread = null;
    
    sThread = new ServerThread(ss);
    sThread.start();
    
    System.out.println(
      "Waiting connection...");
      
    try
    {
      sThread.join();
    }  
    catch(InterruptedException ex)
    {
      System.out.println(ex.toString());
    }  
      
    try
    {
      ss.close();
    }  
    catch(Exception ex)
    {
      System.out.println(ex.toString());
    }  
    
    System.exit(0);
  }  
}

// ============================================
// Class ServerThread
// ============================================
class ServerThread extends Thread
{
  ServerSocket ss = null;
  Socket s = null;
  
  // ============================================
  // ServerThread
  // ============================================
  public ServerThread(ServerSocket sSocket)
  {
    ss = sSocket;
  }
  
  // ============================================
  // run
  // ============================================
  public void run()
  {
    InputStream is;
    OutputStream os;
    
    try
    {
      s = ss.accept();  
    }
    catch(Exception ex)
    {
      stop(); 
    }
    
    System.out.println("Connected.");
    
    try
    {
      is = s.getInputStream();
      os = s.getOutputStream();  
      
      while(true)
      {
	String szStr = recvString(is);
	sendString(os, "* " + szStr + " *");
	os.flush();
	
	System.out.println(szStr);
	
	if(szStr.equals("quit"))
	  break;    
      }
      
      is.close();
      os.close();
    }
    catch(Exception ex)
    {
      stop(); 
    }

    try
    {
      s.close();    
      ss.close();
    }
    catch(Exception ex)
    {
      stop(); 
    }
  }
  
  // ============================================
  // sendString
  // ============================================
  static void sendString(OutputStream os,
    String s)
    throws IOException
  {
    for(int i = 0; i < s.length(); i++)
    {
      os.write((byte)s.charAt(i));
    }
    os.write('\n');
    os.flush();
  }

  // ============================================
  // recvString
  // ============================================
  static String recvString(InputStream is)
    throws IOException
  {
    String szBuf = "";
    int ch = is.read();

    while (ch >= 0 && ch != '\n')
    {
      szBuf += (char)ch;
      ch = is.read();
    }
    return szBuf;
  }
}