import com.psd_helper.backend.Conversions
import dev.federicocapece.drawzone.Group
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import kotlin.random.Random

class ConversionsTest {
    private lateinit var canvas: Group

    @BeforeEach
    fun configureSystemUnderTest() {
        canvas = Group()
    }

    @TestFactory
    fun createTests(): List<DynamicTest> {
        val random = Random(java.time.Clock.systemDefaultZone().millis())

        val bases = Conversions.Base.values()
        val conversionCombos = bases.map { b1 ->
            bases.map { b2->
                Pair(b1,b2)
            }
        }.flatten().filter { it.first != it.second }.filter { it.first != Conversions.Base.CA2 && it.second != Conversions.Base.CA2 }

        return conversionCombos.map {
            DynamicTest.dynamicTest("Base ${it.first} to Base ${it.second} conversion test"){
                //picking random number and converting it to base first
                val inputNumber = random.nextInt(0,Int.MAX_VALUE)
                val inputForProgram = inputNumber.toString(it.first.toInt())


                println("Converting: $inputForProgram (${it.first}) to base ${it.second}")

                val outputByProgram = Conversions.convert(inputForProgram, it.first, it.second, canvas)
                val outputCorrect = inputNumber.toString(it.second.toInt())

                Assertions.assertEquals(outputCorrect.toUpperCase(), outputByProgram.toUpperCase())
            }
        }

    }

}