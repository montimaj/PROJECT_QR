package com.oracle.android;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;

import java.security.SecureRandom;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import edu.sxccal.qrcodescanner.QRCode;
import edu.sxccal.qrcodescanner.QR;
//Creates 'sig' and 'suepk' files
public class GenSig
{
    public static void Gen_sig(String file) throws Exception
    {
    	FileInputStream fis = new FileInputStream(file);        
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "AndroidOpenSSL"); 
        //Algorithm provider for Android java is AndroidOpenSSL 
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "AndroidOpenSSL");
        keyGen.initialize(3072, random);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();
        Signature rsa = Signature.getInstance("SHA1withRSA", "AndroidOpenSSL");
        rsa.initSign(priv);            
        BufferedInputStream bufin = new BufferedInputStream(fis);
        byte[] buffer = new byte[3072];
        int len;
        while (bufin.available() != 0)
        {
        	len = bufin.read(buffer);
            rsa.update(buffer, 0, len);
        }
        bufin.close();
        byte[] realSig = rsa.sign();  
        String f1=QRCode.filePath+"/sig",f2=QRCode.filePath+"/suepk";
        FileOutputStream sigfos = new FileOutputStream(f1);
        sigfos.write(realSig);
        sigfos.close();
        byte[] key = pub.getEncoded();
        FileOutputStream keyfos = new FileOutputStream(f2);
        keyfos.write(key);
        keyfos.close();
        QR.str+="Signature: "+f1+"\nPublic key: "+f2;	          
    }
}

/*								***		LIBRARY OVERVIEW	***	                              						*/

/*SecureRandom: provides a cryptographically strong random number generator (RNG). 
  Class Details: https://docs.oracle.com/javase/7/docs/api/java/security/SecureRandom.html

  KeyPairGenerator: Generates pairs of public and private keys.
  KeyPairGenerator.initialize(int keysize, SecureRandom random): Initializes the key pair generator 
  for a certain keysize with the given source of randomness 
  Class Details: https://docs.oracle.com/javase/7/docs/api/java/security/KeyPairGenerator.html  

  KeyPair: Simple holder for a key pair (a public key and a private key).
  Class Details: https://docs.oracle.com/javase/7/docs/api/java/security/KeyPair.html
  
  Signature: Used to provide applications the functionality of a digital signature algorithm
  Class Details: https://docs.oracle.com/javase/7/docs/api/java/security/Signature.html
  
  BufferedInputStream: A BufferedInputStream adds functionality to another input stream
  Class Details: docs.oracle.com/javase/7/docs/api/java/io/BufferedInputStream.html
*/

  