/**
 * 
 */
package gagazhn.time;

import java.util.HashMap;

/**
 * @author gagazhn
 *
 */
public class Statistic {
	private static HashMap<String, long[]> interval = new HashMap<String, long[]>();
	private static HashMap<String, long[]> sum = new HashMap<String, long[]>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void start(String tag) {
		long current = System.currentTimeMillis();
		long[] val = get(interval, tag);
		val[0] = current;
	}
	
	public static void note(boolean renew, String tag) {
		long current = System.currentTimeMillis();
		long[] s = get(sum, tag);
		long[] val = get(interval, tag);
		s[0] += current - val[0];
		
		if (renew) {
			val[0] = current;
		} 
	}
	
	private static long[] get(HashMap<String, long[]> map, String tag) {
		long[] val = map.get(tag);
		if (val == null) {
			val = new long[1];
			map.put(tag, val);
		}
		
		return val;
	}
	
	public static long getTime(String tag) {
		return get(sum, tag)[0];
	}
}
