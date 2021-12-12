package hh.palvelinohjelmointi.elokuvaprojekti.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SuoratoistopalveluRepository extends CrudRepository<Suoratoistopalvelu, Long> {

	List<Suoratoistopalvelu> findByNimi(String nimi);
}
