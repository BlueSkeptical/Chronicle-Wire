package utece;

import net.openhft.chronicle.wire.SelfDescribingMarshallable;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;
//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class TextWithArraysTest {

	private Boolean ifEqualActor1, ifEqualActor2;
	static class WithArrays extends SelfDescribingMarshallable {
        boolean[] booleans;
        byte[] bytes;
        short[] shorts;
        char[] chars;
        int[] ints;
        long[] longs;
        float[] floats;
        double[] doubles;
        String[] words;
    }

	public TextWithArraysTest () {
		this.ifEqualActor1 = true;
		this.ifEqualActor2 = true;
	}

	@Actor
	public void actor1() {
	    String t = "!net.openhft.chronicle.wire.TextWithArraysTest$WithArrays {\n" +
                "  booleans: !!null \"\",\n" +
                "  bytes: !!null \"\",\n" +
                "  shorts: !!null \"\",\n" +
                "  chars: !!null \"\",\n" +
                "  ints: !!null \"\",\n" +
                "  longs: !!null \"\",\n" +
                "  floats: !!null \"\",\n" +
                "  doubles: !!null \"\",\n" +
                "  words: !!null \"\"\n" +
                "}\n";
	    if (new WithArrays().toString().equals(t)) {
	        ifEqualActor1 = false;
	    }
        WithArrays wa = new WithArrays();
        wa.booleans = new boolean[]{true, false};
        wa.bytes = new byte[]{-1, 0, 1};
        wa.shorts = new short[]{-1, 0, 1};
        wa.chars = "Hello".toCharArray();
        wa.ints = new int[]{-1, 0, 1};
        wa.longs = new long[]{-1, 0, 1};
        wa.floats = new float[]{-1, 0, 1};
        wa.doubles = new double[]{-1, 0, 1};
        wa.words = "Hello World Bye for now".split(" ");
        t = "!net.openhft.chronicle.wire.TextWithArraysTest$WithArrays {\n" +
                "  booleans: [ true, false ],\n" +
                "  bytes: [ -1, 0, 1 ],\n" +
                "  shorts: [ -1, 0, 1 ],\n" +
                "  chars: [ H, e, l, l, o ],\n" +
                "  ints: [ -1, 0, 1 ],\n" +
                "  longs: [ -1, 0, 1 ],\n" +
                "  floats: [ -1.0, 0.0, 1.0 ],\n" +
                "  doubles: [ -1.0, 0.0, 1.0 ],\n" +
                "  words: [ Hello, World, Bye, for, now ]\n" +
                "}\n";
        if (!t.equals(wa.toString())) {
            ifEqualActor1 = false;
        }
	}

	@Actor
	public void actor2() {
        String t = "!net.openhft.chronicle.wire.TextWithArraysTest$WithArrays {\n" +
                "  booleans: !!null \"\",\n" +
                "  bytes: !!null \"\",\n" +
                "  shorts: !!null \"\",\n" +
                "  chars: !!null \"\",\n" +
                "  ints: !!null \"\",\n" +
                "  longs: !!null \"\",\n" +
                "  floats: !!null \"\",\n" +
                "  doubles: !!null \"\",\n" +
                "  words: !!null \"\"\n" +
                "}\n";
	    if (new WithArrays().toString().equals(t)) {
	        ifEqualActor2 = false;
	    }
        WithArrays wa = new WithArrays();
        wa.booleans = new boolean[]{true, false};
        wa.bytes = new byte[]{-1, 0, 1};
        wa.shorts = new short[]{-1, 0, 1};
        wa.chars = "Hello".toCharArray();
        wa.ints = new int[]{-1, 0, 1};
        wa.longs = new long[]{-1, 0, 1};
        wa.floats = new float[]{-1, 0, 1};
        wa.doubles = new double[]{-1, 0, 1};
        wa.words = "Hello World Bye for now".split(" ");
        t = "!net.openhft.chronicle.wire.TextWithArraysTest$WithArrays {\n" +
                "  booleans: [ true, false ],\n" +
                "  bytes: [ -1, 0, 1 ],\n" +
                "  shorts: [ -1, 0, 1 ],\n" +
                "  chars: [ H, e, l, l, o ],\n" +
                "  ints: [ -1, 0, 1 ],\n" +
                "  longs: [ -1, 0, 1 ],\n" +
                "  floats: [ -1.0, 0.0, 1.0 ],\n" +
                "  doubles: [ -1.0, 0.0, 1.0 ],\n" +
                "  words: [ Hello, World, Bye, for, now ]\n" +
                "}\n";
        if (!t.equals(wa.toString())) {
            ifEqualActor2 = false;
        }
	}

	@Arbiter
	public void arbiter(ZZ_Result r) {
		r.r1 = ifEqualActor1;
		r.r2 = ifEqualActor2;
	}
}
