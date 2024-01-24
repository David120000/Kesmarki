package rd.kesmarki_persons_sb_rest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rd.kesmarki_persons_sb_rest.Model.Address;
import rd.kesmarki_persons_sb_rest.Model.Contact;
import rd.kesmarki_persons_sb_rest.Model.Person;
import rd.kesmarki_persons_sb_rest.Model.ResponseObject;
import rd.kesmarki_persons_sb_rest.Service.AppService;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    @Autowired
    private AppService appService;


    @GetMapping("/person/get/{id}")
    public ResponseEntity<ResponseObject<Person>> getPersonById(@PathVariable(name="id") int id) {
        return ResponseEntity.ok(appService.getPersonById(id));
    }


    @PostMapping("/person/save")
    public ResponseEntity<ResponseObject<Person>> savePerson(@RequestBody Person person) {
        return ResponseEntity.ok(appService.savePerson(person));
    }


    @GetMapping("/person/delete")
    public ResponseEntity<ResponseObject<Integer>> deletePersonById(@RequestParam(name="id") int id) {
        return ResponseEntity.ok(appService.deletePersonById(id));
    }


    @GetMapping("/address/get/{id}")
    public ResponseEntity<ResponseObject<Address>> getAddressById(@PathVariable(name="id") int id) {
        return ResponseEntity.ok(appService.getAddressById(id));
    }


    @PostMapping("/address/save")
    public ResponseEntity<ResponseObject<Address>> saveAddress(@RequestBody Address address) {
        return ResponseEntity.ok(appService.saveAddress(address));
    }


    @GetMapping("/address/delete")
    public ResponseEntity<ResponseObject<Integer>> deleteAddressById(@RequestParam(name="id") int id) {
        return ResponseEntity.ok(appService.deleteAddressById(id));
    }


    @GetMapping("/contact/get/{id}")
    public ResponseEntity<ResponseObject<Contact>> getContactById(@PathVariable(name="id") int id) {
        return ResponseEntity.ok(appService.getContactById(id));
    }


    @PostMapping("/contact/save")
    public ResponseEntity<ResponseObject<Contact>> saveContact(@RequestBody Contact contact) {
        return ResponseEntity.ok(appService.saveContact(contact));
    }


    @GetMapping("/contact/delete")
    public ResponseEntity<ResponseObject<Integer>> deleteContactById(@RequestParam(name="id") int id) {
        return ResponseEntity.ok(appService.deleteContactById(id));
    }
}
