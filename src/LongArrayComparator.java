import java.util.Comparator;


public class LongArrayComparator implements Comparator<Long[]> {

	@Override
	public int compare(Long[] l1, Long[] l2) {
		if(l1.length != l2.length)
			return l1.length - l2.length;
		for (int i = 0; i < l2.length; i++) {
			if(l1[i] == l2[i])
				continue;
			return (int) (l1[i] - l2[i]);
		}
		return 0;
	}

}
