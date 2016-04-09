import java.util.LinkedList;



public class MDS {
	
	MyData data;
	
	public MDS() {
		data = new MyData();
	}
	
    int insert(long id, double price, long[] description, int size) {
    	if(data.idMap.containsKey(id)){
    		Record r = data.idMap.get(id);
    		removeFromOldReferenceLists(r);
    		r.price = price;
    		if(size != 0){
    			r.description = description;
    			r.size = size;
    		}
    		return 0;
    	}
    	else{
    		Record r = new Record(id, price, description, size);
    		data.idMap.put(id, r);
    		return 1;
    	}
    }

	double find(long id) {
    	Record r = data.idMap.get(id);
    	if(r!=null)
    		return r.price;
    	return 0;
    }

    long delete(long id) {
    	Record r = data.idMap.get(id);
    	if(r!=null){
    		
    	}
    	return 0;
    }

    double findMinPrice(long des) {
	return 0;
    }

    double findMaxPrice(long des) {
	return 0;
    }

    int findPriceRange(long des, double lowPrice, double highPrice) {
	return 0;
    }

    double priceHike(long minid, long maxid, double rate) {
	return 0;
    }

    int range(double lowPrice, double highPrice) {
	return 0;
    }

    int samesame() {
	return 0;
    }
    
    private void removeFromOldReferenceLists(Record r) {
		LinkedList<LinkedList<Record>> references = r.referenceList;
		
	}
}