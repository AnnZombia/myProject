import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class GetFile
{
  public static void main(String args[])
  {
    FrameWindow frame;
    frame =  new FrameWindow(
      "Get File from Web Site");
    frame.setVisible(true);
  }
}

// =======================================
// Class FrameWindow
// =======================================
class FrameWindow extends Frame
  implements ActionListener, WindowListener
{
  TextArea ta;
  MenuBar mb;
  Menu mFile;
  MenuItem miOpen;
  MenuItem miSave;
  MenuItem miExit;
  FileDialog fdlg;
  
  String szNewURL = null;
  
  // ============================================
  // FrameWindow
  // ============================================
  public FrameWindow(String szTitle)
  {
    super(szTitle);
    setSize(400, 300);
    
    mb = new MenuBar();
    mFile = new Menu("File");
    
    miOpen = new MenuItem("Open URL...");
    mFile.add(miOpen);
    
    miSave = new MenuItem("Save As File...");
    mFile.add(miSave);
    
    mFile.add("-");
    
    miExit = new MenuItem("Exit");
    mFile.add(miExit);
    
    mb.add(mFile);
    
    miOpen.addActionListener(this);
    miSave.addActionListener(this);
    miExit.addActionListener(this);
    
    setMenuBar(mb);
    
    this.addWindowListener(this);
    ta = new TextArea(10, 30);

    setLayout(new BorderLayout());
    add("Center", ta);
  }

  // ============================================
  // actionPerformed
  // ============================================
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource().equals(miOpen))
    {
      GetURLDialog nUrl = 
	new GetURLDialog("Enter new URL", this);
	
      nUrl.show();
      szNewURL = nUrl.getURL();
      
      if(szNewURL != null)
      {
	getUrlFile(szNewURL);
      }
    }
    
    else if(e.getSource().equals(miSave))
    {
      if(szNewURL != null)
      {
        saveUrlFile(szNewURL);
      }	
    }
      
    else if(e.getSource().equals(miExit))
    {
      setVisible(false);
      System.exit(0);
    }
  }

  // ============================================
  // getUrlFile
  // ============================================
  void getUrlFile(String szUrl)
  {
    String str;
    byte buf[] = new byte[100];
    URL u;
    
    try
    {
      u = new URL(szUrl);
      InputStream is = u.openStream();
      
      while(true)
      {
        int nReaded = is.read(buf);
	
        if(nReaded == -1)
          break;
	  
        str = new String(buf);
        ta.append(str.substring(0, nReaded));
      }
    }
    catch(Exception ex)
    {
      System.out.println(ex.toString());
    }
  }  
    
  // ============================================
  // saveUrlFile
  // ============================================
  void saveUrlFile(String szUrl)
  {
    String str;
    byte buf[] = new byte[100];
    URL u;
    
    try
    {
      u = new URL(szUrl);
      InputStream is = u.openStream();
      
      fdlg = new FileDialog(
        this, "Save file as...", 
	FileDialog.SAVE);
      fdlg.show();
      
      if(fdlg.getDirectory() == null &&
	 fdlg.getFile() == null)
	 return;

      FileOutputStream fos = 
        new FileOutputStream(
	fdlg.getDirectory() + fdlg.getFile());
      
      ta.append("\nSave as: " + 
        fdlg.getDirectory() +
        fdlg.getFile() + "\n");
      
      while(true)
      {
        int nReaded = is.read(buf);
	
        if(nReaded == -1)
          break;
	fos.write(buf, 0, nReaded);  
      }
      is.close();
      fos.close();
    }
    catch(Exception ex)
    {
      System.out.println(ex.toString());
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
}

// ============================================
// Class GetURLDialog
// ============================================
class GetURLDialog extends Dialog
  implements ActionListener, WindowListener
{
  TextField tfURL;
  Label lbName;
  Button bOK;
  Button bCancel;
  
  String szNewURL = null;
  
  // ============================================
  // GetURLDialog
  // ============================================
  public GetURLDialog(
    String szTitle, Frame parent)
  {
    super(parent, szTitle, true);
    setSize(400, 300);
    this.addWindowListener(this);
    
    tfURL = new TextField(20);
    tfURL.setText("http://");
    tfURL.selectAll();
    lbName = new Label("Enter URL:");
    bOK = new Button("OK");
    bCancel = new Button("Cancel");
    
    bOK.addActionListener(this);
    bCancel.addActionListener(this);

    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints c = 
      new GridBagConstraints();
    
    setLayout(gbl);
    
// -----------------------
// URL
// -----------------------

    c.anchor = GridBagConstraints.NORTHWEST; 
    c.fill   = GridBagConstraints.NONE;  
    c.gridheight = 1;
    c.gridwidth  = GridBagConstraints.REMAINDER; 
    c.gridx = GridBagConstraints.RELATIVE; 
    c.gridy = GridBagConstraints.RELATIVE; 
    c.insets = new Insets(10, 10, 10, 10);
    
    gbl.setConstraints(lbName, c);
    add(lbName);
    
    c.fill = GridBagConstraints.HORIZONTAL;  
    c.gridwidth  = GridBagConstraints.REMAINDER; 
    c.weighty = 1.0;
    
    gbl.setConstraints(tfURL, c);
    add(tfURL);
    
// -----------------------
// OK
// -----------------------

    c.weighty = 0;
    c.fill   = GridBagConstraints.NONE;  
    c.gridwidth  = 1;
    c.ipadx = 32;
    
    gbl.setConstraints(bOK, c);
    add(bOK);
    
// -----------------------
// Cancel
// -----------------------

    c.gridwidth  = GridBagConstraints.REMAINDER; 
    c.ipadx = 10;
    c.weightx = 1.0;
    
    gbl.setConstraints(bCancel, c);
    add(bCancel);
  }  

  // ============================================
  // actionPerformed
  // ============================================
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource().equals(bOK))
    {
      String szURL = tfURL.getText();
      
      if(!szURL.equals(""))
        szNewURL = szURL;
      else	
        szNewURL = null;
 
      setVisible(false);
    }
    
    else if(e.getSource().equals(bCancel))
    {
      szNewURL = null;
      setVisible(false);
    }
  }
  
  // ============================================
  // getURL
  // ============================================
  public String getURL()
  {
    return szNewURL;
  }
  
  // ============================================
  // windowClosing
  // ============================================
  public void windowClosing(WindowEvent e)
  {
    setVisible(false);
  }
  
  public void windowOpened(WindowEvent e) {}
  public void windowClosed(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowActivated(WindowEvent e) {}
  public void windowDeactivated(WindowEvent e) {}
}