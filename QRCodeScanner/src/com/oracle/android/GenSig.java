package com.oracle.android;
import java.io.*;
import java.security.*;

import edu.sxccal.qrcodescanner.*;

public class GenSig
{

    public static void Gen_sig(String file)
    {

        try
        {           

            FileInputStream fis = new FileInputStream(file);
            GenQR.tv.setText("");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "AndroidOpenSSL");
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
            GenQR.tv.setText("Signature: "+f1);
            GenQR.tv.append("\nPublic key: "+f2);
	  }
	  catch (Exception e)
	  {
		  Log.create_log(e,GenQR.tv);		  
	  }        
    }
}


 
