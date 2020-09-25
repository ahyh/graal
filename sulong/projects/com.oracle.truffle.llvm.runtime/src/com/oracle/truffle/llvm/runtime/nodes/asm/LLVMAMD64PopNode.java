/*
 * Copyright (c) 2017, 2020, Oracle and/or its affiliates.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.oracle.truffle.llvm.runtime.nodes.asm;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.llvm.runtime.memory.LLVMStack.LLVMStackAccess;
import com.oracle.truffle.llvm.runtime.nodes.api.LLVMExpressionNode;
import com.oracle.truffle.llvm.runtime.nodes.memory.load.LLVMI32LoadNode;
import com.oracle.truffle.llvm.runtime.nodes.memory.load.LLVMI64LoadNode;
import com.oracle.truffle.llvm.runtime.pointer.LLVMPointer;

public abstract class LLVMAMD64PopNode extends LLVMExpressionNode {

    protected final LLVMStackAccess stackAccess;

    protected LLVMAMD64PopNode(LLVMStackAccess stackAccess) {
        this.stackAccess = stackAccess;
    }

    public abstract static class LLVMAMD64PopwNode extends LLVMAMD64PopNode {
        protected LLVMAMD64PopwNode(LLVMStackAccess stackAccess) {
            super(stackAccess);
        }

        @Specialization
        protected Object doI16(VirtualFrame frame,
                        @Cached LLVMI64LoadNode load) {
            LLVMPointer stackPointer = stackAccess.executeGet(frame);
            try {
                return load.executeWithTarget(stackPointer);
            } finally {
                stackAccess.executeSet(frame, stackPointer.increment(LLVMExpressionNode.I16_SIZE_IN_BYTES));
            }
        }
    }

    public abstract static class LLVMAMD64PoplNode extends LLVMAMD64PopNode {
        protected LLVMAMD64PoplNode(LLVMStackAccess stackAccess) {
            super(stackAccess);
        }

        @Specialization
        protected Object doI32(VirtualFrame frame,
                        @Cached LLVMI32LoadNode load) {
            LLVMPointer stackPointer = stackAccess.executeGet(frame);
            try {
                return load.executeWithTarget(stackPointer);
            } finally {
                stackAccess.executeSet(frame, stackPointer.increment(LLVMExpressionNode.I32_SIZE_IN_BYTES));
            }
        }
    }

    public abstract static class LLVMAMD64PopqNode extends LLVMAMD64PopNode {
        protected LLVMAMD64PopqNode(LLVMStackAccess stackAccess) {
            super(stackAccess);
        }

        @Specialization
        protected Object doI64(VirtualFrame frame,
                        @Cached LLVMI64LoadNode load) {
            LLVMPointer stackPointer = stackAccess.executeGet(frame);
            try {
                return load.executeWithTarget(stackPointer);
            } finally {
                stackAccess.executeSet(frame, stackPointer.increment(LLVMExpressionNode.I64_SIZE_IN_BYTES));
            }
        }
    }
}
