package com.jxgyl.message.web.controller;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 密码处理
 * 
 * @author iss002
 *
 */
public abstract class PasswordHandler {

	private static final String SPACER = "/";
	private static final String PRI_KEY_STR = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMJ6tbbYrsWM4HBeBTgwtxkUBWDq/X3Dx08QF3Tc7gvthbr38GVxQLVZKUPt6th3j6XwkZjTNHO1JmaamfYPOZ8pAlIJstYAmhq5pZWfveETKD2qt5q8lLRV29TXqqaUbvVR6P3zDrnKS6ty4OIQTvJ5AwTdZGJqmN6ymLbGB+xTAgMBAAECgYEAjVW4caWPfiofggoSKP/eUO9n6UNd7eQ2GJNNOLTGmwyHP5Wos1BXAnRFtQRWw/JABwyKA1Kar+xad/6rHVN7P5cbZNehpiEPHLaRGinB+e+NrrBGX9Ip9w+H5q/4jPuZmGPeVtfhiNPWix0ftybmUpfKVEF/bGGv9iKK60NUKIECQQDs7nHleS5WCFpOHp8m9b+2oLtbDLPOCCZqQXI4Dan8nGd4b5PgkCqW3lUTmpFGuakEyCGwmFB17MBd0/D8ukuxAkEA0iGdQqfgMlW8WPaFqus9zDPBMCSXYQxX8ogZLil72bo82e6/HTiHu6d6mfRQMWCEnE0N+3pcNexA+cDvl3gtQwJAbyHY4p3YkhoQ4ZuTYx7Z8buqpaZroDRKcISKIqimFwr1rZQIdMiqcWGP3kYd5mKnWMrWopKPCwElqd0DqEdpwQJBAIFa4Axge8uAq+m4GKEXH3vNgGe6ntXHW3yZ+6ZHM07dLwiZyFycqDIE75xGofdDi18MsEbXFFy9i2I0O0nrWTECQQC85DOMSoTABPh2G20tgF+CuRgHs1nBQCEDJ19V3BAkJbJmtkCRzVPJV4MEpseD1+MDlhH94Gp/9ns2pjLTbJo3";
	private static final String PUB_KEY_STR = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCerW22K7FjOBwXgU4MLcZFAVg6v19w8dPEBd03O4L7YW69/BlcUC1WSlD7erYd4+l8JGY0zRztSZmmpn2DzmfKQJSCbLWAJoauaWVn73hEyg9qreavJS0VdvU16qmlG71Uej98w65ykurcuDiEE7yeQME3WRiapjespi2xgfsUwIDAQAB";
	private static final RSACipherService CIPHER_SERVICE = new RSACipherService();
	private static final PrivateKey PRI_KEY = CIPHER_SERVICE.buildPrivateKey(PRI_KEY_STR);
	private static final PublicKey PUB_KEY = CIPHER_SERVICE.buildPublicKey(PUB_KEY_STR);
	private static final String REGEX = "^(.*)/(.*)/([0-9]+)$";
	private static final int USERCODE_GROUP_INDEX = 1;
	private static final int PASSWORD_GROUP_INDEX = 2;
	private static final int TIMESTAMP_GROUP_INDEX = 3;

	/**
	 * “用户编码+密码+时间戳” 加密
	 * 
	 * @param userCode
	 * @param password
	 * @return
	 */
	public static String encrypt(String userCode, String password) {
		return CIPHER_SERVICE.BASE64ecode(CIPHER_SERVICE.encryptByKey(join(userCode, password).getBytes(), PUB_KEY));
	}

	/**
	 * 解密 “用户编码+密码+时间戳”
	 * 
	 * @param uuid
	 * @return
	 * @throws IOException 
	 */
	public static String decrypt(String uuid) throws IOException {
		return CIPHER_SERVICE.decryptByKey(CIPHER_SERVICE.BASE64decode(uuid), PRI_KEY);
	}

	/**
	 * “用户编码+密码+时间戳” 拼接 --> “用户编码/密码/时间戳”
	 * 
	 * @param userCode
	 * @param password
	 * @return
	 */
	private static String join(String userCode, String password) {
		final StringBuilder uuid = new StringBuilder();
		uuid.append(userCode).append(SPACER).append(password).append(SPACER).append(System.currentTimeMillis());
		return uuid.toString();
	}

	/**
	 * 获得用户编码
	 * 
	 * @param join
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static String getUserCode(String join) throws IllegalArgumentException {
		Matcher matcher = Pattern.compile(REGEX).matcher(join);
		if (matcher.matches()) {
			return matcher.group(USERCODE_GROUP_INDEX);
		}
		throw new IllegalArgumentException("参数不符合拼接规则");
	}

	/**
	 * 获得密码
	 * 
	 * @param join
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static String getPassword(String join) throws IllegalArgumentException {
		Matcher matcher = Pattern.compile(REGEX).matcher(join);
		if (matcher.matches()) {
			return matcher.group(PASSWORD_GROUP_INDEX);
		}
		throw new IllegalArgumentException("参数不符合拼接规则");
	}

	/**
	 * 获得时间戳
	 * 
	 * @param join
	 * @return
	 */
	public static String getTimestamp(String join) throws IllegalArgumentException {
		Matcher matcher = Pattern.compile(REGEX).matcher(join);
		if (matcher.matches()) {
			return matcher.group(TIMESTAMP_GROUP_INDEX);
		}
		throw new IllegalArgumentException("参数不符合拼接规则");
	}
}
