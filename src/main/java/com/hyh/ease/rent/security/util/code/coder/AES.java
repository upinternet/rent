package com.hyh.ease.rent.security.util.code.coder;

import com.hyh.ease.rent.security.util.code.Coder;
import com.hyh.ease.rent.security.util.code.util.ByteUtil;
import com.hyh.ease.rent.security.util.code.util.KeyGen;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;


/**
 * 基于AES的编码/解码器
 * 
 * @author duanyy
 * @version 1.6.12.7 [20181108 duanyy] <br>
 * - 改造coder框架<br>
 */
public class AES implements Coder {
	protected static final Logger LOG = LoggerFactory.getLogger(AES.class);
	public String getAlgorithm() {
		return "AES";
	}
	
	@Override
	public String createKey(){
		return KeyGen.getKey(8);
	}
	
	@Override
	public String createKey(String init){
		return init;
	}

	@Override
	public String encode(String data,String key) {
		return encode(data,key,false,false);
	}
	
	@Override
	public String encode(String data, String key, boolean hex, boolean urlSafe) {
		try {
			String algorithm = getAlgorithm();
			KeyGenerator keygen = KeyGenerator.getInstance(algorithm);			
			keygen.init(128, new SecureRandom(key.getBytes())); 
			SecretKey secretKey = keygen.generateKey();
			
			Cipher c = Cipher.getInstance(algorithm);			
	        c.init(Cipher.ENCRYPT_MODE, secretKey);	 
	        
			byte [] result = c.doFinal(data.getBytes());
			return ByteUtil.byte2string(result, hex, urlSafe);
		}catch (Exception ex){
			LOG.error(ExceptionUtils.getStackTrace(ex));
			return data;
		}
	}

	@Override
	public String decode(String data,String key) {
		return decode(data,key,false,false);
	}	
	
	@Override
	public String decode(String data, String key, boolean hex, boolean urlSafe) {
		try {
			byte [] result = ByteUtil.string2byte(data, hex, urlSafe);
			
			String algorithm = getAlgorithm();
			KeyGenerator keygen = KeyGenerator.getInstance(algorithm);			
			keygen.init(128, new SecureRandom(key.getBytes())); 
			SecretKey secretKey = keygen.generateKey();

			Cipher c = Cipher.getInstance(algorithm);		
			c.init(Cipher.DECRYPT_MODE, secretKey);  
			return  new String(c.doFinal(result));
		}catch (Exception ex){
			LOG.error(ExceptionUtils.getStackTrace(ex));
			return data;
		}		
	}

	@Override
	public String createKey(String init, boolean hex, boolean urlSafe) {
		return init;
	}
}
