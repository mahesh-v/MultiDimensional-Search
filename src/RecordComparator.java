import java.util.Comparator;


public class RecordComparator implements Comparator<Record> {

	@Override
	public int compare(Record r1, Record r2) {
		long[] l1 = r1.description;
		long[] l2 = r2.description;
		if(l1.length != l2.length)
			return l1.length - l2.length;
		if(r1.sum!=r2.sum)
			return (int) (r1.sum-r2.sum);
		for (int i = 0; i < l2.length; i++) {
			if(l1[i] == l2[i])
				continue;
			return (int) (l1[i] - l2[i]);
		}
		return 0;
	}

}
