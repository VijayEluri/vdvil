package org.jdesktop.swt.animation.demos;

import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.Animator.RepeatBehavior;
import org.jdesktop.core.animation.timing.Interpolator;
import org.jdesktop.core.animation.timing.KeyFrames;
import org.jdesktop.core.animation.timing.PropertySetter;
import org.jdesktop.core.animation.timing.TimingSource;
import org.jdesktop.core.animation.timing.TimingTarget;
import org.jdesktop.core.animation.timing.interpolators.SplineInterpolator;
import org.jdesktop.swt.animation.timing.sources.SWTTimingSource;

/**
 * The full-blown demo with all of the bells and whistles. This one uses the
 * facilities shown in all of the other variations, but adds both multi-step and
 * non-linear interpolation. It does this by creating a KeyFrames object to hold
 * the times/values/splines used for each segment of the race. It also adds an
 * animation for the rotation of the car (since the car should turn as it goes
 * around the curves) and sound effects (just to go completely overboard).
 * <p>
 * This demo is discussed in Chapter 15 on pages 414&ndash;419 of <i>Filthy Rich
 * Clients</i> (Haase and Guy, Addison-Wesley, 2008). In the book it is referred
 * to as <tt>MultiStepRace</tt> rather than <tt>RaceCompleteMultiStep</tt>.
 * 
 * @author Chet Haase
 * @author Tim Halloran
 */
public final class RaceCompleteMultiStep {

  public static void main(String args[]) {
    final Display display = Display.getDefault();
    final Shell shell = new Shell(display);
    shell.setLayout(new FillLayout());

    final TimingSource ts = new SWTTimingSource(display);
    Animator.setDefaultTimingSource(ts);
    ts.init();

    final RaceCompleteMultiStep race = new RaceCompleteMultiStep(shell, "SWT Race (Multi-Step)");

    shell.pack();
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    race.stopSoundEffects();
    ts.dispose();
    display.dispose();
    System.exit(0);
  }

  public static final int RACE_TIME = 10000;

  private final Animator animator;
  private final RaceSoundEffects soundEffects;

