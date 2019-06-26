package com.hyh.ease.rent.security.util;

import com.hyh.ease.rent.security.domain.AccessKey;
import com.hyh.ease.rent.security.util.code.Coder;
import com.hyh.ease.rent.security.util.code.coder.HmacSHA1;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 签名验证
 */
public class Signature{

	private static Coder coder = new HmacSHA1();
	private static int ttl = 24*60*60*1000;
	
	public static boolean verify(AccessKey key, HttpServletRequest req) {
		checkTimestamp(key,req);

		checkSignature(key,req);
		
		return true;
	}
	
	protected static void checkSignature(AccessKey key, HttpServletRequest req){
		String now = req.getHeader("timestamp");
		String payload = req.getHeader("payload");

		String uri = req.getRequestURI();
		String queryString = req.getQueryString();
		if (StringUtils.isNotEmpty(queryString)){
			uri += "?" + queryString;
		}

		StringBuffer toSign = new StringBuffer();
		toSign.append(key.getId()).append("\n");
		toSign.append(now).append("\n");
		toSign.append(uri);

		if (StringUtils.isNotEmpty(payload)){
			toSign.append("\n").append(payload);
		}

		String signed = coder.encode(toSign.toString(), key.getKeyContent());
		String signature = req.getHeader("signature");
		if (!signed.equals(signature)){
			throw new RuntimeException(String.format("The signature is not correct.",signature));
		}
	}
	
	protected static void checkTimestamp(AccessKey key, HttpServletRequest req){
		String now = req.getHeader("timestamp");
		long timestamp = getLong(now,0);
		if (timestamp <= 0){
			throw new RuntimeException(String.format("Can not find argument timestamp."));
		}
		
		if (Math.abs(System.currentTimeMillis() - timestamp) > ttl){
			throw new RuntimeException(String.format("Timestamp %d is expired.",timestamp));
		}
	}

	private static long getLong(String now, long i) {
		try {
			return Long.parseLong(now);
		}catch (NumberFormatException ex){
			return i;
		}
	}
}
