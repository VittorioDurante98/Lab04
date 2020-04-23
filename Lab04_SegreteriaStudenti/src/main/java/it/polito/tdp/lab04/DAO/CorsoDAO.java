package it.polito.tdp.lab04.DAO;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	Map<String, Corso> mappaCorsi = new TreeMap<String, Corso>();
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				corsi.add(c);
				mappaCorsi.put(c.getCodins(), c);
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(Corso corso) {
		if(mappaCorsi.containsKey(corso.getCodins()))
			return mappaCorsi.get(corso.getCodins());
		else
			return null;
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		String sql = "SELECT s.matricola, s.nome, s.cognome, s.cds FROM studente AS s, corso AS c, iscrizione AS i "
				+ "WHERE s.matricola = i.matricola AND c.codins=i.codins AND c.nome=?";
		List<Studente> stud = new LinkedList<Studente>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getNome());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"),rs.getString( "CDS"));
				stud.add(s);
			}

			conn.close();
			return stud;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		
		String sql="insert into iscrizione values (?,?)";
		boolean  fatto= false;
		List<Corso> lCorsi = new LinkedList<>(getTuttiICorsi());
		StudenteDAO sd= new StudenteDAO(); 
		if(corso.getNome()=="" || sd.getStudentePerMatricola(studente.getMatricola())==null)
			return fatto;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			Corso c =null;
			for(int i=0; i<lCorsi.size(); i++) {
				if(lCorsi.get(i).getNome().compareTo(corso.getNome())==0) {
					c= lCorsi.get(i);
				}
			}
			
			
			st.setString(1, c.getCodins());
			ResultSet rs = st.executeQuery();

			fatto= true;
			conn.close();


		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		return fatto;
	}

}
