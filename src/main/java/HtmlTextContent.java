public final class HtmlTextContent implements TextContent {

    private final String text;

    public HtmlTextContent(final String text) {
        this.text = text;
    }

    @Override
    public String asString() {
        return text;
    }

    @Override
    public String asQuote() {
        final StringBuilder quoteBuilder = new StringBuilder();

        quoteBuilder.append("<blockquote>");
        quoteBuilder.append(text);
        quoteBuilder.append("</blockquote>");

        return quoteBuilder.toString();
    }

    @Override
    public boolean isEmpty() {
        return text.isEmpty();
    }
}
