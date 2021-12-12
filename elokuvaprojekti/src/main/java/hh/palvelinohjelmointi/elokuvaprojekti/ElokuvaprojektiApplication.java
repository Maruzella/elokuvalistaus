package hh.palvelinohjelmointi.elokuvaprojekti;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.palvelinohjelmointi.elokuvaprojekti.domain.Elokuva;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.ElokuvaRepository;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.Genre;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.GenreRepository;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.Suoratoistopalvelu;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.SuoratoistopalveluRepository;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.User;
import hh.palvelinohjelmointi.elokuvaprojekti.domain.UserRepository;


@SpringBootApplication
public class ElokuvaprojektiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElokuvaprojektiApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ElokuvaRepository erepository, SuoratoistopalveluRepository srepository, GenreRepository grepository, UserRepository urepository) {
		return (args) -> {
	
			Genre g1 = new Genre("Draama");
			Genre g2 = new Genre("Komedia");
			Genre g3 = new Genre("Kauhu");
			Genre g4 = new Genre("Toiminta");
			Genre g5 = new Genre("Seikkailu");
			Genre g6 = new Genre("Romantiikka");
			
			grepository.save(g1);
			grepository.save(g2);
			grepository.save(g3);
			grepository.save(g4);	
			grepository.save(g5);
			grepository.save(g6);
			
			Suoratoistopalvelu s1 = new Suoratoistopalvelu("Netflix");
			Suoratoistopalvelu s2 = new Suoratoistopalvelu("Disney+");
			Suoratoistopalvelu s3 = new Suoratoistopalvelu("HBO Max");
			Suoratoistopalvelu s4 = new Suoratoistopalvelu("Viaplay");
			
			srepository.save(s1);
			srepository.save(s2);
			srepository.save(s3);
			srepository.save(s4);			
			
			Elokuva e1 = new Elokuva("Troija", "K16", 2004,"Tarina legendaarisesta sodasta, joka käytiin rakkauden takia.", 2, 36);
			Elokuva e2 = new Elokuva("Piin elämä", "K12", 2012, "Selviytymistarina intialaispojan matkasta meren armoilla ja ystävyydestä Bengalin tiikerin kanssa", 2, 19);
			Elokuva e3 = new Elokuva("Yksin kotona", "K7", 1990, "Muun perheen lähtiessä lomalle, Kevin jää yksin kotiin. Pian hän joutuu puolustamaan kotiaan murtovarkailta.", 1, 43);
			Elokuva e4 = new Elokuva("Cast away", "K13", 2000, "Chuckin lentokone putoaa Tyyneenmereen ja hän päätyy yksin autiolle saarelle. Siitä alkaa hänen selviytymisseikkailunsa.", 2, 24);
			
			e1.addSuoratoistopalvelu(s3);
			e2.addSuoratoistopalvelu(s2);
			e3.addSuoratoistopalvelu(s2);
			
			e4.addSuoratoistopalvelu(s1);
			e4.addSuoratoistopalvelu(s3);
			
			e1.addGenre(g1);
			
			e2.addGenre(g1);
			
			e3.addGenre(g2);
			
			e4.addGenre(g1);
			e4.addGenre(g5);
			e4.addGenre(g6);
			
			erepository.save(e1);
			erepository.save(e2);
			erepository.save(e3);
			erepository.save(e4);
			
			User user1 = new User("kitkat", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER", "kitkat@kitkat.fi");
			User user2 = new User("random", "$2a$10$T272ft3./nRQZ5v30vHuQuvpN2gsOrYdVDwfLFxJsXsuad5/8vmSq", "USER", "user@user.com");
			User user3 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN", "admin@elokuvalistaus.net");

			user1.addElokuva(e4);
			user1.addElokuva(e2);
			user1.addElokuva(e1);

			user2.addElokuva(e1);
			user2.addElokuva(e3);
			
			urepository.save(user1);
			urepository.save(user2);
			urepository.save(user3);
		};
	}
	
}
