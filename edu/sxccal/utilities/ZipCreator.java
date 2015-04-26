package edu.sxccal.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;

//User library to create a zip file	
//throws java.lang.Exception if an exception occurs
public class ZipCreator
{   
  public static void create_zip(String zipFile,String[] infiles) throws IOException
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
}

/*		***		LIBRARY OVERVIEW	***	                              		*/

/*java.util.zip: Provides classes for reading and writing the standard ZIP and GZIP file formats.
  Class Details: http://docs.oracle.com/javase/7/docs/api/java/util/zip/package-summary.html

  BufferedOutputStream: Setting up such an output stream, an application can write bytes 
  to the underlying output stream without necessarily causing a call to the underlying system for each byte written.
  Class Details: https://docs.oracle.com/javase/7/docs/api/java/io/BufferedOutputStream.html
*/