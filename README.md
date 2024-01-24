# Kesmarki Application Project

This microservice is a REST-style Spring Boot application handling personal data, like name, address, and the contacts for each addresses.
The application runs on Java 17 with Spring Boot 3.2.2, and connects to Microsoft SQL Server 2019.

## Spring dependencies

- Spring Web
- Spring Data JPA
- MS SQL Driver
- Lombok


## API Endpoint Documentation
General return value format:

responseObject {
- taskExecutedSuccessfully: boolean,
- message: string,
- affectedObject: type of the API's returned object
}


### PERSON Objects
Person {
- id: int,
- name: string,
- identityCardNo: string (8 characters, 6 number and 2 capital letters),
- permanentAddressId: int (refers to an Address object's id),
- temporaryAddressId: int | null (refers to an Address object's id)
}


GET /api/person/get/{id}
- Pathvariable: INT as id of a Person
- Returns: Person object or null if no such found with the given id

POST /api/person/save
- RequestBody: PERSON object
- Returns: The persisted Person object

GET /api/person/delete
- RequestParam: INT as id of a Person
- Returns: The id of the deleted Person object


### ADDRESS Objects
Address {
- id: int,
- countryCode: string (2 characters of ISO 3166-1 codes),
- city: string,
- postalCode: string,
- street: string
}


GET /api/address/get/{id}
- PathVariable: INT as id of an Address
- Returns: Address object or null if no such found with the given id

POST /api/address/save
- RequestBody: ADDRESS object
- Returns: The persisted Address object

GET /api/address/delete
- RequestParam: INT as id of an Address
- Returns: INT as the id of the deleted Address object


### CONTACT Objects
Contact {
- id: int,
- addressId: int (refers to an Address object's id),
- phoneNumber: string,
- email: string
}


GET /api/contact/get/{id}
- PathVariable: INT as id of a Contact
- Returns Contact object or null if no such found with the given id

POST /api/contact/save
- RequestBody: CONTACT object
- Returns: The persisted Contact object

GET /api/contact/delete
- RequestParam: INT as id of a Contact
- Returns: The id of the deleted Contact object


### FULLDATA Objects
FullData {
- personId: int,
- name: string,
- identityCardNo: string (8 characters, 6 number and 2 capital letters),
- permanentAddress: int (refers to an Address object's id),
- permanentAddressContacts: Contact[],
- temporaryAddress: int | null (refers to an Address object's id),
- temporaryAddressContacts: Contact[] | null
}


GET /api/fulldata/get/bypersonid
- RequestParam: INT as id of a Person
- Returns: FullData or null if no Person is found with the given id

GET /api/fulldata/list/bypersonname
- RequestParam: STRING as Person name to look up by exact match
- Returns: FullData[] or empty array if no match is found

GET /api/fulldata/list/bycity
- RequestParam: STRING as the city parameter of Address objects to look up by exact match
- Returns: FullData[] or empty array if no match is found

GET /api/fulldata/delete
- This request deletes the given Person and the connected Address and Contact data if no other Entries refer to those.
- RequestParam: INT as id of a Person
- Returns: INT as the id of the deleted Person object's id


### PERSONCONTACT Objects
PersonContact {
- person: Person,
- contacts: Contact[]
}


GET /api/personandcontact/get/bypersonid
- RequestParam: INT as id of a Person
- Returns: PersonContact or null if  no Person is found with the given id

GET /api/personandcontact/list/bypersonname
- RequestParam: STRING as Person name to look up by exact match
- Returns: PersonContact[] or empty array if no match is found
