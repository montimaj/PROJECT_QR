package com.oracle.android;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;

import java.security.SecureRandom;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.Signature;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import edu.sxccal.qrcodescanner.QRCode;
import edu.sxccal.qrcodescanner.QR;
import org.spongycastle.jce.provider.BouncyCastleProvider;

/**
 * Creates 'sig' and 'suepk' files
 * @since 1.0
 */
public class GenSig
{
    /**
     * @param file Input file
     * @throws Exception
     */
    public static void Gen_sig(String file) throws Exception
    {
    	FileInputStream fis = new FileInputStream(file);
        Security.insertProviderAt(new BouncyCastleProvider(),1);
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "SC");
        //Algorithm provider for Android java is AndroidOpenSSL 
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        int keysize=3072;
        keyGen.initialize(keysize, random);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();
        Signature rsa = Signature.getInstance("SHA256withRSA", "SC");
        rsa.initSign(priv);            
        BufferedInputStream bufin = new BufferedInputStream(fis);
        byte[] buffer = new byte[keysize];
        int len;
        while (bufin.available() != 0)
        {
        	len = bufin.read(buffer);
            rsa.update(buffer, 0, len);
        }
        bufin.close();
        byte[] realSig = Base64.encode(rsa.sign(), Base64.DEFAULT);
        String f1=QRCode.filePath+"/sig",f2=QRCode.filePath+"/suepk";
        FileOutputStream sigfos = new FileOutputStream(f1);
        sigfos.write(realSig);
        sigfos.close();
        byte[] key = Base64.encode(pub.getEncoded(), Base64.DEFAULT);
        FileOutputStream keyfos = new FileOutputStream(f2);
        keyfos.write(key);
        keyfos.close();
        QR.str+="Signature: "+f1+"\nPublic key: "+f2;	          
    }
}