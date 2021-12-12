package hh.palvelinohjelmointi.elokuvaprojekti.webcontroller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hh.palvelinohjelmointi.elokuvaprojekti.domain.Elokuva;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.ElokuvaRepository;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.Genre;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.GenreRepository;


@Controller
public class GenreController {

	@Autowired
	private GenreRepository repository;
	
	@Autowired
	private ElokuvaRepository erepository;
	
	
	// Listataan genret genret-sivulle
		@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')") 
		@RequestMapping(value="/genret")
		public String getGenret(Model model) {
			
			List<Genre> genret = (List<Genre>) repository.findAll();
			Collections.sort(genret);
			
			// Model-oliolla siirretään lista genreistä html-dokumentin käytettäväksi
			model.addAttribute("genret", genret);
			
			// Palautetaan html-sivun nimi, jonka halutaan näkyvän
			return "genret";
		}
		
		// Uuden genren luominen
		@PreAuthorize("hasAnyAuthority('ADMIN')") 
		@RequestMapping(value="/addgenre")
		public String addGenre(Model model) {
			
			//Luodaan uusi Genre-olio ja model-olion avulla siirretään se eteenpäin
			model.addAttribute("genre", new Genre());
			
			//Ohjataan sivulle, jolta lomake tietojen täyttämistä varten löytyy
			return "addgenre";
		}
		
		// Uuden genren / muokkausten tallentaminen
		@PreAuthorize("hasAnyAuthority('ADMIN')") 
		@RequestMapping(value="/savegenre", method = RequestMethod.POST)
		public String saveGenre(@ModelAttribute Genre genre) {
			
			// Repositoryn avulla tallennetaan uudet tiedot tietokantaan
			repository.save(genre);
			
			//Ohjataan takaisin genret-sivulle
			return "redirect:/genret";
		}
		
		
		// Genren muokkaus
		@PreAuthorize("hasAnyAuthority('ADMIN')") 
		@RequestMapping(value="/editgenre/{id}", method = RequestMethod.GET)
		public String editGenre(@PathVariable("id") Long id, Model model) {
		
			model.addAttribute("genre", repository.findById(id));
			
			return "editgenre";
		}
		
		
		//Genren poistaminen
		@PreAuthorize("hasAnyAuthority('ADMIN')") 
		@RequestMapping(value="/deletegenre/{id}", method = RequestMethod.GET)
		public String deleteGenre(@PathVariable("id") Long genreId, Model model) {
			
			//Elokuvarepositoryn avulla etsitään elokuvat, joiden tiedoista poistettava genre löytyy
			Iterable<Elokuva> elokuvat = erepository.findByGenreId(genreId);
			
			//Loopataan saatu lista läpi ja poistetaan genret elokuvien tiedoista -> Poistetaan viiteavaimet
			for(Elokuva elokuva : elokuvat) {
				elokuva.removeGenre(repository.findById(genreId).get());
			}
			
			//Poistetaan genre
			repository.deleteById(genreId);
			
			// Ohjataan poiston jälkeen lataamaan genret-sivu uudelleen
			return "redirect:/genret";

		}
		
		//Yhden genren tietojen näyttäminen
		@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')") 
		@RequestMapping(value="/genre/{id}", method = RequestMethod.GET)
		public String oneElokuva(@PathVariable("id") Long genreId, Model model){
		
			model.addAttribute("genre", repository.findById(genreId).get());
			
			
			return "genre";
		}
		
	
}
