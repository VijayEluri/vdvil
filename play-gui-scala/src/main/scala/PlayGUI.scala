package no.lau.vdvil.player

import scala.swing._
import event.ButtonClicked
import no.lau.vdvil.gui.TagGUI
import no.lau.tagger.scala.model.{ScalaMediaFile, ScalaSong}

/**
 * Play GUI for playing .vdl files
 */
object PlayGUI extends SimpleSwingApplication {
  //These are just test variables
  val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"
  val testSong: ScalaSong = TagGUI.fetchDvlAndMp3FromWeb(returningDvlUrl).get
  var compositionOption: Option[ScalaComposition] = None


  def top = new MainFrame {
    title = "Play GUI"
    contents = new FlowPanel {
      contents += new Label("Start from")
      contents += startField
      contents += new Label("Stop")
      contents += stopField
      contents += playButton
      contents += playCompositionButton
      contents += new Label("BPM")
      contents += bpmField
      contents += loadButton
    }
  }

  val bpmField = new TextField(4) {
    reactions += {case _ => if (!text.isEmpty) {testSong.bpm = text.toFloat; println("BPM Changed to " + testSong.bpm)}}
  }
  val startField = new TextField("0", 4)
  val stopField = new TextField(4)
  val playButton = new Button("Play Segment") {
    reactions += {case ButtonClicked(_) => playSegment(startField.text.toFloat, stopField.text.toFloat, testSong)}
  }
  val playCompositionButton = new Button("Play Composition") {
    reactions += {case ButtonClicked(_) => compositionPlayer.pauseAndplay(startField.text.toFloat)}
  }
  val loadButton = new Button("Load") {
    reactions += {
      case ButtonClicked(_) => {
        val composition = new ScalaComposition(150F, CompositionExample.parts)
        compositionOption = Some(composition)
        stopField.text_=(composition.durationAsBeats.toString)
        bpmField.text_=(composition.masterBpm.toString)
      }
    }
  }


  var songPlayer: ScalaPlayer = null

  /**
   * Plays the segment of your choice
   */
  def playSegment(startFrom: Float, stopAt: Float, song: ScalaSong) {
    if (songPlayer != null)
      songPlayer.playPause(-1F, -1F) //Call to stop the player
    val mf = song.mediaFile
    val pathToMp3Option = TagGUI.cacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum)
    if (pathToMp3Option.isDefined) {
      val copyOfSong = new ScalaSong(song.reference, new ScalaMediaFile(pathToMp3Option.get, mf.checksum, mf.startingOffset), song.segments, song.bpm)
      songPlayer = new ScalaPlayer(copyOfSong)
      songPlayer.playPause(startFrom, stopAt)
    }
  }

  val compositionPlayer = new ScalaCompositionPlayer(None) {
    def pauseAndplay(startFrom: Float) {
      stop
      scalaCompositionOption = compositionOption
      play(startFrom)
    }
  }

  /*
 override def startup(args: Array[String]) {
  val t = top
  t.size_=(new Dimension(800, 600))
  t.visible = true
 } */
}