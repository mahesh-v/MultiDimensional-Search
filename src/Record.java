import java.util.ArrayList;


public class Record {
	long id;
	double price;
	long[] description;
	int size;
	ArrayList<Record> descMapList;
	ArrayList<Record> priceMapList;
	
	public Record(long id, double price, long[] description, int size) {
		this.id = id;
		this.price = price;
		this.description = description;
		this.size = size;
	}
//
//	public void convertDesc(long[] description, int size) {
//		boolean overflowUpdated = false;
//		for (int i = 0; i < size; i++) {
//			this.description+=description[i];
//			if(!overflowUpdated && this.description < 0){
//				overflows++;
//				overflowUpdated = true;
//			}
//			if(this.description>0)
//				overflowUpdated = false;
//		}
//	}
//
//	public void updateDescription(long[] description, int size) {
//		this.size = size;
//		this.overflows=0;
//		convertDesc(description, size);
//	}
}
