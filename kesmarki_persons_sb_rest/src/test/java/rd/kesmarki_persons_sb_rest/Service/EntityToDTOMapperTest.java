package rd.kesmarki_persons_sb_rest.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rd.kesmarki_persons_sb_rest.Model.Address;
import rd.kesmarki_persons_sb_rest.Model.Contact;
import rd.kesmarki_persons_sb_rest.Model.FullDataDTO;
import rd.kesmarki_persons_sb_rest.Model.Person;
import rd.kesmarki_persons_sb_rest.Model.PersonContactDTO;
import rd.kesmarki_persons_sb_rest.Model.ResponseObject;

import static org.junit.jupiter.api.Assertions.*;

public class EntityToDTOMapperTest {

    private EntityToDTOMapper mapper;

    private static Person person1;
    private static Person person2;
    private static Address address1;
    private static Address address2;
    private static Address address3;
    private static Contact contact1;
    private static Contact contact2;


    @BeforeAll
    static void beforeAll() {

        person1 = new Person();
        person1.setId(2);
        person1.setName("Minta Péter");
        person1.setIdentityCardNo("145511HK");
        person1.setPermanentAddressId(10307);
        person1.setTemporaryAddressId(139);

        person2 = new Person();
        person2.setId(45);
        person2.setName("Teszt Márta");
        person2.setIdentityCardNo("234402PA");
        person2.setPermanentAddressId(1);
        person2.setTemporaryAddressId(null);

        address1 = new Address();
        address1.setId(139);
        address1.setCountryCode("HU");
        address1.setCity("Zalaegerszeg");
        address1.setPostalCode("8900");
        address1.setStreet("Fő utca 3.");

        address2 = new Address();
        address2.setId(10307);
        address2.setCountryCode("PL");
        address2.setCity("Warsawa");
        address2.setPostalCode("00-005");
        address2.setStreet("Walecznych 7");

        address3 = new Address();
        address3.setId(1);
        address3.setCountryCode("HU");
        address3.setCity("Siófok");
        address3.setPostalCode("8600");
        address3.setStreet("Napfény sétány 99.");

        contact1 = new Contact();
        contact1.setId(301);
        contact1.setAddressId(10307);
        contact1.setEmail("peter@outlook.pl");
        contact1.setPhoneNumber("+48-22-420-20-20");

        contact2 = new Contact();
        contact2.setId(61);
        contact2.setAddressId(139);
        contact2.setEmail("hello@mintakft.hu");
        contact2.setPhoneNumber("+36-30/8831-233");
    }

    @BeforeEach
    void beforeEach() {
        mapper = new EntityToDTOMapper();
    }

    @Test
    void testCreateFullDataDTO() {

        List<Contact> contactList = new ArrayList<>();
        FullDataDTO dtoPerson2 = mapper.createFullDataDTO(person2, address3, contactList);

        assertEquals(person2.getName(), dtoPerson2.getName());
        assertEquals(person2.getId(), dtoPerson2.getPersonId());
        assertEquals(person2.getIdentityCardNo(), dtoPerson2.getIdentityCardNo());
        assertEquals(person2.getPermanentAddressId(), dtoPerson2.getPermanentAddress().getId());
        assertEquals(address3, dtoPerson2.getPermanentAddress());
        assertEquals(contactList, dtoPerson2.getPermanentAddressContacts());
        assertEquals(contactList.size(), dtoPerson2.getPermanentAddressContacts().size());

        assertNotEquals(person1.getId(), dtoPerson2.getPersonId());
        assertNotEquals(person1.getIdentityCardNo(), dtoPerson2.getIdentityCardNo());

        assertNull(dtoPerson2.getTemporaryAddress());
        assertNull(dtoPerson2.getTemporaryAddressContacts());
    }

