public enum ExtractorEnum {

    IMDB("https://api.mocki.io/v2/549a5d8b") {
        @Override
        public ContentExtractor getContentExtractor() {
            return new ContentExtractorImdb();
        }
    },
    NASA("https://api.mocki.io/v2/549a5d8b/NASA-APOD") {
        @Override
        public ContentExtractor getContentExtractor() {
            return new ContentExtractorNasa();
        }
    };

    private String apiUrl;

    ExtractorEnum(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public abstract ContentExtractor getContentExtractor();
}
