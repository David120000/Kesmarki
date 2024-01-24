package rd.kesmarki_persons_sb_rest.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rd.kesmarki_persons_sb_rest.Model.Address;
import rd.kesmarki_persons_sb_rest.Model.Contact;
import rd.kesmarki_persons_sb_rest.Model.FullDataDTO;
import rd.kesmarki_persons_sb_rest.Model.Person;
import rd.kesmarki_persons_sb_rest.Model.PersonContactDTO;
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

        ResponseObject<Person> responseObject = mapper.createResponseObject(isPresent, person, errorMessage);

        return responseObject;
    }


    public ResponseObject<List<Person>> getPersonByName(String name) {

        boolean personsFound = true;
        String errorMessage = null;

        List<Person> personListByName = personRepository.findByName(name);

        if(personListByName.size() == 0) {
            errorMessage = "There is no Person in the database with the name " + name + ".";
            personsFound = false;
        }

        ResponseObject<List<Person>> responseObject = mapper.createResponseObject(personsFound, personListByName, errorMessage);

        return responseObject;
    }


    public ResponseObject<Person> savePerson(Person person) {

        if( person == null || (person != null && (person.getName() == null || person.getIdentityCardNo() == null)) ) {
            throw new IllegalArgumentException("Person object or at least one of its mandatory arguments is null.");
        }

        String errorMessage = "";

        boolean persistableName = validator.validatePersonNameFormat(person.getName());
        boolean persistableIdCardNo = validator.validateIdCardNumberFormat(person.getIdentityCardNo());
        boolean persistableAddressIds = validator.validateAddressIds(person.getPermanentAddressId(), person.getTemporaryAddressId());
        boolean validRequest = (persistableName && persistableIdCardNo && persistableAddressIds);

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
                errorMessage += "Person's identity card number should be composed of six numbers and two capital letters. ";
            }

            if(persistableAddressIds == false) {
                errorMessage += "Person's permanent and temporary addresses should be different.";
            }
        }

        ResponseObject<Person> responseObject = mapper.createResponseObject(validRequest, person, errorMessage);
        
        return responseObject;
    }


    public ResponseObject<Integer> deletePersonById(int id) {

        personRepository.deleteById(id);
        ResponseObject<Integer> responseObject = mapper.createResponseObject(true, Integer.valueOf(id));

        return responseObject;
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

        ResponseObject<Address> responseObject = mapper.createResponseObject(isPresent, address, errorMessage);

        return responseObject;
    }


    public ResponseObject<List<Address>> getAddressByCity(String city) {

        boolean addressFound = true;
        String errorMessage = null;

        List<Address> addressListByCity = addressRepository.findByCity(city);

        if(addressListByCity.size() == 0) {
            errorMessage = "There is no Address from " + city + " in the database.";
            addressFound = false;
        }

        ResponseObject<List<Address>> responseObject = mapper.createResponseObject(addressFound, addressListByCity, errorMessage);

        return responseObject;
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

        ResponseObject<Address> responseObject = mapper.createResponseObject(executableRequest, address, errorMessage);
        
        return responseObject;
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

        ResponseObject<Integer> responseObject = mapper.createResponseObject(executableRequest, Integer.valueOf(id), errorMessage);

        return responseObject;
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

        ResponseObject<Contact> responseObject = mapper.createResponseObject(isPresent, contact, errorMessage);

        return responseObject;
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

        ResponseObject<Contact> responseObject = mapper.createResponseObject(executableRequest, contact, errorMessage);
        
        return responseObject;
    }


    public ResponseObject<Integer> deleteContactById(int id) {

        contactRepository.deleteById(id);
        ResponseObject<Integer> responseObject = mapper.createResponseObject(true, Integer.valueOf(id));

        return responseObject;
    }


    public ResponseObject<FullDataDTO> getFullDataDTOByPersonId(int id) {
        
        FullDataDTO dto = null;

        ResponseObject<Person> personResponse = this.getPersonById(id);
        String errorMessage = personResponse.getMessage() + " ";

        if(personResponse.isTaskExecutedSuccessfully()) {

            dto = this.getFullDataDTOByPerson(personResponse.getAffectedObject());
            this.extendFullDataDTOErrorMessage(errorMessage, dto);
        }

        ResponseObject<FullDataDTO> responseObject = mapper.createResponseObject(personResponse.isTaskExecutedSuccessfully(), dto, errorMessage);

        return responseObject;
    }


    public ResponseObject<List<FullDataDTO>> getFullDataDTOListByPersonName(String name) {

        List<FullDataDTO> dtoList = new ArrayList<>();
        boolean recordsFound = true;
        String errorMessage = "";

        ResponseObject<List<Person>> personListResponse = this.getPersonByName(name);

        if(personListResponse.getAffectedObject().size() > 0) {

            for(Person person : personListResponse.getAffectedObject()) {

                FullDataDTO dto = this.getFullDataDTOByPerson(person);
                this.extendFullDataDTOErrorMessage(errorMessage, dto);
                dtoList.add(dto);
            }
        }
        else {
            recordsFound = false;
            errorMessage = personListResponse.getMessage();
        }

        ResponseObject<List<FullDataDTO>> responseObject = mapper.createResponseObject(recordsFound, dtoList, errorMessage);

        return responseObject;
    }


    private FullDataDTO getFullDataDTOByPerson(Person person) {

        Address permanentAddress = this.getAddressById( person.getPermanentAddressId() ).getAffectedObject();
        Address temporaryAddress = null;

        if(person.getTemporaryAddressId() != 0) {
            temporaryAddress = this.getAddressById( person.getTemporaryAddressId() ).getAffectedObject();
        }

        List<Contact> permanentAddressContacts = contactRepository.findContactByAddressId( permanentAddress.getId() );
        List<Contact> temporaryAddressContacts = null;

        if(temporaryAddress != null) {
            temporaryAddressContacts = contactRepository.findContactByAddressId( temporaryAddress.getId() );
        }

        FullDataDTO dto = mapper.createFullDataDTO(person, permanentAddress, permanentAddressContacts, temporaryAddress, temporaryAddressContacts);

        return dto;
    }


    private void extendFullDataDTOErrorMessage(String errorMessageToBeExtended, FullDataDTO dto) {

        String messagePrefix = "Please note that " + dto.getName() + " (id " + dto.getPersonId() + ") ";

        if(dto.getPermanentAddressContacts().size() == 0) {
            errorMessageToBeExtended += messagePrefix + " permanent address (id " + dto.getPermanentAddress().getId() + ") has no contacts. ";
        }

        if(dto.getTemporaryAddress() == null) {
            errorMessageToBeExtended += messagePrefix + " has no temporary address. ";
        }

        if(dto.getTemporaryAddress() != null && dto.getTemporaryAddressContacts().size() == 0) {
            errorMessageToBeExtended += messagePrefix + " temporary address (id " + dto.getTemporaryAddress().getId() + ") has no contacts. ";
        }
    }


    public ResponseObject<List<FullDataDTO>> getFullDataDTOListByCity(String city) {

        List<FullDataDTO> dtoList = new ArrayList<>();
        boolean recordsFound = true;
        String errorMessage = "";

        ResponseObject<List<Address>> addressListResponse = this.getAddressByCity(city);

        if(addressListResponse.getAffectedObject().size() > 0) {

            for(Address address : addressListResponse.getAffectedObject()) {

                List<Person> peopleAtThisAddress = personRepository.findByAddressId(address.getId());

                for(Person person : peopleAtThisAddress) {
                    
                    FullDataDTO dto = this.getFullDataDTOByPerson(person);
                    this.extendFullDataDTOErrorMessage(errorMessage, dto);
                    dtoList.add(dto);
                }
            }
        }
        else {
            recordsFound = false;
            errorMessage = addressListResponse.getMessage();
        }

        ResponseObject<List<FullDataDTO>> responseObject = mapper.createResponseObject(recordsFound, dtoList, errorMessage);

        return responseObject;
    }


    public ResponseObject<PersonContactDTO> getPersonContactDTOByPersonId(int id) {

        PersonContactDTO dto = null;

        ResponseObject<Person> personResponse = this.getPersonById(id);
        String errorMessage = personResponse.getMessage() + " ";

        if(personResponse.isTaskExecutedSuccessfully()) {

            Person person = personResponse.getAffectedObject();
            dto = this.getPersonContactDTOByPerson(person); 
            
            if(dto.getContacts().size() == 0) {
                errorMessage += "Please note that " + person.getName() + " (id " + person.getId() + ") "
                + "has no contacts in the database. ";
            }
        }

        ResponseObject<PersonContactDTO> responseObject = mapper.createResponseObject(personResponse.isTaskExecutedSuccessfully(), dto, errorMessage);

        return responseObject;
    }


    public ResponseObject<List<PersonContactDTO>> getPersonContactDTOListByPersonName(String name) {

        List<PersonContactDTO> dtoList = new ArrayList<>();
        boolean recordsFound = true;
        String errorMessage = "";

        ResponseObject<List<Person>> personListResponse = this.getPersonByName(name);

        if(personListResponse.getAffectedObject().size() > 0) {

            for(Person person : personListResponse.getAffectedObject()) {

                PersonContactDTO dto = getPersonContactDTOByPerson(person);
                dtoList.add(dto);

                if(dto.getContacts().size() == 0) {
                    errorMessage += "Please note that " + person.getName() + " (id " + person.getId() + ") "
                    + "has no contacts in the database. ";
                }
            }
        }
        else {
            recordsFound = false;
            errorMessage = personListResponse.getMessage();
        }

        ResponseObject<List<PersonContactDTO>> responseObject = mapper.createResponseObject(recordsFound, dtoList, errorMessage);

        return responseObject;
    }


    private PersonContactDTO getPersonContactDTOByPerson(Person person) {

        List<Contact> contacts = new ArrayList<>();

        List<Contact> contactsAtPermanentAddress = contactRepository.findContactByAddressId( person.getPermanentAddressId() );
        List<Contact> contactsAtTemporaryAddress = null;

        if(person.getTemporaryAddressId() != null) {
            contactsAtTemporaryAddress = contactRepository.findContactByAddressId( person.getTemporaryAddressId() );
        }
        
        contacts.addAll(contactsAtPermanentAddress);

        if(contactsAtTemporaryAddress != null) {
            contacts.addAll(contactsAtTemporaryAddress);
        }

        PersonContactDTO dto = mapper.createPersonContactDTO(person, contactsAtTemporaryAddress);

        return dto;
    }


    public ResponseObject<Integer> deleteAllPersonDataByPersonId(int id) {

        String errorMessage = "";

        ResponseObject<Person> personResponse = this.getPersonById(id);

        if(personResponse.isTaskExecutedSuccessfully()) {
            // get the person object for its address ids, then delete it from the database
            Person person = personResponse.getAffectedObject();
            this.deletePersonById(id);
            
            List<Person> peopleAtThisAddress = personRepository.findByAddressId( person.getPermanentAddressId() );
            // check how many other people are connected to the person's permanent address:
            if(peopleAtThisAddress.size() == 0) {

                List<Contact> contactsForThisAddress = contactRepository.findContactByAddressId( person.getPermanentAddressId() );

                for(Contact contact : contactsForThisAddress) {
                    
                    this.deleteContactById(contact.getId());
                }
                // when there are no more persons or contacts bound to the permanent address, remove it:
                this.deleteAddressById( person.getPermanentAddressId() );

                if(person.getTemporaryAddressId() != null) {
                    
                    peopleAtThisAddress = personRepository.findByAddressId( person.getTemporaryAddressId() );
                    // check how many other people are connected to the person's temporary address:
                    if(peopleAtThisAddress.size() == 0) {

                        contactsForThisAddress = contactRepository.findContactByAddressId( person.getTemporaryAddressId() );

                        for(Contact contact : contactsForThisAddress) {
                            
                            this.deleteContactById(contact.getId());
                        }
                        // when there are no more persons or contacts bound to the temporary address, remove it:
                        this.deleteAddressById( person.getTemporaryAddressId() );
                    }
                    else {
                        errorMessage += "The temporary address id " + person.getTemporaryAddressId() + " connected to " + person.getName() + " (id " + person.getId() + ") cannot be removed " 
                        + "beacuse other people also were bound to this address. ";
                    }
                }


            }
            else {
                errorMessage += "The permanent address id " + person.getPermanentAddressId() + " connected to " + person.getName() + " (id " + person.getId() + ") cannot be removed " 
                + "beacuse other people also were bound to this address. ";
            }
        }
        else {
            errorMessage += personResponse.getMessage();
        }

        ResponseObject<Integer> responseObject = mapper.createResponseObject(personResponse.isTaskExecutedSuccessfully(), Integer.valueOf(id), errorMessage);

        return responseObject;
    }

    
}
