package hh.palvelinohjelmointi.elokuvaprojekti;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hh.palvelinohjelmointi.elokuvaprojekti.domain.Elokuva;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.ElokuvaRepository;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.Genre;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.GenreRepository;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.Suoratoistopalvelu;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.SuoratoistopalveluRepository;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.User;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.UserRepository;

@ExtendWith(SpringExtension.class) 
@DataJpaTest
public class RepositoryTests {

	@Autowired
	private ElokuvaRepository erepository;
	
	@Autowired
	private GenreRepository grepository;
	
	@Autowired
	private SuoratoistopalveluRepository srepository;
	
	@Autowired
	private UserRepository urepository;
	
	@Test  // testataan ElokuvaRepositoryn findNimi()-metodin toimivuutta
    public void findByNimiShouldReturnElokuva() {
        List<Elokuva> elokuvat = erepository.findByNimi("Troija");
        
        assertThat(elokuvat).hasSize(1);
        assertThat(elokuvat.get(0).getIkaraja().equals("K16"));
    }
	
	@Test  // testataan uuden elokuvan lisäystä
    public void newElokuva() {
    	Elokuva elokuva = new Elokuva("The Avengers", "K12", 2012, "Maailman mahtavimmat supersankarit kokoontuvat taistellakseen ulkoavaruudellista uhkaa vastaan", 2, 23);
    	erepository.save(elokuva);
    	assertThat(elokuva.getId()).isNotNull();
    }
	
	@Test // Testataan elokuva poisto
	public void deleteElokuva() {
		List<Elokuva> elokuva = erepository.findByNimi("The avengers");
		assertThat(!elokuva.isEmpty());
		
		erepository.deleteAll(elokuva);
		assertThat(elokuva.isEmpty());
	}
	
	@Test  // testataan GenreRepositoryn findNimi()-metodin toimivuutta
    public void findByNimiShouldReturnGenre() {
        List<Genre>genret = grepository.findByNimi("Draama");
        
        assertThat(genret).hasSize(1);
    }
	
	@Test  // testataan uuden genren lisäystä
    public void newGenre() {
    	Genre genre = new Genre("Musikaali");
    	grepository.save(genre);
    	assertThat(genre.getId()).isNotNull();
    }
	
	@Test // Testataan genren poisto
	public void deleteGenre() {
		List<Genre> genre = grepository.findByNimi("Musikaali");
		assertThat(!genre.isEmpty());
		
		grepository.deleteAll(genre);
		assertThat(genre.isEmpty());
	}
	
	@Test  // testataan SuoratoistopalveluRepositoryn findNimi()-metodin toimivuutta
    public void findByNimiShouldReturnSPalvelu() {
        List<Suoratoistopalvelu> spalvelut = srepository.findByNimi("Netflix");
        
        assertThat(spalvelut).hasSize(1);
    }
	
	@Test  // testataan uuden suoratoistopalvelun lisäystä
    public void newSuoratoistopalvelu() {
    	Suoratoistopalvelu spalvelu = new Suoratoistopalvelu("Youtube");
    	srepository.save(spalvelu);
    	assertThat(spalvelu.getId()).isNotNull();
    }
	
	@Test // Testataan suoratoistopalvelun poistoa
	public void deleteSuoratoistopalvelu() {
		List<Suoratoistopalvelu> spalvelu = srepository.findByNimi("Youtube");
		assertThat(!spalvelu.isEmpty());
		
		srepository.deleteAll(spalvelu);
		assertThat(spalvelu.isEmpty());
	}
	
	@Test  // testataan UserRepositoryn findUsername()-metodin toimivuutta
    public void findByUsernameShouldReturnUser() {
        User user= urepository.findByUsername("admin");
        
        assertThat(user.getRole().equals("ADMIN"));
    }
	
	@Test  // testataan uuden käyttäjän lisäystä
    public void newUser() {
    	User user = new User("hellurei", "$2a$10$niTQPXeEQn8mvm54CgJ83OEyRCbEtWYyB3.U2UDkv3IVvEDE7nEZS", "USER", "hei@hei.fi");
    	urepository.save(user);
    	assertThat(user.getId()).isNotNull();
    }
	
}
