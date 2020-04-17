package it.polito.tdp.lab04;

import java.net.URL;
import java.util.*;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> menuCorsi;

    @FXML
    private Button tnIscritti;

    @FXML
    private TextField txtMatricola;

    @FXML
    private CheckBox checkMatricola;

    @FXML
    private TextField txtnome;

    @FXML
    private TextField txtcognome;

    @FXML
    private Button tnCorsi;

    @FXML
    private Button tnIscriviti;

    @FXML
    private TextArea txtRisultato;

    @FXML
    private Button tnReset;
    
    @FXML
    void doStudente(ActionEvent event) {
    	txtRisultato.clear();
    	
    	if(txtMatricola.getText().isEmpty()) {
    		txtRisultato.appendText("Scrivi una matricola scemo");
    		return;
    	}
    		
    	
    	try {
    		int matricola = Integer.parseInt(txtMatricola.getText());
        	Studente s = model.richiamaStudente(matricola);
        	txtnome.setText(s.getNome());
        	txtcognome.setText(s.getCognome());
    	}catch(NumberFormatException e) {
    		txtRisultato.setText("Inserire una matricola nel formato corretto (numero intero)");
    	}
   
    }

    @FXML
    void doIscrizione(ActionEvent event) {

    }

    @FXML
    void doReset(ActionEvent event) {
    	txtRisultato.clear();
    	txtcognome.clear();
    	txtnome.clear();
    	txtMatricola.clear();
    	checkMatricola.setSelected(false);
    }

    @FXML
    void searchCorsi(ActionEvent event) {

    }

    @FXML
    void searchIscritti(ActionEvent event) {
    	
    	String s = menuCorsi.getValue();
    	if(s=="")
    		txtRisultato.appendText("Scegli un corso valido COGLIO**");
    	Corso c = new Corso(null, 0, s,0);
    	List<Studente> liS = new LinkedList<Studente>(model.richiamoDellaLista(c));
    	txtRisultato.appendText(liS.toString());
    }

    @FXML
    void initialize() {
        assert menuCorsi != null : "fx:id=\"menuCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tnIscritti != null : "fx:id=\"tnIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert checkMatricola != null : "fx:id=\"checkMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtnome != null : "fx:id=\"txtnome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtcognome != null : "fx:id=\"txtcognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tnCorsi != null : "fx:id=\"tnCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tnIscriviti != null : "fx:id=\"tnIscriviti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tnReset != null : "fx:id=\"tnReset\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model=model;
    	
    	menuCorsi.getItems().addAll(model.tornanomiCorsi());
    	txtnome.setDisable(true);
    	txtcognome.setDisable(true);
    	txtRisultato.setDisable(true);
    }
}
