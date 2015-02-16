package com.oracle;
import java.io.*;
import java.security.*;

public class GenSig
{

    public static void Gen_sig(String file,String dest)throws Exception
    {

        try
        {           

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "SunRsaSign");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(3072, random);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();
            Signature rsa = Signature.getInstance("SHA1withRSA", "SunRsaSign");
            rsa.initSign(priv);
            FileInputStream fis = new FileInputStream(file);
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
            FileOutputStream sigfos = new FileOutputStream(dest+"/sig");
            sigfos.write(realSig);
            sigfos.close();
            byte[] key = pub.getEncoded();
            FileOutputStream keyfos = new FileOutputStream(dest+"/suepk");
            keyfos.write(key);
            keyfos.close();
	  }
	  catch (Exception e)
	  {
            System.err.println("Caught exception " + e.toString());
	  }
    }
}


 
