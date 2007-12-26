package no.bouvet.kpro.model.stigstest;

import no.bouvet.topicmap.query.*;
import no.bouvet.topicmap.dao.TopicDAO;
import no.bouvet.topicmap.dao.TopicType;
import no.bouvet.kpro.persistence.VaudevilleAssociationType;
import no.bouvet.kpro.persistence.VaudevilleTopicType;
import no.bouvet.kpro.persistence.VaudevilleTopicMap;
import java.util.List;
import java.util.ArrayList;
import net.ontopia.topicmaps.core.TopicIF;

public class Event extends TopicDAO {

    private static VaudevilleTopicMap tm;

    enum OccurrenceTypes {START, LENGTH}

    public Event() {
        super(null);
    }

    public Event(TopicIF topicIF) {
        super(topicIF);
    }

    static {
        tm = new VaudevilleTopicMap("musikk.xtm");
    }


    public Event getParent() {
        TologQuery tologQuery = new TologQuery(VaudevilleAssociationType.PART_WHOLE, new StandardTopicParameter(VaudevilleTopicType.WHOLE), new StandardTopicParameter(this));
        TopicIF topic = tm.queryForSingleValue(tologQuery, new StandardTopicParameter(VaudevilleTopicType.WHOLE));
        return new Event(topic);
    }

    public List<Event> getChildren() {
        List<Event> results = new ArrayList<Event>();
        TologQuery tologQuery = new TologQuery(VaudevilleAssociationType.PART_WHOLE, new StandardTopicParameter(this), new StandardTopicParameter(VaudevilleTopicType.PART));
        List<TopicIF> topics = tm.queryForList(tologQuery, new StandardTopicParameter(VaudevilleTopicType.PART));
        for (TopicIF topic : topics) {
            results.add(new Event(topic));
        }
        return results;
    }

    public Media getMedia() {
        TologQuery tologQuery = new TologQuery(VaudevilleAssociationType.MEDIA_EVENT, new StandardTopicParameter(VaudevilleTopicType.MEDIA), new StandardTopicParameter(this));
        TopicIF topicIF = tm.queryForSingleValue(tologQuery, new StandardTopicParameter(VaudevilleTopicType.MEDIA));
        return new Media(topicIF);
    }

    public double getStartTime() {
        ITologQuery tologQuery = new SimpleTologQueryString("vdvil:start(%TOPIC%, $START)?", this, "TOPIC");
        String result = tm.queryForSingleValue(tologQuery, SimpleTopicParameterFactory.create("START"));
        return Double.valueOf(result);
    }

    public double getLength() {
        ITologQuery tologQuery = new SimpleTologQueryString("vdvil:length(%TOPIC%, $LENGTH)?", this, "TOPIC");
        String result = tm.queryForSingleValue(tologQuery, SimpleTopicParameterFactory.create("LENGTH"));
        return Double.valueOf(result);
    }
    
    public int getBPM() {
        ITologQuery tologQuery = new SimpleTologQueryString("vdvil:bpm(%TOPIC%, $BPM)?", this, "TOPIC");
        String result = tm.queryForSingleValue(tologQuery, SimpleTopicParameterFactory.create("BPM"));
        return Integer.valueOf(result);
    }

    public TopicType getTopicType() {
        return VaudevilleTopicType.EVENT;
    }
}
