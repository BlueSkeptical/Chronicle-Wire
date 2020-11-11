package net.openhft.chronicle.wire;

import net.openhft.chronicle.core.io.AbstractReferenceCounted;
import net.openhft.chronicle.core.pool.ClassAliasPool;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

public class InvalidYamWithCommonMistakesTestMultithreadedTC extends MultithreadedTestCase {
	@Before
    public void enableReferenceTracing() {
        AbstractReferenceCounted.enableReferenceTracing();
    }

    public void thread1() {
        DtoB expected = new DtoB("hello8");

        Marshallable actual = Marshallable.fromString("!net.openhft.chronicle.wire.InvalidYamWithCommonMistakesTestMultithreadedTC$DtoB " +
                "{\n" +
                "  y:hello8\n" +
                "}\n");
        Assert.assertEquals(expected, actual);
    }

    public void thread2() {

        DtoB expected = new DtoB("hello8");

        Marshallable actual = Marshallable.fromString("!net.openhft.chronicle.wire.InvalidYamWithCommonMistakesTestMultithreadedTC$DtoB " +
                "{\n" +
                "  y:hello8\n" +
                "}\n");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void testDtp() throws Throwable {
    	TestFramework.runManyTimes(new InvalidYamWithCommonMistakesTestMultithreadedTC(), 100);
    }
    
    @After
    public void assertReferencesReleased() {
        AbstractReferenceCounted.assertReferencesReleased();
    }

    public static class Dto extends SelfDescribingMarshallable {
        String y;
        DtoB x;

        Dto(final String y, final DtoB x) {
            this.y = y;
            this.x = x;
        }

        String y() {
            return y;
        }

        DtoB x() {
            return x;
        }
    }

    public static class DtoB extends SelfDescribingMarshallable {
        String y;

        public DtoB(final String y) {
            this.y = y;
        }

        String y() {
            return y;
        }

        public DtoB y(final String y) {
            this.y = y;
            return this;
        }
    }

}
