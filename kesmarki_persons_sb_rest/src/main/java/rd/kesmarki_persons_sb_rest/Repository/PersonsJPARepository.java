package rd.kesmarki_persons_sb_rest.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rd.kesmarki_persons_sb_rest.Model.Person;

@Repository
public interface PersonsJPARepository extends JpaRepository<Person, Integer> {
    
    List<Person> findByName(String name);
    
}
