package com.hyh.ease.rent.security.util.code.coder;

import com.hyh.ease.rent.security.util.code.Coder;
import com.hyh.ease.rent.security.util.code.util.ByteUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * HmacSHA1编码
 * 
 * @author yyduan
 * @since 1.6.10.6
 * @version 1.6.12.7 [20181108 duanyy] <br>
 * - 改造coder框架<br>
 */
public class HmacSHA1 implements Coder {
	protected static final Logger LOG = LoggerFactory.getLogger(HmacSHA1.class);
	
	public String getAlgorithm() {
		return "HmacSHA1";
	}
	
	protected ThreadLocal<Mac> macLocal = new ThreadLocal<Mac>(){
        protected Mac initialValue() {
        	try {
        		return Mac.getInstance(getAlgorithm()); 
        	}catch (Exception ex){
    			LOG.error(ExceptionUtils.getStackTrace(ex));
    			return null;
        	}
        };
    };	
	
	@Override
	public String encode(String data, String key,boolean hex,boolean urlSafe) {
		try {
        	SecretKey secretKey = new SecretKeySpec(key.getBytes(), getAlgorithm());  
        	Mac mac = macLocal.get();
            mac.init(secretKey);  
            byte[] bytes = mac.doFinal(data.getBytes());  
            return ByteUtil.byte2string(bytes, hex, urlSafe);
		}catch (Exception ex){
			LOG.error(ExceptionUtils.getStackTrace(ex));
			return data;
		}
	}
	
	@Override
	public String encode(String data, String key) {
		return encode(data,key,false,false);
	}

	@Override
	public String decode(String data, String key) {
		return data;
	}
	
	@Override
	public String decode(String data, String key,boolean hex,boolean urlSafe) {
		return data;
	}	

	@Override
	public String createKey() {		
		return createKey("",false,false);
	}
	
	@Override
	public String createKey(String key){
		return createKey();
	}

	@Override
	public String createKey(String init, boolean hex, boolean urlSafe) {
		try {
			KeyGenerator generator = KeyGenerator.getInstance(getAlgorithm());
	        SecretKey key = generator.generateKey();  
	        return ByteUtil.byte2string(key.getEncoded(), hex, urlSafe);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(ExceptionUtils.getStackTrace(e));
		} 
	}
}
