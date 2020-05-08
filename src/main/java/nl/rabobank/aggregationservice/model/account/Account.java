package nl.rabobank.aggregationservice.model.account;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Account {

    /**
     * The International Bank Account Number (IBAN).
     */
    private String iban;

    private String owner;
    private Long balance;
    private LocalDate created;
    private LocalDate ended;

    /**
     * Returns true when the account is active; false otherwise.
     *
     * @return true when active; false otherwise
     */
    public boolean isActive() {
        return ended == null || LocalDate.now().isBefore(ended);
    }

    /**
     * Returns true when given owner is authorized for this bank account; false otherwise.
     *
     * @param owner the owner name to check the authorization for
     * @return true when authorized; false otherwise
     */
    public boolean isAuthorized(final String owner) {
        if (owner == null) {
            return false;
        }
        return this.owner.equals(owner);
    }
}
