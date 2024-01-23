package rd.kesmarki_persons_sb_rest.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import rd.kesmarki_persons_sb_rest.Model.Address;
import rd.kesmarki_persons_sb_rest.Model.Contact;
import rd.kesmarki_persons_sb_rest.Model.FullDataDTO;
import rd.kesmarki_persons_sb_rest.Model.Person;
import rd.kesmarki_persons_sb_rest.Model.PersonContactDTO;
import rd.kesmarki_persons_sb_rest.Model.ResponseObject;

@Service
public class EntityToDTOMapper {
    
    public <T> ResponseObject<T> createResponseObject(boolean taskSuccess, T affectedObject, String message) {

        return new ResponseObject<T>(taskSuccess, message, affectedObject);
    }


    public <T> ResponseObject<T> createResponseObject(boolean taskSuccess, T affectedObject) {   

        return this.createResponseObject(taskSuccess, affectedObject, null);
    }


    public FullDataDTO createFullDataDTO(
            Person person, 
            Address permanentAddress, 
            List<Contact> permanentAddressContacts, 
            Address temporaryAddress, 
            List<Contact> temporaryAddressContatcs) 
    {

        FullDataDTO dto = new FullDataDTO(
            person.getId(),
            person.getName(),
            person.getIdentityCardNo(),
            permanentAddress,
            permanentAddressContacts,
            temporaryAddress,
            temporaryAddressContatcs
        );

        return dto;
    }


    public FullDataDTO createFullDataDTO(Person person, Address permanentAddress, List<Contact> permanentAddressContacts) {  

        return this.createFullDataDTO(person, permanentAddress, permanentAddressContacts, null, null);
    }


    public PersonContactDTO createPersonContactDTO(Person person, List<Contact> contacts) {

        return new PersonContactDTO(person, contacts);
    }


}
