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
import net.openhft.chronicle.core.io.AbstractReferenceCounted;
import net.openhft.chronicle.core.util.ObjectUtils;
import net.openhft.chronicle.wire.*;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;

import java.nio.ByteBuffer;

//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id ="true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class ByteBufferMarshallingTest {

    private Boolean ifEqualActor1, ifEqualActor2;
    public ByteBufferMarshallingTest () {
        AbstractReferenceCounted.enableReferenceTracing();
        this.ifEqualActor1 = true;
        this.ifEqualActor2 = true;
    }

    @Actor
    public void actor1() {
        Bytes<ByteBuffer> bytes = Bytes.elasticByteBuffer();
        Wire wire = new RawWire(bytes);

        AClass o1 = new AClass(1, true, (byte) 2, '3', (short) 4, 5, 6, 7, 8, "nine");

        o1.writeMarshallable(wire);

        AClass o2 = ObjectUtils.newInstance(AClass.class);
        o2.readMarshallable(wire);

        if (!o1.equals(o2)) {
            this.ifEqualActor1 = false;
        }
        bytes.releaseLast();
    }

    @Actor
    public void actor2() {
        Bytes<ByteBuffer> bytes = Bytes.elasticByteBuffer();
        Wire wire = new RawWire(bytes);

        AClass o1 = new AClass(1, true, (byte) 2, '3', (short) 4, 5, 6, 7, 8, "nine");

        o1.writeMarshallable(wire);

        AClass o2 = ObjectUtils.newInstance(AClass.class);
        o2.readMarshallable(wire);

        if (!o1.equals(o2)) {
            this.ifEqualActor2 = false;
        }
        bytes.releaseLast();
    }

    @Arbiter
    public void arbiter(ZZ_Result r){
        r.r1 = ifEqualActor1;
        r.r2 = ifEqualActor2;
    }

}
