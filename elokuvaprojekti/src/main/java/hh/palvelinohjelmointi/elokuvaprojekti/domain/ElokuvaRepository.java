package hh.palvelinohjelmointi.elokuvaprojekti.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface ElokuvaRepository extends CrudRepository<Elokuva, Long>  {


	List<Elokuva> findByNimi(String nimi);

	@Query(value = "SELECT * "
			+ "FROM ELOKUVA "
			+ "JOIN EKUVA_GENRET "
			+ "ON ID = ELOKUVA_ID "
			+ "WHERE GENRE_ID = :genreId", 
			nativeQuery = true)
	Iterable<Elokuva> findByGenreId(Long genreId);
	
	@Query(value = "SELECT * "
			+ "FROM ELOKUVA "
			+ "JOIN EKUVA_SPALVELUT "
			+ "ON ID = ELOKUVA_ID "
			+ "WHERE SPALVELU_ID = :spalveluId", 
			nativeQuery = true)
	Iterable<Elokuva> findBySpalveluId(Long spalveluId);
}
