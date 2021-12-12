package hh.palvelinohjelmointi.elokuvaprojekti.webcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SiteController {

	// Pyyntö etusivun luomista/hakua varten
	@RequestMapping(value="/index")
	public String index() {
		return "index";
	}
	
	//Pyyntö kirjautumissivun luontia varten
	@RequestMapping(value="/login")
	public String login() {
		return "login";
	} 
	
	
}
