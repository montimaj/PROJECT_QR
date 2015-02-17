package edu.sxccal.qrcodescanner;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip
{    
    private static final int BUFFER_SIZE = 4096;      
    public static String[] unzip(String zipFilePath, String destDirectory) 
    {    	
    	String f1=destDirectory + "/suepk";
    	String f2=destDirectory + "/sig";
    	String f3="";
    	try
        {	    	
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
	        
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
    	String files[]={f1,f2,f3};    	
        return files;
    }    
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException
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