package net.openhft.chronicle.wire;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;
import net.openhft.chronicle.core.io.AbstractReferenceCounted;

public class Base128LongConverterTestMultithreadedTC extends MultithreadedTestCase {
	@Before
    public void enableReferenceTracing() {
        AbstractReferenceCounted.enableReferenceTracing();
    }
	
	public void thread1() {
		LongConverter c = Base128LongConverter.INSTANCE;
      for (String s : ",a,ab,abc,abcd,abcde,123456,1234567,12345678,123456789,~~~~~~~~~".split(",")) {
          long v = c.parse(s);
          StringBuilder sb = new StringBuilder();
          c.append(sb, v);
          Assert.assertEquals(s, sb.toString());
      }
	}
	
	public void thread2() {
		LongConverter c = Base128LongConverter.INSTANCE;
      for (String s : ",a,ab,abc,abcd,abcde,123456,1234567,12345678,123456789,~~~~~~~~~".split(",")) {
          long v = c.parse(s);
          StringBuilder sb = new StringBuilder();
          c.append(sb, v);
          Assert.assertEquals(s, sb.toString());
      }
	}

    @Test
    public void parse() throws Throwable {
    	TestFramework.runManyTimes(new Base128LongConverterTestMultithreadedTC(), 100);
    }
    
    @After
    public void assertReferencesReleased() {
        AbstractReferenceCounted.assertReferencesReleased();
    }
}