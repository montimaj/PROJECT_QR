package com.oracle;
import java.io.*;
import java.security.*;
import java.security.spec.*;

public class VerSig {

    public static void verify(String suepk, String sign, String data) {

        try
        {
            FileInputStream keyfis = new FileInputStream(suepk);
            byte[] encKey = new byte[keyfis.available()];  
            keyfis.read(encKey);
            keyfis.close();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SunRsaSign");
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
            FileInputStream sigfis = new FileInputStream(sign);
            byte[] sigToVerify = new byte[sigfis.available()]; 
            sigfis.read(sigToVerify);
            sigfis.close();
            Signature sig = Signature.getInstance("SHA1withRSA", "SunRsaSign");
            sig.initVerify(pubKey);
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
        catch (Exception e)
        {
            System.err.println("Caught exception " + e.toString());
	}
    }
}


 
