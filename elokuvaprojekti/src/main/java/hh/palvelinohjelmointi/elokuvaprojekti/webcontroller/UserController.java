package hh.palvelinohjelmointi.elokuvaprojekti.webcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.Authentication;

import hh.palvelinohjelmointi.elokuvaprojekti.domain.Elokuva;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.ElokuvaRepository;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.User;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ElokuvaRepository erepository;
	
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@GetMapping("/user")
	public String userProfile(Authentication auth, Model model) {
		
		model.addAttribute("kayttaja", repository.findByUsername(auth.getName()));
		
		model.addAttribute("kayttajat", repository.findAll());
		
		return "user";
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN')") 
	@RequestMapping(value="/deleteuser/{id}", method = RequestMethod.GET)
	public String deleteElokuva(@PathVariable("id") Long userId) {
		
		//Etsitään käyttäjän tiedot repositoryn avulla
		User kayttaja = repository.findById(userId).get();
		
		//Verrataan käyttäjän roolia arvoon 'user'. Vain user-roolin omaavat tunnukset voidaan poistaa
		if(kayttaja.getRole().contains("USER")) {
		
		// Repositoryn avulla poistetaan kayttaja
		repository.deleteById(userId);
		}
		
		// Ladataan Käyttäjä-sivu uudelleen 
		// (joko poiston jälkeen tai ilman poistoa, jos yritetty poistaa käyttäjä, jolla rooli 'admin')
		return "redirect:/user";
		
	}
	
	@PreAuthorize("hasAnyAuthority('USER')") 
	@RequestMapping(value="/deletetelokuva/{id}", method = RequestMethod.GET)
	public String deleteTElokuva(@PathVariable("id") Long elokuvaId, Authentication auth, Model model) {
		
		//Etsitään toive-elokuvalista repositoryn avulla
		List<Elokuva> elokuvat = repository.findByUsername(auth.getName()).getToiveElokuvat();
		
		//VLoopataan toive-elokuvalista läpi ja poistetaan elokuvaId:n omaava(t) elokuva(t)
		for(Elokuva elokuva : elokuvat) {
			if(elokuva.getId() == elokuvaId) {
				//Elokuvan poisto listalta
				repository.findByUsername(auth.getName()).removeElokuva(elokuva);
				
				//Tallennetaan käyttäjätiedot päivitettyinä
				repository.save(repository.findByUsername(auth.getName()));
				
				//Poistutaan loopista
				break;
			}
		}
		
		// Ladataan Käyttäjä-sivu uudelleen 
		return "redirect:/user";
		
	}
	
	@PreAuthorize("hasAnyAuthority('USER')") 
	@RequestMapping(value="/addTElokuva/{id}", method = RequestMethod.GET)
	public String addTElokuva(@PathVariable("id") Long elokuvaId, Authentication auth, Model model) {
		
		//Etsitään elokuva elokuvarepositoryn avulla
		Elokuva elokuva = erepository.findById(elokuvaId).get();
		
		//Etsitään käyttäjän toivelista
		List<Elokuva> toiveElokuvat = repository.findByUsername(auth.getName()).getToiveElokuvat();
		
		//Tarkistetaan onko elokuva jo listalla. Jos on, lisäystä ei tehdä
		if (!toiveElokuvat.contains(elokuva)) {
			//Lisätään elokuva käyttäjän toivelistalle
			repository.findByUsername(auth.getName()).addElokuva(elokuva);
		
			//Tallennetaan käyttäjätiedot päivitettyinä
			repository.save(repository.findByUsername(auth.getName()));
			
			//To-do: Ilmoitus onnistuneesta lisäyksestä näkyviin sivuille
			
		}
		
		return "redirect:/elokuva/" + elokuvaId;
	}
	
}
