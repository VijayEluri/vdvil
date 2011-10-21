package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;
import no.lau.vdvil.cache.DownloaderFacade;
import no.vdvil.renderer.image.swinggui.ImagePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageRenderer extends AbstractRenderer {
    private ImageListener[] listener;
    List<ImageInstruction> runningImageInstructionList = new ArrayList<ImageInstruction>();
    JFrame frame;
    private DownloaderFacade cache;
    Logger log = LoggerFactory.getLogger(getClass());

    public ImageRenderer(int width, int height, DownloaderFacade cache) {
        this.cache = cache;
        listener = new ImageListener[] {new ImagePanel()};

        frame = new JFrame("ImageRendererGUITest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        for (ImageListener imageListener : listener) {
            frame.getContentPane().add((Component) imageListener);
        }
    }

    @Override
    public boolean start(int time) {
        return true;
    }

    public void handleInstruction(int time, Instruction instruction) {
        log.debug("Got instruction {} to be played at {}", instruction, time);
        if (instruction != null) {
            if (instruction instanceof ImageInstruction) {
                ImageInstruction imageInstruction = (ImageInstruction) instruction;
                runningImageInstructionList.add(imageInstruction);
                render(imageInstruction);
            }
        }
    }

    private void render(ImageInstruction imageInstruction) {
        for (final ImageListener imageListener : listener) {
            try {
                final InputStream imageStream = cache.fetchAsStream(imageInstruction.imageUrl);
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    imageListener.show(imageStream);
                }
            });
                if(!frame.isVisible())
                    frame.setVisible(true);
            } catch (IOException e) {
                log.error("Error loading image {}", imageInstruction.imageUrl, e);
            }
        }
    }

    @Override
    public boolean isRendering() {
        return !runningImageInstructionList.isEmpty();
    }

    @Override
    public void stop(Instruction instruction) {
        runningImageInstructionList.remove(instruction);
        if(runningImageInstructionList.isEmpty())
            frame.setVisible(false);
    }
}
