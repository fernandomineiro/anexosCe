package moduloAnexos.util;

import java.time.LocalDate;

public final class Formatador {

	public static Integer localDateEmInteger(LocalDate date) {
		if (date == null) {
			return 0;
		}
		return date.getYear()*10000 + date.getMonthValue()*100 + date.getDayOfMonth();
	}
	
	public static LocalDate integerEmLocalDate(Integer date) {
		if (date.toString().length() < 8) {
			return null;
		}
		return LocalDate.of(
				Integer.valueOf(date.toString().substring(0, 4)), 
				Integer.valueOf(date.toString().substring(4, 6)), 
				Integer.valueOf(date.toString().substring(6, 8)));
	}
}
