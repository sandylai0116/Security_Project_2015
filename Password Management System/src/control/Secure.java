package control;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import model.SubAccount;
import model.User;


public class Secure {
	public String hash_SHA1(String body){
		String hashValue = DigestUtils.sha1Hex(body);
		return hashValue;
	}
	public String encrytion(List<SubAccount> subAccount){
		return null;
	}
	public List<SubAccount> decrytion(String cipher){
		return null;
	}
	
	public String integrityEncrypt(String fileContent){
		return null;
	}
	public boolean integrityCheck(String key, String fileContent){
		return false;
	}
	public List<SubAccount> keyGenerator(User user, String masterKey, int lengthOfPassword){
		return null;
	}
}
