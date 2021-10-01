import java.util.StringTokenizer;

public final class SimpleTextContent implements TextContent {

    private final String text;

    public SimpleTextContent(final String text) {
        this.text = text;
    }

    @Override
    public String asString() {
        return text;
    }

    @Override
    public String asQuote() {
        final StringTokenizer textLines = new StringTokenizer(text, "\n");
        final StringBuilder quoteBuilder = new StringBuilder();

        while (textLines.hasMoreTokens()) {
            quoteBuilder.append('>');
            quoteBuilder.append(' ');
            quoteBuilder.append(textLines.nextToken());
            quoteBuilder.append('\n');
        }

        return quoteBuilder.toString();
    }

    @Override
    public boolean isEmpty() {
        return text.isEmpty();
    }
}
