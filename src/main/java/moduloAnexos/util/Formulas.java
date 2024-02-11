package moduloAnexos.util;

import org.springframework.stereotype.Component;

@Component
public class Formulas {

	private String padLeft(String text, String padChar, Integer size) {
		String format = "%" + padChar + size + "d";
		return String.format(format, Integer.parseInt(text));
	}

	public Integer calculaDV(Integer numero) {
		String SDICODDV;
		Integer SDINUMERO;
		Integer SDIRESULTADO;
		Integer SDIDV;
		Integer SDISOMA;
		Integer SDIMULT;
		Integer SDIRESTO;
		Integer SDII;
		Integer DVI;

		SDICODDV = padLeft(String.valueOf(numero), "0", 14);
		SDII = 13;
		SDIMULT = 2;
		SDISOMA = 0;

		while (true) {
			SDINUMERO = Integer.valueOf(SDICODDV.substring(SDII, SDII + 1));
			SDIRESULTADO = SDINUMERO * SDIMULT;
			SDISOMA = SDISOMA + SDIRESULTADO;
			SDII = SDII - 1;

			if (SDII == 0) {
				break;
			}

			SDIMULT = SDIMULT + 1;

			if (SDIMULT == 10) {
				SDIMULT = 2;
			}
		}

		SDIRESTO = SDISOMA % 11;
		SDIDV = SDIRESTO - 11;

		if (SDIDV < 0) {
			SDIDV = SDIDV * -1;
		}

		if (SDIDV > 9) {
			SDIDV = 0;
		}

		DVI = SDIDV;

		return DVI;
	}

}
