package reposcanner
import java.nio.file.Path

case class GitRepo(
    path: Path,
    dirty: Boolean
)