package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
	private List<Citta> citta = new ArrayList<Citta>(); 
	private double costoMinimo = 0;
	private static  MeteoDAO md = new  MeteoDAO();
	List<SimpleCity> soluzione;
	List<String> localita;


	public Model() throws MeteoException {
		localita  = getMeteoDAO().getCitta();
		
		for (String s : localita)
			citta.add(new Citta(s));
	}

	public enum Months {

		JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC;

		private static Map<Integer, Months> ss = new TreeMap<Integer, Months>();
		private static final int START_VALUE = 1;
		private int value;

		static {
			for (int i = 0; i < values().length; i++) {
				values()[i].value = START_VALUE + i;
				ss.put(values()[i].value, values()[i]);
			}
		}

		public static Months fromInt(int i) {
			return ss.get(i - 1);
		}

		public int value() {
			return value;
		}
	}

	
	public String getUmiditaMedia(int mese) throws MeteoException {
		List<RilevamentoUmiditaMedia> ruml = getMeteoDAO().getAllRilevamentiUmiditaMedie(mese);
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
		this.costoMinimo=0;
		this.soluzione= new ArrayList<SimpleCity>();
		
		recursive(level, soluzioneParziale);

		StringBuffer sb = new StringBuffer();
		for (SimpleCity sc : soluzione) {
			sb.append(sc).append(" - ");
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

		for (Citta c : citta) {

			SimpleCity sc = new SimpleCity(c.getNome(),
					c.getRilevamenti().get(level).getUmidita());
			soluzioneParziale.add(level, sc);
			c.increaseCounter();

			if (checkSoluzioneParziale(soluzioneParziale, level)) {
				recursive(level + 1, soluzioneParziale);
			}

			// backtracking
			c.decreaseCounter();
			soluzioneParziale.remove(level);

		}

	}

	private boolean checkSoluzioneParziale(List<SimpleCity> soluzioneParziale, int level) {
		
		// Se e' nulla non e' valida
		if (soluzioneParziale == null)
			return false;

		// Se la soluzione parziale e' vuota, e' valida
		if (soluzioneParziale.size() == 0)
			return true;

		
		// valida se usata meno di 6 volte e costo < della soluzione salvata
		for (Citta c : citta) {
			if (c.getCounter() > NUMERO_GIORNI_CITTA_MAX) {
				return false;
			}
		}

		// Controllo sul vincolo del numero minimo di giorni consecutivi
		SimpleCity previous = soluzioneParziale.get(0);
		int counter = 0;

		for (SimpleCity sc : soluzioneParziale) {
			if (!previous.equals(sc)) {
				if (counter < NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) {
					return false;
				}
				counter = 1;
				previous = sc;
			} else {
				counter++;
			}
		}

		
		return true;
	}

	private void inzializeCittaParameters(int mese) throws MeteoException {
		// TODO Auto-generated method stub
		
		
		for (Citta c : citta){
			c.setRilevamenti(getMeteoDAO().getAllRilevamentiLocalitaMese(mese, c.getNome()));	
			c.setCounter(0);
		}
		


	}

	private MeteoDAO getMeteoDAO() {
		return md;
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
