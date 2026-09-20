package reposcanner
import scala.io.StdIn
import java.nio.file.Path

@main def main(): Unit =
  val root = StdIn.readLine("Enter the root directory you want to scan from" + "\n").trim
  val repos = RepoScanner.findRepos(Path.of(root))
  finalPrint(repos)
  


def printRepo(gitrepo: GitRepo): Unit = 
	println(gitrepo.path.toString() + "\n")
	
def finalPrint(ls: List[GitRepo]): Unit = 
	println(s"Found ${ls.length} git repositories" + "\n")
	println(s"There were ${RepoScanner.num_no_access.toString()} AccessDeniedExceptions generated" + "\n")
	println("The following repositories were found:" + "\n")
	ls.foreach(repo => printRepo(repo));
	