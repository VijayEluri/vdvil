package no.lau.vdvil.player

import scala.swing._
import event.ButtonClicked
import swing.TabbedPane.Page
import org.slf4j.LoggerFactory
import java.net.URL
import no.vdvil.renderer.audio.TestMp3s
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer
import no.lau.vdvil.handler._
import no.lau.vdvil.handler.persistence._
import no.lau.vdvil.timing.MasterBeatPattern

/**
 * Play GUI for playing .vdl files
 */
object PlayGUI extends SimpleSwingApplication {
  val tabs = new TabbedPane

  val log = LoggerFactory.getLogger(this.getClass)
  val javaZoneDemoCompositionUrl = TestMp3s.javaZoneComposition_WithoutImages

  def top = new MainFrame {
    title = "Play GUI"
    menuBar = new MenuBar {
      contents += new Menu("Load") {
        contents += new MenuItem(Action("from web") {
          Dialog.showInput(menuBar, "", "Load from", Dialog.Message.Plain, Swing.EmptyIcon, Nil, javaZoneDemoCompositionUrl.toString).map(chosenPath => startDownload(new URL(chosenPath)))
        })
      }
    }

    contents = new BorderPanel {
      add(tabs, BorderPanel.Position.Center)
    }
  }

 override def startup(args: Array[String]) {
  val t = top
  t.size_=(new Dimension(800, 600))
  t.visible = true
 }

  def startDownload(url:URL){
    println(url)
    val composition:Composition = new DownloadAndParseFacade {
      addCache(new SimpleFileCache)
      addParser(new CompositionXMLParser(this))
    }.parse(PartXML.create(url)).asInstanceOf[Composition]
    println(composition)
    tabs.pages.append(new Page(composition.name, new PlayPanel(composition).ui))
  }
}

class PlayPanel(val composition: Composition) {
  lazy val ui = new FlowPanel {
    contents += new Label("Start from")
    contents += startField
    contents += new Label("Stop")
    contents += stopField
    contents += playCompositionButton
    contents += new Label("BPM")
    contents += bpmField
  }
  val beatPattern = composition.masterBeatPattern
  val bpmField = new TextField(beatPattern.masterBpm.toString, 4)
  val startField = new TextField("0", 4)
  val stopField = new TextField(beatPattern.toBeat.toString, 4)
  val playCompositionButton = new Button("Play Composition") {
    reactions += {case ButtonClicked(_) => compositionPlayer.pauseAndplay(startField.text.toInt)}
  }
  val compositionPlayer = new PreconfiguredVdvilPlayer() {
    def pauseAndplay(startFrom: Int) {
      val beatPattern = new MasterBeatPattern(startField.text.toInt, stopField.text.toInt, bpmField.text.toFloat)
      init(composition, beatPattern)
      //stop
      println(beatPattern)
      println("Playing " + composition.multimediaParts.size)
      play()
    }
  }
}
