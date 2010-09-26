package no.bouvet.kpro.tagger.persistence;

import no.lau.tagger.model.Segment;
import no.lau.tagger.model.SimpleSong;
import com.thoughtworks.xstream.XStream;

import java.io.*;

public class XStreamParser <T extends SimpleSong> {

    public static final String path = System.getProperty("user.home") + "/kpro";

    XStream xstream = new XStream();

    public XStreamParser() {
        xstream.alias("track", SimpleSong.class);
        xstream.alias("segment", Segment.class);
    }

    public String toXml(SimpleSong songToSerialize) {
        return xstream.toXML(songToSerialize);
    }

    public SimpleSong fromXML(String fromXML) {
        return (SimpleSong) xstream.fromXML(fromXML);
    }

    public void save(T songToSave, String fileToSave) throws IOException {
        String xml = toXml(songToSave);
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        BufferedWriter outputStream = new BufferedWriter(new FileWriter(fileToSave));
        outputStream.write(xml);
        outputStream.close();
    }

    public T load(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            return (T) xstream.fromXML(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
