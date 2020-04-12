package com.example.lab2;

public class Tech {
    private String name;
    private String graphic;
    private String helptext;

    Tech(){
        this.name = "";
        this.graphic = "";
        this.helptext = "";
    }

    public String get_name() {
        return name;
    }

    public void set_name(String name) {
        this.name = name;
    }

    public String get_graphic() {
        return graphic;
    }

    public void set_graphic(String graphic) {
        this.graphic = graphic;
    }

    public String get_helptext() {
        return helptext;
    }

    public void set_helptext(String helptext) {
        this.helptext = helptext;
    }
}
