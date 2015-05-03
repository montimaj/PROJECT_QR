package edu.sxccal.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;

/**
* Creates a zip file
* @since 1.0
* @see java.util.zip
*/
public class ZipCreator
{   
  /**
  * @param zipFile Path to input zip file
  * @param infiles Contains the path of the files to be zipped
  * @throws IOException
  */
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