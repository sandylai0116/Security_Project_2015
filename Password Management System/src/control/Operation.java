package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.SubAccount;

public class Operation {
	private Secure secure;
	private ReadIO readIO;
	private HashMap<String,String> user;
	private String username;
	private String pw;
	private String cipher;
	public Operation(String path){
		readIO = new ReadIO(path);
		secure = new Secure();
		this.username = "";
		this.pw = "";
		this.cipher = "";
	}
	public boolean init(){
		try {
			user = readIO.get();
			if (user != null){
				if (!secure.integrityCheck(user.get("integrity"),user.get("body"))){
					return false;
				}
				user.remove("integrity");
				user.remove("body");	//remove useless dump data
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	public  boolean userAuthenticate(String username, String pw){
		String h1 = secure.hash_SHA1(username);
		String h2 = secure.hash_SHA1(pw);
		if (user != null)
			if (user.containsKey(h1.concat(h2))){
				this.username = username;
				this.pw = pw;
				this.cipher = user.get(h1.concat(h2));
				return true;
			}
			else
				return false;
		else
			return false;
	}
	public boolean isUsernameValid(String username){
		String h1 = secure.hash_SHA1(username);
		if (user!=null)
			if (user.containsKey(h1))
				return false;
			else
				return true;
		else 
			return true;
	}
	public void register(String username, String pw){
		this.username = username;
		this.pw = pw;
	}
	public List<SubAccount> displayDomain(){
		if (this.cipher!="")
			return secure.decryptionSubAccount(this.username,this.pw,this.cipher);
		else return null;
	}
	public List<SubAccount> keyGen(List<SubAccount> domain, String masterKey, int LengthOfPw){
		return secure.keyGenerator(this.username, this.pw, domain,masterKey, LengthOfPw);
	}
	public void saveChanges(List<SubAccount> domain) throws IOException{
		readIO.put_start();
		String h1 = secure.hash_SHA1(this.username);
		String h2 = secure.hash_SHA1(this.pw);
		String whole = "";
		if(user!=null){
			if (user.containsKey(h1.concat(h2)))
				user.remove(h1.concat(h2));// null when new user
			for (Map.Entry<String, String> each : user.entrySet())
			{
				readIO.put(each.getKey(),each.getValue());
				whole = whole.concat(each.getKey().concat(each.getValue()));
			}
		}
		String cipher = secure.encryptionSubAccount(this.username,this.pw,domain);
		whole = whole.concat(h1.concat(h2).concat(cipher));
		readIO.put(h1.concat(h2), cipher);
		readIO.put(secure.integrityEncrypt(whole),"");
		readIO.writeClose();
	}
	public String getUsername(){
		return this.username;
	}
}
