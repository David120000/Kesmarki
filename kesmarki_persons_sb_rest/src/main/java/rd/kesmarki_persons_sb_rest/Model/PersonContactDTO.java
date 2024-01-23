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
public class PersonContactDTO {

    private Person person;
    private List<Contact> contacts;
    
}
