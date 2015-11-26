package control;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

import model.SubAccount;
import model.User;


public class Secure {
	public static String salt = "s>uzgQ}#,NR6,(T&.asf";
	private static String IV = "AAAAAAAAAAAAAAAA";
	
	public String hash_SHA1(String body){
		String hashValue = DigestUtils.sha1Hex(body + salt);
		return hashValue;
	}
	
	public String encryptionSubAccount(String username, String pw, List<SubAccount> subAccounArraytList){	
		//Serialization
		//List<SubAccount> subAccounArraytList = user.getSubAccount();
		byte[] bytesOfSubAccount = null;
		String serializedString = null;
		try {
			bytesOfSubAccount = toByteArray(subAccounArraytList);
			serializedString = new String(bytesOfSubAccount, "ISO-8859-1");
		} catch (Exception e) {
			System.out.println("Error in encrypting subAccount!");
			return null;
		}		
		//encryption	AES-128bits
		String output = null;
		String encryptionKey = DigestUtils.sha512Hex(username + pw + salt);
		encryptionKey = passwordGeneratorBasedOnHashString(encryptionKey,16);
		System.out.println(encryptionKey.getBytes());
		try {
			byte[] cipher = encrypt(serializedString, encryptionKey);
			output = new String(cipher, "ISO-8859-1");
		} catch (Exception e) {
			System.out.println("Error in encrypting subAccount!");
			e.printStackTrace();
			return null;
		}
		return output;
	}
	
	public List<SubAccount> decryptionSubAccount(String userName, String password, String cipher) {	
		//decryption	AES-128bits
		String encryptionKey = DigestUtils.sha512Hex(userName + password + salt);
		encryptionKey = passwordGeneratorBasedOnHashString(encryptionKey,16);
		String decrypted = null;
		try {
			byte[] cipherinBytes = cipher.getBytes("ISO-8859-1");
			decrypted = decrypt(cipherinBytes, encryptionKey);
			if (decrypted == null)  throw new Exception();
		} catch (Exception e1) {
			System.out.println("Error in decrypting subAccount!");
			return null;
		}
		//deserialization	
		byte[] bytesOfSubAccount = null;
		List<SubAccount> subAccount = new ArrayList<SubAccount>();
		try {
			bytesOfSubAccount = decrypted.getBytes("ISO-8859-1");
			subAccount = (List<SubAccount>) toObject(bytesOfSubAccount);
		} catch (Exception e) {
			System.out.println("Error in decrypting subAccount!");
			return null;
		}
		return subAccount;
	}
	
	public String integrityEncrypt(String fileContent){
		return hash_SHA1(fileContent);
	}
	public boolean integrityCheck(String key, String fileContent){
		if(key.equals(hash_SHA1(fileContent))) return true;
		else return false;
	}
	public List<SubAccount> keyGenerator(String username,String password, List<SubAccount> subAccount, String masterKey, int lengthOfPassword){
		for(int i=0;i<subAccount.size();i++){
			String hash = DigestUtils.sha512Hex(username+password+masterKey+subAccount.get(i).getDomain()+subAccount.get(i).getUsername() + salt);
			subAccount.get(i).setPassword(passwordGeneratorBasedOnHashString(hash,lengthOfPassword));
		}
		return subAccount;
	}
	
	private static byte[] toByteArray(Object obj) throws IOException {
		byte[] bytes = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return bytes;
	}

	private static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
        return obj;
    }
	
	private static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
	    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
	    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
	    cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
	    return cipher.doFinal(plainText.getBytes("UTF-8"));
	  }

	private static String decrypt(byte[] cipherText, String encryptionKey) throws Exception{
	    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
	    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
	    cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
	    return new String(cipher.doFinal(cipherText),"UTF-8");
	}
	
	private static String passwordGeneratorBasedOnHashString(String hash,int lengthOfPassword){
		if(hash.length()/2<lengthOfPassword){
			System.out.println("Length of password is too large!\n");
			return null;
		}
		String output = "";
		for(int i = 0;i<hash.length() && output.length() <lengthOfPassword;i=i+2){
			if(i+2>hash.length()) break;
			String partOfHex = hash.substring(i,i+2);
			int value = Integer.parseInt(partOfHex, 16); 
			output += mapAlphaNum(value);
		}	
		return output;
	}
	
	private static String mapAlphaNum(int input) {
		String alphanum =
			"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~!@#$%^&?*()_+-=,./|{}[]'`<>\\";	//size = 91
		int position = input%alphanum.length();
		return alphanum.substring(position,position+1);
	}
}
