package com.hyh.ease.rent.security.util.code.coder;

import com.hyh.ease.rent.security.util.code.Coder;
import com.hyh.ease.rent.security.util.code.util.ByteUtil;
import com.hyh.ease.rent.security.util.code.util.KeyGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base64转换
 * @author yyduan
 * @version 1.6.12.7 [20181108 duanyy] <br>
 * - 改造coder框架<br>
 */
public class Base64 implements Coder {
	protected static final Logger LOG = LoggerFactory.getLogger(Base64.class);
	public String getAlgorithm() {
		return "Base64";
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
		return ByteUtil.byte2string(data.getBytes(), false, urlSafe);
	}

	@Override
	public String decode(String data,String key) {
		return decode(data,key,false,false);
	}	
	
	@Override
	public String decode(String data, String key, boolean hex, boolean urlSafe) {
		return new String(ByteUtil.string2byte(data, false, urlSafe));
	}

	@Override
	public String createKey(String init, boolean hex, boolean urlSafe) {
		return init;
	}
}