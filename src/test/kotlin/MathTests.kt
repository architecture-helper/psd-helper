import com.psd_helper.backend.Conversions
import com.psd_helper.drawable.Division
import dev.federicocapece.drawzone.drawables.Text
import org.junit.jupiter.api.*

class MathTests {
    @BeforeEach
    fun configureSystemUnderTest() {
        Tests.canvas.clear()
    }


    @RepeatedTest(Tests.NTIMES, name = RepeatedTest.LONG_DISPLAY_NAME)
    fun division(){
        val (number, dividend) = arrayOf(Tests.random.nextInt(0,Int.MAX_VALUE), Tests.random.nextInt(0,Int.MAX_VALUE)).sortedDescending()

        println("Dividing: $number/$dividend")

        val division = Division(Text(number.toString()),dividend, Tests.canvas)

        Assertions.assertEquals(number%dividend, division.remainderText.text.trim().toInt())
        Assertions.assertEquals(number/dividend, division.resultText.text.trim().toInt())

    }

    @TestFactory
    fun sum(): List<DynamicTest> {
        val bases = Conversions.Base.values().filter { it != Conversions.Base.CA2 }

        val output = arrayListOf<DynamicTest>()

        val times = Tests.NTIMES/bases.count()
        bases.map {base-> repeat(times){ index ->
            output.add(DynamicTest.dynamicTest("Sum base $base: repetition $index of $times"){
                //picking random number and converting it to base first
                val inputNumber1 = Tests.random.nextInt(0,Int.MAX_VALUE/2)
                val inputNumber2 = Tests.random.nextInt(0,Int.MAX_VALUE/2)

                val inputForProgram1 = inputNumber1.toString(base.toInt())
                val inputForProgram2 = inputNumber2.toString(base.toInt())


                println("Sum($base): $inputForProgram1 + $inputForProgram2")

                val outputByProgram = PositionalSum(inputForProgram1, inputForProgram2, base).result.text.trim()
                val outputCorrect = (inputNumber1 + inputNumber2).toString(base.toInt())

                Assertions.assertEquals(outputCorrect.toUpperCase(), outputByProgram.toUpperCase())
            })

        } }
        return output
    }

    fun ca2Sum(){
        //TODO: write unit test for CA2 PositionalSum
    }

}