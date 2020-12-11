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

import net.openhft.chronicle.wire.LongConverter;
import org.openjdk.jcstress.annotations.*;
import net.openhft.chronicle.wire.Base128LongConverter;
import org.openjdk.jcstress.infra.results.ZZ_Result;

//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id ="true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class Base128LongConverterTest {
    private Boolean ifEqualActor1=true, ifEqualActor2=true;
    @Actor
    public void actor1() {
        LongConverter c = Base128LongConverter.INSTANCE;
        for (String s : ",a,ab,abc,abcd,abcde,123456,1234567,12345678,123456789,~~~~~~~~~".split(",")) {
            long v = c.parse(s);
            StringBuilder sb = new StringBuilder();
            c.append(sb, v);
            if (!sb.toString().equals(s)) {
                ifEqualActor1 = false;
            }
        }
    }

    @Actor
    public void actor2() {
        LongConverter c = Base128LongConverter.INSTANCE;
        for (String s : ",a,ab,abc,abcd,abcde,123456,1234567,12345678,123456789,~~~~~~~~~".split(",")) {
            long v = c.parse(s);
            StringBuilder sb = new StringBuilder();
            c.append(sb, v);
            if (!sb.toString().equals(s)) {
                ifEqualActor2 = false;
            }
        }
    }

    @Arbiter
    public void arbiter(ZZ_Result r){
        r.r1 = ifEqualActor1;
        r.r2 = ifEqualActor2;
    }
}
