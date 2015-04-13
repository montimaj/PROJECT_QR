package com.oracle;

import java.io.*;
import java.security.*;
public class GenSig
{
    public static void Gen_sig(String file,String dest)throws Exception
    {

        try
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
	  catch (Exception e)
	  {
            System.err.println("Caught exception " + e.toString());
	  }
    }
}


 
