package rd.kesmarki_persons_sb_rest.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FullDataDTO {
    
    private int personId;
    private String name;
    private String identityCardNo;
    private Address permanentAddress;
    private List<Contact> permanentAddressContacts;
    private Address temporaryAddress;
    private List<Contact> temporaryAddressContacts;

}
