package model;

/**
 * Message entity
 *
 * @author Roman Dmitriev
 */
public class Message {

    /**
     * Message's id
     */
    private int id;
    /**
     * Message string
     */
    private String message;
    /**
     * User that sends the message id
     */
    private int sender;
    /**
     * receiver of message id
     */
    private int receiver;

    /**
     * Creates a new Message Object
     * @param id
     * @param message
     * @param sender
     * @param receiver
     */
    public Message(int id, String message, int sender, int receiver) {
        this.id = id;
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * @return receiver id
     */
    public int getReceiver() {
        return receiver;
    }

    /**
     * @return sender id
     */
    public int getSender() {
        return sender;
    }

    /**
     * @return message string
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return message's id
     */
    public int getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id;
    }
}
