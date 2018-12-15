package com.cenfotec.examen.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FechaHelper {
	public static String getFechaVuelo(Date fecha) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();

		c.setTime(fecha);
		c.add(Calendar.DATE, 1);

		return dateFormat.format(c.getTime());
	}
}