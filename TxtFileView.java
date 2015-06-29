import java.net.;
import java.util.;
import java.io.;

public class TxtFileView 
{
  public static void main(String args[])
  {
    String s;
    URL u;
    InputStream is;
    
    while(true)
    {    
      System.out.println(
       nEnter URL of file ('quit' to exit));
	
      s = new String(getKbdString());
      if(s.equals(quit))
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
      
      try
      {
	is = (InputStream)u.openStream();
      }
      catch(Exception ex)
      {
        System.out.println(ex.toString());
	continue;
      }
      
      try
      {
	int ch;
	while(true)
        {
	  ch = is.read();
	  if(ch == -1)
	    break;
	    
	  System.out.print((char)ch);
	}	
        is.close();
      }
      catch(Exception ex)
      {
        System.out.println(ex.toString());
      }
    }  
  }
  
   ============================================
   getKbdString
   ============================================
  static public String getKbdString()
  {
    byte bKbd[] = new byte[256];
    int iCnt = 0;
    String szStr = ;
    
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