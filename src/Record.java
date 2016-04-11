


/**
 * Record object to store every individual record containing 
 * long id, double price and long[] description
 * 
 * @author Darshan Narayana Reddy and Mahesh Venkateswaran
 *
 */
public class Record {
	long id;
	double price;
	long[] description;
	long sum;
	int size;
	
	public Record(long id, double price, long[] description, int size) {
		this.id = id;
		this.price = price;
		updateDescription(description, size);
	}

	public void updateDescription(long[] description, int size) {
		this.description = new long[size];
		this.sum =0;
		for (int i = 0; i < size; i++) {
			sum+=description[i];
			this.description[i] = description[i];
		}
		this.size = size;
	}
}
