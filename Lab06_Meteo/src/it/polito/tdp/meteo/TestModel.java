package it.polito.tdp.meteo;

import it.polito.tdp.meteo.exception.MeteoException;

public class TestModel {

	public static void main(String[] args) {

		Model m;
		try {
			m = new Model();
		
		/*
		try {
			System.out.println(m.getUmiditaMedia(12));
		} catch (MeteoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		System.out.println(m.trovaSequenza(12));
		
//		System.out.println(m.trovaSequenza(4));
		
		} catch (MeteoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
