package com.oracle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;

import java.security.SecureRandom;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class GenSig
{
    public static void Gen_sig(String file,String dest) throws Exception
    {
	    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "SunRsaSign"); //Generate a key pair
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(3072, random); //initialize a 3072-bit key with a SecureRandom object
            KeyPair pair = keyGen.generateKeyPair(); //generate private and public key
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();
            
            /* Create a Signature object and initialize it with the private key */
            Signature rsa = Signature.getInstance("SHA1withRSA", "SunRsaSign");
            rsa.initSign(priv); 
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bufin = new BufferedInputStream(fis);
            byte[] buffer = new byte[3072];
            int len;
            while (bufin.available() != 0) //update input file
            {
                len = bufin.read(buffer);
                rsa.update(buffer, 0, len);
            }
            bufin.close();
            byte[] realSig = rsa.sign(); //Now that all the data to be signed has been read in,generate a signature for it     
            FileOutputStream sigfos = new FileOutputStream(dest+"/sig"); //create 'sig' file
            sigfos.write(realSig);//write the signature to 'sig' file
            sigfos.close();
            byte[] key = pub.getEncoded();
            FileOutputStream keyfos = new FileOutputStream(dest+"/suepk"); //create 'suepk' file
            keyfos.write(key);//write the public key to 'suepk' file
            keyfos.close();  	  
    }
}
/*		***		LIBRARY OVERVIEW	***	                              		*/

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