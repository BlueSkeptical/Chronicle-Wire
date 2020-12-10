package net.openhft.chronicle.wire;

import net.openhft.chronicle.core.OS;
import net.openhft.chronicle.core.io.IORuntimeException;
import org.junit.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;

// Test created as a result of agitator tests i.e. random character changes
public class TextWireAgitatorTestMultithreadedTC extends MultithreadedTestCase {

    public void thread1() {
        if (!OS.isWindows())
            throw new IORuntimeException("Only fails this way on Windows");
        TextWireTest.MyDto myDto = Marshallable.fromString("!" + TextWireTest.MyDto.class.getName() + " { }");
        Assert.assertEquals("!net.openhft.chronicle.wire.TextWireTest$MyDto {\n" +
                "  strings: [  ]\n" +
                "}\n", myDto.toString());

        TextWireTest.MyDto myDto2 = Marshallable.fromString("!" + TextWireTest.MyDto.class.getName().toLowerCase() + " { }");
        Assert.assertNotNull(myDto2);
    }
    
    public void thread2() {
        if (!OS.isWindows())
            throw new IORuntimeException("Only fails this way on Windows");
        TextWireTest.MyDto myDto = Marshallable.fromString("!" + TextWireTest.MyDto.class.getName() + " { }");
        Assert.assertEquals("!net.openhft.chronicle.wire.TextWireTest$MyDto {\n" +
                "  strings: [  ]\n" +
                "}\n", myDto.toString());

        TextWireTest.MyDto myDto2 = Marshallable.fromString("!" + TextWireTest.MyDto.class.getName().toLowerCase() + " { }");
        Assert.assertNotNull(myDto2);
    }
    
    @Test(expected = IORuntimeException.class)
    public void lowerCaseClass() throws Throwable {
    	TestFramework.runManyTimes(new TextWireAgitatorTestMultithreadedTC(), 100);
    }

    static class MyFlagged extends SelfDescribingMarshallable {
        boolean flag;
    }
}
