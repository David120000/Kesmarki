package rd.kesmarki_persons_sb_rest.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rd.kesmarki_persons_sb_rest.Model.Contact;

@Repository
public interface ContactsJPARepository extends JpaRepository<Contact, Integer> {
    
    List<Contact> findContactByAddressId(int id);

}
