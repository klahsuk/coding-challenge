package coding_challenge.model;

import coding_challenge.exception.InvalidTransactionException;
import jakarta.persistence.Embeddable;

@Embeddable
public class IBAN {
    private String iban;

    public IBAN(String ibanInput) {
        if (ibanInput == null || ibanInput.length() != 22) {
            throw new InvalidTransactionException("IBAN format is incorrect: " + ibanInput);
        }

        String iban = ibanInput.toUpperCase();
        String countryCode = iban.substring(0,2);

        if(!countryCode.matches("[A-Z]{2}")) {
            throw new InvalidTransactionException("IBAN Country Code is invalid: " + countryCode);
        }

        this.iban = iban.toUpperCase();
    }

    public IBAN(){
        super();
    }
}