    @Test
    void testCreateFullDataDTO2() {

        List<Contact> contactListPermanent = Arrays.asList(contact1);
        List<Contact> contactListTemporary = Arrays.asList(contact2);
        FullDataDTO dtoPerson1 = mapper.createFullDataDTO(person1, address2, contactListPermanent, address1, contactListTemporary);
        
        assertNotNull(dtoPerson1);

        assertEquals(person1.getId(), dtoPerson1.getPersonId());
        assertEquals(person1.getIdentityCardNo(), dtoPerson1.getIdentityCardNo());
        assertEquals(person1.getName(), dtoPerson1.getName());
        assertEquals(person1.getPermanentAddressId(), dtoPerson1.getPermanentAddress().getId());
        assertEquals(person1.getTemporaryAddressId(), dtoPerson1.getTemporaryAddress().getId());
        assertEquals(contactListPermanent, dtoPerson1.getPermanentAddressContacts());
        assertEquals(contactListPermanent.get(0).getId(), dtoPerson1.getPermanentAddressContacts().get(0).getId());
        assertEquals(contactListTemporary, dtoPerson1.getTemporaryAddressContacts());
        assertEquals(
            contactListTemporary.get( (contactListTemporary.size()-1) ),
            dtoPerson1.getTemporaryAddressContacts().get( (dtoPerson1.getTemporaryAddressContacts().size()-1) )
            );
        
        assertNotEquals(person2.getId(), dtoPerson1.getPersonId());
    }

    @Test
    void testCreatePersonContactDTO() {

        List<Contact> contatList = Arrays.asList(contact1, contact2);
        PersonContactDTO dtoPerson1 = mapper.createPersonContactDTO(person1, contatList);

        assertTrue(dtoPerson1.getContacts().contains(contact2));
        assertTrue(dtoPerson1.getContacts().contains(contact1));

        assertNotEquals(person2.getId(), dtoPerson1.getPerson().getId());

        assertEquals(person1, dtoPerson1.getPerson());
        assertEquals(person1.getId(), dtoPerson1.getPerson().getId());
        assertEquals(person1.getIdentityCardNo(), dtoPerson1.getPerson().getIdentityCardNo());
        assertEquals(person1.getName(), dtoPerson1.getPerson().getName());
    }

    @Test
    void testCreateResponseObject() {

        List<Address> addressList1 = Arrays.asList(address1, address3, address2);
        ResponseObject<List<Address>> responseObject1 = mapper.createResponseObject(true, addressList1, "");

        List<Address> addressList2 = new ArrayList<>();
        ResponseObject<List<Address>> responseObject2 = mapper.createResponseObject(false, addressList2, "There is no Address from Paris in the database.");

        assertEquals(addressList1, responseObject1.getAffectedObject());
        assertEquals(addressList1.get(0).getClass(), responseObject1.getAffectedObject().get(0).getClass());
        assertEquals(addressList1.get(2).getId(), responseObject1.getAffectedObject().get(2).getId());
        assertEquals(addressList2, responseObject2.getAffectedObject());
        assertEquals(addressList2.size(), responseObject2.getAffectedObject().size());

        assertTrue(responseObject1.isTaskExecutedSuccessfully());

        assertFalse(responseObject2.isTaskExecutedSuccessfully());

        assertNotNull(responseObject2.getMessage());
    }

    @Test
    void testCreateResponseObject2() {

        ResponseObject<String> responseObject1 = mapper.createResponseObject(true, "Hello, World!");
        ResponseObject<Person> responseObject2 = mapper.createResponseObject(false, person2);

        assertEquals(responseObject1.getAffectedObject(), "Hello, World!");
        assertEquals(person2.getClass(), responseObject2.getAffectedObject().getClass());
        assertEquals(person2, responseObject2.getAffectedObject());
        assertEquals(person2.getId(), responseObject2.getAffectedObject().getId());

        assertNotEquals(responseObject1.isTaskExecutedSuccessfully(), responseObject2.isTaskExecutedSuccessfully());
        assertNotEquals(person1, responseObject2.getAffectedObject());
        assertNotEquals(53, responseObject1.getAffectedObject());
        
        assertTrue(responseObject1.isTaskExecutedSuccessfully());

        assertFalse(responseObject2.isTaskExecutedSuccessfully());

        assertNull(responseObject1.getMessage());
        assertNull(responseObject2.getMessage());
    }
}

