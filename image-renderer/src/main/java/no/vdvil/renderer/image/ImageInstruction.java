package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;
import java.io.InputStream;
import java.net.URL;


public class ImageInstruction extends Instruction {
    public final URL imageUrl;
    public InputStream cachedStream = null;

    public ImageInstruction(int start, int end, float bpm, final URL imageUrl, InputStream cached) {
        this.imageUrl = imageUrl;
        this.cachedStream = cached;
        float speedFactor = Renderer.RATE * 60 / bpm;
        //An uncertain problem that appends ~44100 millseconds to everything. Weired!
        //TODO FIX TIMING ISSUES HERE!!!!!!!!!!!!!!!
        _start = new Float(start * speedFactor + (bpm * 44100 / 120)).intValue();
        System.out.println("bpm * 44100 / 120 = " + bpm * 44100 / 120);
        _end = new Float(end * speedFactor + 55125).intValue();
    }
}
