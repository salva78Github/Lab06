package it.polito.tdp.meteo.db;

import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.bean.Rilevamento;

public class TestDAO {

	public static void main(String[] args) {

		MeteoDAO meteoDAO = new MeteoDAO();

		System.out.println(meteoDAO.getCities());
		
		System.out.println();
		
		List<Rilevamento> list = meteoDAO.getAllRilevamenti();
		// STAMPA: localita, giorno, mese, anno, umidita (%)
		for (Rilevamento r : list) {
			System.out.format("%-10s %2td/%2$2tm/%2$4tY %3d%%\n", r.getLocalita(), r.getData(), r.getUmidita());
		}
		
		System.out.println();

		System.out.println("Umidita media per Gennaio:");
		Map<String, Double> avgHum = meteoDAO.getAvgRilevamentiMese(1);
		for (String s : avgHum.keySet()){
			System.out.println(String.format("localita: %s \tumidita': %f", s, avgHum.get(s)));
		}
		
		System.out.println();
		
		System.out.println("Umidita media per Febbraio:");
		avgHum = meteoDAO.getAvgRilevamentiMese(2);
		for (String s : avgHum.keySet()){
			System.out.println(String.format("localita: %s \tumidita': %f", s, avgHum.get(s)));
		}
		
		System.out.println();
		
		System.out.println("Rilevamenti di Dicembre:");
		System.out.println(meteoDAO.getAllRilevamentiLocalitaMese(12, "Genova"));
		System.out.println(meteoDAO.getAllRilevamentiLocalitaMese(12, "Milano"));
		System.out.println(meteoDAO.getAllRilevamentiLocalitaMese(12, "Torino"));
		System.out.println();
	}

}
