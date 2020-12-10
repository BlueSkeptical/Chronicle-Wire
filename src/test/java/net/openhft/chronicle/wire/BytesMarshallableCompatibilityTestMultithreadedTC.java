package net.openhft.chronicle.wire;

import net.openhft.chronicle.bytes.Bytes;

import org.junit.Assert;
import org.junit.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public final class BytesMarshallableCompatibilityTestMultithreadedTC extends MultithreadedTestCase {

    public void thread1() throws Exception {
    	//System.out.println(Thread.currentThread().getId());
        final Container container = new Container();
        container.number = 17;
        container.label = "non-deterministic";
        container.truth = Boolean.TRUE;

        final Bytes<ByteBuffer> bytes = Bytes.elasticHeapByteBuffer(64);

        container.writeMarshallable(bytes);

        final Container copy = new Container();
        copy.readMarshallable(bytes);

        Assert.assertEquals(container.number, copy.number);
        Assert.assertEquals(container.label, copy.label);
        Assert.assertEquals(container.truth, copy.truth);
    }
    
    public void thread2() throws Exception {
        final Container container = new Container();
        container.number = 17;
        container.label = "non-deterministic";
        container.truth = Boolean.TRUE;

        final Bytes<ByteBuffer> bytes = Bytes.elasticHeapByteBuffer(64);

        container.writeMarshallable(bytes);

        final Container copy = new Container();
        copy.readMarshallable(bytes);

        Assert.assertEquals(container.number, copy.number);
        Assert.assertEquals(container.label, copy.label);
        Assert.assertEquals(container.truth, copy.truth);
    }
    
    @Test
    public void shouldSerialiseToBytes() throws Throwable {
    	TestFramework.runManyTimes(new BytesMarshallableCompatibilityTestMultithreadedTC(), 100);
    }

    private static final class Container extends BytesInBinaryMarshallable {
        private int number;
        private String label;
        private Boolean truth;
    }
}
