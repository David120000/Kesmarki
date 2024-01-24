package rd.kesmarki_persons_sb_rest.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rd.kesmarki_persons_sb_rest.Model.Person;

@Repository
public interface PersonsJPARepository extends JpaRepository<Person, Integer> {
    
    List<Person> findByName(String name);
    
    @Query("SELECT p FROM Person p WHERE p.permanentAddressId = :addressid OR p.temporaryAddressId = :addressid")
    List<Person> findByAddressId(@Param("addressid") int addressId);

    
}
