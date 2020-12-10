package net.openhft.chronicle.wire;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FlowMethodTestMultithreadedTC extends MultithreadedTestCase {
    @SuppressWarnings("rawtypes")
    public void thread1() throws IOException {
        TextMethodTester test = new YamlMethodTester<>(
                "flow-in.yaml",
                out -> out,
                Flow1.class,
                "flow-in.yaml")
                .setup("flow-in.yaml") // calls made here are not validated in the output.
                .run();
        Assert.assertEquals(test.expected(), test.actual());
    }
 
    @SuppressWarnings("rawtypes")
    public void thread2() throws IOException {
        TextMethodTester test = new YamlMethodTester<>(
                "flow-in.yaml",
                out -> out,
                Flow1.class,
                "flow-in.yaml")
                .setup("flow-in.yaml") // calls made here are not validated in the output.
                .run();
        Assert.assertEquals(test.expected(), test.actual());
    }
    
    @Test
    public void runYaml() throws Throwable {
    	TestFramework.runManyTimes(new FlowMethodTestMultithreadedTC(), 100);
    }
}