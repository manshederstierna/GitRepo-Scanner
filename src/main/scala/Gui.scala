package reposcanner

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import scalafx.stage.DirectoryChooser
import scalafx.scene.control.TableView
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableCell, TableColumn, TableView}
import scalafx.scene.paint.Color
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.layout.Priority

object Globals:
	val repoItems = ObservableBuffer.empty[GitRepo]

object Gui extends JFXApp3:
	override def start(): Unit = 
		stage = createStage()
		
	
def createStage(): JFXApp3.PrimaryStage = 
	new JFXApp3.PrimaryStage:
		title = "Git Repo Scanner"
		scene = new Scene(900,630):
			content = createContent()



def createContent(): VBox =
	val table = createRepoTable()

	VBox.setVgrow(table, Priority.Always)

	new VBox:
		spacing = 10
		padding = Insets(15)

		children = Seq(
			new Label("Git Repository Scanner"):
				style = "-fx-font-size: 22px; -fx-font-weight: bold;",
			createScanButton(),
			table,
			createQuitButton()
		)		
		
def createQuitButton(): Button =
	new Button("Quit"):
		style = "-fx-background-color: #ff0000; -fx-text-fill: white;"
		onAction = _ =>
			System.exit(0)
			
def createScanButton(): Button = 
	new Button("Scan for git repositories"):
		style = "-fx-background-color: #ff0000; -fx-text-fill: white;"
		onAction = _ =>
			scan_repos()
			

def scan_repos(): Unit = 
	val chooser = new DirectoryChooser():
		title = "Choose a root directory to scan for git repositories"
		
	val selectedDirectory = chooser.showDialog(Gui.stage)
	
	if selectedDirectory != null then
		val root = selectedDirectory.toPath
		val repos = RepoScanner.findRepos(root)
		Globals.repoItems.clear()
		Globals.repoItems ++= repos
	
	
	
def createRepoTable(): TableView[GitRepo] =
	new TableView[GitRepo](Globals.repoItems): 
		columns ++= Seq(
			createPathColumn()
		)
		
def createPathColumn(): TableColumn[GitRepo, String] =
	new TableColumn[GitRepo, String]("Path"):
		prefWidth = 630
		cellValueFactory = data => 
			StringProperty(data.value.path.toString())