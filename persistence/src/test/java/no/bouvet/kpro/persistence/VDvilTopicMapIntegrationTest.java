package no.bouvet.kpro.persistence;

import no.bouvet.topicmap.query.StandardTopicParameter;
import no.bouvet.topicmap.query.TologQuery;
import no.bouvet.topicmap.query.ITopicParameter;
import no.bouvet.topicmap.dao.TopicDAO;
import no.bouvet.topicmap.core.TopicMapUtil;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import net.ontopia.topicmaps.query.core.QueryResultIF;
import net.ontopia.topicmaps.query.core.QueryProcessorIF;
import net.ontopia.topicmaps.query.core.InvalidQueryException;
import net.ontopia.topicmaps.query.core.DeclarationContextIF;
import net.ontopia.topicmaps.query.utils.QueryUtils;


public class VDvilTopicMapIntegrationTest {


    static VaudevilleTopicMap tm;

    private static ITopicParameter EVENT = new StandardTopicParameter(VaudevilleTopicType.EVENT);
    private static ITopicParameter MEDIA = new StandardTopicParameter(VaudevilleTopicType.MEDIA);
    private static ITopicParameter WHOLE = new StandardTopicParameter(VaudevilleTopicType.WHOLE);
    private static ITopicParameter PART = new StandardTopicParameter(VaudevilleTopicType.PART);

    @BeforeClass
    public static void setUp(){
        tm = new VaudevilleTopicMap("musikk.xtm");
    }

    @Test
    public void testGetSingleTopic() {
        TopicDAO topicDAO = tm.getTopicDAOByPSI("https://wiki.bouvet.no/snap-rythm_is_a_dancer");
        assertEquals("Snap - Rhythm is a Dancer.mp3", topicDAO.getName());
    }

    @Test
    public void testPartWholeAssociation() {
        TologQuery tologQuery = new TologQuery(VaudevilleAssociationType.PART_WHOLE, PART, WHOLE);
        List resutl = tm.queryForList(tologQuery, WHOLE);
        assertEquals(2, resutl.size());
    }
    
    @Test
    public void testMediaEventAssociation() {
        TologQuery tologQuery = new TologQuery(VaudevilleAssociationType.MEDIA_EVENT, MEDIA, EVENT);
        List resutl = tm.queryForList(tologQuery, MEDIA);
        assertEquals(1, resutl.size());
    }

}
