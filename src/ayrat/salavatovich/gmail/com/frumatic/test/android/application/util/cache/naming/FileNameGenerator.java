package ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.cache.naming;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.HexUtils;

public class FileNameGenerator {

	private static final String HASH_ALGORITHM = "MD5";

	public static String generate(String key) {
		byte[] md5 = getMD5(key.getBytes());
		return HexUtils.bytesToHexString(md5);
	}

	private static byte[] getMD5(byte[] data) {
		byte[] hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(data);
			hash = digest.digest();
		} catch (NoSuchAlgorithmException e) {
			hash = String.valueOf(data.hashCode()).getBytes();
		}
		return hash;
	}

}
