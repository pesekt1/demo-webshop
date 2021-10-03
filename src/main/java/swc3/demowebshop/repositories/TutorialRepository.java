package swc3.demowebshop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;
import swc3.demowebshop.entities.Tutorial;
import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

	//The implementation is plugged in by Spring Data JPA automatically.
	List<Tutorial> findByPublished(boolean published);
	List<Tutorial> findByTitleContaining(String title);

	//overloaded methods using Pageable parameter:
	@RestResource(
			path = "findByPublishedPaged",
			rel = "findByPublishedPaged") //to diferentiate, there cannot be multiple same endpoints for DATA-REST
	Page<Tutorial> findByPublished(boolean published, Pageable pageable);

	@RestResource(exported = false) //to exclude it from DATA-REST
	Page<Tutorial> findByTitleContaining(String title, Pageable pageable);


	//********************* custom queries **********************************

	//JPQL - Java Persistence Query Language
	@Query("SELECT t FROM Tutorial t WHERE t.published = true")
	List<Tutorial> findAllPublished();

	//native query
	@Query(
			value = "SELECT * FROM Tutorials t WHERE t.published = true ",
			nativeQuery = true) // this enables native SQL
	List<Tutorial> findAllPublishedNative();

	//indexed parameters in JPQL:
	@Query("SELECT t FROM Tutorial t WHERE t.published = ?1 and t.id > ?2")
	List<Tutorial> findPublishedWithHigherId(Boolean published, Long id);

	//named parameters in JPQL:
	@RestResource(
			path = "findByPublishedNamedParams",
			rel = "findByPublishedNamedParams")
	@Query("SELECT t FROM Tutorial t WHERE t.published = :published and t.id > :id ")
	List<Tutorial> findPublishedWithHigherId(
			@Param("id") Long id,
			@Param("published") Boolean published);

	//TODO: Does not work, it needs @EnableTransactionManagement on the datasource
	//modifying query
	@Modifying
	@Transactional
	@Query("UPDATE Tutorial t SET t.published = true WHERE t.id = :id")
	void publishById(@Param("id") Long id);
}
