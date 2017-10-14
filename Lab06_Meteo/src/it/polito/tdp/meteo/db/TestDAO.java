package it.polito.tdp.meteo.db;

import java.util.List;

import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.RilevamentoUmiditaMedia;
import it.polito.tdp.meteo.exception.MeteoException;

public class TestDAO {

	public static void main(String[] args) {

		MeteoDAO dao = new MeteoDAO();

		List<Rilevamento> list = dao.getAllRilevamenti();

		// STAMPA: localita, giorno, mese, anno, umidita (%)
		for (Rilevamento r : list) {
			System.out.format("%-10s %2td/%2$2tm/%2$4tY %3d%%\n", r.getLocalita(), r.getData(), r.getUmidita());
		}
		
		List<RilevamentoUmiditaMedia> ruml;
		for(int i = 1; i<=12; i++){
			try {
				ruml = dao.getAllRilevamentiUmiditaMedie(i);
				System.out.format("main di test: MESE: " + i + "\n");
				for(RilevamentoUmiditaMedia rum : ruml){
					System.out.format("main di test " +  rum + "\n");
				}
				
				
			} catch (MeteoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
//		System.out.println(dao.getAllRilevamentiLocalitaMese(1, "Genova"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(1, "Genova"));
//		
//		System.out.println(dao.getAllRilevamentiLocalitaMese(5, "Milano"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(5, "Milano"));
//		
//		System.out.println(dao.getAllRilevamentiLocalitaMese(5, "Torino"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(5, "Torino"));
		
	}

}
