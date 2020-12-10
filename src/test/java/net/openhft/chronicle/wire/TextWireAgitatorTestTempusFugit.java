package net.openhft.chronicle.wire;

import net.openhft.chronicle.core.OS;
import net.openhft.chronicle.core.io.IORuntimeException;
import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Rule;

// Test created as a result of agitator tests i.e. random character changes
public class TextWireAgitatorTestTempusFugit extends WireTestCommon {
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();
	
    @Test(expected = IORuntimeException.class)
    @Concurrent(count = 2)
	@Repeating(repetition = 100)
    public void lowerCaseClass() {
        if (!OS.isWindows())
            throw new IORuntimeException("Only fails this way on Windows");
        TextWireTest.MyDto myDto = Marshallable.fromString("!" + TextWireTest.MyDto.class.getName() + " { }");
        assertEquals("!net.openhft.chronicle.wire.TextWireTest$MyDto {\n" +
                "  strings: [  ]\n" +
                "}\n", myDto.toString());

        TextWireTest.MyDto myDto2 = Marshallable.fromString("!" + TextWireTest.MyDto.class.getName().toLowerCase() + " { }");
        assertNotNull(myDto2);
    }

    static class MyFlagged extends SelfDescribingMarshallable {
        boolean flag;
    }
}
