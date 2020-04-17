package it.polito.tdp.lab04.model;

import java.util.*;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	StudenteDAO sdao = new StudenteDAO();
	CorsoDAO cDAO = new CorsoDAO();
	
	public List<String> tornanomiCorsi(){
		List<String> nomeCorsi = new LinkedList<>();
		
		nomeCorsi.add("");
		for(Corso c: cDAO.getTuttiICorsi())
			nomeCorsi.add(c.getNome());
		
		return nomeCorsi;
	}
	
	public Studente richiamaStudente(int matricola){
		
		
		
		Studente s = sdao.getStudentePerMatricola(matricola);
		
		return s;
	}
	
	public List<Studente> richiamoDellaLista(Corso corso){
		List<Studente> listaS = new LinkedList<Studente>(cDAO.getStudentiIscrittiAlCorso(corso));
		return listaS;
	}

}
