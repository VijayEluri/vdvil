package no.vdvil.renderer.audio;

import com.thoughtworks.xstream.XStream;
import no.lau.vdvil.cache.DownloaderFacade;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import java.io.IOException;
import java.net.URL;

public class AudioXMLParser implements MultimediaParser {

    DownloaderFacade downloader;
    XStream xstream;

    public AudioXMLParser(DownloaderFacade downloader) {
        this.downloader = downloader;
        xstream = new XStream();
        xstream.alias("track", Track.class);
        xstream.alias("mediaFile", MediaFile.class);
        xstream.alias("segment", Segment.class);
    }

    public AudioDescription parse(CompositionInstruction compositionInstruction) throws IOException {
        URL dvlUrl = compositionInstruction.dvl().url();
        Track track = (Track) xstream.fromXML(downloader.fetchAsStream(dvlUrl));
        Segment segment = track.findSegment(compositionInstruction.id());
        return new AudioDescription(segment, compositionInstruction, track);
    }

    public void setDownloaderAndParseFacade(DownloaderFacade downloader) {
        this.downloader = downloader;
    }
}

