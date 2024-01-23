package rd.kesmarki_persons_sb_rest.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rd.kesmarki_persons_sb_rest.Model.Address;
import rd.kesmarki_persons_sb_rest.Model.Contact;
import rd.kesmarki_persons_sb_rest.Model.Person;
import rd.kesmarki_persons_sb_rest.Model.ResponseObject;
import rd.kesmarki_persons_sb_rest.Repository.AddressesJPARepository;
import rd.kesmarki_persons_sb_rest.Repository.ContactsJPARepository;
import rd.kesmarki_persons_sb_rest.Repository.PersonsJPARepository;

@Service
public class AppService {

    @Autowired
    private InputFormatValidator validator;

    @Autowired
    private EntityToDTOMapper mapper;

    @Autowired
    private PersonsJPARepository personRepository;

    @Autowired
    private AddressesJPARepository addressRepository;

    @Autowired
    private ContactsJPARepository contactRepository;


    public ResponseObject<Person> getPersonById(int id) {

        Person person = null;
        String errorMessage = "";

        Optional<Person> personOptional = personRepository.findById(id);
        boolean isPresent = personOptional.isPresent();

        if(isPresent) {
            person = personOptional.get();
        }
        else {
            errorMessage = "Cannot find a Person with id " + id + ".";
        }

        return new ResponseObject<Person>(isPresent, errorMessage, person);
    }


    public ResponseObject<Person> savePerson(Person person) {

        if( person == null || (person != null && (person.getName() == null || person.getIdentityCardNo() == null)) ) {
            throw new IllegalArgumentException("Person object or at least one of its mandatory arguments is null.");
        }

        String errorMessage = "";

        boolean persistableName = validator.validatePersonNameFormat(person.getName());
        boolean persistableIdCardNo = validator.validateIdCardNumberFormat(person.getIdentityCardNo());
        boolean validRequest = (persistableName && persistableIdCardNo);

        if(validRequest) {

            try {
                personRepository.save(person);
            }
            catch(Exception e) {
                // primarily occurs when 'permanentAddress' is missing from the request or invalid
                errorMessage += "Person cannot be persisted because at least one of its mandatory field is null or invalid.";
                validRequest = false;
            }      
        }
        else {
            errorMessage += "Person cannot be presisted. ";

            if(persistableName == false) {
                errorMessage += "Person's name should start with a capital letter, then contain lowercase letters and single spaces. Single dot(.) and hypen(-) characters are allowed. ";
            }
    
            if(persistableIdCardNo == false) {
                errorMessage += "Person's identity card number should be composed of six numbers and two capital letters.";
            }
        }
        
        return new ResponseObject<Person>(validRequest, errorMessage, person);
    }


    public ResponseObject<Integer> deletePersonById(int id) {

        personRepository.deleteById(id);

        return new ResponseObject<Integer>(true, "", Integer.valueOf(id));
    }


    public ResponseObject<Address> getAddressById(int id) {
        
        Address address = null;
        String errorMessage = "";

        Optional<Address> addressOptional = addressRepository.findById(id);
        boolean isPresent = addressOptional.isPresent();

        if(isPresent) {
            address = addressOptional.get();
        }
        else {
            errorMessage = "Cannot find an Address with id " + id + ".";
        }

        return new ResponseObject<Address>(isPresent, errorMessage, address);
    }


    public ResponseObject<Address> saveAddress(Address address) {

        if( address == null || (address != null && address.getCountryCode() == null) ) {
            throw new IllegalArgumentException("Address object or at least one of its mandatory arguments is null.");
        }

        String errorMessage = "";

        boolean executableRequest = validator.validateCountryCode(address.getCountryCode());

        if(executableRequest) {

            try {
                addressRepository.save(address);
            }
            catch(Exception sqlExc) {
                // primarily occurs when at least one argument other than countryCode is null in the request
                errorMessage += "Address cannot be persisted because at least one of its mandatory field is null or invalid.";
                executableRequest = false;
            }          
        }
        else {
            errorMessage += "Address cannot be presisted. The country code should match an ISO 3166-1 code.";
        }
        
        return new ResponseObject<Address>(executableRequest, errorMessage, address);
    }


    public ResponseObject<Integer> deleteAddressById(int id) {

        boolean executableRequest = true;
        String errorMessage = "";

        try{
            addressRepository.deleteById(id);
        }
        catch(Exception e) {
            // primarily occurs when the given Address' id is foreign key in a different table
            errorMessage = "Address with id " + id + " cannot be removed because it is linked to a different database entity.";
            executableRequest = false;
        }

        return new ResponseObject<Integer>(executableRequest, errorMessage, Integer.valueOf(id));
    }


    public ResponseObject<Contact> getContactById(int id) {
        
        Contact contact = null;
        String errorMessage = "";

        Optional<Contact> contactOptional = contactRepository.findById(id);
        boolean isPresent = contactOptional.isPresent();

        if(isPresent) {
            contact = contactOptional.get();
        }
        else {
            errorMessage = "Cannot find a Contact with id " + id + ".";
        }

        return new ResponseObject<Contact>(isPresent, errorMessage, contact);
    }


    public ResponseObject<Contact> saveContact(Contact contact) {

        if( contact == null || (contact != null && (contact.getPhoneNumber() == null || contact.getEmail() == null)) ) {
            throw new IllegalArgumentException("Address object or at least one of its mandatory arguments is null.");
        }

        String errorMessage = "";

        boolean validPhoneNumber = validator.validatePhoneNumberFormat(contact.getPhoneNumber());
        boolean validEmail = validator.validateEmailAddressFormat(contact.getEmail());
        boolean executableRequest = (validPhoneNumber && validEmail);

        if(executableRequest) {

            try {
                contactRepository.save(contact);
            }
            catch(Exception e) {
                // primarily occurs when the addressId does not exist in the database
                errorMessage += "Contact cannot be persisted because the address id is invalid.";
                executableRequest = false;
            }          
        }
        else {
            errorMessage += "Contact cannot be presisted. ";

            if(validPhoneNumber == false) {
                errorMessage += "Phone number should only contain numbers and single separator characters. " 
                + "Separators can be spaces, hypens(-), and slashes(/). The international format starting with a plus(+) is also allowed. ";
            }

            if(validEmail == false) {
                errorMessage += "The following email address format is accepted: \"username@domain.com\" " 
                + "Username can contain English letters, numbers, and the following characters: _ ! # $ % & ' * + / = ? ` { | } ~ ^ . - " 
                + "The domain name can only be composed of English letters, numbers, dots(.), and hypens(-).";
            }
        }
        
        return new ResponseObject<Contact>(executableRequest, errorMessage, contact);
    }


    public ResponseObject<Integer> deleteContactById(int id) {

        contactRepository.deleteById(id);

        return new ResponseObject<Integer>(true, "", Integer.valueOf(id));
    }
    
}
