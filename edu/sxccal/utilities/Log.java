package edu.sxccal.utilities;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

//Creates the log file Log.txt
public class Log
{
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

/*		***		LIBRARY OVERVIEW	***	                              		   */

/*PrintWriter: Prints formatted representations of objects to a text-output stream
  Class Details: http://docs.oracle.com/javase/7/docs/api/java/io/PrintWriter.html
  
  BufferedWriter: Writes text to a character-output stream, buffering characters so as to provide
  for the efficient writing of single characters, arrays, and strings. 
  Class Details: https://docs.oracle.com/javase/7/docs/api/java/io/BufferedWriter.html
  
  FileWriter: Convenience class for writing character files and is meant for writing streams of characters.
  Class Details: http://docs.oracle.com/javase/7/docs/api/java/io/FileWriter.html
  
  Date: The class Date represents a specific instant in time, with millisecond precision. 
  Class Details: http://docs.oracle.com/javase/7/docs/api/java/util/Date.html 
*/