package edu.sxccal.utilities;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
* Creates the log file Log.txt
* @since 2.0
*/
public class Log
{
  /**
  * Appends exception messages to Log.txt along with the date and time of log generation
  * @param e Exception object 
  * @return Exception message
  */
  public static String create_log(Exception e)
  {    
    try
    {     
      File f=new File("Logs");      
      if(!f.exists()) 
    	  f.mkdir();      
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");	   
      Date date = new Date();     
      String s=dateFormat.format(date)+"-> "+e.toString(); //Write date,time,error message to Log.txt
      PrintWriter pw=new PrintWriter((new BufferedWriter(new FileWriter("Logs/Log.txt", true)))); //append String to Log.txt
      pw.println(s);
      pw.close();
      s="Oops! Errors have been detected!\n"+e.toString()+"\nCheck: "+f.getAbsolutePath()+" for more details";
      return s;
    }
    catch(Exception exception)
    {
      System.out.println(exception.getMessage());
    }
    return "";
  } 
}
