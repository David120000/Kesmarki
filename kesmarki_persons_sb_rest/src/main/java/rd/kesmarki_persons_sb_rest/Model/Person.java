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
@Table(name="persons")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String identityCardNo;

    @Column(name="permanent_address_id")
    private int permanentAddressId;

    @Column(name="temporary_address_id")
    private int temporaryAddressId;

}
