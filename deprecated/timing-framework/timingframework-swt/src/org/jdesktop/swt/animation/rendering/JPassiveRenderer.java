package org.jdesktop.swt.animation.rendering;

import java.util.concurrent.TimeUnit;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.jdesktop.core.animation.i18n.I18N;
import org.jdesktop.core.animation.rendering.JRenderer;
import org.jdesktop.core.animation.rendering.JRendererTarget;
import org.jdesktop.core.animation.timing.TimingSource;
import org.jdesktop.core.animation.timing.TimingSource.PostTickListener;

/**
 * Manages passive rendering on a Swing {@link Canvas}.
 * <p>
 * To use this renderer a client constructs a {@link Canvas} and passes it to
 * the constructor with a {@link JRendererTarget} implementation and a timing
 * source. A typical sequence would be
 * 
 * <pre>
 * Canvas on = new Canvas(shell, SWT.DOUBLE_BUFFERED);
 * final JRendererTarget&lt;Display, GC&gt; target = this;
 * final TimingSource timingSource = new SWTTimingSource();
 * JRenderer renderer = new JPassiveRenderer(on, target, timingSource);
 * timingSource.init();
 * </pre>
 * 
 * In the above snippet <tt>on</tt> will be rendered to. The enclosing instance,
 * <tt>this</tt>, implements {@link JRendererTarget} and will be called to
 * customize what is displayed on-screen.
 * 
 * @author Tim Halloran
 */
public class JPassiveRenderer implements JRenderer<Canvas> {

  /*
   * Thread-confined to the SWT UI thread
   */
  private final Canvas f_on;
  private final JRendererTarget<Display, GC> f_target;
  private final TimingSource f_ts;
  private boolean f_renderingStarted = false;
  private final PostTickListener f_postTick = new PostTickListener() {
    public void timingSourcePostTick(TimingSource source, long nanoTime) {
      long now = System.nanoTime();
      if (f_renderCount != 0) {
        f_totalRenderTime += now - f_lastRenderTimeNanos;
      }
      f_lastRenderTimeNanos = now;
      f_renderCount++;
      f_target.renderUpdate();
      f_on.redraw();
    }
  };

  /*
   * Statistics counters
   */
  private long f_lastRenderTimeNanos;
  private long f_totalRenderTime = 0;
  private long f_renderCount = 0;

  public JPassiveRenderer(Canvas on, JRendererTarget<Display, GC> target, TimingSource timingSource) {
    if (on == null)
      throw new IllegalArgumentException(I18N.err(1, "on"));
    f_on = on;

    if (!on.getDisplay().getThread().equals(Thread.currentThread()))
      throw new IllegalStateException(I18N.err(200));

    if (target == null)
      throw new IllegalArgumentException(I18N.err(1, "life"));
    f_target = target;

    if (timingSource == null)
      throw new IllegalArgumentException(I18N.err(1, "timingSource"));
    f_ts = timingSource;

    f_on.addPaintListener(new PaintListener() {
      public void paintControl(PaintEvent e) {
        /*
         * Check if initialization is necessary.
         */
        if (f_on.isVisible() && !f_renderingStarted) {
          f_renderingStarted = true;
          f_target.renderSetup(f_on.getDisplay());
          f_ts.addPostTickListener(f_postTick);
        }
        final GC g = e.gc;
        final int width = f_on.getBounds().width;
        final int height = f_on.getBounds().height;
        f_target.render(g, width, height);
      }
    });
  }

  @Override
  public Canvas getOn() {
    return f_on;
  }

  @Override
  public void invokeLater(Runnable task) {
    f_on.getDisplay().asyncExec(task);
  }

  @Override
  public TimingSource getTimingSource() {
    return f_ts;
  }

  @Override
  public long getFPS() {
    final long avgCycleTime = getAverageCycleTimeNanos();
    if (avgCycleTime != 0) {
      return TimeUnit.SECONDS.toNanos(1) / avgCycleTime;
    } else
      return 0;
  }

  @Override
  public long getAverageCycleTimeNanos() {
    if (f_renderCount != 0) {
      return (f_totalRenderTime) / f_renderCount;
    } else
      return 0;
  }

  @Override
  public void shutdown() {
    f_ts.removePostTickListener(f_postTick);
    f_target.renderShutdown();
  }
}
