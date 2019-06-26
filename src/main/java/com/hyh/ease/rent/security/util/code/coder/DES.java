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
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * 基于DES算法编码/解码器
 * 
 * @author duanyy
 * @version 1.6.12.7 [20181108 duanyy] <br>
 * - 改造coder框架<br>
 */
public class DES implements Coder {
	protected static final Logger LOG = LoggerFactory.getLogger(DES.class);
	public String getAlgorithm() {
		return "DES";
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
	public String encode(String data, String key, boolean hex, boolean urlSafe) {
		try {
			String algorithm = getAlgorithm();
            SecureRandom random = new SecureRandom();  
            DESKeySpec desKey = new DESKeySpec(key.getBytes());  
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
	public String decode(String data,String key) {
		return decode(data,key,false,false);
	}	
	

	@Override
	public String decode(String data, String key, boolean hex, boolean urlSafe) {
		try {
			String algorithm = getAlgorithm();

            SecureRandom random = new SecureRandom();  
            DESKeySpec desKey = new DESKeySpec(key.getBytes());  
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
	public String createKey(String init, boolean hex, boolean urlSafe) {
		return init;
	}
}
