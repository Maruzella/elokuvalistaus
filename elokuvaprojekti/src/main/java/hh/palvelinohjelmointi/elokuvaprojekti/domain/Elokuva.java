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
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="Elokuva")
@JsonIgnoreProperties(value = { "kayttajat" })
public class Elokuva implements Comparable<Elokuva> {

	// Id tuotetaan automaattisesti
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;
	
	// Määritellään muut muuttujat
	@Column(name = "nimi", nullable = false)
	private String nimi;
	private String ikaraja;
	private int julkaisuVuosi;
	private String kuvaus;
	private int kestoTunnit;
	private int kestoMinuutit;

	@ManyToMany
	@JoinTable(
			name="ekuva_spalvelut",
	joinColumns= @JoinColumn(name = "elokuva_id"),
	inverseJoinColumns = @JoinColumn(name = "spalveluId"))
	@JsonIgnoreProperties("elokuvat")
	private List<Suoratoistopalvelu> suoratoistopalvelut = new ArrayList<Suoratoistopalvelu>();
	
	@ManyToMany
	@JoinTable(
			name="ekuva_genret",
	joinColumns= @JoinColumn(name = "elokuva_id"),
	inverseJoinColumns = @JoinColumn(name = "genre_id"))
	@JsonIgnoreProperties("elokuvat")
	private List<Genre> genret = new ArrayList<Genre>();
	
	@ManyToMany(mappedBy="toiveElokuvat", fetch = FetchType.LAZY)
	private List<User> kayttajat = new ArrayList<User>();
	
	
	//Määritellään konstruktorit olion luomista varten
	
	public Elokuva() {
		
	}

	public Elokuva(String nimi) {
		super();
		this.nimi = nimi;
	}
	

	public Elokuva(String nimi, String ikaraja) {
		super();
		this.nimi = nimi;
		this.ikaraja = ikaraja;
	}

	public Elokuva(String nimi, String ikaraja, int julkaisuVuosi) {
		super();
		this.nimi = nimi;
		this.ikaraja = ikaraja;
		this.julkaisuVuosi = julkaisuVuosi;
	}
	
	public Elokuva(String nimi, String ikaraja, int julkaisuVuosi, String kuvaus) {
		super();
		this.nimi = nimi;
		this.ikaraja = ikaraja;
		this.julkaisuVuosi = julkaisuVuosi;
		this.kuvaus = kuvaus;
	}
	
	public Elokuva(String nimi, String ikaraja, int julkaisuVuosi, String kuvaus, int kestoTunnit, int kestoMinuutit) {
		super();
		this.nimi = nimi;
		this.ikaraja = ikaraja;
		this.julkaisuVuosi = julkaisuVuosi;
		this.kuvaus = kuvaus;
		this.kestoTunnit = kestoTunnit;
		this.kestoMinuutit = kestoMinuutit;
	}
	

	
	//Määritellään getterit ja setterit muuttujille, jotta voidaan päivittää tietoja

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

	public String getIkaraja() {
		return ikaraja;
	}

	public void setIkaraja(String ikaraja) {
		this.ikaraja = ikaraja;
	}
	
	public int getJulkaisuVuosi() {
		return julkaisuVuosi;
	}

	public void setJulkaisuVuosi(int julkaisuVuosi) {
		this.julkaisuVuosi = julkaisuVuosi;
	}

	public String getKuvaus() {
		return kuvaus;
	}

	public void setKuvaus(String kuvaus) {
		this.kuvaus = kuvaus;
	}

	public int getKestoTunnit() {
		return kestoTunnit;
	}

	public void setKestoTunnit(int kestoTunnit) {
		this.kestoTunnit = kestoTunnit;
	}

	public int getKestoMinuutit() {
		return kestoMinuutit;
	}

	public void setKestoMinuutit(int kestoMinuutit) {
		this.kestoMinuutit = kestoMinuutit;
	}

	
	public List<Suoratoistopalvelu> getSuoratoistopalvelut() {
		Collections.sort(suoratoistopalvelut);
		return suoratoistopalvelut;
	}

	public void setSuoratoistopalvelut(List<Suoratoistopalvelu> suoratoistopalvelut) {
		this.suoratoistopalvelut = suoratoistopalvelut;
	}
	
	public List<Genre> getGenret() {
		Collections.sort(genret);
		return genret;
	}

	public void setGenret(List<Genre> genret) {
		this.genret = genret;
	}
	
	public List<User> getKayttajat() {
		Collections.sort(kayttajat);
		return kayttajat;
	}

	public void setKayttajat(List<User> kayttajat) {
		this.kayttajat = kayttajat;
	}
	
	// Metodit suoratoistopalveluiden lisäämiseen/poistamiseen	
	public void addSuoratoistopalvelu(Suoratoistopalvelu spalvelu) {
		suoratoistopalvelut.add(spalvelu);
		spalvelu.getElokuvat().add(this);
	}
	
	public void removeSuoratoistopalvelu(Suoratoistopalvelu spalvelu) {
		suoratoistopalvelut.remove(spalvelu);
		spalvelu.getElokuvat().remove(this);
	}
	
	//Metodit genren lisäämiseen/poistamiseen
	public void addGenre(Genre genre) {
		genret.add(genre);
		genre.getElokuvat().add(this);
	}
	
	public void removeGenre(Genre genre) {
		genret.remove(genre);
		genre.getElokuvat().remove(this);
	}
	
	//Metodi, jonka avulla elokuvat saadaan aakkosjärjestykseen
	@Override
	public int compareTo(Elokuva elokuva) {
	
		   return this.nimi.compareTo(elokuva.getNimi());
		   
	}
	

	@Override //Määritellään String- joka oliosta palautetaan
	public String toString() {
		return id + " Elokuva " + id + ". Nimi: " + nimi + ", ikäraja: " + ikaraja + ", julkaistu: " + julkaisuVuosi + ", juoni: " + kuvaus + "\nkesto: " + kestoTunnit
				+ " h " + kestoMinuutit + " min";
	}
	
}
