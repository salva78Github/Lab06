package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.RilevamentoUmiditaMedia;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;
import it.polito.tdp.meteo.exception.MeteoException;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private Citta[] citta = new Citta[3]; // 0 Torino, 1 Milano, 2, Genova
	private double costoMinimo = 0;
	List<SimpleCity> soluzione = new ArrayList<SimpleCity>();

	public Model() {

	}

	public String getUmiditaMedia(int mese) throws MeteoException {
		List<RilevamentoUmiditaMedia> ruml = new MeteoDAO().getAllRilevamentiUmiditaMedie(mese);
		StringBuilder sb = new StringBuilder();
		for (RilevamentoUmiditaMedia rum : ruml) {
			sb.append(rum).append("\n");
		}

		return sb.toString();
	}

	public String trovaSequenza(int mese) {
		try {
			inzializeCittaParameters(mese);
		} catch (MeteoException me) {
			return "Errore di inizializzazione dei dati: " + me.getMessage();
		}
		int level = 0;

		List<SimpleCity> soluzioneParziale = new ArrayList<SimpleCity>();

		recursive(level, soluzioneParziale);

		StringBuffer sb = new StringBuffer();
		for (SimpleCity sc : soluzione) {
			sb.append(sc);
		}
		sb.append("Costo Totale Minimo: ").append(costoMinimo);

		return sb.toString();
	}

	private void recursive(int level, List<SimpleCity> soluzioneParziale) {

		// condizione terminazione
		if (soluzioneParziale.size() == NUMERO_GIORNI_TOTALI) {
			double punteggioSoluzione = punteggioSoluzione(soluzioneParziale);
			System.out.println("<recursive> punteggioSoluzione: " + punteggioSoluzione);
			if (costoMinimo==0 || punteggioSoluzione < costoMinimo) {
				costoMinimo = punteggioSoluzione;
				soluzione.clear();
				soluzione.addAll(soluzioneParziale);
			}

		}

		for (int i = 0; i < 3; i++) {

			SimpleCity sc = new SimpleCity(citta[i].getNome(),
					citta[i].getRilevamenti().get(level).getUmidita());
			soluzioneParziale.add(level, sc);
			citta[i].increaseCounter();

			if (checkSoluzioneParziale(soluzioneParziale, level)) {
				recursive(level + 1, soluzioneParziale);
			}

			// backtracking
			citta[i].decreaseCounter();
			soluzioneParziale.remove(level);

		}

	}

	private boolean checkSoluzioneParziale(List<SimpleCity> soluzioneParziale, int level) {
		// valida se usata meno di 6 volte e costo < della soluzione salvata
		for (int i = 0; i < 3; i++) {
			if (citta[i].getCounter() > NUMERO_GIORNI_CITTA_MAX) {
				return false;
			}
		}

		if (level > NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) {
			String citta = soluzioneParziale.get(level).getNome();
			String cittaPrecedente = soluzioneParziale.get(level - 1).getNome();
			System.out.println("<checkSoluzioneParziale> cittaPrecedente: " + cittaPrecedente);
			
			
			
			if(!citta.equals(cittaPrecedente)){
				return (cittaPrecedente.equals(soluzioneParziale.get(level - 2).getNome())&&cittaPrecedente.equals(soluzioneParziale.get(level - 3).getNome()));
			}
		}	
		
		return true;
	}

	private void inzializeCittaParameters(int mese) throws MeteoException {
		// TODO Auto-generated method stub
		citta[0] = new Citta("Torino", new MeteoDAO().getAllRilevamentiLocalitaMese(mese, "Torino"));
		citta[1] = new Citta("Milano", new MeteoDAO().getAllRilevamentiLocalitaMese(mese, "Milano"));
		citta[2] = new Citta("Genova", new MeteoDAO().getAllRilevamentiLocalitaMese(mese, "Genova"));

	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {
		double score = 0.0;
		SimpleCity previus = soluzioneCandidata.get(0);
		for (SimpleCity c : soluzioneCandidata) {
			if(!c.equals(previus)){
				score += COST;
			}
			previus = c;
			score += c.getCosto();
		}

		return score;
	}

}
