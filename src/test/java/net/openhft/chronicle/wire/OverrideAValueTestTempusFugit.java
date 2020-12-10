package net.openhft.chronicle.wire;

import net.openhft.chronicle.core.util.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Rule;
import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import static org.junit.Assert.assertEquals;

public class OverrideAValueTestTempusFugit extends WireTestCommon {
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();
	
    @Test
    @Concurrent(count = 2)
	@Repeating(repetition = 100)
    public void testDontTouchImmutables() {
        @Nullable NumberHolder nh = Marshallable.fromString("!" + NumberHolder.class.getName() + " { num: 2 } ");
        assertEquals(1, NumberHolder.ONE.intValue());
        assertEquals(2, nh.num.intValue());
    }

    static class NumberHolder extends SelfDescribingMarshallable {
        @SuppressWarnings("UnnecessaryBoxing")
        public static final Integer ONE = new Integer(1);
        @NotNull
        Integer num = ONE;
    }

    static class ObjectHolder extends SelfDescribingMarshallable {
        @SuppressWarnings("UnnecessaryBoxing")
        public static final NumberHolder NH = new NumberHolder();
        @NotNull
        NumberHolder nh = NH;
    }

    static class ParentClass extends SelfDescribingMarshallable {
        @NotNull
        String name = "name";
    }

    static class SubClass extends ParentClass {
        double value = 1.28;
    }

    static class ParentHolder extends SelfDescribingMarshallable {
        final ParentClass object = new ParentClass();
    }
}
