package rd.kesmarki_persons_sb_rest.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rd.kesmarki_persons_sb_rest.Model.Address;

@Repository
public interface AddressesJPARepository extends JpaRepository<Address, Integer> {

    List<Address> findByCity(String city);
    
}
