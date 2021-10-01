import org.cactoos.scalar.Solid;
import org.cactoos.scalar.Unchecked;

import javax.mail.Multipart;
import javax.mail.Part;

public final class PartTextContent implements TextContent {

    private final Unchecked<TextContent> text;

public PartTextContent(final Part p) {
    this(
        new Unchecked<>(
            new Solid<>(() -> {
                final TextContent result;
                if (p.isMimeType("text/*")) {
                    final String s = (String)p.getContent();
                    if (p.isMimeType("text/html")) {
                        result = new HtmlTextContent(s);
                    } else {
                        result = new SimpleTextContent(s);
                    }
                } else if (p.isMimeType("multipart/alternative")) {
                    // prefer html text over plain text
                    final Multipart mp = (Multipart) p.getContent();
                    result = new MultipartAlternativeTextContent(mp);
                } else if (p.isMimeType("multipart/*")) {
                    final Multipart mp = (Multipart) p.getContent();
                    result = new MultipartTextContent(mp);
                } else {
                    result = new SimpleTextContent("");
                }

                return result;
            })
        )
    );
}

    public PartTextContent(final Unchecked<TextContent> text) {
        this.text = text;
    }

    @Override
    public String asString() {
        return text.value().asString();
    }

    @Override
    public String asQuote() {
        return text.value().asQuote();
    }

    @Override
    public boolean isEmpty() {
        return text.value().isEmpty();
    }
}
