package hh.palvelinohjelmointi.elokuvaprojekti.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Kayttajat")
public class User  implements Comparable<User> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    private String role;
    
    @Column(name = "sposti", nullable = false)
    private String sposti;
    
	@ManyToMany
	@JoinTable(
			name="ekuva_kayttajat",
	joinColumns= @JoinColumn(name = "kayttaja_id"),
	inverseJoinColumns = @JoinColumn(name = "elokuva_id"))
	@JsonIgnoreProperties("kayttajat")
	private List<Elokuva> toiveElokuvat = new ArrayList<>();
    
    public User() {
    }

	public User(String username, String passwordHash, String role, String sposti) {
		super();
		this.username = username;
		this.passwordHash = passwordHash;
		this.role = role;
		this.sposti = sposti;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String getSposti() {
		return sposti;
	}

	public void setSposti(String sposti) {
		this.sposti = sposti;
	}

	public List<Elokuva> getToiveElokuvat() {
		Collections.sort(toiveElokuvat);
		return toiveElokuvat;
	}

	public void setToiveElokuvat(List<Elokuva> toiveElokuvat) {
		this.toiveElokuvat = toiveElokuvat;
	}	
	
	//Mahdollisuudet lisätä ja poistaa elokuvia
	public void addElokuva(Elokuva toiveElokuva) {
		toiveElokuvat.add(toiveElokuva);
		toiveElokuva.getKayttajat().add(this);
	}
	
	public void removeElokuva(Elokuva toiveElokuva) {
		toiveElokuvat.remove(toiveElokuva);
		toiveElokuva.getKayttajat().remove(this);
	}
	
	//Metodi, jonka avulla elokuvat saadaan aakkosjärjestykseen
	@Override
	public int compareTo(User user) {
	
		   return this.username.compareTo(user.getUsername());
		   
	}
}
