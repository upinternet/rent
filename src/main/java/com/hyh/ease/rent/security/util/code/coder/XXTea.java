package com.hyh.ease.rent.security.util.code.coder;

import com.hyh.ease.rent.security.util.code.Coder;
import com.hyh.ease.rent.security.util.code.util.ByteUtil;
import com.hyh.ease.rent.security.util.code.util.KeyGen;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XXTea加密算法
 * @author yyduan
 * @version 1.6.12.7 [20181108 duanyy] <br>
 * - 改造coder框架<br>
 */
public class XXTea implements Coder {
	protected static final Logger LOG = LoggerFactory.getLogger(XXTea.class);
	public String getAlgorithm() {
		return "XXTea";
	}
	
	@Override
	public String encode(String data, String key) {
		return encode(data,key,true,true);
	}
	
	public String encode(String data, String key,boolean hex,boolean urlSafe) {
		try {
			byte [] dataBytes = data.getBytes();
			byte [] keyBytes = ByteUtil.string2byte(key, hex,urlSafe);
			
			byte [] result = toByteArray(
					encrypt(toIntArray(dataBytes, true), toIntArray(keyBytes, false)),
					false);
			
			return ByteUtil.byte2string(result, hex,urlSafe);
		} catch (Exception e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			return data;
		}
	}	

	@Override
	public String decode(String data, String key) {
		return decode(data,key,true,true);
	}
	
	public String decode(String data, String key,boolean hex,boolean urlSafe) {
		try {
			byte [] dataBytes = ByteUtil.string2byte(data,hex,urlSafe);
			byte [] keyBytes = ByteUtil.string2byte(key, hex,urlSafe);
			
			byte [] result = toByteArray(
					decrypt(toIntArray(dataBytes, false), toIntArray(keyBytes, false)),
					true);
			
			return new String(result);
		} catch (Exception e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			return data;
		}
	}	

	@Override
	public String createKey() {
		return KeyGen.getKey(8);
	}

	@Override
	public String createKey(String init){
		return init;
	}
	
	public String createKey(String init,boolean hex,boolean urlSafe){
		return init;
	}	
	
	public static
	int[] encrypt(int[] v, int[] k) {
		int n = v.length - 1;

		if (n < 1) {
			return v;
		}
		if (k.length < 4) {
			int[] key = new int[4];

			System.arraycopy(k, 0, key, 0, k.length);
			k = key;
		}
		int z = v[n], y = v[0], delta = 0x9E3779B9, sum = 0, e;
		int p, q = 6 + 52 / (n + 1);

		while (q-- > 0) {
			sum = sum + delta;
			e = sum >>> 2 & 3;
			for (p = 0; p < n; p++) {
				y = v[p + 1];
				z = v[p] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
						+ (k[p & 3 ^ e] ^ z);
			}
			y = v[0];
			z = v[n] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
					+ (k[p & 3 ^ e] ^ z);
		}
		return v;
	}

	public static
	int[] decrypt(int[] v, int[] k) {
		int n = v.length - 1;

		if (n < 1) {
			return v;
		}
		if (k.length < 4) {
			int[] key = new int[4];

			System.arraycopy(k, 0, key, 0, k.length);
			k = key;
		}
		int z = v[n], y = v[0], delta = 0x9E3779B9, sum, e;
		int p, q = 6 + 52 / (n + 1);

		sum = q * delta;
		while (sum != 0) {
			e = sum >>> 2 & 3;
			for (p = n; p > 0; p--) {
				z = v[p - 1];
				y = v[p] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
						+ (k[p & 3 ^ e] ^ z);
			}
			z = v[n];
			y = v[0] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
					+ (k[p & 3 ^ e] ^ z);
			sum = sum - delta;
		}
		return v;
	}
	
	public static
	int[] toIntArray(byte[] data, boolean includeLength) {
		int n = (((data.length & 3) == 0) ? (data.length >>> 2)
				: ((data.length >>> 2) + 1));
		int[] result;

		if (includeLength) {
			result = new int[n + 1];
			result[n] = data.length;
		} else {
			result = new int[n];
		}
		n = data.length;
		for (int i = 0; i < n; i++) {
			result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3);
		}
		return result;
	}

	public static
	byte[] toByteArray(int[] data, boolean includeLength) {
		int n = data.length << 2;
		if (includeLength) {
			int m = data[data.length - 1];

			if (m > n || m <= 0) {
				return null;
			} else {
				n = m;
			}
		}
		byte[] result = new byte[n];

		for (int i = 0; i < n; i++) {
			result[i] = (byte) ((data[i >>> 2] >>> ((i & 3) << 3)) & 0xff);
		}
		return result;
	}

}
