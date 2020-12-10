package net.openhft.chronicle.wire;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FlowMethodTestTempusFugit extends WireTestCommon {
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();
	
    @SuppressWarnings("rawtypes")
    @Test
    @Concurrent(count = 2)
	@Repeating(repetition = 100)
    public void runYaml() throws IOException {
        TextMethodTester test = new YamlMethodTester<>(
                "flow-in.yaml",
                out -> out,
                Flow1.class,
                "flow-in.yaml")
                .setup("flow-in.yaml") // calls made here are not validated in the output.
                .run();
        assertEquals(test.expected(), test.actual());
    }
}