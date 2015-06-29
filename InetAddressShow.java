import java.net.*;
import java.util.*;

public class InetAddresShow 
{
  public static void main(String args[])
  {
    String s;
    InetAddress iaLocalAddress = null;
    InetAddress iaRemoteAddress = null;
    InetAddress[] iaRemoteAll;

    try
    {
      iaLocalAddress = 
        InetAddress.getLocalHost();
    }
    catch(UnknownHostException ex)
    {
      System.out.println(ex.toString()); 
    }
    	
    System.out.println(
      "------------------------");
    System.out.println("Local host address");
    System.out.println(
      "------------------------");
    
    printInetAddresssInfo(iaLocalAddress);  
	    
    while(true)
    {    
      System.out.println(
	"Enter address ('quit' to exit): ");
	
      s = new String(getKbdString());
      if(s.equals("quit"))
	break;
      
      try
      {
        iaRemoteAddress = 
	  InetAddress.getByName(s);
	  
        iaRemoteAll = 
          InetAddress.getAllByName(s);
      }
      catch(UnknownHostException ex)
      {
        System.out.println(ex.toString());
	continue;
      }
      
      System.out.println(
        "------------------------");
      System.out.println("Remote host address");
      System.out.println(
        "------------------------");
      
      printInetAddresssInfo(iaRemoteAddress);
      
      System.out.println(
        "------------------------");
      System.out.println(
        "All remote host addresses");
      System.out.println(
        "------------------------");
      
      for(int i = 0;i < iaRemoteAll.length; i++)
      {
        printInetAddresssInfo(iaRemoteAll[i]);
      }
    }  
  }
  
  // ============================================
  // printInetAddresssInfo
  // ============================================
  static void 
  printInetAddresssInfo(InetAddress ia)
  {
    System.out.println("Address: " + ia);
      
    System.out.println("Host name: " + 
      ia.getHostName()); 
     
    byte[] ip = new byte[4];
    
    ip = ia.getAddress();
    
    System.out.println("IP Address: " + 
      (0xff & (int)ip[0]) + "." + 
      (0xff & (int)ip[1]) + "." +
      (0xff & (int)ip[2]) + "." + 
      (0xff & (int)ip[3])); 
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