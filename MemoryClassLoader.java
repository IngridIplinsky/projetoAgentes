package povoar;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

public class MemoryClassLoader extends ClassLoader {
  public MemoryClassLoader(ClassLoader pai) {
		super(pai);
	}
	
	public Class<?> loadRemoteClass(String name) {
		ByteArrayOutputStream buffer =  new ByteArrayOutputStream();
		try {
			int[] byteCode = getBytecode(name + ".class");
			for(int value : byteCode) {
				buffer.write(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] data = buffer.toByteArray();
		return defineClass(name, data, 0, data.length);
	}
	
	private int[] getBytecode(String nomeArquivo) {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		try {
			FileReader reader = new FileReader(nomeArquivo);
			int data = reader.read();
			while(data != -1) {
				arrayList.add(new Integer(data));
				data = reader.read();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int[] byteCode = new int[arrayList.size()];
		for (int i = 0; i < arrayList.size(); i++) {
			byteCode[i] = arrayList.get(i).intValue();
		}
		return byteCode;
	}

}
