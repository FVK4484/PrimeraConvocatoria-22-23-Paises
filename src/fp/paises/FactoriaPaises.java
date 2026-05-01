package fp.paises;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fp.utiles.Checkers;
import fp.utiles.Ficheros;

public class FactoriaPaises {
	
	private static final String SEPARADOR_PRINCIPAL = ";";
	private static final String SEPARADOR_SECUNDARIO = ",";
	private static final String SEPARADOR_TERCIARIO = ":";
	
	private static Pais parseaPais(String lineaCSV) {
		Checkers.checkNoNull(lineaCSV);
		String [] trozos = lineaCSV.split(SEPARADOR_PRINCIPAL);
		Checkers.check("Debe haber un total de 9 trozos.", trozos.length == 9);
		String codigoISO = trozos[0].trim();
		String nombre = trozos[1].trim();
		Long poblacion = Long.valueOf(trozos[2].trim());
		Double area = Double.valueOf(trozos[3].trim());
		String capital = trozos[4].trim();
		Continente continente = parseaContinente(trozos[5]);
		String moneda = trozos[6].trim();
		List<Idioma> idiomas = parseaIdiomas(trozos[7].trim());
		Set<String> vecinos = parseaVecinos(trozos[8].trim());
		return new Pais(codigoISO, nombre, poblacion, area, capital, continente, moneda, idiomas, vecinos);
	}
	
	private static Continente parseaContinente(String lineaCSV) {
		Continente res = Continente.SOUTH_AMERICA;
		if (lineaCSV.trim().toUpperCase().equals("AF")) {
			res = Continente.AFRICA;
		} else if (lineaCSV.trim().toUpperCase().equals("AN")) {
			res = Continente.ANTARCTICA;
		} else if (lineaCSV.trim().toUpperCase().equals("AS")) {
			res = Continente.ASIA;
		} else if (lineaCSV.trim().toUpperCase().equals("EU")) {
			res = Continente.EUROPE;
		} else if (lineaCSV.trim().toUpperCase().equals("NA")) {
			res = Continente.NORTH_AMERICA;
		} else if (lineaCSV.trim().toUpperCase().equals("OC")) {
			res = Continente.OCEANIA;
		}
		return res;
	}

	private static Set<String> parseaVecinos(String lineaCSV) {
		Set<String> vecinos = new HashSet<>();
		String [] trozos = lineaCSV.split(SEPARADOR_SECUNDARIO);
		for (String trozo : trozos) {
			vecinos.add(trozo);
		}
		return vecinos;
	}

	private static List<Idioma> parseaIdiomas(String lineaCSV) {
		List<Idioma> idiomas = new ArrayList<>();
		if (lineaCSV != null && !lineaCSV.isEmpty()) {
			String [] trozos = lineaCSV.split(SEPARADOR_SECUNDARIO);
			for (String trozo : trozos) {
				String [] cachos = trozo.split(SEPARADOR_TERCIARIO);
				Checkers.check("Debe de haber un total de 2 cachos.", cachos.length == 2);
				String nombre = cachos[0].trim();
				Double porcentaje = Double.valueOf(cachos[1].trim());
				idiomas.add(new Idioma(nombre, porcentaje));
			}
		}
		return idiomas;
	}
	
	public static List<Pais> leePaises(String ficheroCSV) {
		String errMsgg = String.format("Error leyendo el fichero: " + ficheroCSV);
		List<String> lineasCSV = Ficheros.leeFichero(errMsgg, ficheroCSV);
		return lineasCSV.stream()
				.skip(1)
				.map(l -> parseaPais(l))
				.collect(Collectors.toList());
		
	}
	
}
