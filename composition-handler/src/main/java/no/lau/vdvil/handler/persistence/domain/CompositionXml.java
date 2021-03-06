package no.lau.vdvil.handler.persistence.domain;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.timing.MasterBeatPattern;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Internal classes only used for parsing to/from XML/JSON!
 */

public class CompositionXml {
    public String name;
    public Float masterBpm;
    public List<PartXML> parts;

    public Composition asComposition(URL url, DownloadAndParseFacade downloadAndParseFacade) {
        List<MultimediaPart> newparts = new ArrayList<MultimediaPart>();
        int beatLength = 0;
        for (final PartXML partXML : this.parts) {
            if (partXML.end() > beatLength)
                beatLength = partXML.end();
            try {
                newparts.add(downloadAndParseFacade.parse(partXML));
            } catch (IOException e) {
                System.out.println("Unable to parse or download "+partXML.dvl.name());
            }
        }
        return new Composition(this.name, new MasterBeatPattern(0, beatLength, this.masterBpm), newparts, url);
    }
}
