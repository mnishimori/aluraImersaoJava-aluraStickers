import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientHttp {

    public String returnData(String url){
        //String url = imdbOptions.get(inputOption) + properties.getProperty("imdb-api-key");
        //System.out.println(properties.getProperty("imdb-api-key"));
        //String url = "https://api.mocki.io/v2/549a5d8b";
        String body = "";

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(url)).GET().build();
            HttpResponse<String> response = httpClient.send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());
            body = response.body();
        } catch (IOException | InterruptedException e) {
            throw new UrlNotFoundException("Não existe conteúdo na URL " + url);
        }

        return body;
    }
}
