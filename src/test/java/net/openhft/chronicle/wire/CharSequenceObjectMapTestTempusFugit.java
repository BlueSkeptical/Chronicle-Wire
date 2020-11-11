package net.openhft.chronicle.wire;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

public class CharSequenceObjectMapTestTempusFugit extends WireTestCommon {

	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();
	
    @Test
    @Concurrent(count = 2)
	@Repeating(repetition = 100)
    public void put() {
    	//System.out.println(Thread.currentThread().getId());
        CharSequenceObjectMap<String> map = new CharSequenceObjectMap<>(10);
        for (int i = 10; i < 20; i++) {
            map.put("" + i, "" + i);
        }
        for (int i = 10; i < 20; i++) {
            assertEquals("" + i, map.get("" + i));
        }
    }

}