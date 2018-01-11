package publisher;

/**
 * Thrown when an issue occurs within the publisher.
 */
public class PublisherException extends RuntimeException {
    /**
     * The class's constructor.
     */
    public PublisherException() {

    }

    /**
     * The class's constructor.
     *
     * @param message A message describing why the exception was thrown.
     */
    public PublisherException(String message)
    {
        super(message);
    }
}
