package it.polito.tdp.meteo;

import java.util.List;

import it.polito.tdp.meteo.bean.RilevamentoUmiditaMedia;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;
import it.polito.tdp.meteo.exception.MeteoException;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;

	public Model() {

	}

	public String getUmiditaMedia(int mese) throws MeteoException {
		List<RilevamentoUmiditaMedia> ruml = new MeteoDAO().getAllRilevamentiUmiditaMedie(mese);
		StringBuilder sb = new StringBuilder();
		for(RilevamentoUmiditaMedia rum : ruml){
			sb.append(rum).append("\n");
		}
		
		return sb.toString();
	}

	public String trovaSequenza(int mese) {

		return "TODO!";
	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {

		double score = 0.0;
		return score;
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {

		return true;
	}

}
