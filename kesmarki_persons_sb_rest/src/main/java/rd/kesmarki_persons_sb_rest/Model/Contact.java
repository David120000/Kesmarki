package rd.kesmarki_persons_sb_rest.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="contacts")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="address_id")
    private int addressId;

    @Column(name="phone_number")
    private String phoneNumber;

    private String email;
    
}
