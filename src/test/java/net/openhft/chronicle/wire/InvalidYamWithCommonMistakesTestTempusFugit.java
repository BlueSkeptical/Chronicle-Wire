package net.openhft.chronicle.wire;

import net.openhft.chronicle.core.pool.ClassAliasPool;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

public class InvalidYamWithCommonMistakesTestTempusFugit extends WireTestCommon {
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();

    @Test
    @Concurrent(count = 2)
	@Repeating(repetition = 100)
    public void testDtp() {
    	//System.out.println(Thread.currentThread().getId());

        DtoB expected = new DtoB("hello8");

        Marshallable actual = Marshallable.fromString("!net.openhft.chronicle.wire.InvalidYamWithCommonMistakesTestTempusFugit$DtoB " +
                "{\n" +
                "  y:hello8\n" +
                "}\n");
        Assert.assertEquals(expected, actual);
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
