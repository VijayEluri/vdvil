package no.lau.vdvil.mix;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.DvlXML;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.*;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoronaTest extends SuperPlayingSetup{
    @Test
    public void play() {
        super.play(new MasterBeatPattern(0, 16, 150F));
    }
    Track corona = TestMp3s.corona;

    @Override
    protected Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(createPart(0, 20, corona.segments.get(3), corona));
        parts.add(createPart(4, 8, corona.segments.get(6), corona));
        return new Composition(getClass().getSimpleName(), masterBeatPattern, parts, TestMp3s.NULL);
    }

    private MultimediaPart createPart(int start, int end, Segment segment, Track track) {
        DvlXML dvl = new DvlXML("", track.mediaFile.fileName);
        return new AudioDescription(segment, new PartXML(segment.id, start, end, dvl), track);
    }
}