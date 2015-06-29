import java.net.*;
import java.util.*;
import java.io.*;

public class SocketClient
{
  public static void main(String args[])
  {
    System.out.println(
      "* Socket Client *");
      
    Socket s = null;
    
    try
    {
      s = new Socket("localhost", 9999);
    }  
    catch(Exception ex)
    {
      System.out.println(ex.toString());
      System.exit(0);
    }  
     
    int nPort = s.getLocalPort();
    System.out.println("Local Port: " + nPort);
      
    InputStream is;
    OutputStream os;
    
    try
    {
      is = s.getInputStream();
      os = s.getOutputStream();
    
      String szStr;
      while(true)
      {
        szStr = getKbdString();
	
        sendString(os, szStr);
	os.flush();
	
	if(szStr.equals("quit"))
	  break;    
	  
	szStr = recvString(is);
	
	System.out.println(szStr);
      }
      
      is.close();
      os.close();
      s.close();
    }  
    catch(Exception ex)
    {
      System.out.println(ex.toString());
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
	  
  // ============================================
  // getKbdString
  // ============================================
  static public String getKbdString()
  {
    byte bKbd[] = new byte[256];
    int iCnt = 0;
    String szStr = "";
    
    try
    {
      iCnt = System.in.read(bKbd);
    }
    catch(Exception ex)
    {
      System.out.println(ex.toString()); 
    }
      
    szStr = new String(bKbd, 0, iCnt);
    szStr = szStr.trim();
    return szStr;
  }
}