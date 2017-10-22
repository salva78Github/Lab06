package it.polito.tdp.meteo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.RilevamentoUmiditaMedia;
import it.polito.tdp.meteo.exception.MeteoException;

public class MeteoDAO {

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

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) throws MeteoException {
		String query = "SELECT data, umidita " +
					   "FROM situazione " +
					   "WHERE MONTH(data) = ? " +
					   "AND localita = ? " +
					   "ORDER BY data";
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Rilevamento> rl = new ArrayList<Rilevamento>();

		try{
			c = DBConnect.getInstance().getConnection();
			ps = c.prepareStatement(query);
			ps.setInt(1, mese);
			ps.setString(2, localita);
			rs = ps.executeQuery();
			
			while(rs.next()){
				Rilevamento r = new Rilevamento(localita, rs.getDate("data"), rs.getInt("umidita"));
				//System.out.println("<getAllRilevamentiLocalitaMese> " + r);
				rl.add(r);
			}
			
			return rl;
			
		} catch(SQLException sqle){
			sqle.printStackTrace();
			throw new MeteoException("Errore db", sqle);
			
		}
		
		
		
	}

	public Double getAvgRilevamentiLocalitaMese(int mese, String localita) {
				return 0.0;
	}


	public List<RilevamentoUmiditaMedia> getAllRilevamentiUmiditaMedie(int mese) throws MeteoException{
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<RilevamentoUmiditaMedia> ruml = new ArrayList<RilevamentoUmiditaMedia>();
		
		try{
			String query = " SELECT localita, AVG(umidita) AS umidita_media FROM situazione WHERE MONTH(data) = ? GROUP BY localita";
			c = DBConnect.getInstance().getConnection();
			ps = c.prepareStatement(query);
			ps.setInt(1, mese);
			rs = ps.executeQuery();
			
			while(rs.next()){
				RilevamentoUmiditaMedia rum = new RilevamentoUmiditaMedia(rs.getString("localita"), rs.getDouble("umidita_media"));
				System.out.println("<getAllRilevamentiUmiditaMedie> " + rum);
				ruml.add(rum);
			}
			
			return ruml;
			
		} catch(SQLException sqle){
			sqle.printStackTrace();
			throw new MeteoException("Errore db", sqle);
			
		}

	}
	
	public List<String> getCitta() throws MeteoException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> citta = new ArrayList<String>();
		
		try{
			String query = " SELECT DISTINCT localita FROM situazione";
			c = DBConnect.getInstance().getConnection();
			ps = c.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()){
				String localita = rs.getString("localita");
				System.out.println("<getCitta> " + localita);
				citta.add(localita);
			}
			
			return citta;
			
		} catch(SQLException sqle){
			sqle.printStackTrace();
			throw new MeteoException("Errore db", sqle);
			
		}
		
	}
	
}
