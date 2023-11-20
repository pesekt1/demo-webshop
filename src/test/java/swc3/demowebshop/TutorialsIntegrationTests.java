package swc3.demowebshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import swc3.demowebshop.entities.Tutorial;
import swc3.demowebshop.repositories.TutorialRepository;
import static org.assertj.core.api.Assertions.assertThat;

//spring boot will do all the work on the database within a transaction and then it rolls back.
//so the database is not affected by the tests.
//@SpringBootTest
//@AutoConfigureDataJpa
//@Transactional
//class TutorialsIntegrationTests {
//
//    @Autowired
//    private TutorialRepository repository;
//
//    @BeforeEach
//    public void init(){
//        repository.deleteAll();
//    }
//
//    @Test
//    void should_find_no_tutorials_if_repository_is_empty() {
//        Iterable<Tutorial> tutorials = repository.findAll();
//        assertThat(tutorials).isEmpty();
//    }
//
//    @Test
//    void should_store_a_tutorial() {
//        Tutorial tutorial = repository.save(new Tutorial("Tut title", "Tut desc", true));
//        assertThat(tutorial)
//            .hasFieldOrPropertyWithValue("title", "Tut title")
//            .hasFieldOrPropertyWithValue("description", "Tut desc")
//            .hasFieldOrPropertyWithValue("published", true);
//    }
//
//}
