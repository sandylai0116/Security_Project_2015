package control;

import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;


public class Secure {
	public String hash_SHA1(String body){
		String hashValue = DigestUtils.sha1Hex(body);
		return hashValue;
	}
	public String encrytion(ArrayList<String> domain){
		return null;
	}
	public String decrytion(String body){
		return null;
	}
	public String integrityEncrypt(String body){
		return null;
	}
	public boolean integrityCheck(String key, String body){
		return false;
	}
	public String keyGenerator(String body, String masterKey, String userInfo){
		return null;
	}
}
