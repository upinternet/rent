package com.hyh.ease.rent.security.util.code.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.Hex;
/**
 * 工具类
 * @author yyduan
 * @version 1.6.12.7 [20181108 duanyy] <br>
 * - 改造coder框架<br>
 */
public class ByteUtil {
	private ByteUtil(){
		
	}    
	
	public static String byte2string(byte[] data,boolean hex,boolean urlSafe){
		if (hex){
			return Hex.toHexString(data);
		}else{
			return urlSafe? Base64.encodeBase64URLSafeString(data): Base64.encodeBase64String(data);
		}
	}
	
	public static byte [] string2byte(String data,boolean hex,boolean urlSafe){
		if (hex){
			return Hex.decode(data);
		}else{
			return Base64.decodeBase64(data);
		}
	}
}
