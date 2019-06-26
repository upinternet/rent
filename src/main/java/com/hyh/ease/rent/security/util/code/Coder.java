package com.hyh.ease.rent.security.util.code;


/**
 * 
 * SDA编码/解码器
 * 
 * @author duanyy
 * @version 1.6.12.7 [20181108 duanyy] <br>
 * - 改造coder框架<br>
 */
public interface Coder {
	
	/**
	 * 编码
	 * @param data 原始数据
	 * @param key 加密密钥
	 * @return 编码后的数据
	 */
	public String encode(String data, String key);

	public String encode(String data, String key, boolean hex, boolean urlSafe);

	/**
	 * 解码
	 * @param data 编码数据
	 * @param key 解密密钥
	 * @return 解码后的数据
	 */
	public String decode(String data, String key);

	public String decode(String data, String key, boolean hex, boolean urlSafe);

	/**
	 * 生成key
	 * @return key
	 */
	public String createKey();

	/**
	 * 通过一个初始化的值生成key
	 * @param init 初始化的值
	 * @return 对应算法所需要的Key
	 */
	public String createKey(String init);

	public String createKey(String init, boolean hex, boolean urlSafe);
}
