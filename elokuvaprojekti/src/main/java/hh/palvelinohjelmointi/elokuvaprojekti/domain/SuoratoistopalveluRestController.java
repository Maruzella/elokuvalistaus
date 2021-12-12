package hh.palvelinohjelmointi.elokuvaprojekti.domain;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/rest")
public class SuoratoistopalveluRestController {
	
	@Autowired
	private SuoratoistopalveluRepository repository;
	
	// kaikkien suoratoistopalveluiden listaaminen
    @RequestMapping(value="/suoratoistopalvelut", method = RequestMethod.GET)
    public @ResponseBody Iterable<Suoratoistopalvelu> spalvelulistaRest() {	
        return (Iterable<Suoratoistopalvelu>) repository.findAll();
    } 
    
    //Suoratoispalvelun hakeminen id:llä
    @RequestMapping(value="/suoratoistopalvelu/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Suoratoistopalvelu> findSPalveluRest(@PathVariable("id") Long spalveluId) {	
    	return repository.findById(spalveluId);
    }  
    
    //Suoratoistopalvelun lisääminen
    @RequestMapping(value="/suoratoistopalvelut", method = RequestMethod.POST)
    public @ResponseBody Suoratoistopalvelu saveSPalveluRest(@RequestBody Suoratoistopalvelu spalvelu) {	
    	return repository.save(spalvelu);
    }
    

}
