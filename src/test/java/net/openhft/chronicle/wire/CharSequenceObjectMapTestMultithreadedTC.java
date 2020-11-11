package net.openhft.chronicle.wire;

import org.junit.Test;

import net.openhft.chronicle.core.io.AbstractReferenceCounted;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;
import net.openhft.chronicle.core.io.AbstractReferenceCounted;

public class CharSequenceObjectMapTestMultithreadedTC extends MultithreadedTestCase {
	@Before
    public void enableReferenceTracing() {
        AbstractReferenceCounted.enableReferenceTracing();
    }

    public void thread1() {
    	//System.out.println(Thread.currentThread().getId());
        CharSequenceObjectMap<String> map = new CharSequenceObjectMap<>(10);
        for (int i = 10; i < 20; i++) {
            map.put("" + i, "" + i);
        }
        for (int i = 10; i < 20; i++) {
            Assert.assertEquals("" + i, map.get("" + i));
        }
    }
    
    public void thread2() {
    	//System.out.println(Thread.currentThread().getId());
        CharSequenceObjectMap<String> map = new CharSequenceObjectMap<>(10);
        for (int i = 10; i < 20; i++) {
            map.put("" + i, "" + i);
        }
        for (int i = 10; i < 20; i++) {
            Assert.assertEquals("" + i, map.get("" + i));
        }
    }
    
    @Test
    public void put() throws Throwable {
    	TestFramework.runManyTimes(new CharSequenceObjectMapTestMultithreadedTC(), 100);
    }
    
    @After
    public void assertReferencesReleased() {
        AbstractReferenceCounted.assertReferencesReleased();
    }

}