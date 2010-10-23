package no.lau.vdvil.downloading

import collection.immutable.HashMap
import actors.Actor
import swing.TabbedPane.Page
import no.lau.vdvil.player._
import swing.{GridPanel, Label}

class DownloadingPanel(song: Song) extends Actor with DvlLabel {
  lazy val dvlLabels: Map[Dvl, Label] = asMap

  lazy val ui = new GridPanel(dvlLabels.size, 1) {
    dvlLabels.foreach(contents += _._2)
  }

  def asMap: Map[Dvl, Label] = {
    var map = new HashMap[Dvl, Label]
    song.dvls.foreach(dvl => map += dvl -> new Label(dvl.url))
    map
    //for{dvl <- dvls} yield (dvl -> new Label(dvl.url))
  }

  def setLabel(dvl: Dvl, text: String) {dvlLabels(dvl).text_=(text)}
  //TODO This code should be merged with Coordinator!!!
  def act {
    loop {
      react {
        case Start => {
          val coordinator = new DownloadCoordinatorActor(song, this, this)
          coordinator.start
          song.dvls.foreach(dvl => new DownloadActor(dvl, coordinator).start)
        }
        case Success => {
          val composition = new ScalaComposition(150F, CompositionExample.parts)
          PlayGUI.tabs.pages.append(new Page("PLAYPANEL", new PlayPanel(composition).ui))
          exit
        }
        case Error => {
          exit
        }
      }
    }
  }
}
case class Start
case class Success
case class Error

trait DvlLabel {
  def setLabel(dvl: Dvl, text: String)
}