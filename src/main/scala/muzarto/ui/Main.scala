package muzarto.ui

import com.sun.javafx.runtime.VersionInfo
import de.sciss.osc.Message
import de.sciss.osc.TCP.Client
import muzarto.engine.{Configuration, OscEngine, ShowControlEngine}
import muzarto.model.{ClipDurationReader, ShowReader}

import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.scene.input.{KeyCode, KeyEvent}

object Main extends JFXApp {

  println("JavaFX version: " + VersionInfo.getRuntimeVersion)

  Configuration.parse(parameters.raw) match {
    case None =>
    case Some(configuration) =>

//      val oscEngine = new OscEngine(configuration)
//      val oscChannel: Client = oscEngine.c
//      oscChannel ! Message("/eos/ping")

      val loadedShow = new ShowReader(configuration).read()

      val engine: ShowControlEngine = new ShowControlEngine(/*oscChannel*/)
      val mainView = new MainView(engine.model, engine).build

      new Thread(() => {
        val show = new ClipDurationReader().read(loadedShow)
        Platform.runLater {
          engine.model.showProperty.value = Some(show)
        }
      }).start()

      stage = new PrimaryStage {
        x = 0
        y = 0
        title = s"Muzarto :: ${loadedShow.title}"
        scene = new Scene(mainView, 800, 800) {
          // https://github.com/joffrey-bion/javafx-themes
          // TODO try out https://github.com/mousesrc/JavaFXDarculaTheme/blob/master/src/main/java/com/github/mousesrc/jfxdarcula/darcula.css
          stylesheets = Seq(
            classOf[Resource].getResource("/modenadark.css").toString,
            classOf[Resource].getResource("/media.css").toString,
          )

          onKeyPressed = (k: KeyEvent) => k.code match {
            case KeyCode.Space => println("space bar pressed")
            case _ =>
          }
        }
      }

    {
      val videoStage = new VideoWindow(engine.model.mediaPlayerModel).build()
      videoStage.x = 800 + 41
      videoStage.y = 0
      videoStage.show()
    }
  }

  override def stopApp(): Unit = {
    println("Stopping")
    // oscChannel.close()
  }

}
