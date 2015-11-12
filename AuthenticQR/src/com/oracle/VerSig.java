package com.oracle;

import java.io.FileInputStream;
import java.io.BufferedInputStream;

import java.security.KeyFactory;
import java.security.Signature;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
/**
* Verifies whether the input file is authentic
* @since 1.0
* @see java.security
* @see java.security.spec
*/
public class VerSig 
{
    /**
    * @param suepk Path to the public key file
    * @param sign Path to the signature file
    * @param data Path to the file to be verified
    * @return verification result in boolean 
    * @throws Exception
    */
    public static boolean verify(String suepk, String sign, String data) throws Exception
    {       
            FileInputStream keyfis = new FileInputStream(suepk); //import encoded public key
            byte[] encKey = new byte[keyfis.available()];  
            keyfis.read(encKey);
            keyfis.close();
            Security.insertProviderAt(new BouncyCastleProvider(), 1);
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(encKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            // Generate a public key object from the provided key specification(key material)
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec); 
            FileInputStream sigfis = new FileInputStream(sign); //import the signature bytes
            byte[] sigToVerify = new byte[sigfis.available()]; 
            sigfis.read(sigToVerify);
            sigfis.close();
            Signature sig = Signature.getInstance("SHA256withRSA", "BC"); //create a signature object 
            sig.initVerify(pubKey); //sign it with the public key
            
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
            boolean verifies = sig.verify(Base64.decodeBase64(sigToVerify)); 
            return verifies; //return verification result       
    }    
}