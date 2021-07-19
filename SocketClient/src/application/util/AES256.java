package application.util;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256 {
	public static String alg = "AES/CBC/PKCS5Padding";
	private final String key = numberGen(32, 1);
	private final String iv = key.substring(0, 16);

	public String encrypt(String text) throws Exception {
		Cipher cipher = Cipher.getInstance(alg);
		SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), "AES");
		IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

		byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
		return java.util.Base64.getEncoder().encodeToString(encrypted);
	}

	public String decrypt(String cipherText) throws Exception {
		Cipher cipher = Cipher.getInstance(alg);
		SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), "AES");
		IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

		byte[] decodedBytes = java.util.Base64.getDecoder().decode(cipherText);
		byte[] decrypted = cipher.doFinal(decodedBytes);
		return new String(decrypted, "UTF-8");
	}

	public static String numberGen(int len, int dupCd) {

		Random rand = new Random();
		String numStr = "";

		for (int i = 0; i < len; i++) {

			String ran = Integer.toString(rand.nextInt(10));

			if (dupCd == 1) {
				// 중복 허용시 numStr에 append
				numStr += ran;
			} else if (dupCd == 2) {
				// 중복을 허용하지 않을시 중복된 값 검사
				if (!numStr.contains(ran)) {
					// 중복된 값이 없으면 numStr에 append
					numStr += ran;
				} else {
					// 생성된 난수가 중복되면 다시 생성
					i -= 1;
				}
			}
		}
		return numStr;
	}

	public static void main(String[] args) throws Exception {

		AES256 aes256 = new AES256();
		String text = "981103";
		String cipherText = aes256.encrypt(text);
		System.out.println("평문: " + text);
		System.out.println("암호화: " + cipherText);
		System.out.println("복호화: " + aes256.decrypt(cipherText));
	}
}