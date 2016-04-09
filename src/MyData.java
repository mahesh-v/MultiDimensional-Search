import java.util.LinkedList;
import java.util.TreeMap;


public class MyData {
	
	TreeMap<Long, Record> idMap;
	TreeMap<Long, LinkedList<Record>> descripMap;
	TreeMap<Double, LinkedList<Record>> priceMap;
	
	public MyData() {
		idMap = new TreeMap<Long, Record>();
		priceMap = new TreeMap<Double, LinkedList<Record>>();
		descripMap = new TreeMap<Long, LinkedList<Record>>();
	}
}