  /** Creates a new instance of BasicRace */
  public RaceCompleteMultiStep(Shell shell, String appName) {
    final RaceGUI basicGUI = new RaceGUI(shell, appName);

    animator = new Animator.Builder().setDuration(RACE_TIME, TimeUnit.MILLISECONDS).setRepeatCount(Animator.INFINITE)
        .setRepeatBehavior(RepeatBehavior.LOOP).build();

    // We're going to need a more involved PropertyRange object
    // that has all curves of the track in it, as well as
    // non-linear movement around the curves
    Point values[] = { RaceTrackView.START_POS, RaceTrackView.FIRST_TURN_START, RaceTrackView.FIRST_TURN_END,
        RaceTrackView.SECOND_TURN_START, RaceTrackView.SECOND_TURN_END, RaceTrackView.THIRD_TURN_START,
        RaceTrackView.THIRD_TURN_END, RaceTrackView.FOURTH_TURN_START, RaceTrackView.START_POS };
    // Calculate the keyTimes based on the distances that must be
    // traveled on each leg of the journey
    double totalDistance = 0;
    double segmentDistance[] = new double[values.length];
    for (int i = 0; i < (values.length - 1); ++i) {
      segmentDistance[i] = distance(values[i], values[i + 1]);
      totalDistance += segmentDistance[i];
    }
    segmentDistance[(values.length - 1)] = distance(values[(values.length - 1)], values[0]);
    totalDistance += segmentDistance[(values.length - 1)];
    double times[] = new double[values.length];
    double elapsedTime = 0.0f;
    times[0] = 0.0f;
    times[values.length - 1] = 1.0f;
    for (int i = 0; i < (values.length - 2); ++i) {
      times[i + 1] = elapsedTime + (segmentDistance[i] / totalDistance);
      elapsedTime = times[i + 1];
    }

    /*
     * For realistic movement, we want a big acceleration on the straightaways.
     */
    Interpolator initialSpline = new SplineInterpolator(1.00f, 0.00f, 0.2f, .2f);
    Interpolator straightawaySpline = new SplineInterpolator(0.50f, 0.20f, .50f, .80f);
    Interpolator curveSpline = new SplineInterpolator(0.50f, 0.20f, .50f, .80f);
    Interpolator finalSpline = new SplineInterpolator(0.50f, 0.00f, .50f, 1.00f);
    Interpolator[] interps = { null, initialSpline, curveSpline, straightawaySpline, curveSpline, straightawaySpline, curveSpline,
        straightawaySpline, finalSpline };

    final KeyFrames.Builder<Point> builder = new KeyFrames.Builder<Point>(values[0]);
    for (int i = 1; i < values.length; i++) {
      builder.addFrame(values[i], times[i], interps[i]);

    }
    final KeyFrames<Point> keyFrames = builder.build();

    /*
     * This PropertySetter enables the animation for the car movement all the
     * way around the track.
     */
    final TimingTarget modifier = PropertySetter.getTarget(basicGUI.getTrack(), "carPosition", keyFrames);
    animator.addTarget(modifier);

    /*
     * Now create similar keyframes for rotation of car.
     */
    int[] rotationKeyValues = { 360, 315, 270, 225, 180, 135, 90, 45, 0 };
    Interpolator straightawayTurnSpline = new SplineInterpolator(1.0f, 0.0f, 1.0f, 0.0f);
    Interpolator curveTurnSpline = new SplineInterpolator(0.0f, 0.5f, 0.5f, 1.0f);
    Interpolator[] rotationInterps = { null, straightawayTurnSpline, curveTurnSpline, straightawayTurnSpline, curveTurnSpline,
        straightawayTurnSpline, curveTurnSpline, straightawayTurnSpline, curveTurnSpline };
    final KeyFrames.Builder<Integer> rotationBuilder = new KeyFrames.Builder<Integer>(rotationKeyValues[0]);
    for (int i = 1; i < values.length; i++) {
      rotationBuilder.addFrame(rotationKeyValues[i], times[i], rotationInterps[i]);
    }
    KeyFrames<Integer> rotationKeyFrames = rotationBuilder.build();
    final TimingTarget rotationModifier = PropertySetter.getTarget(basicGUI.getTrack(), "carRotation", rotationKeyFrames);
    animator.addTarget(rotationModifier);

    /*
     * Finally, add sound effects, triggered by the same animator.
     */
    soundEffects = new RaceSoundEffects(rotationKeyFrames);
    animator.addTarget(soundEffects);

    final RaceControlPanel controlPanel = basicGUI.getControlPanel();
    final Button goButton = controlPanel.getGoButton();
    final Button reverseButton = controlPanel.getReverseButton();
    final Button pauseResumeButton = controlPanel.getPauseResumeButton();
    final Button stopButton = controlPanel.getStopButton();

    goButton.setEnabled(true);
    reverseButton.setEnabled(false);
    pauseResumeButton.setEnabled(false);
    stopButton.setEnabled(false);

    goButton.addListener(SWT.Selection, new Listener() {
      @Override
      public void handleEvent(Event event) {
        animator.start();
        goButton.setEnabled(false);
        reverseButton.setEnabled(true);
        pauseResumeButton.setEnabled(true);
        stopButton.setEnabled(true);
        basicGUI.getTrack().setCarReverse(false);
      }
    });
    reverseButton.addListener(SWT.Selection, new Listener() {
      @Override
      public void handleEvent(Event event) {
        if (animator.isPaused()) {
          java.awt.Toolkit.getDefaultToolkit().beep();
          return;
        }

        final boolean reverseSucceeded = animator.reverseNow();
        if (reverseSucceeded) {
          basicGUI.getTrack().toggleCarReverse();
        } else {
          animator.startReverse();
          basicGUI.getTrack().setCarReverse(true);
        }
      }
    });
    pauseResumeButton.addListener(SWT.Selection, new Listener() {
      @Override
      public void handleEvent(Event event) {
        if (animator.isPaused()) {
          animator.resume();
          reverseButton.setEnabled(true);
          stopButton.setEnabled(true);
        } else {
          if (animator.isRunning()) {
            animator.pause();
            reverseButton.setEnabled(false);
            stopButton.setEnabled(false);
          }
        }
      }
    });
    stopButton.addListener(SWT.Selection, new Listener() {
      @Override
      public void handleEvent(Event event) {
        animator.stop();
        goButton.setEnabled(true);
        reverseButton.setEnabled(false);
        pauseResumeButton.setEnabled(false);
        stopButton.setEnabled(false);
      }
    });
  }

  public void stopSoundEffects() {
    if (soundEffects != null)
      soundEffects.stop();
  }

  public static double distance(Point pt1, Point pt2) {
    double px = pt1.x - pt2.x;
    double py = pt1.y - pt2.y;
    return Math.sqrt(px * px + py * py);
  }
}
