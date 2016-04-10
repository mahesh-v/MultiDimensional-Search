
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
    			if(r.size>7)
    				removeFromSSMap(r);
    			removeFromDescriptionMap(r);
    			r.updateDescription(description, size);
    			addToDescriptionMap(r);
    			if(size>7)
    				addToSSMap(r);
    		}
    		return 0;
    	}
    	else{
    		Record r = new Record(id, price, description, size);
    		data.idMap.put(id, r);
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
    		removeFromDescriptionMap(r);
    		data.idMap.remove(id);
    		if(r.size>7)
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
    	for (LinkedList<Record> list : data.ssMap.values()) {
			if(list.size()>1){
				count+=getSameSameCount(list);
			}
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
		LinkedList<Record> list = data.ssMap.get(r.sum);
		if(list==null){
			list = new LinkedList<>();
			data.ssMap.put(r.sum, list);
		}
		list.add(r);
	}

	private void removeFromSSMap(Record r) {
		LinkedList<Record> list = data.ssMap.get(r.sum);
		list.remove(r);
		if(list.size() == 0)
			data.ssMap.remove(r.sum);
	}
    
    private int getSameSameCount(LinkedList<Record> list) {
		int count = 0;
		for (Record record : list) {
			Arrays.sort(record.description);
		}
		boolean flag=true;
		Collections.sort(list, new RecordComparator());
		for (int i = 1; i < list.size(); i++) {
			if(isSortedRecordsEqual(list.get(i-1), list.get(i))){
				if(flag){
					count+=2;
					flag=false;
				}
				else
					count++;
			}
			else{
				flag=true;
			}
		}
//		if(count>0)
//			return count+1;
		return count;
	}

	private boolean isSortedRecordsEqual(Record r1, Record r2) {
		long[] l1 = r1.description;
		long[] l2 = r2.description;
		if(l1.length != l2.length)
			return false;
		for (int i = 0; i < l2.length; i++) {
			if(l1[i] == l2[i])
				continue;
			return false;
		}
		return true;
	}
}