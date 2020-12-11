package utece;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.wire.BytesInBinaryMarshallable;
import java.nio.ByteBuffer;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;

//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class BytesMarshallableCompatibilityTest {

	private Boolean ifEqualActor1, ifEqualActor2;
	private static final class Container extends BytesInBinaryMarshallable {
        private int number;
        private String label;
        private Boolean truth;
    }

	public BytesMarshallableCompatibilityTest () {
		this.ifEqualActor1 = true;
		this.ifEqualActor2 = true;
	}

	@Actor
	public void actor1() {
	    final Container container = new Container();
        container.number = 17;
        container.label = "non-deterministic";
        container.truth = Boolean.TRUE;
        final Bytes<ByteBuffer> bytes = Bytes.elasticHeapByteBuffer(64);
        container.writeMarshallable(bytes);
        final Container copy = new Container();
        copy.readMarshallable(bytes);
        if (container.number != copy.number || container.label != copy.label || container.truth != copy.truth) {
            ifEqualActor1 = false;
        }
	}

	@Actor
	public void actor2() {
        final Container container = new Container();
        container.number = 17;
        container.label = "non-deterministic";
        container.truth = Boolean.TRUE;
        final Bytes<ByteBuffer> bytes = Bytes.elasticHeapByteBuffer(64);
        container.writeMarshallable(bytes);
        final Container copy = new Container();
        copy.readMarshallable(bytes);
        if (container.number != copy.number || container.label != copy.label || container.truth != copy.truth) {
            ifEqualActor2 = false;
        }
	}

	@Arbiter
	public void arbiter(ZZ_Result r) {
		r.r1 = ifEqualActor1;
		r.r2 = ifEqualActor2;
	}
}