package edu.sxccal.qrcodescanner;

import java.io.FileOutputStream;
import java.io.PrintWriter;

import android.widget.TextView;

public class Log 
{	
	public static void create_log(Exception e, TextView tv)
	{		
		try
		 {		 
			 String log=QRCode.filePath+"/log.txt"; //create log file
			 FileOutputStream fp=new FileOutputStream(log);
			 tv.append("\n\nOops!\nErrors have been detected\nCheck: "+log);
			 PrintWriter pw=new PrintWriter(fp,true);
			 pw.write(e.toString()+"\n");
			 pw.flush();
			 pw.close();
			 fp.close();			 
		 }catch(Exception e2){}
     }      	
}