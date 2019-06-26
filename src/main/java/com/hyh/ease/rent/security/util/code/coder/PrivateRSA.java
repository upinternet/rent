package com.hyh.ease.rent.security.util.code.coder;


import com.hyh.ease.rent.security.util.code.Coder;
import com.hyh.ease.rent.security.util.code.util.KeyGen;
import com.hyh.ease.rent.security.util.code.util.RSAUtil;

/**
 * 基于RSA采用私钥加密/解密
 * 
 * @author duanyy
 * @version 1.6.12.7 [20181108 duanyy] <br>
 * - 改造coder框架<br>
 */
public class PrivateRSA implements Coder {
	@Override
	public String encode(String data,String key) {
		return RSAUtil.encryptWithPrivateKey(data, key);
	}
	
	@Override
	public String decode(String data,String key) {
		return RSAUtil.decryptWithPrivateKey(data, key);
	}
	
	public String sign(String data,String key){
		return RSAUtil.sign(data, key);
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
		return RSAUtil.encryptWithPrivateKey(data, key);
	}

	@Override
	public String decode(String data, String key, boolean hex, boolean urlSafe) {
		return RSAUtil.decryptWithPrivateKey(data, key);
	}

	@Override
	public String createKey(String init, boolean hex, boolean urlSafe) {
		return KeyGen.getKey(8);
	}
}