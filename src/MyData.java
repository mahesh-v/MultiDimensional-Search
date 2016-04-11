import java.util.LinkedList;
import java.util.TreeMap;


/**
 * Class the store the indexes that are used in MDS
 * 
 * @author Darshan Narayana Reddy and Mahesh Venkateswaran
 *
 */
public class MyData {
	
	TreeMap<Long, Record> idMap;
	TreeMap<Long, LinkedList<Record>> descripMap;
	
	public MyData() {
		idMap = new TreeMap<Long, Record>();
		descripMap = new TreeMap<Long, LinkedList<Record>>();
	}
}
