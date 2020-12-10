package net.openhft.chronicle.wire;

import net.openhft.chronicle.bytes.Bytes;

import org.junit.Rule;
import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public final class BytesMarshallableCompatibilityTestTempusFugit extends WireTestCommon {
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();


    @Test
    @Concurrent(count = 2)
	@Repeating(repetition = 100)
    public void shouldSerialiseToBytes() throws Exception {
    	//System.out.println(Thread.currentThread().getId());
        final Container container = new Container();
        container.number = 17;
        container.label = "non-deterministic";
        container.truth = Boolean.TRUE;

        final Bytes<ByteBuffer> bytes = Bytes.elasticHeapByteBuffer(64);

        container.writeMarshallable(bytes);

        final Container copy = new Container();
        copy.readMarshallable(bytes);

        assertEquals(container.number, copy.number);
        assertEquals(container.label, copy.label);
        assertEquals(container.truth, copy.truth);
    }

    private static final class Container extends BytesInBinaryMarshallable {
        private int number;
        private String label;
        private Boolean truth;
    }
}
