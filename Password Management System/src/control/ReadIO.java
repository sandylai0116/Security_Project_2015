package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ReadIO {
	private String path ="";
	private BufferedReader read;
	private BufferedWriter writer;
	private final int USER_INFO_LENGTH = 80;
	
	ReadIO(String path){
		this.path = path;
	}
	public HashMap<String,String> get() throws IOException{
		HashMap<String,String> user_all = new HashMap<String,String>();
		read = new BufferedReader(new FileReader(path));
		String line = "";
		String integrityKey = "";
		String whole = "";
		boolean last = false;
		String next; 
		line = read.readLine();
		while (!last){
			last = ((next = read.readLine()) == null);
			if (!last){
				String user= line.substring(0, USER_INFO_LENGTH);
				String data = line.substring(USER_INFO_LENGTH);
				user_all.put(user, data);
				whole += line;
				line = next;
			}else{
				integrityKey = line;
			}
		}
		user_all.put("integrity",integrityKey);
		user_all.put("body",whole);
		read.close();
		writer = new BufferedWriter(new FileWriter(path));
		return user_all;
	}
	public void put(String name_pw,String cipherBody) throws IOException{
		String cipher = name_pw.concat(cipherBody).concat("\n");
		writer.write(cipher);
	}
	public void writeClose() throws IOException{
		writer.close();
	}

}
