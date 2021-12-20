package com.flexbank.ws.client.ibanapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexbank.ws.configuration.ibanapi.IbanApiConfiguration;
import com.flexbank.ws.configuration.ibanapi.IbanApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class IbanApiClient {

    private final RestTemplate restTemplate;
    private final IbanApiConfiguration ibanApiConfiguration;

    @Autowired
    public IbanApiClient(RestTemplate restTemplate, IbanApiConfiguration ibanApiConfiguration) {
        this.restTemplate = restTemplate;
        this.ibanApiConfiguration = ibanApiConfiguration;
    }

    public IbanApiModel validateIban(String iban){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> map= new HashMap<>();
        map.put("iban", iban);
        map.put("api_key", ibanApiConfiguration.getApiKey());

        HttpEntity<Map<String, String>> request =
                new HttpEntity<Map<String, String>>(map, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(IbanApiConstants.VALIDATE_IBAN_URL,
                        request , String.class );

        IbanApiModel ibanApiModel = getIbanApiModel(response);

        return ibanApiModel;
    }

    private IbanApiModel getIbanApiModel(ResponseEntity<String> response) {
        Map<String,Object> ibanApiResponse = null;
        try {
            ibanApiResponse = new ObjectMapper().readValue(response.getBody(), HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String result = ibanApiResponse.get("result").toString();
        Map<String,Object> data = (Map<String, Object>) ibanApiResponse.get("data");
        Map<String,String> bank = (Map<String, String>) data.get("bank");
        String swiftCode = bank.get("bic");

        IbanApiModel ibanApiModel = IbanApiModel.builder()
                .result(result)
                .swiftCode(swiftCode)
                .build();
        return ibanApiModel;
    }
}
