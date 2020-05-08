package nl.rabobank.aggregationservice.client;

import nl.rabobank.aggregationservice.client.exception.PowerOfAttorneyServiceClientResponseErrorHandler;
import nl.rabobank.aggregationservice.client.model.PowerOfAttorneyDTO;
import nl.rabobank.aggregationservice.client.model.PowerOfAttorneyReferenceDTO;
import nl.rabobank.aggregationservice.client.model.account.AccountDTO;
import nl.rabobank.aggregationservice.client.model.card.CreditCardDTO;
import nl.rabobank.aggregationservice.client.model.card.DebitCardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PowerOfAttorneyServiceClientTest {

    private static final String DEFAULT_URL = "http://poa.local/api";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    RestTemplateBuilder restTemplateBuilder;

    @Mock
    RestTemplate restTemplate;

    private PowerOfAttorneyServiceClient client;

    @BeforeEach
    void setup() {
        final PowerOfAttorneyServiceClientResponseErrorHandler errorHandler = new PowerOfAttorneyServiceClientResponseErrorHandler();
        when(restTemplateBuilder.errorHandler(errorHandler)
                .setConnectTimeout(any(Duration.class))
                .setReadTimeout(any(Duration.class))
                .build())
                .thenReturn(restTemplate);

        client = new PowerOfAttorneyServiceClient(
                restTemplateBuilder,
                errorHandler,
                DEFAULT_URL,
                10,
                20
        );
    }

    @Test
    void getPowerOfAttorneys() {
        client.getPowerOfAttorneys();
        verify(restTemplate).getForObject("http://poa.local/api/power-of-attorneys", PowerOfAttorneyReferenceDTO[].class);
    }

    @Test
    void getPowerOfAttorney() {
        client.getPowerOfAttorney("0001");
        verify(restTemplate).getForObject("http://poa.local/api/power-of-attorneys/{id}", PowerOfAttorneyDTO.class, "0001");
    }

    @Test
    void getDebitCard() {
        client.getDebitCard("1111");
        verify(restTemplate).getForObject("http://poa.local/api/debit-cards/{id}", DebitCardDTO.class, "1111");
    }

    @Test
    void getCreditCard() {
        client.getCreditCard("2222");
        verify(restTemplate).getForObject("http://poa.local/api/credit-cards/{id}", CreditCardDTO.class, "2222");
    }

    @Test
    void getAccount() {
        client.getAccount("123456789");
        verify(restTemplate).getForObject("http://poa.local/api/accounts/{id}", AccountDTO.class, "123456789");
    }
}