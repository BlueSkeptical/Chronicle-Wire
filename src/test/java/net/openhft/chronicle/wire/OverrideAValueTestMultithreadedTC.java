package net.openhft.chronicle.wire;

import net.openhft.chronicle.core.util.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

public class OverrideAValueTestMultithreadedTC extends MultithreadedTestCase {

    public void thread1() {
        @Nullable NumberHolder nh = Marshallable.fromString("!" + NumberHolder.class.getName() + " { num: 2 } ");
        Assert.assertEquals(1, NumberHolder.ONE.intValue());
        Assert.assertEquals(2, nh.num.intValue());
    }
    
    public void thread2() {
        @Nullable NumberHolder nh = Marshallable.fromString("!" + NumberHolder.class.getName() + " { num: 2 } ");
        Assert.assertEquals(1, NumberHolder.ONE.intValue());
        Assert.assertEquals(2, nh.num.intValue());
    }

    @Test
    public void testDontTouchImmutables() throws Throwable {
    	TestFramework.runManyTimes(new OverrideAValueTestMultithreadedTC(), 100);
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
