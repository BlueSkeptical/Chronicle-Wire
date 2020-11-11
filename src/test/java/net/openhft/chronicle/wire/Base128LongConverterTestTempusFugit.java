package net.openhft.chronicle.wire;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

public class Base128LongConverterTestTempusFugit extends WireTestCommon {
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();

    @Test
    @Concurrent(count = 2)
	@Repeating(repetition = 100)
    public void parse() {
        LongConverter c = Base128LongConverter.INSTANCE;
//        System.out.println(c.asString(-1L));
        for (String s : ",a,ab,abc,abcd,abcde,123456,1234567,12345678,123456789,~~~~~~~~~".split(",")) {
            long v = c.parse(s);
            StringBuilder sb = new StringBuilder();
            c.append(sb, v);
            assertEquals(s, sb.toString());
        }
    }
/*
    @Test
    @Concurrent(count = 4)
	@Repeating(repetition = 4)
    public void asString() {
        LongConverter c = Base128LongConverter.INSTANCE;
        Random rand = new Random();
        for (int i = 0; i < 100000; i++) {
            long l = rand.nextLong();
            String s = c.asString(l);
            Assert.assertEquals(s, l, c.parse(s));
        }
    }*/
}