import java.net.*;
import java.util.*;

public class URLShow 
{
  public static void main(String args[])
  {
    String s;
    URL u;
	    
    while(true)
    {    
      System.out.println(
	"Enter URL-address ('quit' to exit): ");
	
      s = new String(getKbdString());
      if(s.equals("quit"))
	break;
      
      try
      {
	u = new URL(s);
      }
      catch(MalformedURLException ex)
      {
        System.out.println(ex.toString());
	continue;
      }
      
      printURLAddresssInfo(u);
    }  
  }
  
  // ============================================
  // printURLAddresssInfo
  // ============================================
  static void printURLAddresssInfo(URL u)
  {
    System.out.println("URL Address: " + u);
    System.out.println("Host: " + u.getHost());
    System.out.println("Port: " + u.getPort());
    System.out.println(
      "Protocol: " + u.getProtocol());
    System.out.println(
      "Filename: " + u.getFile());
    System.out.println(
      "Reference: " + u.getRef());
    System.out.println(
      "External Form: " + u.toExternalForm());
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