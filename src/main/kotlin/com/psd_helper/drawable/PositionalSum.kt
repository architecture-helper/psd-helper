import com.psd_helper.Constants.DIGITS
import com.psd_helper.backend.Conversions
import com.psd_helper.spaceFill
import dev.federicocapece.drawzone.Group
import dev.federicocapece.drawzone.drawables.Line
import dev.federicocapece.drawzone.drawables.Text
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import kotlin.NumberFormatException
import kotlin.math.max

class PositionalSum(
        num1Value:String,
        num2Value:String,
        var base: Conversions.Base = Conversions.Base.B2,
        x:Double = .0,
        y:Double = .0) : Group(x, y) {

    val num1: Text
    val num2: Text
    val reports: Text
    val result: Text

    private fun validateNumber(number: String) : String {
        //if there are digits not in the range [0-base[
        if(number.toUpperCase().any { DIGITS.indexOf(it) !in 0 until base.toInt() }){
            throw NumberFormatException("Number is not in base $base")
        }
        return number
    }

    init {
        if(base == Conversions.Base.CA2 && num1Value.length != num2Value.length)
            throw NumberFormatException("Two CA2 numbers must have the same number of bits, zero-fill them or ca2-fill them according to what you want")

        val maxLen = max(num1Value.length, num2Value.length)

        //region Creating and placing components
        reports = Text(color = Color.SLATEGRAY)
        add(reports)

        num1 = Text(validateNumber(num1Value).spaceFill(maxLen + 1), y = reports.bottom).also {add(it)}
        num2 = Text(validateNumber(num2Value).spaceFill(maxLen + 1), y = num1.bottom).also {add(it)}
        Line.horizontal(num2.bottom + 2, num2.x, num2.right).also {add(it)}
        result = Text("", y= bottom + 2).also {add(it)}

        //endregion

        //executing the calculations
        val reportsTemp = " ".repeat(maxLen+1).toMutableList()
        val resultTemp = " ".repeat(maxLen+1).toMutableList()
        for (i in maxLen downTo 0){
            val sum = (charToBasedInt(num1.text[i]) +
                    charToBasedInt(num2.text[i]) +
                    charToBasedInt(reportsTemp[i])
                    ).toString(base.toInt())

            resultTemp[i] = sum.last()
            if(i != 0)
                reportsTemp[i-1] = if(sum.length == 1) ' ' else sum.first()
        }
        reports.text = reportsTemp.joinToString("")
        result.text = with(resultTemp.joinToString("").trimStart('0')){if(this.isNotEmpty()) this else "0"}

    }

    private fun charToBasedInt(char:Char): Int = if(char == ' ') 0 else char.toString().toInt(base.toInt())

}