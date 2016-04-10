
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    		removeFromOldReferenceLists(r);
    		r.price = price;
    		addToPriceMap(r);
    		if(size != 0){
    			if(r.size>7)
    				removeFromSSMap(r);
    			r.updateDescription(description, size);
    			if(size>7)
    				addToSSMap(r);
    		}
    		addToDescriptionMap(r);
    		return 0;
    	}
    	else{
    		Record r = new Record(id, price, description, size);
    		data.idMap.put(id, r);
    		addToPriceMap(r);
    		addToDescriptionMap(r);
    		if(size>7)
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
    		removeFromOldReferenceLists(r);
    		data.idMap.remove(id);
    		if(r.size>7)
    			removeFromSSMap(r);
    		return r.sum;
    	}
    	return 0;
    }

	double findMinPrice(long des) {
		TreeMap<Double, ArrayList<Record>> priceRecordMap = data.descripMap.get(des);
		if(priceRecordMap == null)
			return 0;
		return priceRecordMap.firstKey();
    }

    double findMaxPrice(long des) {
		TreeMap<Double, ArrayList<Record>> priceRecordMap = data.descripMap.get(des);
		if(priceRecordMap == null)
			return 0;
		return priceRecordMap.lastKey();
    }

    int findPriceRange(long des, double lowPrice, double highPrice) {
    	NavigableMap<Double, ArrayList<Record>> map = data.descripMap.get(des).subMap(lowPrice, true, highPrice, true);
    	int count = 0;
    	for (ArrayList<Record> list : map.values()) {
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
		TreeMap<Double, ArrayList<Record>> priceList = data.priceMap;
		return priceList.subMap(lowPrice,true,highPrice,true).size();
    }

    int samesame() {
    	int count = 0;
    	for (ArrayList<Record> list : data.ssMap.values()) {
			if(list.size()>1){
				count+=getSameSameCount(list);
			}
		}
    	return count;
    }

	private void removeFromOldReferenceLists(Record r) {
    	removeFromPriceMap(r);
    	removeFromDescriptionMap(r);
	}

	private void removeFromDescriptionMap(Record r) {
		for (int i = 0; i<r.size;i++) {
    		TreeMap<Double, ArrayList<Record>> descPriceMap = data.descripMap.get( r.description[i]);
    		ArrayList<Record> list = descPriceMap.get(r.price);
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
		ArrayList<Record> priceMapList = data.priceMap.get(r.price);
    	priceMapList.remove(r);
    	if(priceMapList.size() == 0)
    		data.priceMap.remove(r.price);
	}

	private void addToDescriptionMap(Record r) {
		for (long l : r.description) {
			TreeMap<Double, ArrayList<Record>> pMap = data.descripMap.get(l);
			if(pMap == null){
				pMap = new TreeMap<Double, ArrayList<Record>>();
				data.descripMap.put(l, pMap);
			}
			ArrayList<Record> list = pMap.get(r.price);
			if(list == null)
				list = new ArrayList<Record>();
			list.add(r);
			pMap.put(r.price, list);
		}
	}

	private void addToPriceMap(Record r) {
		ArrayList<Record> priceList = data.priceMap.get(r.price);
		if(priceList == null) {
			priceList = new ArrayList<Record>();
			data.priceMap.put(r.price, priceList);
		}
		priceList.add(r);
	}

	private void addToSSMap(Record r) {
		ArrayList<Record> list = data.ssMap.get(r.sum);
		if(list==null){
			list = new ArrayList<Record>();
			data.ssMap.put(r.sum, list);
		}
		list.add(r);
	}

	private void removeFromSSMap(Record r) {
		ArrayList<Record> list = data.ssMap.get(r.sum);
		list.remove(r);
		if(list.size() == 0)
			data.ssMap.remove(r.sum);
	}
    
    private int getSameSameCount(ArrayList<Record> list) {
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