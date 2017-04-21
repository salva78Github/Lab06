package it.polito.tdp.meteo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polito.tdp.meteo.bean.Rilevamento;

public class MeteoDAO {

	/*
	 * Metodo di test. Restituisce la lista di tutti i rilevamenti presenti nel DB.
	 */
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/*
	 * Restituisce la lista di rilevamenti per una citta' ed un mese
	 */
	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {

		final String sql = "SELECT Data, Umidita FROM situazione WHERE localita = ? and  MONTH(Data) = ? ";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, localita);
			st.setInt(2, mese);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(localita, rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/*
	 * Restistuisce la lista di tutte le citta' presenti nel DB
	 */
	public List<String> getCities() {
		final String sql = "SELECT DISTINCT localita FROM situazione";

		try {
			List<String> cities = new ArrayList<String>();

			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				cities.add(rs.getString("localita"));
			}

			conn.close();
			return cities;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/*
	 * Dato un mese restituisce una mappa di <citta e umidita' media> 
	 */
	public Map<String, Double> getAvgRilevamentiMese(int mese) {

		final String sql = "SELECT localita, AVG(umidita) as umiditaMedia FROM situazione WHERE MONTH(Data) = ? GROUP BY localita";

		try {
			Map<String, Double> map = new TreeMap<String, Double>();

			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				map.put(rs.getString("localita"), rs.getDouble("umiditaMedia"));
			}

			conn.close();
			return map;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * Non utilizzato, ma rappresenta una possibile alternativa
	 * Data una citta' ed un mese, restituisce l'umidita' media
	 */
	public Double getAvgRilevamentiLocalitaMese(int mese, String localita) {

		final String sql = "SELECT AVG(Umidita) as UmiditaMedia FROM situazione WHERE localita = ? and  MONTH(Data) = ? ";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, localita);
			st.setInt(2, mese);

			ResultSet rs = st.executeQuery();

			double result = 0.0;

			if (rs.next()) {
				result = rs.getDouble("UmiditaMedia");
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
