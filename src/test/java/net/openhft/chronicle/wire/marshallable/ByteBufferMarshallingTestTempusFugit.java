package net.openhft.chronicle.wire.marshallable;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.core.util.ObjectUtils;
import net.openhft.chronicle.wire.RawWire;
import net.openhft.chronicle.wire.Wire;
import net.openhft.chronicle.wire.WireTestCommon;

import org.junit.Rule;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

public class ByteBufferMarshallingTestTempusFugit extends WireTestCommon {
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();
	
    @Test
    @Concurrent(count = 2)
	@Repeating(repetition = 100)
    public void writeReadByteBuffer() {
    	//System.out.println(Thread.currentThread().getId());
        Bytes<ByteBuffer> bytes = Bytes.elasticByteBuffer();
        Wire wire = new RawWire(bytes);

        AClass o1 = new AClass(1, true, (byte) 2, '3', (short) 4, 5, 6, 7, 8, "nine");

        o1.writeMarshallable(wire);

        AClass o2 = ObjectUtils.newInstance(AClass.class);
        o2.readMarshallable(wire);

        assertEquals(o1, o2);
        bytes.releaseLast();
    }
}
