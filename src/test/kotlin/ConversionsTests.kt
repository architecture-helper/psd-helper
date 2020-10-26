import com.psd_helper.backend.Conversions
import org.junit.jupiter.api.*

class ConversionsTests {

    @BeforeEach
    fun configureSystemUnderTest() {
        Tests.canvas.clear()
    }

    @TestFactory
    fun convert(): List<DynamicTest> {
        val bases = Conversions.Base.values()
        val conversionCombos = bases.map { b1 ->
            bases.map { b2->
                Pair(b1,b2)
            }
        }.flatten().filter { it.first != it.second }.filter { it.first != Conversions.Base.CA2 && it.second != Conversions.Base.CA2 }

        val output = arrayListOf<DynamicTest>()

        val times = Tests.NTIMES / conversionCombos.count()
        conversionCombos.map {bases -> repeat(times) { index->
            output.add(DynamicTest.dynamicTest("Conversion ${bases.first} to ${bases.second}: repetition $index of $times") {
                //picking random number and converting it to base first
                val inputNumber = Tests.random.nextInt(0, Int.MAX_VALUE)
                val inputForProgram = inputNumber.toString(bases.first.toInt())


                println("Converting: $inputForProgram (${bases.first}) to base ${bases.second}")

                val outputByProgram = Conversions.convert(inputForProgram, bases.first, bases.second, Tests.canvas)
                val outputCorrect = inputNumber.toString(bases.second.toInt())

                Assertions.assertEquals(outputCorrect.toUpperCase(), outputByProgram.toUpperCase())

            })

        } }

        return output
    }
}