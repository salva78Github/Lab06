package it.polito.tdp.meteo;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.exception.MeteoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class MeteoController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<Model.Months> boxMese;

	@FXML
	private Button btnCalcola;

	@FXML
	private Button btnUmidita;

	@FXML
	private TextArea txtResult;

	private Model model;
	
	@FXML
	void doCalcolaSequenza(ActionEvent event) {
		int mese = boxMese.getValue().value();
		System.out.println("<doCalcolaSequenza> mese: " + mese);
		
		String sequenza = this.model.trovaSequenza(mese);
		txtResult.setText(sequenza);
		
		
	}

	@FXML
	void doCalcolaUmidita(ActionEvent event) {
		int mese = boxMese.getValue().value();
		System.out.println("<doCalcolaUmidita> mese: " + mese);
		String umiditaMedie;
		try {
			umiditaMedie = this.model.getUmiditaMedia(mese);
			System.out.println("<doCalcolaUmidita> umiditaMedie: " + umiditaMedie);
			txtResult.setText(umiditaMedie);
		} catch (MeteoException me) {
			// TODO Auto-generated catch block
			txtResult.setText("Errore nel calcolo delle umidità medie: " + me.getMessage());
		}
		
		
	}

	public void setModel(Model model){
		this.model = model;
	}
	
	@FXML
	void initialize() {
		assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Meteo.fxml'.";
		// qui perché non dipende dai dati
		for (Model.Months mese : Model.Months.values()) {
			boxMese.getItems().add(mese);
		}
	}

}
