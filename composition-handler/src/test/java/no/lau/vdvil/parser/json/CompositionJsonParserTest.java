package no.lau.vdvil.parser.json;

import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.handler.persistence.domain.CompositionXml;
import org.junit.Test;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class CompositionJsonParserTest {

    @Test
    public void testParsingComposition() throws Exception {
        CompositionJsonParser parser = new CompositionJsonParser(null);
        InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("/CompositionExample.dvl.json"));

        CompositionXml comp = parser.parseJsonStringToTrack(reader);
        assertEquals(new Float(150), comp.masterBpm);
        assertEquals("A simple example", comp.name);
        PartXML part0 = comp.parts.get(0);
        assertEquals("id1", part0.id());
        assertEquals(0, part0.start());
        assertEquals(32, part0.duration());

        PartXML part1 = comp.parts.get(1);
        assertEquals("id2", part1.id());
        assertEquals(32, part1.start());
        assertEquals(32, part1.duration()
        );
    }
}