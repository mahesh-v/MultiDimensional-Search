import java.util.LinkedList;


public class Record {
	long id;
	double price;
	long[] description;
	int size;
	LinkedList<LinkedList<Record>> referenceList;
	
	public Record(long id, double price, long[] description, int size) {
		this.id = id;
		this.price = price;
		this.description = new long[size];
		for (int i = 0; i < size; i++) {
			this.description[i] = description[i];
		}
		this.size = size;
		referenceList = new LinkedList<LinkedList<Record>>();
	}
}
