package com.hyh.ease.rent.security.util.code.coder;

import com.hyh.ease.rent.security.util.code.Coder;
import com.hyh.ease.rent.security.util.code.util.ByteUtil;
import com.hyh.ease.rent.security.util.code.util.KeyGen;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.SecureRandom;


/**
 * 基于3DES算法的编码/解码器
 * 
 * @author duanyy
 * @version 1.6.12.7 [20181108 duanyy] <br>
 * - 改造coder框架<br>
 */
public class DES3 implements Coder {
	
	protected static final Logger LOG = LoggerFactory.getLogger(DES3.class);
	
	public String getAlgorithm() {
		return "DESede";
	}
	
	public String createKey(){
		return KeyGen.getKey(24);
	}	
	
	@Override
	public String createKey(String init){
		return init;
	}
	
	protected String padding(String key){
		if (key.length() > 24){
			return key.substring(0,24);
		}else{
			for (int i = key.length() ; i < 24 ; i ++){
				key += "0";
			}
			return key;
		}
	}

	@Override
	public String encode(String data, String key, boolean hex, boolean urlSafe) {
		try {
			String _key = padding(key);
			String algorithm = getAlgorithm();

            SecureRandom random = new SecureRandom();  
            DESedeKeySpec desKey = new DESedeKeySpec(_key.getBytes());  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);  
            SecretKey securekey = keyFactory.generateSecret(desKey);
            
            Cipher c = Cipher.getInstance(algorithm);  
            c.init(Cipher.ENCRYPT_MODE, securekey, random);  	        
			
            byte [] result = c.doFinal(data.getBytes());
            return ByteUtil.byte2string(result, hex, urlSafe);
		}catch (Exception ex){
			LOG.error(ExceptionUtils.getStackTrace(ex));
			return data;
		}
	}
	
	@Override
	public String encode(String data,String key) {
		return encode(data,key,false,false);
	}
	
	@Override
	public String decode(String data,String key,boolean hex,boolean urlSafe) {
		try {
			String _key = padding(key);
			String algorithm = getAlgorithm();

            SecureRandom random = new SecureRandom();  
            DESedeKeySpec desKey = new DESedeKeySpec(_key.getBytes());  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);  
            SecretKey securekey = keyFactory.generateSecret(desKey);
            
            Cipher c = Cipher.getInstance(algorithm);	
			c.init(Cipher.DECRYPT_MODE, securekey,random);
			
			byte [] result = ByteUtil.string2byte(data, hex, urlSafe);
			return  new String(c.doFinal(result));
		}catch (Exception ex){
			LOG.error(ExceptionUtils.getStackTrace(ex));
			return data;
		}
	}	
	
	@Override
	public String decode(String data, String key) {
		return decode(data,key,false,false);
	}

	@Override
	public String createKey(String init, boolean hex, boolean urlSafe) {
		return init;
	}
}
