package hh.palvelinohjelmointi.elokuvaprojekti.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);
	
	@Query(value = "SELECT * "
			+ "FROM KAYTTAJAT "
			+ "JOIN EKUVA_KAYTTAJAT "
			+ "ON ID = KAYTTAJA_ID "
			+ "WHERE ELOKUVA_ID = :elokuvaId", 
			nativeQuery = true)
	Iterable<User> findByElokuvaId(Long elokuvaId);
	
}