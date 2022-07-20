import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class FilmesImdb {

    public static void main(String[] args) {
        String boldFormatCode = "\u001b[1m";
        String redColorCode = "\u001b[31m";
        String yellowColorCode = "\u001b[33m";
        String resetFormatCode = "\u001b[0m";

        Properties properties = getProperties();

        Map<String, String> imdbOptions = new LinkedHashMap<>();
        imdbOptions.put("1", "https://imdb-api.com/en/API/Top250Movies/");
        imdbOptions.put("2", "https://imdb-api.com/en/API/MostPopularMovies/");
        imdbOptions.put("3", "https://imdb-api.com/en/API/Top250TVs/");
        imdbOptions.put("4", "https://imdb-api.com/en/API/MostPopularTVs/");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Selecione uma opção:");
        System.out.println("1-Melhores filmes, 2-Filmes mais populares, 3-Melhores séries ou 4-Séries mais populares");
        String inputOption = scanner.nextLine();

        String errorMessage = "";
        int inputOptionSelected = 0;
        try {
            inputOptionSelected = Integer.parseInt(inputOption);
        } catch (Exception e) {
            errorMessage = "Opção inválida informada";
        }

        if (inputOptionSelected > 0 && inputOptionSelected < 5) {
            System.out.println("Opção selecionada " + boldFormatCode + " " + yellowColorCode +
                    inputOptionSelected + resetFormatCode + " url " + imdbOptions.get(inputOption));

            // Realizar uma requisição HTTP na API do IMDB
            String body = "";
            //String url = imdbOptions.get(inputOption) + properties.getProperty("imdb-api-key");
            //System.out.println(properties.getProperty("imdb-api-key"));
            String url = "https://api.mocki.io/v2/549a5d8b";

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(url)).GET().build();

            try {
                HttpResponse<String> response = httpClient.send(httpRequest,
                        HttpResponse.BodyHandlers.ofString());
                body = response.body();
            } catch (Exception e) {
                body = "";
            }

            // Extrair o título, poster e classificação dos filmes
            if (!body.isBlank()) {
                JsonParser jsonParser = new JsonParser();
                List<Map<String, String>> listaDeFilmes = jsonParser.parser(body);

                // Exibir os dados recuperados
                GeradorDeFigurinhas geradorDeFigurinhas = new GeradorDeFigurinhas();

                for (Map<String, String> filme: listaDeFilmes){
                    try {
                        String fileName = filme.get("title");
                        String imageUrl = filme.get("image");
                        InputStream is = new URL(imageUrl).openStream();
                        geradorDeFigurinhas.criarFigurinha(is, fileName);

                        System.out.println(fileName);
                        System.out.print(filme.get("imDbRating") + " ");
                        System.out.println("⭐".repeat((int) Math.floor(Double.parseDouble(filme.get("imDbRating")))));
                        System.out.println(imageUrl);
                        System.out.println("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                errorMessage = "Nenhum dado a ser impresso!";
            }
        } else {
            errorMessage = "Opção inválida informada";
        }
        if (!errorMessage.isBlank()) {
            System.out.println(redColorCode + errorMessage + resetFormatCode);
        }
    }

    public static Properties getProperties()  {
        Properties props = new Properties();
        FileInputStream file = null;
        try {
            file = new FileInputStream("./properties/.config.properties");
            props.load(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
