package no.bouvet.kpro;

import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;
import no.bouvet.kpro.renderer.simple.LyricGUI;
import no.bouvet.kpro.renderer.simple.LyricRenderer;
import no.bouvet.kpro.renderer.simple.LyricPart;
import no.bouvet.kpro.renderer.audio.*;
import no.bouvet.kpro.tagger.PlayStuff;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.AbstractPart;
import no.lau.tagger.model.AudioPart;
import no.lau.tagger.model.SimpleSong;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class AudioAndLyricsTest {

    @Test
    public void testFullRendering() throws Exception {
        Composition composition = new Composition(135F, parts());
        Instructions instructions = PlayStuff.createInstructionsFromParts(composition);

        AudioTarget target = new AudioPlaybackTarget();
        Renderer renderer = null;

        final LyricGUI lyricGUI = createLyricGUI();

        try {
            System.out.println("Duration Time: " + instructions.getDuration() / Renderer.RATE + " seconds");

            // Create the Renderer with an AudioRenderer instance
            renderer = new Renderer(instructions);
            renderer.addRenderer(new AudioRenderer(target));
            renderer.addRenderer(new LyricRenderer(lyricGUI));

            // Start the renderer at the beginning
            System.out.println("Starting renderer...");
            renderer.start(0);

            // Wait for the renderer to finish
            while (renderer.isRendering()) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }

                int samples = target.getOutputPosition();
                double percent = Math.floor((double) samples / (double) instructions.getDuration() * 1000) / 10;

                System.out.println("Rendered " + samples + " samples (" + percent + "%)...");
            }
            System.out.println("Fin");
        } finally {
            if (renderer != null)
                renderer.stop();
            target.close();
        }
    }


    public LyricGUI createLyricGUI() {
        final LyricGUI lyricGUI = new LyricGUI();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                lyricGUI.create();
                lyricGUI.show();
            }
        });
        return lyricGUI;
    }


    public static List<? extends AbstractPart> parts() {
        SimpleSong returning = new XStreamParser().load("/Users/stiglau/kpro/holden-nothing-93_returning_mix.dvl");
        List<AbstractPart> parts = new ArrayList<AbstractPart>();
        parts.add(new AudioPart(returning, 0F, 16F, returning.segments.get(3)));
        parts.add(new LyricPart("Hello World!", 0F, 12F));
        parts.add(new AudioPart(returning, 12F, 32F, returning.segments.get(6)));
        parts.add(new LyricPart("Stig er kul!", 12F, 32F));
        parts.add(new AudioPart(returning, 32F, 62.5F, returning.segments.get(9)));
        parts.add(new LyricPart("And so on!", 32F, 62F));
        parts.add(new AudioPart(returning, 62F, 63.5F, returning.segments.get(10)));
        parts.add(new AudioPart(returning, 63F, 64.5F, returning.segments.get(11)));
        parts.add(new AudioPart(returning, 64F, 128F, returning.segments.get(12)));
        parts.add(new AudioPart(returning, 128F, 256F, returning.segments.get(14)));
        return parts;
    }

}
