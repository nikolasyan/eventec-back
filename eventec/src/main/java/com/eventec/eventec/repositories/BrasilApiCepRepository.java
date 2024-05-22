package com.eventec.eventec.repositories;

import com.eventec.eventec.models.Endereco;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.HttpURLConnection;
import java.time.Duration;

@Component
public class BrasilApiCepRepository implements CepRepository {

    private static final String BASE_URL = "https://brasilapi.com.br/api/cep/v2/";
    private static final int CONNECTION_TIMEOUT = 10000;
    private final Gson gson = new Gson();
    private final Logger logger = LoggerFactory.getLogger(BrasilApiCepRepository.class);

    @Override
    public Endereco findByCep(String cep) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + cep))
                    .timeout(Duration.ofMillis(CONNECTION_TIMEOUT))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                return gson.fromJson(responseBody, Endereco.class);
            } else {
                logger.error("Erro ao consultar CEP {}: {}", cep, responseBody);
                throw new IllegalStateException(String.format("Erro na resposta da API ao consultar CEP %s: %d - %s", cep, response.statusCode(), responseBody));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaura o status de interrupção
            throw new IllegalStateException("A operação foi interrompida", e);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao buscar CEP " + cep, e);
        }
    }
}