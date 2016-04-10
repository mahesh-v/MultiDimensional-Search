import java.util.LinkedList;
import java.util.TreeMap;


public class MyData {
	
	TreeMap<Long, Record> idMap;
	TreeMap<Long, LinkedList<Record>> descripMap;
	TreeMap<Long, LinkedList<Record>> ssMap;
	
	public MyData() {
		idMap = new TreeMap<Long, Record>();
		descripMap = new TreeMap<Long, LinkedList<Record>>();
		ssMap = new TreeMap<Long, LinkedList<Record>>();
	}
}
