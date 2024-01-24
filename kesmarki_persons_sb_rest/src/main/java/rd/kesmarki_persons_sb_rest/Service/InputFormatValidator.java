package rd.kesmarki_persons_sb_rest.Service;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class InputFormatValidator {

    private final String nameRegex;
    private final String idCardNoRegex;
    private final String phoneNumberRegex;
    private final String emailAddressRegex;


    public InputFormatValidator() {

        nameRegex = "^([A-ZÁÉÚŐÓÜÖÍ]([a-záéúőóüöí.]+[ ]?)){2,}$";
        idCardNoRegex = "^[1-9][0-9]{5}[A-Z]{2}$";
        phoneNumberRegex = "^[+]?([0-9]+[ -/]?)+[0-9]$";
        emailAddressRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    }


    public boolean validatePersonNameFormat(String personName) {

        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(personName);

        return matcher.find();
    }


    public boolean validateIdCardNumberFormat(String idCardNo) {

        Pattern pattern = Pattern.compile(idCardNoRegex);
        Matcher matcher = pattern.matcher(idCardNo);

        return matcher.find();
    }


    public boolean validateCountryCode(String countryCode) {
   
        boolean validCountryCode = false;

        if(countryCode.length() == 2 && countryCode.equals(countryCode.toUpperCase())) {

            String[] countryCodes = Locale.getISOCountries();

            for(int i = 0; i < countryCodes.length; i++) {
            
                if(countryCodes[i].equals(countryCode)) {
                    validCountryCode = true;
                    break;
                }
            }

        }  

        return validCountryCode;
    }


    public boolean validatePhoneNumberFormat(String phoneNumber) {

        Pattern pattern = Pattern.compile(phoneNumberRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        
        return matcher.find();
    }


    public boolean validateEmailAddressFormat(String emailAddress) {

        Pattern pattern = Pattern.compile(emailAddressRegex);
        Matcher matcher = pattern.matcher(emailAddress);

        return matcher.find();
    }


    public boolean validateAddressIds(int permanentAddressId, Integer temporaryAddressId) {
        
        boolean differ = false;

        if(temporaryAddressId != null) {
            differ = (permanentAddressId != temporaryAddressId);
        }
        else {
            differ = true;
        }

        return differ;
    }
    
}
