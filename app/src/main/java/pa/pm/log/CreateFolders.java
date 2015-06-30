package pa.pm.log;
import java.io.File;

import android.os.Environment;
public class CreateFolders{
	public void createFolder(){
		File folder3 = new File(Environment.getExternalStorageDirectory()
				.toString() + "/cartaoprograma");
		folder3.mkdirs();
		
	}
	public void delete() {
		File folder2 = new File(Environment.getExternalStorageDirectory()
				.toString() + "/cartaoprograma");
		for (File f : folder2.listFiles()) {

			if (f.isFile()) {
				f.delete();
			}
		}
	}
	


}