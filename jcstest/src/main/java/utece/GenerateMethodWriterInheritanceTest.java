/*
 * Copyright (c) 2017, Red Hat Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package utece;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.bytes.MethodReader;
import net.openhft.chronicle.wire.*;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;


import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicBoolean;

import static net.openhft.chronicle.wire.WireType.BINARY;

// See jcstress-samples or existing tests for API introduction and testing guidelines

//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id ="true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class GenerateMethodWriterInheritanceTest {

    private Boolean ifEqualActor1, ifEqualActor2;
    public GenerateMethodWriterInheritanceTest () {
        this.ifEqualActor1 = true;
        this.ifEqualActor2 = true;
    }

    @Actor
    public void actor1() {
        final Wire wire = BINARY.apply(Bytes.elasticByteBuffer());

        final AnInterface writer = wire.methodWriter(AnInterface.class, ADescendant.class);

        writer.sayHello("hello world");

        final AtomicBoolean callRegistered = new AtomicBoolean();

        final MethodReader reader = wire.methodReader(new SameInterfaceImpl(callRegistered));

        if (!(reader.readOne() && callRegistered.get() && !(reader instanceof  VanillaMethodReader) && !Proxy.isProxyClass(writer.getClass()))) {
            this.ifEqualActor1 = false;
        }
    }

    @Actor
    public void actor2() {
        final Wire wire = BINARY.apply(Bytes.elasticByteBuffer());

        final AnInterface writer = wire.methodWriter(AnInterface.class, ADescendant.class);

        writer.sayHello("hello world");

        final AtomicBoolean callRegistered = new AtomicBoolean();

        final MethodReader reader = wire.methodReader(new SameInterfaceImpl(callRegistered));

        if (!(reader.readOne() && callRegistered.get() && !(reader instanceof  VanillaMethodReader) && !Proxy.isProxyClass(writer.getClass()))) {
            this.ifEqualActor2 = false;
        }
    }

    @Arbiter
    public void arbiter(ZZ_Result r){
        r.r1 = ifEqualActor1;
        r.r2 = ifEqualActor2;
    }
    interface AnInterface {
        void sayHello(String name);
    }
    interface AnInterfaceSameName {
        void sayHello(String name);
    }

    interface ADescendant extends AnInterface {
    }

    static class SameMethodNameImpl implements AnInterface, AnInterfaceSameName {
        private final AtomicBoolean callRegistered;

        public SameMethodNameImpl(AtomicBoolean callRegistered) {
            this.callRegistered = callRegistered;
        }

        @Override
        public void sayHello(String name) {
            callRegistered.set(true);
        }
    }

    static class SameInterfaceImpl implements AnInterface, ADescendant {
        private final AtomicBoolean callRegistered;

        public SameInterfaceImpl(AtomicBoolean callRegistered) {
            this.callRegistered = callRegistered;
        }

        @Override
        public void sayHello(String name) {
            callRegistered.set(true);
        }
    }
}
