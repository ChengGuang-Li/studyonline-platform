package org.studyonline.base.utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * <P>
 * ID number tools
 * </p>
 *
 */
public class IDCardUtil {

	/**
	 * Get date of birth, gender, age through ID number
	 * @param idNumber
	 * @return Returned date of birth format: 1990-01-01 Gender format: F-female, M-male
	 */
	public static Map<String, String> getInfo(String idNumber) {
		String birthday = "";
		String age = "";
		String gender = "";

		int year = Calendar.getInstance().get(Calendar.YEAR);
		char[] number = idNumber.toCharArray();
		if (idNumber.length() == 15) {
			birthday = "19" + idNumber.substring(6, 8) + "-" + idNumber.substring(8, 10) + "-" + idNumber
					.substring(10, 12);
			gender =
					Integer.parseInt(idNumber.substring(idNumber.length() - 3, idNumber.length())) % 2 == 0 ? "F" : "M";
			age = (year - Integer.parseInt("19" + idNumber.substring(6, 8))) + "";
		} else if (idNumber.length() == 18) {
			birthday = idNumber.substring(6, 10) + "-" + idNumber.substring(10, 12) + "-" + idNumber.substring(12, 14);
			gender = Integer.parseInt(idNumber.substring(idNumber.length() - 4, idNumber.length() - 1)) % 2 == 0 ?
					"女" :
					"男";
			age = (year - Integer.parseInt(idNumber.substring(6, 10))) + "";
		}
		Map<String, String> map = new HashMap();
		map.put("birthday", birthday);
		map.put("age", age);
		map.put("gender", gender);
		return map;
	}

	public static void main(String[] args) {
		System.out.println(getInfo("658182198109222913"));
	}
}
