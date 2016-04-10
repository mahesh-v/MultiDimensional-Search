import java.util.ArrayList;
import java.util.TreeMap;


public class MyData {
	
	TreeMap<Long, Record> idMap;
	TreeMap<Long, TreeMap<Double, ArrayList<Record>>> descripMap;
	TreeMap<Double, ArrayList<Record>> priceMap;
	TreeMap<Long, ArrayList<Record>> ssMap;
	
	public MyData() {
		idMap = new TreeMap<Long, Record>();
		priceMap = new TreeMap<Double, ArrayList<Record>>();
		descripMap = new TreeMap<Long, TreeMap<Double, ArrayList<Record>>>();
		ssMap = new TreeMap<Long, ArrayList<Record>>();
	}
}
