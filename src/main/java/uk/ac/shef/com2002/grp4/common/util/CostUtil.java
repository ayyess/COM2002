package uk.ac.shef.com2002.grp4.common.util;

public class CostUtil {

	/**
	 * This converts the cost of a treatment to a string.
	 * @param cost - the cost as an integer
	 * @return a String representation of the cost as £0.00
	 */
	public static String costToDecimalString(int cost) {
		String val = String.valueOf(cost / 100.0);
		int rawLength = String.valueOf(cost).length();
		String s;
		if (cost < 100) {
			if (val.length() > rawLength+1) {
				s = val.substring(0, val.length()-(rawLength+1));
			} else {
				s = val;
			}
		} else {
			s = val;
		}
		if (!s.equals("0")) {
			if (s.indexOf(".") == s.length()-2) s = s + "0";
		}
		return '\u00A3' + s;
	}

}
