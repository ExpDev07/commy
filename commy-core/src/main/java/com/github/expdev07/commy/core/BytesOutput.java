package com.github.expdev07.commy.core;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class BytesOutput {

    private ByteArrayDataOutput out;

    public BytesOutput() {
        this.out = ByteStreams.newDataOutput();
    }

    public BytesOutput write(String utf) {
        out.writeUTF(utf);
        return this;
    }

    public byte[] getBytes() {
        return out.toByteArray();
    }

}
