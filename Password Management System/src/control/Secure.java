package security;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;


public class Secure {
	public String hash_SHA1(String body){
		String hashValue = DigestUtils.sha1Hex(body);
		return hashValue;
	}
	public String encrytion(HashMap<String,String> domain_username){
		return null;
	}
	public HashMap<String,String> decrytion(String body){
		return null;
	}
	public String integrityEncrypt(String wholeBody){
		return null;
	}
	public boolean integrityCheck(String key, String wholeBody){
		return false;
	}
	public HashMap<String,String> keyGenerator(HashMap<String,String> domain_username, String masterKey, String username,String pw, int sizeOfPw){
		return null;
	}
}
