import java.util.ArrayList;
import java.util.TreeMap;


public class MyData {
	
	TreeMap<Long, Record> idMap;
	TreeMap<Long, ArrayList<Record>> descripMap;
	TreeMap<Double, ArrayList<Record>> priceMap;
	
	public MyData() {
		idMap = new TreeMap<Long, Record>();
		priceMap = new TreeMap<Double, ArrayList<Record>>();
		descripMap = new TreeMap<Long, ArrayList<Record>>();
	}
}
