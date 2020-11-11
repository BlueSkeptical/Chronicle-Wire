package net.openhft.chronicle.wire.marshallable;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.core.io.AbstractReferenceCounted;
import net.openhft.chronicle.core.util.ObjectUtils;
import net.openhft.chronicle.wire.RawWire;
import net.openhft.chronicle.wire.Wire;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import java.nio.ByteBuffer;

public class ByteBufferMarshallingTestMultithreadedTC extends MultithreadedTestCase {
	@Before
    public void enableReferenceTracing() {
        AbstractReferenceCounted.enableReferenceTracing();
    }

    public void thread1() {
        Bytes<ByteBuffer> bytes = Bytes.elasticByteBuffer();
        Wire wire = new RawWire(bytes);

        AClass o1 = new AClass(1, true, (byte) 2, '3', (short) 4, 5, 6, 7, 8, "nine");

        o1.writeMarshallable(wire);

        AClass o2 = ObjectUtils.newInstance(AClass.class);
        o2.readMarshallable(wire);

        Assert.assertEquals(o1, o2);
        bytes.releaseLast();
    }
    
    public void thread2() {
        Bytes<ByteBuffer> bytes = Bytes.elasticByteBuffer();
        Wire wire = new RawWire(bytes);

        AClass o1 = new AClass(1, true, (byte) 2, '3', (short) 4, 5, 6, 7, 8, "nine");

        o1.writeMarshallable(wire);

        AClass o2 = ObjectUtils.newInstance(AClass.class);
        o2.readMarshallable(wire);

        Assert.assertEquals(o1, o2);
        bytes.releaseLast();
    }
    
    @Test
    public void writeReadByteBuffer() throws Throwable {
    	TestFramework.runManyTimes(new ByteBufferMarshallingTestMultithreadedTC(), 100);
    }
    
    @After
    public void assertReferencesReleased() {
        AbstractReferenceCounted.assertReferencesReleased();
    }
}
