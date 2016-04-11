import java.util.Arrays;
import java.util.LinkedList;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * MultiDimensional Search class to handle multiple operations 
 * on records that are stored and indexed
 * 
 * @author Darshan Narayana Reddy and Mahesh Venkateswaran
 *
 */
public class MDS {
	
	MyData data;
	private static final double epsilon = 0.000001;
	
	public MDS() {
		data = new MyData();
	}
	
    /**
     * Insert a new record, or update record if id exists
     * @param id
     * @param price
     * @param description
     * @param size
     * @return 0 if record already exists, else 1.
     */
    int insert(long id, double price, long[] description, int size) {
    	if(data.idMap.containsKey(id)){
    		Record r = data.idMap.get(id);
    		r.price = price;
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
    		addToDescriptionMap(r);
    		return 1;
    	}
    }

	/**
	 * Locate the item with given id and return its price
	 * @param id
	 * @return Price of item with given id
	 */
	double find(long id) {
    	Record r = data.idMap.get(id);
    	if(r!=null)
    		return r.price;
    	return 0;
    }

    /**
     * Deletes the record with the specified id
     * 
     * @param id
     * @return Sum of item's description items
     */
    long delete(long id) {
    	Record r = data.idMap.get(id);
    	if(r!=null){
    		removeFromDescriptionMap(r);
    		data.idMap.remove(id);
    		return r.sum;
    	}
    	return 0;
    }

	/**
	 * Finds the item with minimum price, 
	 * which contains des as a part of its description
	 * @param des
	 * @return The price of that item
	 */
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

	/**
	 * Finds the item with maximum price, 
	 * which contains des as a part of its description
	 * @param des
	 * @return The price of that item
	 */
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

    /**
	 * Finds the items with price in the given price range (inclusive), 
	 * and contains des as a part of its description
	 * @param des
	 * @param lowPrice
	 * @param highPrice
	 * @return Number of items which satisfy above criteria
	 */
    int findPriceRange(long des, double lowPrice, double highPrice) {
    	LinkedList<Record> descList = data.descripMap.get(des);
    	int count = 0;
    	for (Record record : descList) {
			if(record.price<=highPrice && record.price>=lowPrice)
				count++;
		}
    	return count;
    }

	/**
	 * Increases the price of items in the range of minid to max id by rate%
	 * 
	 * @param minid
	 * @param maxid
	 * @param rate
	 * @return Total increase in price of all items
	 */
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

	/**
	 * Finds items in the given price range 
	 * and returns the number of such items
	 * @param lowPrice
	 * @param highPrice
	 * @return Count of items in price range
	 */
	int range(double lowPrice, double highPrice) {
		int count=0;
		for (Record r : data.idMap.values()) {
			if(r.price<=highPrice && r.price>=lowPrice)
				count++;
		}
		return count;
    }

    /**
     * Finds the number of records with identical items in their description
     * 
     * @return Count of identical records
     */
    int samesame() {
    	TreeMap<Record, Integer> ssMap = new TreeMap<Record, Integer>(new RecordComparator());
    	for (Record r : data.idMap.values()) {
			addToSameSameMap(r, ssMap);
		}
    	int count = 0;
    	for (Integer c : ssMap.values()) {
    		if(c>1)
    			count+=c;
		}
    	return count;
    }

    //helper method to remove a record from the description map at all description entries
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

	//helper method to add a record to the description map at all description entries
	private void addToDescriptionMap(Record r) {
		for (long l : r.description) {
			LinkedList<Record> list = data.descripMap.get(l);
			if(list == null)
				list = new LinkedList<Record>();
			list.add(r);
			data.descripMap.put(l, list);
		}
	}

	//adds the items to the same same map after sorting its description
	private void addToSameSameMap(Record r, TreeMap<Record, Integer> ssMap) {
		if(r.size<8)
			return;
		Arrays.sort(r.description);
		Integer oc = ssMap.get(r);
		if(oc==null)
			ssMap.put(r, 1);
		else
			ssMap.put(r, oc+1);
	}
}