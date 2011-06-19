package no.lau.vdvil.handler.persistence;

import java.net.URL;

public class PartXML implements CompositionInstruction {
    final String id;
    final int start;
    int end;
    final DvlXML dvl;

    public PartXML(String id, int start, int end, DvlXML dvl) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.dvl = dvl;
    }

    public String id() { return id; }
    public int start() { return start; }
    public int end() { return end; }
    public MultimediaReference dvl() { return dvl; }

    public static CompositionInstruction create(URL url) {
        return new PartXML("Test Part", -1, -1, new DvlXML("Test DVL", url))  ;
    }

    /**
     * The end beat may sometimes be moved to facilitate an early ending.
     * Really don't like this function which makes the CompositionInstruction mutable.
     * The side effect is that one has to load/parse the original source to get the original state
     * Should be a way of creating a copy of the original structure without mutating!
     * @param endBeat set endBeat
     */
    public void setEnd(int endBeat) {
        this.end = endBeat;
    }
}
