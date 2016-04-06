import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;


public class MyData {
	
	TreeMap<Long, Record> idMap;
	HashMap<Long, TreeMap<Double, Record>> descripMap;
	TreeMap<Double, ArrayList<Record>> priceMap;
	
	public MyData() {
		idMap = new TreeMap<Long, Record>();
		priceMap = new TreeMap<Double, ArrayList<Record>>();
		descripMap = new HashMap<Long, TreeMap<Double,Record>>();
	}
}
