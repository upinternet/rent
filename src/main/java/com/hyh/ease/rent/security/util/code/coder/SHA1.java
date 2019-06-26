package com.hyh.ease.rent.security.util.code.coder;

import com.hyh.ease.rent.security.util.code.Coder;
import com.hyh.ease.rent.security.util.code.util.ByteUtil;
import com.hyh.ease.rent.security.util.code.util.KeyGen;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * SHA1 加密
 * 
 * @author duanyy
 *
 * @since 1.0.13
 * 
 * @version 1.6.12.7 [20181108 duanyy] <br>
 * - 改造coder框架<br>
 */
public class SHA1 implements Coder {
	protected static final Logger LOG = LoggerFactory.getLogger(SHA1.class);
	public String getAlgorithm() {
		return "sha-1";
	}

	public String decode(String data, String key) {
		return data;
	}

	public String createKey() {
		return KeyGen.getKey(8);
	}

	@Override
	public String createKey(String init){
		return init;
	}

	@Override
	public String encode(String data, String key) {
		return encode(data,key,false,false);
	}
	
	@Override
	public String encode(String data, String key, boolean hex, boolean urlSafe) {
		try {
			MessageDigest m = MessageDigest.getInstance(getAlgorithm());
			String content = data + key;
			m.update(content.getBytes());
			byte result[] = m.digest();
			return ByteUtil.byte2string(result, hex, urlSafe);
		} catch (NoSuchAlgorithmException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			return data;
		}
	}

	@Override
	public String decode(String data, String key, boolean hex, boolean urlSafe) {
		return data;
	}

	@Override
	public String createKey(String init, boolean hex, boolean urlSafe) {
		return init;
	}
}