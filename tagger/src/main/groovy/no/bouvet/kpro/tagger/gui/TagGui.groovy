package no.bouvet.kpro.tagger.gui

import java.awt.BorderLayout
import javax.swing.JTextField
import javax.swing.JFrame
import javax.swing.JFileChooser
import groovy.swing.SwingBuilder
import no.bouvet.kpro.tagger.PlayerBase
import no.bouvet.kpro.tagger.model.SimpleSong
import no.bouvet.kpro.tagger.persistence.SimpleSongParser
import no.bouvet.kpro.tagger.persistence.XStreamParser

class TagGui {
    def swing = new SwingBuilder()
    JTextField bpmText
    JFrame frame
    PlayerBase playerBase

    public void createGui() {
        frame = swing.frame(title: 'Frame', size: [500, 300], defaultCloseOperation: JFrame.EXIT_ON_CLOSE) {
            borderLayout()

            button(text: 'Load File', constraints: BorderLayout.SOUTH,
                    actionPerformed: {
                        JFileChooser fileChooser = swing.fileChooser(currentDirectory: new File(SimpleSongParser.path))
                        int returnVal = fileChooser.showOpenDialog(frame);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            loadFile(fileChooser.getSelectedFile().getAbsolutePath())
                        }
                    }
            )
        }
        frame.pack()
        frame.show()
    }

    def loadFile(String filePath) {
        XStreamParser parser = new XStreamParser()
        SimpleSong song = parser.load(filePath)
        playerBase = new PlayerBase(song)
        frame.add(playerBase.getDynamicTimeTable(), BorderLayout.CENTER)
        frame.pack()
        frame.show()
    }

    public static void main(String[] args) {
        def tagGui = new TagGui()
        tagGui.createGui()
    }
}


