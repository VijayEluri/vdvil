package no.vdvil.renderer.audio;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestMp3s {
    public static final URL coronamp3 =             createURL("https://github.com/StigLau/vdvil/blob/master/audio-renderer/src/test/resources/Corona_-_Baby_Baby.mp3");
    public static final URL psylteDvl =             createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/loaderror-psylteflesk.dvl");
    public static final URL returningDvl =          createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl");
    public static final URL unfinishedSympathyDvl = createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/unfinished_sympathy.dvl");
    public static final URL not_aloneDvl =          createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/olive-youre_not_alone.dvl");
    public static final URL scares_meDvl =          createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/christian_cambas-it_scares_me.dvl");
    public static final URL spaceDvl =              createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/space_manoeuvres-stage_one_original.dvl");

    public static final URL javaZoneComposition = createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml");
    public static final URL javaZoneComposition_WithoutImages = createURL("http://kpro09.googlecode.com/svn-history/r530/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml");
    public static final URL NULL = createURL("http://null.com");

    public static final Track returning = returning();
    public static final Track corona = corona();

    private static URL createURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("This should never happen!", e);
        }
    }

    static Track returning() {
        MediaFile mediaFile = new MediaFile(createURL("http://kpro09.googlecode.com/svn/test-files/holden-nothing-93_returning_mix.mp3"), 5.945F, "3e3477a6ccba67aa9f3196390f48b67d");
        List<Segment> segments = new ArrayList<Segment>();
        segments.add(new Segment("4336519975847252321",  0, 64, "low"));
        segments.add(new Segment("4638184666682848978", 64, 128, ""));
        segments.add(new Segment("2754708889643705332", 128, 256, "Up"));
        segments.add(new Segment("4533227407229953527", 256, 320, "Down"));
        segments.add(new Segment("6401936245564505757", 320, 416, "Setting up"));
        segments.add(new Segment("30189981949854134", 416, 448, "Want nothing 1. time"));
        segments.add(new Segment("6182122145512625145", 448, 480, ""));
        segments.add(new Segment("6978423701190173373", 480, 512, "Action satisfaction"));
        segments.add(new Segment("3657904262668647219", 512, 544, "Calming synth"));
        segments.add(new Segment("3378726703924324403", 544, 576, "Lyrics - 1. part"));
        segments.add(new Segment("4823965795648964701", 576, 608, "want nothing - 2. part"));
        segments.add(new Segment("5560598317419002938", 608, 640, "want nothing - 2. time"));
        segments.add(new Segment("9040781467677187716", 640, 704, "want nothing - 3. time"));
        segments.add(new Segment("5762690949488488062", 704, 768, "synth"));
        segments.add(new Segment("651352148519104110", 768, 1024, "Want nothing - 3. time"));
        return new Track("Holden an Thompsen - Nothing", 130.0F, mediaFile, segments);
    }

    static Track corona() {
        MediaFile mediaFile = new MediaFile(coronamp3, 44100 * 0.445f, "No Checksum");
        List<Segment> segments = new ArrayList<Segment>();
        segments.add(new Segment("a", 0, 16,  "Baby, why can't we just stay together"));
        segments.add(new Segment("b", 16, 32,  "Baby, why can't we just stay together"));
        segments.add(new Segment("c", 32, 64,  "Intro"));
        segments.add(new Segment("d", 64, 96,  "Riff 1. time"));
        segments.add(new Segment("e", 96, 128,  "1. Refrain  I want to roll inside your soul,"));
        segments.add(new Segment("f", 128, 128 + 32,  "2. Verse - Caught you down by suprise"));
        segments.add(new Segment("g", 128 + 32, 128 + 64,  "Baby baby, why can't we just stay together"));
        segments.add(new Segment("h", 128 + 64, 128 + 96, "riff 2. time"));
        segments.add(new Segment("i", 128 + 96, 256,  "Deep inside I know you need it"));
        segments.add(new Segment("j", 256, 256 + 32, "Caught you down by suprise"));
        return new Track("Corona - Baby baby", 132.98f, mediaFile, segments);
    }
}

