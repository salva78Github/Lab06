package it.polito.tdp.meteo.bean;

public class RilevamentoUmiditaMedia {

	private final String localita;
	private final double umiditaMedia;

	/**
	 * @param localita
	 * @param umiditaMedia
	 */
	public RilevamentoUmiditaMedia(String localita, double umiditaMedia) {
		super();
		this.localita = localita;
		this.umiditaMedia = umiditaMedia;
	}

	/**
	 * @return the localita
	 */
	public String getLocalita() {
		return localita;
	}

	/**
	 * @return the umiditaMedia
	 */
	public double getUmiditaMedia() {
		return umiditaMedia;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((localita == null) ? 0 : localita.hashCode());
		long temp;
		temp = Double.doubleToLongBits(umiditaMedia);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		RilevamentoUmiditaMedia other = (RilevamentoUmiditaMedia) obj;
		if (localita == null) {
			if (other.localita != null)
				return false;
		} else if (!localita.equals(other.localita))
			return false;
		if (Double.doubleToLongBits(umiditaMedia) != Double.doubleToLongBits(other.umiditaMedia))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[localita=" + localita + "] umiditaMedia=" + umiditaMedia;
	}

}
