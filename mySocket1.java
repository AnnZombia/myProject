import java.net.*;
import java.net.Socket;
import java.io.*;

public class mySocket1 {
	ServerSocket ss = null;
	Socket s = null;
	String szStr = null;
	FileInputStream File;
	OutputStream os = null;
	PrintStream ps = null;
	InputStream is = null;
	BufferedReader Buffer;
	String line = "";
			
public mySocket1 () {
		try {
			File = new FileInputStream("file.txt");
			Buffer = new BufferedReader(new InputStreamReader(File));
			ss = new ServerSocket(1107);
			s = ss.accept();
			is = s.getInputStream();
			os = s.getOutputStream(); 
			ps = new PrintStream(os); }
		catch (Exception ex) {System.out.println(ex.toString()); }
				
		while ( line != null ) {
			try {
				line = Buffer.readLine();
				sendString(os, line);
				
			} catch (IOException e) {
				e.printStackTrace(); } } 

		while(true) {
			try {
				line = recvString(is); }
			catch (Exception ex) {System.out.println(ex.toString()); }
			
		String eq="quit";
		if(line.regionMatches(0, eq, 0, 4 ))
			break; } }
				
public static void main(String[] args) {
		mySocket1 socket = new mySocket1(); }

private void sendString(OutputStream os, String s) throws IOException {
	if (s != null) {
		ps.println(s);
		os.write('\n');
		os.flush(); } }

private String recvString(InputStream is2) throws IOException {
		String szBuf = "";
		int ch = is.read();
		while (ch >= 0 && ch != '\n') {
		szBuf += (char)ch;
		ch = is.read(); }
		return szBuf; } }