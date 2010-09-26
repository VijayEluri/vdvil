package no.lau.vdvil.gui

import scala.swing._
import TabbedPane._
import no.lau.vdvil.cache.VdvilCacheHandler
import no.lau.tagger.scala.model.{ToScalaSong, ScalaSegment, ScalaSong}
import org.slf4j.{LoggerFactory, Logger}

/**
 * Note - to make the TagGUI functional, it can be necessary to make a small change to the file and recompile.
 */
object TagGUI extends SimpleSwingApplication {
  val log:Logger =  LoggerFactory.getLogger(classOf[ScalaDynamicDvlTable])//TODO THIS IS SOOOOO WRONG!!!!
  var dvlFilePath = System.getProperty("user.home") + "/kpro"

  val cacheHandler = new VdvilCacheHandler
  var dvlTable: ScalaDynamicDvlTable = null
  //These are just test variables
  val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"

  val tabs = new TabbedPane

  def top = new MainFrame {
    title = "Tagging GUI"
    //size = new Dimension(800, 600) //Need a frame repack

    menuBar = new MenuBar {
      import Dialog._
      contents += new Menu("Load") {
        contents += new MenuItem(Action("From File") {

          val fileChooser = new FileChooser(new java.io.File(dvlFilePath))
          val returnVal = fileChooser.showOpenDialog(this);
          if (returnVal == FileChooser.Result.Approve) {
            dvlFilePath = fileChooser.selectedFile.getAbsolutePath
            try {
              val loadedSong = ToScalaSong.fromJava(cacheHandler.loadSimpleSongFromDvlOnDisk(dvlFilePath))
              showEditingPanel(dvlFilePath, NeatStuff.setAllNullIdsRandom(loadedSong))
            } catch {case _ => log.error("Could not parse file {}", dvlFilePath)} 
          }
        })
        contents += new MenuItem(Action("From Web") {
          val urlOption = showInput(menuBar, "", "Load from", Message.Plain, Swing.EmptyIcon, Nil, returningDvlUrl)
          if (urlOption.isDefined) {
            val songOption = fetchDvlAndMp3FromWeb(urlOption.get)
            if (songOption.isDefined)
              showEditingPanel(urlOption.get, songOption.get)
          }
        })
      }
    }

    contents = new BorderPanel {
      add(tabs, BorderPanel.Position.Center)
    }
  }

  def fetchDvlAndMp3FromWeb(url: String): Option[ScalaSong] = {
    try {
      val javaSong = new VdvilCacheHandler().fetchSimpleSongAndCacheDvlAndMp3(url, null)
      Some(NeatStuff.setAllNullIdsRandom(ToScalaSong.fromJava(javaSong)))
    } catch {case _ => log.error("Could not download or parse {}", url); None} // TODO This catch all is not working -> WHY!? 
  }

  def showEditingPanel(input: String, song: ScalaSong) = {
    dvlTable = new ScalaDynamicDvlTable(input, song)
    //TODO Frame Repack
    tabs.pages += new Page(song.reference, dvlTable.ui)
  }
}

object NeatStuff {
  import scala.util.Random
  import scala.collection.mutable.ListBuffer

  def setAllNullIdsRandom(original: ScalaSong): ScalaSong = {
    var newSegmentList = new ListBuffer[ScalaSegment]()

    for (thisSegment <- original.segments) {
      if (thisSegment.id == null) {
        val id = String.valueOf(Math.abs(Random.nextLong))
        newSegmentList += new ScalaSegment(id, thisSegment.start, thisSegment.end, thisSegment.text)
      }
      else
        newSegmentList += thisSegment
    }
    return new ScalaSong(original.reference, original.mediaFile, newSegmentList.toList, original.bpm)
  }

  def updateSegmentInSimpleSong(editedSegment: ScalaSegment, oldSong: ScalaSong): ScalaSong = {
    var newSegmentList = new ListBuffer[ScalaSegment]()

    for (thisSegment <- oldSong.segments) {
      if (thisSegment.id == editedSegment.id) {
        newSegmentList += editedSegment
      } else {
        newSegmentList += thisSegment
      }
    }
    return new ScalaSong(oldSong.reference, oldSong.mediaFile, newSegmentList.toList, oldSong.bpm)
  }
}



