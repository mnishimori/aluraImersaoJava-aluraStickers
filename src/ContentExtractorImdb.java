import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContentExtractorImdb implements ContentExtractor{

    @Override
    public List<Content> extractContent(String json) {

        JsonParser jsonParser = new JsonParser();
        List<Map<String, String>> attributesList = jsonParser.parser(json);

        List<Content> contentList = new ArrayList<>();
        if (attributesList != null && !attributesList.isEmpty()) {

            contentList = attributesList.stream().map(a -> {
                return new Content(a.get("title"), a.get("image"));
            }).collect(Collectors.toList());
        }

        return contentList;
    }
}
