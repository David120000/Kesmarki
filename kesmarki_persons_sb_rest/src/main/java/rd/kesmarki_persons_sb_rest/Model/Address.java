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
@Table(name="addresses")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Address {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="country_code")
    private String countryCode;

    private String city;

    @Column(name="postal_code")
    private String postalCode;

    private String street;
    
}
