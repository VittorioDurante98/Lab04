package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


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
}
