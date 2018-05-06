package com.github.expdev07.commy.bungeeplugin;

import com.github.expdev07.commy.core.Message;

public class TestObject implements Message {

    private String name;
    private int id;

    public TestObject(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
