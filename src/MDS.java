
import java.util.*;


public class MDS {
	
	MyData data;
	private static final double epsilon = 0.000001;
	
	public MDS() {
		data = new MyData();
	}
	
    int insert(long id, double price, long[] description, int size) {
    	if(data.idMap.containsKey(id)){
    		Record r = data.idMap.get(id);
    		r.price = price;
    		if(size != 0){
    			removeFromSSMap(r);
    			removeFromDescriptionMap(r);
    			r.updateDescription(description, size);
    			addToDescriptionMap(r);
    			addToSSMap(r);
    		}
    		return 0;
    	}
    	else{
    		Record r = new Record(id, price, description, size);
    		data.idMap.put(id, r);
    		addToDescriptionMap(r);
    		addToSSMap(r);
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
    		removeFromDescriptionMap(r);
    		data.idMap.remove(id);
    		removeFromSSMap(r);
    		return r.sum;
    	}
    	return 0;
    }

	double findMinPrice(long des) {
		LinkedList<Record> descList = data.descripMap.get(des);
		if(descList == null || descList.size() == 0 )
			return 0;
		double minPrice=descList.get(0).price;
		for (Record record : descList) {
			if(record.price < minPrice)
				minPrice = record.price;
		}
		return minPrice;
    }

    double findMaxPrice(long des) {
    	LinkedList<Record> descList = data.descripMap.get(des);
		if(descList == null || descList.size() == 0 )
			return 0;
		double maxPrice=descList.get(0).price;
		for (Record record : descList) {
			if(record.price > maxPrice)
				maxPrice = record.price;
		}
		return maxPrice;
    }

    int findPriceRange(long des, double lowPrice, double highPrice) {
    	LinkedList<Record> descList = data.descripMap.get(des);
    	int count = 0;
    	for (Record record : descList) {
			if(record.price<=highPrice && record.price>=lowPrice)
				count++;
		}
    	return count;
    }

	double priceHike(long minid, long maxid, double rate) {
    	double sum = 0;
    	NavigableMap<Long, Record> map = data.idMap.subMap(minid, true, maxid, true);
    	for (Record r : map.values()) {
			double value = r.price + (r.price * (rate/100));
			value = Math.floor((value+epsilon) * 100) / 100;
			sum+= (value - r.price);
			r.price = value;
		}
    	return sum;
    }

	int range(double lowPrice, double highPrice) {
		int count=0;
		for (Record r : data.idMap.values()) {
			if(r.price<=highPrice && r.price>=lowPrice)
				count++;
		}
		return count;
    }

    int samesame() {
    	int count = 0;
    	for (Integer c : data.ssMap.values()) {
    		if(c>1)
    			count+=c;
		}
    	return count;
    }

	private void removeFromDescriptionMap(Record r) {
		for (int i = 0; i<r.size;i++) {
    		LinkedList<Record> list = data.descripMap.get( r.description[i]);
    		if(list== null)
    			continue;
    		list.remove(r);
    		if(list.size() == 0)
    			data.descripMap.remove(r.description[i]);
		}
	}

	private void addToDescriptionMap(Record r) {
		for (long l : r.description) {
			LinkedList<Record> list = data.descripMap.get(l);
			if(list == null)
				list = new LinkedList<Record>();
			list.add(r);
			data.descripMap.put(l, list);
		}
	}

	private void addToSSMap(Record r) {
		if(r.size<8)
			return;
		Integer oc = data.ssMap.get(r);
		if(oc==null)
			data.ssMap.put(r, 1);
		else
			data.ssMap.put(r, oc+1);
	}

	private void removeFromSSMap(Record r) {
		if(r.size<8)
			return;
		Integer oc = data.ssMap.get(r);
		if(oc==1)
			data.ssMap.remove(r);
		else
			data.ssMap.put(r, oc-1);
	}
}