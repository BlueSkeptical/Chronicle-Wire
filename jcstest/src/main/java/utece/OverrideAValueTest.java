package utece;

import net.openhft.chronicle.wire.Marshallable;
import net.openhft.chronicle.wire.SelfDescribingMarshallable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;
//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class OverrideAValueTest {

	private Boolean ifEqualActor1, ifEqualActor2;

	public OverrideAValueTest () {
		this.ifEqualActor1 = true;
		this.ifEqualActor2 = true;
	}

	static class NumberHolder extends SelfDescribingMarshallable {
        @SuppressWarnings("UnnecessaryBoxing")
        public static final Integer ONE = new Integer(1);
        @NotNull
        Integer num = ONE;
    }

	@Actor
	public void actor1() {
	    @Nullable NumberHolder nh = Marshallable.fromString("!" + NumberHolder.class.getName() + " { num: 2 } ");
        if (NumberHolder.ONE.intValue() != 1 || nh.num.intValue() != 2) {
            ifEqualActor1 = false;
        }
	}

	@Actor
	public void actor2() {
        @Nullable NumberHolder nh = Marshallable.fromString("!" + NumberHolder.class.getName() + " { num: 2 } ");
        if (NumberHolder.ONE.intValue() != 1 || nh.num.intValue() != 2) {
            ifEqualActor2 = false;
        }
	}

	@Arbiter
	public void arbiter(ZZ_Result r) {
		r.r1 = ifEqualActor1;
		r.r2 = ifEqualActor2;
	}
}
