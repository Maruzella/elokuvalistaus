package hh.palvelinohjelmointi.elokuvaprojekti.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Genre")
@JsonIgnoreProperties(value = { "elokuvat" })
public class Genre implements Comparable<Genre>{

		//Automaattinen id:n luominen
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name = "id")
		private long id;
		
		//Muut muuttujat
		@Column(name = "nimi", nullable = false)
		private String nimi;
		
		@ManyToMany(mappedBy="genret", fetch = FetchType.LAZY)
		private List<Elokuva> elokuvat = new ArrayList<>();

		
		//Konstruktorit
		public Genre() {
			super();
		}

		public Genre(String nimi) {
			super();
			this.nimi = nimi;
		}

		
		//Getterit ja Setterit
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getNimi() {
			return nimi;
		}

		public void setNimi(String nimi) {
			this.nimi = nimi;
		}
		
		public List<Elokuva> getElokuvat() {
			Collections.sort(elokuvat);
			return elokuvat;
		}

		public void setElokuvat(List<Elokuva> elokuvat) {
			this.elokuvat = elokuvat;
		}	
		
		
		//Mahdollisuudet lisätä ja poistaa elokuvia
		public void addElokuva(Elokuva elokuva) {
			elokuvat.add(elokuva);
			elokuva.getGenret().add(this);
		}
		
		public void removeElokuva(Elokuva elokuva) {
			elokuvat.remove(elokuva);
			elokuva.getGenret().remove(this);
		}


		
		//Luodaan vertailumetodi, jotta suoratoistopalvelut voidaan järjestää aakkosjärjestykseen
		
		@Override
		public int compareTo(Genre genre) {
		
			   return this.nimi.compareTo(genre.getNimi());
			   
		}
		
		//ToString
		@Override
		public String toString() {
			return "Genre [id=" + id + ", nimi=" + nimi + "]";
		}
		
		
	
}
