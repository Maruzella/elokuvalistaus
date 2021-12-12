package hh.palvelinohjelmointi.elokuvaprojekti.webcontroller;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import hh.palvelinohjelmointi.elokuvaprojekti.domain.Elokuva;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.ElokuvaRepository;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.GenreRepository;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.SuoratoistopalveluRepository;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.User;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.UserRepository;


@Controller
public class ElokuvaController {

	// "Otetaan yhteys" repositoryyn, jotta saadaan tietokannan elokuvalista käyttöön
	@Autowired
	private ElokuvaRepository repository;
	
	@Autowired
	private GenreRepository grepository;
	
	@Autowired
	private SuoratoistopalveluRepository srepository;
	
	@Autowired
	private UserRepository urepository;
	
	// Listataan elokuvat elokuvalistaus-sivulle
	@RequestMapping(value="/elokuvalista")
	public String getElokuvat(Model model) {
		
		List<Elokuva> elokuvat = (List<Elokuva>) repository.findAll();
		Collections.sort(elokuvat);
		
		// Model-oliolla siirretään elokuvalista html-dokumentin käytettäväksi
		model.addAttribute("elokuvat", elokuvat);
		
		// Palautetaan html-sivun nimi, jonka halutaan näkyvän
		return "elokuvalista";
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN')") 
	@RequestMapping(value="/deleteelokuva/{id}", method = RequestMethod.GET)
	public String deleteElokuva(@PathVariable("id") Long elokuvaId, Model model) {
		
		//Userrepositoryn avulla etsitään käyttäjät, joiden tiedoista poistettava elokuva löytyy
		Iterable<User> kayttajat = urepository.findByElokuvaId(elokuvaId);
		
		//Loopataan saatu lista läpi ja poistetaan elokuvat käyttäjien tiedoista -> Poistetaan viiteavaimet
		for(User kayttaja : kayttajat) {
			kayttaja.removeElokuva((repository.findById(elokuvaId).get()));;
		}
		
		
		// Repositoryn avulla poistetaan elokuva
		repository.deleteById(elokuvaId);
		
		// Ohjataan poiston jälkeen lataamaan Elokuvalista-sivu uudelleen
		return "redirect:/elokuvalista";
	}
	
	// Uuden Elokuvan luominen
	@PreAuthorize("hasAnyAuthority('ADMIN')") 
	@RequestMapping(value="/addelokuva")
	public String addElokuva(Model model) {
		
		//Luodaan uusi Elokuva-olio ja model-olion avulla siirretään se eteenpäin
		model.addAttribute("elokuva", new Elokuva());
		//Luodaan lista genreistä ja lähetetään se eteenpäin
		model.addAttribute("genret", grepository.findAll());
		//Luodaan lista suoratoistopalveluista ja lähetetään se eteenpäin
		model.addAttribute("spalvelut", srepository.findAll());
		
		//Ohjataan sivulle, jolta lomake tietojen täyttämistä varten löytyy
		return "addelokuva";
	}
	
	// Uuden elokuva / muokkausten tallentaminen
	@PreAuthorize("hasAnyAuthority('ADMIN')") 
	@RequestMapping(value="/saveelokuva", method = RequestMethod.POST)
	public String saveMovie(Elokuva elokuva, @RequestParam(value = "genres", required = false) int[] genreja, @RequestParam(value = "spalvelut", required = false) int[] spalveluja, Model model) {
			 
		if(genreja != null) {
			for(int genre : genreja) {
				elokuva.addGenre(grepository.findById((Long.valueOf(genre))).get());
			}
		}	
		
		if(spalveluja != null) {
			for(int spalvelu : spalveluja) {
				elokuva.addSuoratoistopalvelu(srepository.findById((Long.valueOf(spalvelu))).get());
			}
		}	
		
		// Repositoryn avulla tallennetaan uudet tiedot tietokantaan
		repository.save(elokuva);
		

		return "redirect:/elokuvalista";

	}
	
	
	// Elokuvan muokkaus
	@PreAuthorize("hasAnyAuthority('ADMIN')") 
	@GetMapping(value="/editelokuva/{id}")
	public String editElokuva(@PathVariable("id") Long elokuvaId, Model model) {
	
		model.addAttribute("elokuva", repository.findById(elokuvaId).get());
		model.addAttribute("genres", grepository.findAll());
		model.addAttribute("spalvelut", srepository.findAll());
		
		return "editelokuva";
	}
	
	
	//Yhden elokuvan tietojen näyttäminen
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')") 
	@RequestMapping(value="/elokuva/{id}", method = RequestMethod.GET)
	public String oneElokuva(@PathVariable("id") Long elokuvaId, Model model){
	
		Elokuva ekuva = repository.findById(elokuvaId).get();
		
		model.addAttribute("elokuva", ekuva);
		
		
		return "elokuva";
	}
	


}
