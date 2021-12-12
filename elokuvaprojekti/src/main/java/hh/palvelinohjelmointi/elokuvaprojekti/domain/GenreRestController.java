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
public class GenreRestController {

	@Autowired
	private GenreRepository repository;
	
	// kaikkien genrejen listaaminen
    @RequestMapping(value="/genret", method = RequestMethod.GET)
    public @ResponseBody Iterable<Genre> genrelistaRest() {	
        return (Iterable<Genre>) repository.findAll();
    } 
    
    //Genren hakeminen id:llä
    @RequestMapping(value="/genre/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Genre> findGenreRest(@PathVariable("id") Long genreId) {	
    	return repository.findById(genreId);
    }  
    
    //Genren lisääminen
    @RequestMapping(value="/genret", method = RequestMethod.POST)
    public @ResponseBody Genre saveGenreRest(@RequestBody Genre genre) {	
    	return repository.save(genre);
    }
    
}
