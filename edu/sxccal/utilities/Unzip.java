package edu.sxccal.utilities;

import java.io.*;
import java.util.zip.*;

//User library to extract zip file
public class Unzip
{    
    private static final int BUFFER_SIZE = 4096;     
    public static void unzip(String zipFilePath, String destDirectory) throws IOException 
    {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) 
        {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();        
        while (entry != null) 
        {
            String filePath = destDirectory + File.separator + entry.getName();
            
	    if (!entry.isDirectory()) 
            {                
		System.out.println(entry.getName());		                
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
