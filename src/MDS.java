import java.util.ArrayList;



public class MDS {
	
	MyData data;
	
	public MDS() {
		data = new MyData();
	}
	
    int insert(long id, double price, long[] description, int size) {
    	if(data.idMap.containsKey(id)){
    		Record r = data.idMap.get(id);
    		r.price = price;
    		if(size != 0){
//    			r.updateDescription(description, size);
    			r.description = description;
    			r.size = size;
    		}
    		return 0;
    	}
    	else{
    		Record r = new Record(id, price, description, size);
    		data.idMap.put(id, r);
    		ArrayList<Record> price_list = data.priceMap.get(price);
    		if(price_list == null){
    			price_list = new ArrayList<Record>();
    			data.priceMap.put(price, price_list);
    		}
    		price_list.add(r);
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
    		data.idMap.remove(id);
    		ArrayList<Record> price_list = data.priceMap.get(r.price);
    		price_list.remove(r);
    		if(price_list.size() == 0)
    			data.priceMap.remove(r.price);
    		
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
}