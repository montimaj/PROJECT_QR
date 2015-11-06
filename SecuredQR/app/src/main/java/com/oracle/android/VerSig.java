package com.oracle.android;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.BufferedInputStream;

import java.security.KeyFactory;
import java.security.Signature;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import edu.sxccal.qrcodescanner.Verify;

/**
 * Verifies input file
 * @since 1.0
 */
 public class VerSig
{
	/**
	 *
	 * @param args Input files where
	 * <p>
	 * args[0] is 'suepk'
	 * args[1] is 'sig'
	 * args[2] is the data file
	 * </p>
	 * @throws Exception
	 */
	public static void verify(String args[]) throws Exception
    {    	
    	FileInputStream keyfis = new FileInputStream(args[0]);
		byte[] encKey = new byte[keyfis.available()];  
		keyfis.read(encKey);
		keyfis.close();
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decode(encKey, Base64.DEFAULT));
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
		boolean verifies=sig.verify(Base64.decode(sigToVerify, Base64.DEFAULT));
		Verify.tv.setText("Digital Signature verification result: "+verifies);
    }		
}