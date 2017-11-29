package net.openhft.chronicle.wire;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.bytes.MethodReader;
import net.openhft.chronicle.core.Mocker;
import org.junit.Test;

import java.io.StringWriter;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/*
 * Created by Peter Lawrey on 17/05/2017.
 */
public class MethodWriterTest {
    @Test
    public void testSubclasses() {
        Wire wire = new TextWire(Bytes.elasticHeapByteBuffer(256));
        Event writer = wire.methodWriterBuilder(Event.class).genericEvent("event").build();
        writer.event("top", new VanillaMethodReaderTest.MRT1("one"));
        writer.event("top", new VanillaMethodReaderTest.MRT2("one", "two"));
        writer.event("mid", new VanillaMethodReaderTest.MRT1("1"));
        writer.event("mid", new VanillaMethodReaderTest.MRT2("1", "2"));

        StringWriter sw = new StringWriter();
        MethodReader reader = wire.methodReader(Mocker.logging(VanillaMethodReaderTest.MRTListener.class, "subs ", sw));
        for (int i = 0; i < 4; i++) {
            assertTrue(reader.readOne());
        }
        assertFalse(reader.readOne());
        String expected = "subs top[!net.openhft.chronicle.wire.VanillaMethodReaderTest$MRT1 {\n" +
                "  field1: one,\n" +
                "  value: a\n" +
                "}\n" +
                "]\n" +
                "subs top[!net.openhft.chronicle.wire.VanillaMethodReaderTest$MRT2 {\n" +
                "  field1: one,\n" +
                "  value: a,\n" +
                "  field2: two\n" +
                "}\n" +
                "]\n" +
                "subs mid[!net.openhft.chronicle.wire.VanillaMethodReaderTest$MRT1 {\n" +
                "  field1: \"1\",\n" +
                "  value: a\n" +
                "}\n" +
                "]\n" +
                "subs mid[!net.openhft.chronicle.wire.VanillaMethodReaderTest$MRT2 {\n" +
                "  field1: \"1\",\n" +
                "  value: a,\n" +
                "  field2: \"2\"\n" +
                "}\n" +
                "]\n";
        String actual = sw.toString().replace("\r", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testDefault() {
        Wire wire = new TextWire(Bytes.elasticHeapByteBuffer(256));
        HasDefault writer = wire.methodWriter(HasDefault.class);

        // MethodWriter records an invocation on the default method
        // callsMethod of the _proxy_, not the interface
        // this should work for replaying events back through a VanillaMethodReader.
        // Change made in Chronicle-Core https://github.com/OpenHFT/Chronicle-Core/commit/86c532d20a304c990cb2a82ebd84ffb355660fd3
        writer.callsMethod("hello,world,bye");
        assertEquals("callsMethod: \"hello,world,bye\"\n" +
                "---\n", wire.toString());

    }

    @FunctionalInterface
    interface Event {
        void event(String eventName, Object o);
    }

    @FunctionalInterface
    public interface HasDefault {
        default void callsMethod(String args) {
            method(args.split(","));
        }

        void method(String... args);
    }
}

