package reposcanner

import scala.collection.mutable.ArrayBuffer
import java.nio.file.Path
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes

object RepoScanner:
	var num_no_access: Integer = 0
	
	def findRepos(root: Path): List[GitRepo] =
		val repos = scala.collection.mutable.ArrayBuffer.empty[GitRepo]
		var sfv:SimpleFileVisitor[Path] = null
		sfv = new SimpleFileVisitor[Path]:
			override def preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult = 
				val gitPath = dir.resolve(".git")
				if(Files.exists(gitPath)) then repos +=(GitRepo(dir))
				FileVisitResult.CONTINUE
				
			override def visitFileFailed(
				file:Path,
				exc: java.io.IOException
			): FileVisitResult = 
				exc match
					case e: AccessDeniedException => 
						num_no_access += 1
					case _ => 
						()
						
				FileVisitResult.CONTINUE
			
    

		FileVisitResult.CONTINUE
		Files.walkFileTree(root, sfv)	
		repos.toList