package com.ninetop.common.util;

import android.util.Base64;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 * 
 *@author       wufengjing
 *@date         2017年1月13日 下午6:13:53
 *@description  RSA非对称加密工具类
 *
 */
public class RSAEncryptUtil {

	private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9kzratPjVF+lkpfGjgA/FBb/ajoxPQNQGIsvPcN5NQpER4Cr36oDfGleHJ0+Msx6I6miwiGWEcEOic/QBSjJF9Tj4jCBUGw9HJrHPOClSc8vbeL3aeMAPJdvrcwQ49H1BGoeWv0ql54kcGaeyS6rhUsK3egNLVT083XHkYBGTgwIDAQAB";
	
	/**
	 * 使用公钥加密
	 *
	 * @param data 需要加密的数据,使用公钥加密
	 * @return 加密后的字符
	 * @throws Exception
	 */
	public static String encrypt(String data) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
		return encryptBASE64(cipher.doFinal(data.getBytes()));
	}

	/**
	 * 获取公钥
	 * @return
	 */
	private static RSAPublicKey getPublicKey() {
		RSAPublicKey publicKey = null;
		try {
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
					decryptBASE64(PUBLIC_KEY));
			// RSA非对称加密算法
			KeyFactory keyFactory = getKeyFactory();
			// 取公钥匙对象
			publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return publicKey;
	}

	/**
	 * @return 获取密钥工厂
	 * @throws NoSuchAlgorithmException
	 */
	private static KeyFactory getKeyFactory() throws NoSuchAlgorithmException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory;
	}

	/**
	 * @param key 加密数据的字节数组
	 * @return base64加密后的数据
	 */
	@SuppressWarnings("restriction")
	private static String encryptBASE64(byte[] key) {
//		return new BASE64Encoder().encodeBuffer(key);
		return  Base64.encodeToString(key,Base64.DEFAULT);
	}

	/**
	 * @param key 需要解密的数据
	 * @return base64解密后的数据
	 * @throws IOException
	 */
	@SuppressWarnings("restriction")
	private static byte[] decryptBASE64(String key) throws IOException {
//		return new BASE64Decoder().decodeBuffer(key);
		return Base64.decode(key,Base64.DEFAULT);
	}
	

	public static void main(String[] args) throws Exception {
		System.out.println("加密前："+12345);
		String encryptStr = encrypt("12345");
		System.out.println("加密后："+encryptStr);
		
	}
}
