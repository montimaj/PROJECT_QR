package com.oracle.android;
import java.io.*;
import java.security.*;
import java.security.spec.*;
import edu.sxccal.qrcodescanner.*;

public class VerSig 
{

	public static void verify(String args[]) 
    	{    	
    		boolean verifies=false;    
		try
		{	    		
	            FileInputStream keyfis = new FileInputStream(args[0]);
	            byte[] encKey = new byte[keyfis.available()];  
	            keyfis.read(encKey);
	            keyfis.close();
	            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
	            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "AndroidOpenSSL");
	            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
	            FileInputStream sigfis = new FileInputStream(args[1]);            
	            byte[] sigToVerify = new byte[sigfis.available()]; 
	            sigfis.read(sigToVerify);
	            sigfis.close();
	            Signature sig = Signature.getInstance("SHA1withRSA", "AndroidOpenSSL");
	            sig.initVerify(pubKey);
	            FileInputStream datafis = new FileInputStream(args[2]);            
	            BufferedInputStream bufin = new BufferedInputStream(datafis);
	            byte[] buffer = new byte[3072];
	            int len;
	            while (bufin.available() != 0)
	            {
	                len = bufin.read(buffer);
	                sig.update(buffer, 0, len);
	            }
	            bufin.close();
	            verifies=sig.verify(sigToVerify);  	            
	            Verify.tv.setText("Digital Signature verification result: "+verifies);	
	     }
    	 catch(Exception e)
	 {
    		 Log.create_log(e, Verify.tv);
         }    		 
      }		
}


 
