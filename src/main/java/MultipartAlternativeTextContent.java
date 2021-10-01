import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.Unchecked;

import javax.mail.Multipart;
import javax.mail.Part;
import java.util.ArrayList;
import java.util.List;

public final class MultipartAlternativeTextContent implements TextContent {

    private final Unchecked<TextContent> text;

    public MultipartAlternativeTextContent(final Multipart multipart) {
        this(new Unchecked<>(() -> {
            final TextContent result;

            final IterableOf<Part> parts = new IterableOf<>(
                new PartsIterator(multipart)
            );
            final List<TextContent> foundPlain = new ArrayList<>();
            final List<TextContent> foundHtml = new ArrayList<>();
            final List<TextContent> foundOthers = new ArrayList<>();
            for (final Part bp: parts) {
                final TextContent foundText = new PartTextContent(bp);
                if (bp.isMimeType("text/plain")) {
                    foundPlain.add(foundText);
                } else if (bp.isMimeType("text/html")) {
                    foundHtml.add(foundText);
                } else {
                    if (!foundText.isEmpty()) {
                        foundOthers.add(foundText);
                    }
                }
            }

            if (!foundHtml.isEmpty()) {
                result = foundHtml.get(0);
            } else if (!foundOthers.isEmpty()) {
                result = foundOthers.get(0);
            } else if (!foundPlain.isEmpty()) {
                result = foundPlain.get(0);
            } else {
                result = new SimpleTextContent("");
            }

            return result;
        }));
    }

    public MultipartAlternativeTextContent(final Unchecked<TextContent> text) {
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
