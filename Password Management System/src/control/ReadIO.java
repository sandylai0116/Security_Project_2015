package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ReadIO {
	private String path ="";
	private BufferedReader read;
	private BufferedWriter writer;
	private File file;
	private final int USER_INFO_LENGTH = 80;
	
	ReadIO(String path){
		this.path = path;
		this.file = new File(path);
	}
	public HashMap<String,String> get() throws IOException{
		HashMap<String,String> user_all = new HashMap<String,String>();
		String line = "";
		String integrityKey = "";
		String whole = "";
		boolean last = false;
		String next;
		if (file.exists()){
			read = new BufferedReader(new FileReader(path));
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
			return user_all;
		}else{
			writer = new BufferedWriter(new FileWriter(path));
			writer.close();
			return null;
		}
		
	}
	public void put_start() throws IOException{
		writer = new BufferedWriter(new FileWriter(path));
	}
	public void put(String name_pw,String cipherBody) throws IOException{
		String cipher = name_pw.concat(cipherBody).concat("\n");
		writer.write(cipher);
	}
	public void writeClose() throws IOException{
		writer.close();
	}

}
