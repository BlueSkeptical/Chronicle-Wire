package utece;

import net.openhft.chronicle.wire.TextMethodTester;
import net.openhft.chronicle.wire.YamlMethodTester;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;

import java.io.IOException;
import java.util.List;

interface Flow1 {
    Flow2 first(String args);
}

interface Flow2 {
    Flow3 second(long num);
}

interface Flow3 {
    void third(List<String> list);
}

//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class FlowMethodTest {

	private Boolean ifEqualActor1, ifEqualActor2;

	public FlowMethodTest () {
		this.ifEqualActor1 = true;
		this.ifEqualActor2 = true;
	}

	@Actor
	public void actor1() {
	    TextMethodTester test = null;
	    try {
	        test = new YamlMethodTester<>(
                "flow-in.yaml",
                out -> out,
                Flow1.class,
                "flow-in.yaml")
                .setup("flow-in.yaml") // calls made here are not validated in the output.
                .run();
        } catch (IOException i) {
	        ifEqualActor1 = false;
	        return;
        }
	    if (!test.expected().equals(test.actual())) {
	        ifEqualActor1 = false;
        }
	}

	@Actor
	public void actor2() {
        TextMethodTester test = null;
	    try {
	        test = new YamlMethodTester<>(
                "flow-in.yaml",
                out -> out,
                Flow1.class,
                "flow-in.yaml")
                .setup("flow-in.yaml") // calls made here are not validated in the output.
                .run();
        } catch (IOException i) {
	        ifEqualActor2 = false;
	        return;
        }
	    if (!test.expected().equals(test.actual())) {
	        ifEqualActor2 = false;
        }
	}

	@Arbiter
	public void arbiter(ZZ_Result r) {
		r.r1 = ifEqualActor1;
		r.r2 = ifEqualActor2;
	}
}
