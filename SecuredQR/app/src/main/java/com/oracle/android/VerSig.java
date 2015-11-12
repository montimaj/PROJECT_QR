package com.oracle.android;

import edu.sxccal.qrcodescanner.Verify;

import java.io.FileInputStream;
import java.io.BufferedInputStream;

import java.security.KeyFactory;
import java.security.Security;
import java.security.Signature;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.spongycastle.jce.provider.BouncyCastleProvider;

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
		Security.insertProviderAt(new BouncyCastleProvider(), 1);
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(encKey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SC");
		PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
		FileInputStream sigfis = new FileInputStream(args[1]);            
		byte[] sigToVerify = new byte[sigfis.available()]; 
		sigfis.read(sigToVerify);
		sigfis.close();
		Signature sig = Signature.getInstance("SHA256withRSA", "SC");
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
		boolean verifies=sig.verify(Base64.decodeBase64(sigToVerify));
		Verify.tv.setText("Digital Signature verification result: "+verifies);
    }		
}