
import java.util.LinkedList;
import java.util.NavigableMap;
import java.util.TreeMap;



public class MDS {
	
	MyData data;
	private static final double epsilon = 0.000001;
	
	public MDS() {
		data = new MyData();
	}
	
    int insert(long id, double price, long[] description, int size) {
    	if(data.idMap.containsKey(id)){
    		Record r = data.idMap.get(id);
    		removeFromPriceMap(r);
    		r.price = price;
    		addToPriceMap(r);
    		if(size != 0){
    			removeFromDescriptionMap(r);
    			r.updateDescription(description, size);
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
		TreeMap<Double, LinkedList<Record>> priceRecordMap = data.descripMap.get(des);
		return priceRecordMap.firstKey();
    }

    double findMaxPrice(long des) {
		TreeMap<Double, LinkedList<Record>> priceRecordMap = data.descripMap.get(des);
		return priceRecordMap.lastKey();
    }

    int findPriceRange(long des, double lowPrice, double highPrice) {
    	NavigableMap<Double, LinkedList<Record>> map = data.descripMap.get(des).subMap(lowPrice, true, highPrice, true);
    	int count = 0;
    	for (LinkedList<Record> list : map.values()) {
			count+=list.size();
		}
    	return count;
    }

    double priceHike(long minid, long maxid, double rate) {
    	double sum = 0;
    	NavigableMap<Long, Record> map = data.idMap.subMap(minid, true, maxid, true);
    	for (Record r : map.values()) {
    		removeFromOldReferenceLists(r);
			double value = r.price + (r.price * (rate/100));
			value = Math.floor((value+epsilon) * 100) / 100;
			sum+= (value - r.price);
			r.price = value;
			addToPriceMap(r);
			addToDescriptionMap(r);
		}
    	return sum;
    }

	int range(double lowPrice, double highPrice) {
		TreeMap<Double, LinkedList<Record>> priceList = data.priceMap;
		return priceList.subMap(lowPrice,true,highPrice,true).size();
    }

    int samesame() {
	return 0;
    }
    
    private void removeFromOldReferenceLists(Record r) {
    	removeFromPriceMap(r);
    	removeFromDescriptionMap(r);
	}

	private void removeFromDescriptionMap(Record r) {
		for (int i = 0; i<r.size;i++) {
    		TreeMap<Double, LinkedList<Record>> descPriceMap = data.descripMap.get( r.description[i]);
    		LinkedList<Record> list = descPriceMap.get(r.price);
    		if(list== null)
    			continue;
    		list.remove(r);
    		if(list.size() == 0)
    			descPriceMap.remove(r.price);
    		if(descPriceMap.entrySet().size() == 0)
    			data.descripMap.remove(r.description[i]);
		}
	}

	private void removeFromPriceMap(Record r) {
		LinkedList<Record> priceMapList = data.priceMap.get(r.price);
    	priceMapList.remove(r);
    	if(priceMapList.size() == 0)
    		data.priceMap.remove(r.price);
	}

	private void addToDescriptionMap(Record r) {
		for (long l : r.description) {
			TreeMap<Double, LinkedList<Record>> pMap = data.descripMap.get(l);
			if(pMap == null){
				pMap = new TreeMap<Double, LinkedList<Record>>();
				data.descripMap.put(l, pMap);
			}
			LinkedList<Record> list = pMap.get(r.price);
			if(list == null)
				list = new LinkedList<Record>();
			list.add(r);
			pMap.put(r.price, list);
		}
	}

	private void addToPriceMap(Record r) {
		LinkedList<Record> priceList = data.priceMap.get(r.price);
		if(priceList == null) {
			priceList = new LinkedList<Record>();
			data.priceMap.put(r.price, priceList);
		}
		priceList.addFirst(r);
	}

    private long sumOfDescription(long[] description) {
		long sum=0;
		for (long l : description) {
			sum+=l;
		}
		return sum;
	}
}