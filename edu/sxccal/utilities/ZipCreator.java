package edu.sxccal.utilities;

import java.io.*;
import java.util.zip.*;

//User library to create a zip file	 
public class ZipCreator
{   
  public static void create_zip(String zipFile,String[] infiles) 
  {    	         
    try 
    {      
      byte[] buffer = new byte[4096];	 
      FileOutputStream fos = new FileOutputStream(zipFile);	 
      ZipOutputStream zos = new ZipOutputStream(fos);	             
      for (int i=0;i<infiles.length;i++)
      {	                 
	File srcFile = new File(infiles[i]);	 
        FileInputStream fis = new FileInputStream(srcFile);                
        zos.putNextEntry(new ZipEntry(srcFile.getName()));	                 
        int length;	 
	while ((length = fis.read(buffer)) > 0)	           
	  zos.write(buffer, 0, length);
	zos.closeEntry();              
	fis.close();	                 
      }           
      zos.close();	             
    }
    catch (IOException e) 
    {
      System.out.println("Error creating zip file: " + e.toString());
    }
  } 
}
