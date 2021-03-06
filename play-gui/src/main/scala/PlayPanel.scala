package no.lau.vdvil.player

import java.net.URL
import no.lau.vdvil.handler.Composition
import no.lau.vdvil.handler.persistence.PartXML
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer
import no.lau.vdvil.timing.MasterBeatPattern
import swing._
import event.ButtonClicked

class PlayPanel(val url:URL) {
  val cache = PreconfiguredVdvilPlayer.downloadAndParseFacade
  var composition: Composition = fetchComposition(url)
  def name = composition.name

  lazy val ui = new FlowPanel {
    contents += new Label("Start from")
    contents += startField
    contents += new Label("Stop")
    contents += stopField
    contents += new Label("BPM")
    contents += bpmField
    contents += playCompositionButton
    contents += reloadButton
  }
  val beatPattern = composition.masterBeatPattern
  val bpmField = new TextField(beatPattern.masterBpm.toString, 4)
  val startField = new TextField("0", 4)
  val stopField = new TextField(beatPattern.toBeat.toString, 4)
  val playCompositionButton = new Button("Play Composition") {
    reactions += {case ButtonClicked(_) => compositionPlayer.pauseAndplay(new MasterBeatPattern(startField.text.toInt, stopField.text.toInt, bpmField.text.toFloat))}
  }
  val reloadButton = new Button("Reload Composition") {
    reactions += {case ButtonClicked(_) => {
      cache.setRefreshCaches(true)
      cache.fetchAsStream(url)
      cache.setRefreshCaches(false)
      composition = fetchComposition(url)
      stopField.text_=(composition.masterBeatPattern.toBeat.toString)
    }}
  }
  val compositionPlayer = new PreconfiguredVdvilPlayer() {
    def pauseAndplay(beatPattern:MasterBeatPattern) {
      stop
      init(composition, beatPattern)
      println("Playing " + composition.name + " " + beatPattern)
      play
    }
  }

  def fetchComposition(url:URL) = cache.parse(PartXML.create(url)).asInstanceOf[Composition]
}