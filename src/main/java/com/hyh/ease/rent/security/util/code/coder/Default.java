package com.hyh.ease.rent.security.util.code.coder;

import com.hyh.ease.rent.security.util.code.Coder;
import com.hyh.ease.rent.security.util.code.util.KeyGen;


/**
 * 缺省的编码/解码器
 * 
 * <br>
 * 
 * 缺省状态下
 * 
 */
public class Default implements Coder {

	@Override
	public String encode(String data,String key) {
		return data;
	}

	@Override
	public String decode(String data,String key) {
		return data;
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
		return data;
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
