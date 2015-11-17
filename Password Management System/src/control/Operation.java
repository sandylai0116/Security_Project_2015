package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Operation {
	private Secure secure;
	private ReadIO readIO;
	private HashMap<String,String> user;
	private String username;
	private String pw;
	Operation(String path){
		readIO = new ReadIO(path);
		secure = new Secure();
		this.username = "";
		this.pw = "";
	}
	public boolean init(){
		try {
			user = readIO.get();
			if (!secure.integrityCheck(user.get("integrity"),user.get("body"))){
				return false;
			}
			user.remove("integrity");
			user.remove("body");	//remove useless dump data
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		return true;
	}
	public String userAuthenticate(String username, String pw){
		String h1 = secure.hash_SHA1(username);
		String h2 = secure.hash_SHA1(pw);
		if (user.containsKey(h1.concat(h2))){
			this.username = username;
			this.pw = pw;
			return user.get(h1.concat(h2));
		}
		else
			return null;
	}
	public boolean isUsernameValid(String username){
		String h1 = secure.hash_SHA1(username);
		if (user.containsKey(h1))
			return false;
		else
			return true;
	}
	//not yet defined
	public ArrayList<String> displayDomain(String body){
		secure.decrytion(body);
		return null;
	}
	public ArrayList<String> keyGen(ArrayList<String> domain, String masterKey){
		ArrayList<String> key = new ArrayList<String>();
		for (String eachDomain: domain){
			key.add(secure.keyGenerator(eachDomain, masterKey, this.username.concat(this.pw)));
		}
		return key;
	}
	public void saveChanges(ArrayList<String> domain) throws IOException{
		String h1 = secure.hash_SHA1(this.username);
		String h2 = secure.hash_SHA1(this.pw);
		String whole = "";
		user.remove(h1.concat(h2));
		for (Map.Entry<String, String> each : user.entrySet())
		{
			readIO.put(each.getKey(),each.getValue());
			whole = whole.concat(each.getKey().concat(each.getValue()));
		}
		String cipher = secure.encrytion(domain);
		whole = whole.concat(h1.concat(h2).concat(cipher));
		readIO.put(h1.concat(h2), cipher);
		readIO.put(secure.integrityEncrypt(whole),"");
		readIO.writeClose();
	}
}
