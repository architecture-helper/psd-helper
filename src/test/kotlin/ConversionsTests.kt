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

        var output = arrayListOf<DynamicTest>()

        repeat(Tests.NTIMES/conversionCombos.count()){
            output.addAll(
                    conversionCombos.map {
                        DynamicTest.dynamicTest("Base ${it.first} to Base ${it.second} conversion test"){
                            //picking random number and converting it to base first
                            val inputNumber = Tests.random.nextInt(0,Int.MAX_VALUE)
                            val inputForProgram = inputNumber.toString(it.first.toInt())


                            println("Converting: $inputForProgram (${it.first}) to base ${it.second}")

                            val outputByProgram = Conversions.convert(inputForProgram, it.first, it.second, Tests.canvas)
                            val outputCorrect = inputNumber.toString(it.second.toInt())

                            Assertions.assertEquals(outputCorrect.toUpperCase(), outputByProgram.toUpperCase())
                        }
                    }
            )
        }

        return output
    }
}