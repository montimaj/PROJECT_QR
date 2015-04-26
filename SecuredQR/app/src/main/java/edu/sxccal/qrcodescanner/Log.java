package edu.sxccal.qrcodescanner;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Log 
{	
	public static void create_log(Exception e, Context context)
	{		
		try
		 {		 
			 String log=QRCode.filePath+"/log.txt"; //create log file
			 String s="Oops!\nErrors have been detected\nCheck: "+log;			 
			 Toast toast = Toast.makeText(context,s,Toast.LENGTH_SHORT);
			 toast.setGravity(Gravity.CENTER,0,0);
			 toast.show();		 
			 PrintWriter pw=new PrintWriter((new BufferedWriter(new FileWriter(log, true))));
			 pw.println("-> "+e.toString());			
			 pw.close();			 			 
		 }catch(Exception e2){}
     }
}

/*				***		LIBRARY OVERVIEW	***	    */

/*PrintWriter: Prints formatted representations of objects to a text-output stream
  Class Details: http://docs.oracle.com/javase/7/docs/api/java/io/PrintWriter.html
*/
