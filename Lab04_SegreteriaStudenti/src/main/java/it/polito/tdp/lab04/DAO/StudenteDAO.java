package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	Map<Integer,Studente> studenti = new LinkedHashMap<Integer, Studente>();

	public Map<Integer,Studente> getTuttiStudenti() {
			
		final String sql = "SELECT * FROM studente";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String nome = rs.getString("nome");
				int matricola = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String cds = rs.getString("cds");
				System.out.println(matricola + " " + cognome + " " + nome + " " + cds);

				
				Studente s= new Studente(matricola, cognome, nome, cds);
				
				studenti.put(s.getMatricola(),s);
			}
			conn.close();
				
			return studenti;
				

		} catch (SQLException e) {
				// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public Studente getStudentePerMatricola(Integer matricola) {
		
		Map<Integer, Studente> mappa = getTuttiStudenti();
		
		if(mappa.containsKey(matricola))
			return mappa.get(matricola);
		
		return null;
	}
	
	public List<Corso> getCorsiPerMatricola(int matricola) {
		String sql = "SELECT c.codins, c.crediti, c.nome, c.pd " + 
				"FROM studente AS s, corso AS c, iscrizione AS i " + 
				"WHERE s.matricola = i.matricola AND c.codins=i.codins AND s.matricola=?";
		List<Corso> lCorsi = new LinkedList<>();
		
		if(getStudentePerMatricola(matricola)==null)
			return null;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String codins = rs.getString("codins");
				int crediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int pd = rs.getInt("pd");
				//System.out.println(codins + " " + crediti + " " + nome + " " + pd);

				
				Corso c= new Corso(codins, crediti, nome, pd);
				
				lCorsi.add(c);
			}
			conn.close();
				
			return lCorsi;
				

		} catch (SQLException e) {
				// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
	}
	public boolean isIscritto(int matricola , String nome) {
		String sql = "SELECT c.codins FROM studente AS s, corso AS c, iscrizione AS i "
				+ "WHERE s.matricola = i.matricola AND c.codins=i.codins AND s.matricola=? AND c.nome=?";
		
		List<String> controllo = new LinkedList<>();
		boolean iscritto = false;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			st.setString(2, nome);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				String codins = rs.getString("codins");
				controllo.add(codins);
			}
			
			if(controllo.size()==1)
				iscritto=true;
			
			conn.close();
				
			return iscritto;
		} catch (SQLException e) {
				// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
	}
	
}
