package rd.kesmarki_persons_sb_rest.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ResponseObject<T> {
    
    private boolean taskExecutedSuccessfully;
    private String message;
    private T affectedObject;

}
