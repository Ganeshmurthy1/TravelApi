package com.tayyarah.insurance.util.api.trawelltag;

import org.apache.log4j.Logger;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Base64;



public class TrawellTagEncryption {
	static final Logger logger = Logger.getLogger(TrawellTagEncryption.class);
	public String getEncrypteData(String XMLString,String ref,String tayyarahsign) {
		String encrptdata = "";
		try{
	  String refrence = ref; 
      String IV = refrence.substring(0, 16);
      byte[] iv = IV.getBytes();
      String sign = tayyarahsign;  
      String KEY = sign.substring(0, 32);
      byte[] key = KEY.getBytes();
      byte[] data = XMLString.getBytes();
      KeyParameter keyParam = new KeyParameter(key); 
      CipherParameters params = new ParametersWithIV(keyParam, iv);   

      // setup AES cipher in CBC mode with PKCS7 padding
      BlockCipherPadding padding = new PKCS7Padding();     
      BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new RijndaelEngine(128)), padding);
      cipher.reset();
      cipher.init(true, params);
      // create a temporary buffer to decode into (it'll include padding)
      byte[] buf = new byte[cipher.getOutputSize(data.length)];
      int len = cipher.processBytes(data, 0, data.length, buf, 0);
      len += cipher.doFinal(buf, len);
      // remove padding
      byte[] out = new byte[len];
      System.arraycopy(buf, 0, out, 0, len);
      // return string representation of decoded bytes
      encrptdata = new String(Base64.encode(out));
    
		}catch(Exception e){
			logger.error("getEncrypteData Exception : ", e);
		}
		return encrptdata;
	}
}
