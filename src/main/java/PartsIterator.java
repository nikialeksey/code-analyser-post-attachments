import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

public final class PartsIterator implements Iterator<Part> {

    private final Multipart multipart;
    private final AtomicInteger position;

    public PartsIterator(
        final Multipart multipart
    ) {
        this(multipart, new AtomicInteger(0));
    }

    public PartsIterator(
        final Multipart multipart,
        final AtomicInteger position
    ) {
        this.multipart = multipart;
        this.position = position;
    }


    @Override
    public boolean hasNext() {
        try {
            return position.intValue() < multipart.getCount();
        } catch (final MessagingException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Part next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "The iterator doesn't have any more items"
            );
        }
        try {
            return multipart.getBodyPart(position.getAndIncrement());
        } catch (final MessagingException e) {
            throw new IllegalStateException(e);
        }
    }
}
