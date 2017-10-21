package it.polito.tdp.meteo.bean;

import java.util.Date;

public class Rilevamento {

	private final String localita;
	private final Date data;
	private final int umidita;

	public Rilevamento(String localita, Date data, int umidita) {
		this.localita = localita;
		this.data = data;
		this.umidita = umidita;
	}

	public String getLocalita() {
		return localita;
	}


	public Date getData() {
		return data;
	}


	public int getUmidita() {
		return umidita;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((localita == null) ? 0 : localita.hashCode());
		result = prime * result + umidita;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rilevamento other = (Rilevamento) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (localita == null) {
			if (other.localita != null)
				return false;
		} else if (!localita.equals(other.localita))
			return false;
		if (umidita != other.umidita)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Rilevamento [localita=" + localita + ", data=" + data + ", umidita=" + umidita + "]";
	}



	
	
}
