package edu.sxccal.qrcodescanner;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

//Module to extract zip file
public class Unzip
{    
    private static final int BUFFER_SIZE = 4096;      
    public static String[] unzip(String zipFilePath, String destDirectory) throws Exception
    {    	
    	String f1=destDirectory + "/suepk";
    	String f2=destDirectory + "/sig";
    	String f3="";
    	ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
	    ZipEntry entry = zipIn.getNextEntry();    	        
	    while (entry != null) 
	    {
	    	String ent=entry.getName(),filePath = destDirectory + "/" + ent;
	        boolean flag=true;
	        if(ent.equals("suepk") || ent.equals("sig"))
	        	flag=false;
	        if (!entry.isDirectory()) 
	        {                
	        	if(flag)	             
	            f3=filePath;	               
	            extractFile(zipIn, filePath);
	        } 
	        else 
	        {                
	        	File dir = new File(filePath);
	            dir.mkdir();
	        }
	        zipIn.closeEntry();
	        entry = zipIn.getNextEntry();
	    }
	    zipIn.close();                
    	String files[]={f1,f2,f3};    	
        return files;
    }    
    private static void extractFile(ZipInputStream zipIn, String filePath) throws Exception
    {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1)
        {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }    
}
/*								***		LIBRARY OVERVIEW	***	                              						*/

/*java.util.zip: Provides classes for reading and writing the standard ZIP and GZIP file formats.
  Class Details: http://docs.oracle.com/javase/7/docs/api/java/util/zip/package-summary.html

  BufferedOutputStream: Setting up such an output stream, an application can write bytes 
  to the underlying output stream without necessarily causing a call to the underlying system for each byte written.
  Class Details: https://docs.oracle.com/javase/7/docs/api/java/io/BufferedOutputStream.html
*/