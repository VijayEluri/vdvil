package no.lau.vdvil.parser.json;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.handler.persistence.MultimediaReference;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.lau.vdvil.timing.TimeInterval;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Value class for holding state when serializing to JSON
 */
class CompositionSerializedJson {
    String name;
    Float masterBpm;
    List<PartJson> parts;
    Map<String, URL> dvls;

    public Composition asComposition(URL url, DownloadAndParseFacade downloadAndParseFacade) {
        List<MultimediaPart> newparts = new ArrayList<MultimediaPart>();
        int beatLength = 0;
        for (final PartJson part : this.parts) {
            if (part.start + part.duration > beatLength)
                beatLength = part.start + part.duration;
            try {
                newparts.add(downloadAndParseFacade.parse(part));
            } catch (IOException e) {
                System.out.println("Unable to parse or download " + part.dvl.name());
            }
        }
        return new Composition(this.name, new MasterBeatPattern(0, beatLength, this.masterBpm), newparts, url);
    }
}

class PartJson implements CompositionInstruction {
    String id;
    int start;
    int duration;
    String dvlref;
    MultimediaReference dvl;

    public String id() { return id; }
    public TimeInterval timeInterval() { return new Interval(start, duration); }
    public int start() { return start; }
    public int duration() {return duration;}
    public int end() {return start + duration;}
    public int cueDifference() { return 0;  //CueDifference cannot be set in this naive implementation 
    }
    public MultimediaReference dvl() {return dvl;}
}