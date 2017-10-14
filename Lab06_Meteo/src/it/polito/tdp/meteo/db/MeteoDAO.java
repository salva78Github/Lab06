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

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		
		return null;
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
	
	
	
}
