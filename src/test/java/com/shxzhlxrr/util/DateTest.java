package com.shxzhlxrr.util;

import java.util.Date;

public class DateTest {

	public static boolean dateCompare(Date startDate, Date endDate, Date nowDate) {
		if (nowDate.before(endDate) && nowDate.after(startDate)) {
			return true;
		}
		return false;

	}

}
