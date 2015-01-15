package net.openhft.chronicle.wire;

import java.io.StreamCorruptedException;

/**
 * Created by peter on 1/10/15.
 */
public interface Marshallable {
    /**
     * Write data to the wire
     *
     * @param wire to write to.
     */
    public void writeMarshallable(Wire wire);

    /**
     * Straight line ordered decoding.
     *
     * @param wire to read from in an ordered manner.
     * @throws java.io.StreamCorruptedException the stream wasn't ordered or formatted as expected.
     */
    public void readMarshallable(Wire wire) throws StreamCorruptedException;
}
