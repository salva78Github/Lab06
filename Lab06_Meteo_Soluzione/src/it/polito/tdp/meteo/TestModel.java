package it.polito.tdp.meteo;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		// I mesi partono da 1 per comodita'
		System.out.println(String.format("Mese JAN, valore: %d\n", Model.Months.valueOf("JAN").value()));
		
		System.out.println(model.getUmiditaMedia(5));
		System.out.println(model.trovaSequenza(5));
		
		System.out.println("\n\n");
		
		System.out.println(model.getUmiditaMedia(12));
		System.out.println(model.trovaSequenza(12));
	}

}
