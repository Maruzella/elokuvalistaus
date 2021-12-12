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
import hh.palvelinohjelmointi.elokuvaprojekti.domain.Suoratoistopalvelu;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.SuoratoistopalveluRepository;

@Controller
public class SuoratoistopalveluController {

	@Autowired
	private SuoratoistopalveluRepository repository;
	
	@Autowired
	private ElokuvaRepository erepository;
	
	
	// Listataan suoratoistopalvelut suoratoistopalvelut-sivulle
		@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')") 
		@RequestMapping(value="/suoratoistopalvelut")
		public String getSuoratoistopalvelut(Model model) {
			
			List<Suoratoistopalvelu> spalvelut = (List<Suoratoistopalvelu>) repository.findAll();
			Collections.sort(spalvelut);
			
			// Model-oliolla siirretään lista suoratoistopalveluista html-dokumentin käytettäväksi
			model.addAttribute("suoratoistopalvelut", spalvelut);
			
			// Palautetaan html-sivun nimi, jonka halutaan näkyvän
			return "suoratoistopalvelut";
		}
		
		// Uuden Suoratoistopalvelun luominen
		@PreAuthorize("hasAnyAuthority('ADMIN')") 
		@RequestMapping(value="/addspalvelu")
		public String addSuoratoistopalvelu(Model model) {
			
			//Luodaan uusi Suoratoistopalvelu-olio ja model-olion avulla siirretään se eteenpäin
			model.addAttribute("suoratoistopalvelu", new Suoratoistopalvelu());
			
			//Ohjataan sivulle, jolta lomake tietojen täyttämistä varten löytyy
			return "addspalvelu";
		}
		
		// Uuden suoratoistopalvelun / muokkausten tallentaminen
		@PreAuthorize("hasAnyAuthority('ADMIN')") 
		@RequestMapping(value="/savespalvelu", method = RequestMethod.POST)
		public String saveSuoratoistopalvelu(@ModelAttribute Suoratoistopalvelu spalvelu) {
			
			// Repositoryn avulla tallennetaan uudet tiedot tietokantaan
			repository.save(spalvelu);
			
			//Ohjataan takaisin suoratoistopalvelut-sivulle
			return "redirect:/suoratoistopalvelut";
		}
		
		
		// Suoratoistopalvelun muokkaus
		@PreAuthorize("hasAnyAuthority('ADMIN')") 
		@RequestMapping(value="/editspalvelu/{id}", method = RequestMethod.GET)
		public String editSuoratoistopalvelu(@PathVariable("id") Long id, Model model) {
		
			model.addAttribute("suoratoistopalvelu", repository.findById(id));
			
			return "editspalvelu";
		}
		
		
		//Suoratoistopalvelun poistaminen
		@PreAuthorize("hasAnyAuthority('ADMIN')") 
		@RequestMapping(value="/deletespalvelu/{id}", method = RequestMethod.GET)
		public String deleteSuoratoistopalvelu(@PathVariable("id") Long spalveluId, Model model) {
			
			// Elokuvarepositoryn avulla etsitään elokuvat, joissa suoratoistopalvelu, jossa sama id
			Iterable<Elokuva> elokuvat = erepository.findBySpalveluId(spalveluId);
			
			//Loopataan saatu lista läpi ja poistetaan suoratoistopalvelut elokuvien tiedoista -> Poistetaan viiteavaimet
			for(Elokuva elokuva : elokuvat) {
				elokuva.removeSuoratoistopalvelu(repository.findById(spalveluId).get());
			}
			
			
			
			// Repositoryn avulla poistetaan suoratoistopalvelu
			repository.deleteById(spalveluId);
			
			// Ohjataan poiston jälkeen lataamaan Suoratoistopalvelut-sivu uudelleen
			return "redirect:/suoratoistopalvelut";
		}
		
		//Yhden suoratoistopalvelun tietojen näyttäminen
		@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')") 
		@RequestMapping(value="/spalvelu/{id}", method = RequestMethod.GET)
		public String oneElokuva(@PathVariable("id") Long spalveluId, Model model){
		
			model.addAttribute("spalvelu", repository.findById(spalveluId).get());
			
			
			return "spalvelu";
		}
	
		
}
