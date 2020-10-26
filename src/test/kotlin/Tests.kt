import dev.federicocapece.drawzone.Group
import java.time.Clock
import kotlin.random.Random

object Tests {
    const val NTIMES = 1000;
    val random = Random(Clock.systemDefaultZone().millis())
    var canvas: Group = Group()
}