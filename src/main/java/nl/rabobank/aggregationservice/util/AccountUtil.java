package nl.rabobank.aggregationservice.util;

/**
 * Utility methods to be applied on bank accounts.
 */
public final class AccountUtil {

    private AccountUtil() {
        throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Extracts the account number from given account.
     * <p>
     * For example, given account might be a Dutch IBAN.
     * In that case this method then extract its account number.
     *
     * @param account the account
     * @return the account number
     */
    public static String extractAccountNumber(final String account) {
        // TODO: improve implementation by using regex
        return account.substring(8);
    }
}
