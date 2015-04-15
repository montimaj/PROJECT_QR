package com.oracle;

import java.io.FileInputStream;
import java.io.BufferedInputStream;

import java.security.KeyFactory;
import java.security.Signature;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class VerSig 
{
    public static void verify(String suepk, String sign, String data) throws Exception
    {       
            FileInputStream keyfis = new FileInputStream(suepk); //import encoded public key
            byte[] encKey = new byte[keyfis.available()];  
            keyfis.read(encKey);
            keyfis.close();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SunRsaSign");
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
            FileInputStream sigfis = new FileInputStream(sign); //import the signature bytes
            byte[] sigToVerify = new byte[sigfis.available()]; 
            sigfis.read(sigToVerify);
            sigfis.close();
            Signature sig = Signature.getInstance("SHA1withRSA", "SunRsaSign"); //create a signature object and sign it with the public key
            sig.initVerify(pubKey);
            
            //update and verify the input file
            FileInputStream datafis = new FileInputStream(data); 
            BufferedInputStream bufin = new BufferedInputStream(datafis);
            byte[] buffer = new byte[3072];
            int len;
            while (bufin.available() != 0)
            {
                len = bufin.read(buffer);
                sig.update(buffer, 0, len);
            }
            bufin.close();
            boolean verifies = sig.verify(sigToVerify);
            System.out.println("signature verifies: " + verifies);       
    }    
}

/*		***		LIBRARY OVERVIEW	***	                              		*/

/*X509EncodedKeySpec: Represents the ASN.1 encoding of a public key
 ASN.1 Details: http://en.wikipedia.org/wiki/Abstract_Syntax_Notation_One
 Class Details: https://docs.oracle.com/javase/7/docs/api/java/security/spec/X509EncodedKeySpec.html
*/