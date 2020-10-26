import com.psd_helper.drawable.Division
import dev.federicocapece.drawzone.drawables.Text
import org.junit.jupiter.api.*

class MathTests {
    @BeforeEach
    fun configureSystemUnderTest() {
        Tests.canvas.clear()
    }


    @RepeatedTest(Tests.NTIMES)
    fun division(){
        val (number, dividend) = arrayOf(Tests.random.nextInt(0,Int.MAX_VALUE), Tests.random.nextInt(0,Int.MAX_VALUE)).sortedDescending()

        println("Dividing: $number/$dividend")

        val division = Division(Text(number.toString()),dividend, Tests.canvas)

        Assertions.assertEquals(number%dividend, division.remainderText.text.trim().toInt())
        Assertions.assertEquals(number/dividend, division.resultText.text.trim().toInt())

    }

    //TODO: write a unit test for the binary sum

}