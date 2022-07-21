import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContentExtractorImdb implements ContentExtractor{

    @Override
    public List<Content> extractContent(String json) {

        JsonParser jsonParser = new JsonParser();
        List<Map<String, String>> attributesList = jsonParser.parser(json);

        List<Content> contentList = new ArrayList<>();
        if (attributesList != null && !attributesList.isEmpty()) {
            String fileName = "";
            String imageUrl = "";

            for (Map<String, String> conteudo: attributesList){
                fileName = conteudo.get("title");
                imageUrl = conteudo.get("image");
                /*String rating = "⭐".repeat((int) Math.floor(Double.parseDouble(content.get("imDbRating"))));
                String grade = content.get("imDbRating") + " ";*/
                contentList.add(new Content(fileName, imageUrl));
            }
        }

        return contentList;
    }
}
