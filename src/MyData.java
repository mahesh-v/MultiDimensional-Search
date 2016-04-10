import java.util.ArrayList;
import java.util.TreeMap;


public class MyData {
	
	TreeMap<Long, Record> idMap;
	TreeMap<Long, ArrayList<Record>> descripMap;
	TreeMap<Long, ArrayList<Record>> ssMap;
	
	public MyData() {
		idMap = new TreeMap<Long, Record>();
		descripMap = new TreeMap<Long, ArrayList<Record>>();
		ssMap = new TreeMap<Long, ArrayList<Record>>();
	}
}
