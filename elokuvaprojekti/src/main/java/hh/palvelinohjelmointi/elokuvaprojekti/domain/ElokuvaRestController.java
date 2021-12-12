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
public class ElokuvaRestController {

	@Autowired
	private ElokuvaRepository repository;
	
	// kaikkien elokuvien listaaminen
    @RequestMapping(value="/elokuvat", method = RequestMethod.GET)
    public @ResponseBody Iterable<Elokuva> elokuvalistaRest() {	
        return (Iterable<Elokuva>) repository.findAll();
    } 
    
    //Elokuvan hakeminen id:llä
    @RequestMapping(value="/elokuva/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Elokuva> findElokuvaRest(@PathVariable("id") Long elokuvaId) {	
    	return repository.findById(elokuvaId);
    }  
    
    //Elokuvan lisääminen
    @RequestMapping(value="/elokuvat", method = RequestMethod.POST)
    public @ResponseBody Elokuva saveElokuvaRest(@RequestBody Elokuva elokuva) {	
    	return repository.save(elokuva);
    }
    
}
