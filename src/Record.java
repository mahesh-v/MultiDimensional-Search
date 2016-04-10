

public class Record {
	long id;
	double price;
	long[] description;
	int size;
	
	public Record(long id, double price, long[] description, int size) {
		this.id = id;
		this.price = price;
		updateDescription(description, size);
		this.size = size;
	}

	public void updateDescription(long[] description, int size) {
		this.description = new long[size];
		for (int i = 0; i < size; i++) {
			this.description[i] = description[i];
		}
	}
}
