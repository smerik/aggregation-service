package nl.rabobank.aggregationservice.client;

import lombok.extern.slf4j.Slf4j;
import nl.rabobank.aggregationservice.client.exception.PowerOfAttorneyServiceClientResponseErrorHandler;
import nl.rabobank.aggregationservice.client.model.PowerOfAttorneyDTO;
import nl.rabobank.aggregationservice.client.model.PowerOfAttorneyReferenceDTO;
import nl.rabobank.aggregationservice.client.model.account.AccountDTO;
import nl.rabobank.aggregationservice.client.model.card.CreditCardDTO;
import nl.rabobank.aggregationservice.client.model.card.DebitCardDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Client for communicating with the Power of Attorney Service API.
 */
@Slf4j
@Component
public class PowerOfAttorneyServiceClient {

    private final RestTemplate restTemplate;
    private final String url;

    public PowerOfAttorneyServiceClient(final RestTemplateBuilder restTemplateBuilder,
                                        final PowerOfAttorneyServiceClientResponseErrorHandler errorHandler,
                                        @Value("${power-of-attorney.url}") final String url,
                                        @Value("${power-of-attorney.timeout.connect:5000}") final long connectTimeout,
                                        @Value("${power-of-attorney.timeout.read:5000}") final long readTimeout) {
        this.restTemplate = restTemplateBuilder
                .errorHandler(errorHandler)
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
        this.url = url;
    }

    /**
     * Provides a list of power of attorneys for current user.
     *
     * @return power of attorneys for current user
     */
    public PowerOfAttorneyReferenceDTO[] getPowerOfAttorneys() {
        LOG.debug("getPowerOfAttorneys()...");
        return restTemplate.getForObject(url + "/power-of-attorneys", PowerOfAttorneyReferenceDTO[].class);
    }

    /**
     * Get Detail of Power of Attorney.
     *
     * @param id the power of attorney identifier
     * @return power of attorney details
     */
    public PowerOfAttorneyDTO getPowerOfAttorney(final String id) {
        LOG.debug("getPowerOfAttorney({})...", id);
        return restTemplate.getForObject(url + "/power-of-attorneys/{id}", PowerOfAttorneyDTO.class, id);
    }

    /**
     * Get details of given debit card.
     *
     * @param id the debit card identifier
     * @return debit card details
     */
    public DebitCardDTO getDebitCard(final String id) {
        LOG.debug("getDebitCard({})...", id);
        return restTemplate.getForObject(url + "/debit-cards/{id}", DebitCardDTO.class, id);
    }

    /**
     * Get details of given credit card.
     *
     * @param id the credit card identifier
     * @return credit card details
     */
    public CreditCardDTO getCreditCard(final String id) {
        LOG.debug("getCreditCard({})...", id);
        return restTemplate.getForObject(url + "/credit-cards/{id}", CreditCardDTO.class, id);
    }

    /**
     * Gets information for given account.
     *
     * @param account the account to get information for
     * @return the account information
     */
    public AccountDTO getAccount(final String account) {
        LOG.debug("getAccount({})...", account);
        return restTemplate.getForObject(url + "/accounts/{id}", AccountDTO.class, account);
    }
}
