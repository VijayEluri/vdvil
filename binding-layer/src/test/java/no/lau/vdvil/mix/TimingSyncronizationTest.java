package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.mix.util.CompositionHelper;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.TestMp3s;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class TimingSyncronizationTest {
    FileRepresentation space = TestMp3s.spaceDvl;
    FileRepresentation surrender = TestMp3s.surrenderDvl;
    Store store = Store.get();

    @Test
    public void play() {
        new PreconfiguredVdvilPlayer().init(composition).playUntilEnd();
    }

    Composition composition = new CompositionHelper() {
        public Composition compose() {
            List<MultimediaPart> parts = new ArrayList<>();
        /*
        parts.add(createAudioPart(returning.segments.get(0).id, new Interval(0, 16), TestMp3s.returningDvl));
        parts.add(createAudioPart(returning.segments.get(6).id, new Interval(16, 16), TestMp3s.returningDvl));
        parts.add(createAudioPart(returning.segments.get(9).id, new Interval(32, 16), TestMp3s.returningDvl));
        parts.add(createAudioPart(returning.segments.get(10).id, new Interval(48, 16), TestMp3s.returningDvl));*/
                parts.add(createAudioPart("4975745638923227772", new Interval(0, 16), space));
                parts.add(createAudioPart("4882945013722419329", new Interval(16, 16), space));
                parts.add(createAudioPart("7564730054968764511", new Interval(32, 16), space));
                parts.add(createAudioPart("7719477605104727855", new Interval(48, 16), space));
                parts.add(createAudioPart("0-128Surrender", new Interval(64, 16), surrender));
                parts.add(createAudioPart("128-256Surrender", new Interval(80, 16), surrender));
                parts.add(createAudioPart("128-256Surrender", new Interval(96, 16), surrender));
                parts.add(createAudioPart("544-656Surrender", new Interval(112, 16), surrender));

                {

                    parts.add(createImagePart("0", new Interval(0, 16), store.createKey("http://fc03.deviantart.net/fs17/f/2007/182/f/4/Dead_Teddy_by_Cast_Down_Doll.jpg", "4648c59ec6235407b59a0327328041b5")));

                    parts.add(createImagePart("1", new Interval(16, 16), store.createKey("http://www.shinyshiny.tv/teddy%20bear%201.jpg", "a9e178def69c92cc9355b1e7512dabe8")));
            /*
parts.add(createImagePart("2", new Interval(8, 4), store.createKey("http://farm6.static.flickr.com/5101/5659214954_5c333c4cd1_d.jpg")));
parts.add(createImagePart("3", new Interval(12, 4), store.createKey("http://farm6.static.flickr.com/5181/5659204108_975723a0fe_d.jpg")));
            */
                    parts.add(createImagePart("4", new Interval(32, 16), store.createKey("https://kpro09.googlecode.com/svn/test-files/pics/teddy/checkered_teddy.jpg", "4a2caec562cd04f5418c5cb7629c8f1c")));
                    parts.add(createImagePart("5", new Interval(48, 16), store.createKey("https://kpro09.googlecode.com/svn/test-files/pics/teddy/Snow_bears.jpg", "b7752d3eee31b6f4e4a2bc5a2b40b3d3")));
                    parts.add(createImagePart("6", new Interval(64, 16), store.createKey("https://kpro09.googlecode.com/svn/test-files/pics/teddy/mac_bear.jpg", "11384f70753cbfa466ceb6f8133f3aec")));
                    parts.add(createImagePart("7", new Interval(80, 16), store.createKey("https://kpro09.googlecode.com/svn/test-files/pics/teddy/relax_bear.jpg", "6fef5b4efb4b9edfc21e66a603093aa9")));
                    parts.add(createImagePart("8", new Interval(96, 16), store.createKey("https://kpro09.googlecode.com/svn/test-files/pics/teddy/sitting_together_bears.jpg", "44e75d099a9b64fe400985089c9fda79")));
                    parts.add(createImagePart("9", new Interval(112, 16), store.createKey("https://kpro09.googlecode.com/svn/test-files/pics/teddy/stacked_bears.jpg", "e838ba5f3c46c97d55e3a9d46afa883d")));
            /*
parts.add(createImagePart("5", new Interval(18, 2), store.createKey("http://farm6.static.flickr.com/5308/5620385926_1fe89c1011_d.jpg")));
parts.add(createImagePart("6", new Interval(20, 2), store.createKey("http://farm6.static.flickr.com/5068/5620372140_6fdf929526_d.jpg")));
parts.add(createImagePart("7", new Interval(22, 2), store.createKey("http://farm6.static.flickr.com/5149/5619778633_8c5c4de76a_d.jpg")));
parts.add(createImagePart("8", new Interval(24, 4), store.createKey("http://farm6.static.flickr.com/5063/5619717619_2e4418cf79_d.jpg")));
parts.add(createImagePart("9", new Interval(28, 4), store.createKey("http://farm6.static.flickr.com/5182/5619705999_7dc97db79a_d.jpg")));
parts.add(createImagePart("10", new Interval(32, 4), store.createKey("http://farm6.static.flickr.com/5253/5590037842_0e14fca7d6_d.jpg")));
parts.add(createImagePart("11", new Interval(36, 4), store.createKey("http://farm6.static.flickr.com/5293/5590025654_cb498fa863_d.jpg")));
parts.add(createImagePart("12", new Interval(40, 4), store.createKey("http://farm6.static.flickr.com/5024/5589424921_0b3cc6147d_d.jpg")));
parts.add(createImagePart("13", new Interval(44, 4), store.createKey("http://farm6.static.flickr.com/5309/5590056328_21b208cd67_d.jpg")));
parts.add(createImagePart("14", new Interval(48, 4), store.createKey("http://farm6.static.flickr.com/5137/5589399371_b5d3860703_d.jpg")));
parts.add(createImagePart("15", new Interval(52, 4), store.createKey("http://farm6.static.flickr.com/5057/5589384627_9dee034844_d.jpg")));
parts.add(createImagePart("16", new Interval(56, 4), store.createKey("http://farm6.static.flickr.com/5150/5565692044_dfbcc9fe93_d.jpg")));
parts.add(createImagePart("17", new Interval(60, 4), store.createKey("http://farm6.static.flickr.com/5026/5565690200_ee1d9d97a0_d.jpg")));
parts.add(createImagePart("18", new Interval(64, 2), store.createKey("http://farm6.static.flickr.com/5144/5565670188_22f94fbca1_d.jpg")));
parts.add(createImagePart("19", new Interval(66, 2), store.createKey("http://farm6.static.flickr.com/5143/5565062389_06f55ea31a_d.jpg")));
parts.add(createImagePart("20", new Interval(68, 2), store.createKey("http://farm6.static.flickr.com/5211/5530266948_926f5edb3b_d.jpg")));
parts.add(createImagePart("21", new Interval(70, 2), store.createKey("http://farm6.static.flickr.com/5211/5530257408_87d9b70ce0_d.jpg")));
parts.add(createImagePart("22", new Interval(72, 2), store.createKey("http://farm6.static.flickr.com/5259/5530242914_037f0934ce_d.jpg")));
parts.add(createImagePart("23", new Interval(74, 2), store.createKey("http://farm6.static.flickr.com/5217/5529650335_49ffea7189_d.jpg")));
parts.add(createImagePart("24", new Interval(76, 2), store.createKey("http://farm6.static.flickr.com/5015/5530204204_86a3224d64_d.jpg")));
parts.add(createImagePart("25", new Interval(78, 2), store.createKey("http://farm6.static.flickr.com/5136/5503627301_2f50a19189_d.jpg")));
parts.add(createImagePart("26", new Interval(80, 2), store.createKey("http://farm6.static.flickr.com/5098/5503626969_cc7df6b45e_d.jpg")));
parts.add(createImagePart("27", new Interval(82, 2), store.createKey("http://farm6.static.flickr.com/5177/5503626689_851065e1fe_d.jpg")));
parts.add(createImagePart("28", new Interval(84, 4), store.createKey("http://farm6.static.flickr.com/5251/5504217288_65e6621d72_d.jpg")));
parts.add(createImagePart("29", new Interval(88, 4), store.createKey("http://farm6.static.flickr.com/5015/5486986508_81012fd4bc_d.jpg")));
parts.add(createImagePart("30", new Interval(92, 4), store.createKey("http://farm6.static.flickr.com/5019/5486986318_cb0439075d_d.jpg")));
parts.add(createImagePart("31", new Interval(96, 4), store.createKey("http://farm6.static.flickr.com/5052/5504216960_a95375d995_d.jpg")));
parts.add(createImagePart("32", new Interval(100, 4), store.createKey("http://farm6.static.flickr.com/5253/5486986126_76135203db_d.jpg")));
parts.add(createImagePart("33", new Interval(104, 4), store.createKey("http://farm6.static.flickr.com/5015/5486389985_618bf75eb3_d.jpg")));
parts.add(createImagePart("34", new Interval(108, 4), store.createKey("http://farm6.static.flickr.com/5219/5483844822_629197aef8_d.jpg")));
parts.add(createImagePart("35", new Interval(112, 4), store.createKey("http://farm6.static.flickr.com/5136/5483251197_d421492171_d.jpg")));
parts.add(createImagePart("36", new Interval(116, 4), store.createKey("http://farm6.static.flickr.com/5059/5483845036_2522a7ae34_d.jpg")));
parts.add(createImagePart("37", new Interval(120, 4), store.createKey("http://farm6.static.flickr.com/5017/5483250387_cfea275b22_d.jpg")));
parts.add(createImagePart("38", new Interval(124, 4), store.createKey("http://farm6.static.flickr.com/5013/5483250641_fc18e59160_d.jpg")));
parts.add(createImagePart("39", new Interval(128, 4), store.createKey("http://farm6.static.flickr.com/5180/5483844312_f2ae9683de_d.jpg")));
parts.add(createImagePart("40", new Interval(132, 4), store.createKey("http://farm6.static.flickr.com/5091/5483843396_73987c22c4_d.jpg")));
parts.add(createImagePart("41", new Interval(136, 4), store.createKey("http://farm6.static.flickr.com/5056/5483843164_053a775e48_d.jpg")));
parts.add(createImagePart("42", new Interval(140, 4), store.createKey("http://farm6.static.flickr.com/5100/5483842892_ca8a2f4dd3_d.jpg")));
parts.add(createImagePart("43", new Interval(144, 4), store.createKey("http://farm6.static.flickr.com/5052/5483842630_8f4e9ef410_d.jpg")));
parts.add(createImagePart("44", new Interval(148, 4), store.createKey("http://farm6.static.flickr.com/5299/5483842384_c63b72d616_d.jpg")));
parts.add(createImagePart("45", new Interval(152, 4), store.createKey("http://farm6.static.flickr.com/5014/5483841854_b137aace2a_d.jpg")));
parts.add(createImagePart("46", new Interval(156, 4), store.createKey("http://farm6.static.flickr.com/5251/5483248781_339a4cabc6_d.jpg")));
parts.add(createImagePart("47", new Interval(160, 4), store.createKey("http://farm6.static.flickr.com/5297/5483841546_e937163e2b_d.jpg")));
parts.add(createImagePart("48", new Interval(164, 4), store.createKey("http://farm6.static.flickr.com/5138/5483248017_97eca81997_d.jpg")));
*/
                }
            return new Composition(getClass().getSimpleName(), new MasterBeatPattern(0, 128, 130F), parts, FileRepresentation.NULL);
        }
    }.compose();
}
