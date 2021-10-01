import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.Unchecked;

import javax.mail.Multipart;
import javax.mail.Part;
import java.util.ArrayList;
import java.util.List;

public final class MultipartTextContent implements TextContent {

    private final Unchecked<TextContent> text;

    public MultipartTextContent(final Multipart multipart) {
        this(new Unchecked<>(() -> {
            final TextContent result;

            final IterableOf<Part> parts = new IterableOf<>(
                new PartsIterator(multipart)
            );
            final List<TextContent> foundTexts = new ArrayList<>();
            for (final Part bp: parts) {
                final TextContent s = new PartTextContent(bp);
                if (!s.isEmpty()) {
                    foundTexts.add(s);
                }
            }
            if (!foundTexts.isEmpty()) {
                result = foundTexts.get(0);
            } else {
                result = new SimpleTextContent("");
            }

            return result;
        }));
    }

    public MultipartTextContent(final Unchecked<TextContent> text) {
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
