package hh.palvelinohjelmointi.elokuvaprojekti;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import hh.palvelinohjelmointi.elokuvaprojekti.webcontroller.ElokuvaController;
import hh.palvelinohjelmointi.elokuvaprojekti.webcontroller.GenreController;
import hh.palvelinohjelmointi.elokuvaprojekti.webcontroller.SiteController;
import hh.palvelinohjelmointi.elokuvaprojekti.webcontroller.SuoratoistopalveluController;
import hh.palvelinohjelmointi.elokuvaprojekti.webcontroller.UserController;

@SpringBootTest
class ElokuvaprojektiApplicationTests {

	@Autowired
	private ElokuvaController econtroller;
	
	@Autowired
	private GenreController gcontroller;
	
	@Autowired
	private SiteController sicontroller;
	
	@Autowired
	private SuoratoistopalveluController sucontroller;
	
	@Autowired
	private UserController ucontroller;
	
	
	
	@Test
	void contextLoads() {
		assertThat(econtroller).isNotNull();
		assertThat(gcontroller).isNotNull();
		assertThat(sicontroller).isNotNull();
		assertThat(sucontroller).isNotNull();
		assertThat(ucontroller).isNotNull();
	}

}
