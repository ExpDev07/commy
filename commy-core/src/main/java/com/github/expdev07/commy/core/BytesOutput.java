package com.github.expdev07.commy.core;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.util.Arrays;

public class BytesOutput {

    private ByteArrayDataOutput out;

    public BytesOutput() {
        this.out = ByteStreams.newDataOutput();
    }
    
    public BytesOutput write(String... utf) {
        Arrays.stream(utf).forEach(out::writeUTF);
        return this;
    }

    public byte[] getBytes() {
        return out.toByteArray();
    }

}
