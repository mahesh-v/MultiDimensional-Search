import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;



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
    		addToPriceMap(r);
    		if(size != 0){
    			r.description = description;
    			r.size = size;
    			addToDescriptionMap(r);
    		}
    		return 0;
    	}
    	else{
    		Record r = new Record(id, price, description, size);
    		data.idMap.put(id, r);
    		addToPriceMap(r);
    		addToDescriptionMap(r);
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
    		removeFromOldReferenceLists(r);
    		data.idMap.remove(id);
    		return sumOfDescription(r.description);
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
		Iterator<LinkedList<Record>> outer_iter = references.iterator();
		while(outer_iter.hasNext()){
			LinkedList<Record> reference = outer_iter.next();
			if(reference!=null)
				reference.remove(r);
			outer_iter.remove();
		}
	}

	private void addToDescriptionMap(Record r) {
		for (long l : r.description) {
			TreeMap<Double, LinkedList<Record>> pMap = data.descripMap.get(l);
			LinkedList<Record> list = pMap.get(r.price);
			if(list == null)
				list = new LinkedList<Record>();
			list.add(r);
			pMap.put(r.price, list);
			r.referenceList.add(list);
		}
	}

	private void addToPriceMap(Record r) {
		// TODO Auto-generated method stub
		
	}

    private long sumOfDescription(long[] description) {
		long sum=0;
		for (long l : description) {
			sum+=l;
		}
		return sum;
	}
}