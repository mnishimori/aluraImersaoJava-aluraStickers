import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class ImageGenaratorApp {

    public static void main(String[] args) {
        String boldFormatCode = "\u001b[1m";
        String redColorCode = "\u001b[31m";
        String yellowColorCode = "\u001b[33m";
        String resetFormatCode = "\u001b[0m";

        // Este properties seria utilizado para recuperar a api-key,
        // que está salva localmente.
        Properties properties = getProperties();

        System.out.println("Selecione uma opção:");
        System.out.println("1-Melhores filmes, 2-Imagens da NASA");
        Scanner scanner = new Scanner(System.in);
        String inputOption = scanner.nextLine();

        try {
            ExtractorEnum extractor = getExtractor(inputOption);
            String url = extractor.getApiUrl();

            System.out.println("Opção selecionada " + boldFormatCode + " " + yellowColorCode +
                    "-" + extractor.name() + resetFormatCode + " url " + url);

            // Realizar uma requisição HTTP na API do IMDB
            ClientHttp clientHttp = new ClientHttp();
            String json = clientHttp.returnData(url);

            // Extrair o título, poster e classificação dos filmes
            ContentExtractor contentExtractor = extractor.getContentExtractor();
            List<Content> contentList = contentExtractor.extractContent(json);

            // Exibir os dados recuperados
            GeradorDeFigurinhas geradorDeFigurinhas = new GeradorDeFigurinhas();

            for (Content content: contentList){
                String fileName = content.getTitle();
                String imageUrl = content.getUrlImage();

                InputStream is = new URL(imageUrl).openStream();
                geradorDeFigurinhas.criarFigurinha(is, fileName);
            }
        } catch (Exception e) {
            System.out.println(redColorCode + e.getMessage() + resetFormatCode);
        }
    }

    public static ExtractorEnum getExtractor(String inputOption) throws Exception{
        String optionSel = getEnumNameSel(inputOption);
        return ExtractorEnum.valueOf(optionSel);
    }

    private static String getEnumNameSel(String inputOption) throws Exception {
        String optionSel = "";
        String message = "";
        try {
            int inputOptionSelected = Integer.parseInt(inputOption);
            if (inputOptionSelected > 0 && inputOptionSelected < 3) {
                optionSel = "NASA";
                if (inputOptionSelected != 2) {
                    optionSel = "IMDB";
                }
            } else {
                message = "A opção informada " + inputOption + " não é válida";
            }
        } catch (Exception e) {
            message = "A opção informada " + inputOption + " não é válida";
        }
        if (!message.isBlank()) {
            throw new Exception(message);
        }
        return optionSel;
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
