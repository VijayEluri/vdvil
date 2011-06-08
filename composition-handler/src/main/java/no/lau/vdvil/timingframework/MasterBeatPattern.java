package no.lau.vdvil.timingframework;

public class MasterBeatPattern {
    private Integer fromBeat;
    private Integer toBeat;
    final Float masterBpm;

    public MasterBeatPattern(int fromBeat, int toBeat, Float masterBpm) {
        this.fromBeat = fromBeat;
        this.toBeat = toBeat;
        this.masterBpm = masterBpm;
    }


    public Float getBpm(int beat) {
        return masterBpm;
    }

    public Float duration() {
        return duration(this.fromBeat, this.toBeat);
    }
    public Float duration(int fromBeat, int toBeat) {
        int durationBeats = toBeat - fromBeat;
        return durationBeats * 60 * 1000 / masterBpm;
    }

    public double percentage(Integer startBeat) {
        return startBeat.doubleValue() / toBeat;
    }
}
