package no.lau.vdvil.timingframework.renderertarget;

import no.lau.vdvil.timing.MasterBeatPattern;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingTarget;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;

public class TimingInstruction {
    public MasterBeatPattern beatPattern;
    public final TimingTarget renderingTarget;


    @Deprecated
    public TimingInstruction(MasterBeatPattern beatPattern) {
        this.beatPattern = beatPattern;
        this.renderingTarget = new SimpleTimingTarget();
    }

    public TimingInstruction(MasterBeatPattern beatPattern, TimingTarget timingTarget) {
        this.beatPattern = beatPattern;
        this.renderingTarget = timingTarget;
    }

    public class SimpleTimingTarget extends TimingTargetAdapter implements TimingTarget {

        public void begin(Animator source) {
            System.out.println("Simple timing Target Begin");
        }

        public void end(Animator source) {
            System.out.println("Simple timing Target End");
        }

        public void timingEvent(Animator source, double fraction) {
            //System.out.println("Simple timing Target TimingEvent");

        }

    }
}
