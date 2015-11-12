package com.oracle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;

import java.security.SecureRandom;
import java.security.Security;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
* Creates a detached digital signature along with the public key 
* <p>
* RSA encryption algorithm is used along with SHA-1
* The keysize is 3072 bits
* @since 1.0
* @see java.security
* @see <a href="http://en.wikipedia.org/wiki/RSA_%28cryptosystem%29">RSA</a>
* @see <a href="http://en.wikipedia.org/wiki/SHA-1">SHA-1</a>
*/

public class GenSig
{
    /**
    * Writes the signature to 'sig' and public key to 'suepk'
    * @param file Input file whose signature and public key is to be generated
    * @param dest Output directory to store the signature and public key files encoded in {@link java.util.Base64} format   
    * @throws Exception 
    */
    public static void Gen_sig(String file,String dest) throws Exception
    {	    
	    	Security.insertProviderAt(new BouncyCastleProvider(), 1);
    		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC"); //Generate a key pair           
            keyGen.initialize(3072, new SecureRandom()); //initialize a 3072-bit key with a SecureRandom object
            KeyPair pair = keyGen.generateKeyPair(); //generate private and public key
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();
            
            /* Create a Signature object and initialize it with the private key */
            Signature rsa = Signature.getInstance("SHA256withRSA", "BC");
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
            byte[] realSig = Base64.encodeBase64(rsa.sign()); //Now that all the data to be signed has been read in,generate a signature for it     
            FileOutputStream sigfos = new FileOutputStream(dest+"/sig"); //create 'sig' file
            sigfos.write(realSig);//write the signature to 'sig' file
            sigfos.close();
            byte[] key = Base64.encodeBase64(pub.getEncoded());
            FileOutputStream keyfos = new FileOutputStream(dest+"/suepk"); //create 'suepk' file
            keyfos.write(key);//write the public key to 'suepk' file
            keyfos.close();  	  
    }
}