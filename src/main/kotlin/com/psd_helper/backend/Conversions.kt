package com.psd_helper.backend

import com.psd_helper.Constants
import dev.federicocapece.drawzone.Group
import dev.federicocapece.drawzone.drawables.Text
import kotlin.math.pow

class Conversions {
    companion object{
        @JvmStatic
        fun convert(inputNum:String, inputBase: Int, outputBase:Int, canvas: Group, preferredWidth: Double = Double.MAX_VALUE) : String{
            /*quick convert to check results later : TODO: TURN THIS INTO A UNIT TEST!!!
            try{
                outputTextField.style = "-fx-text-fill: black;";
                outputTextField.text = inputTextField.text.toInt(inputBase).toString(outputBase).toUpperCase()
            }catch (ex:Exception){
                outputTextField.style = "-fx-text-fill: red;";
                outputTextField.text = "error"
            }
            */

            //clearing previous elaborations
            canvas.clear()

            //if the base is the same there's nothing to convert
            if(inputBase == outputBase)
                return inputNum

            if(inputBase != 10){
                if(outputBase == 10){
                    return sumsOfPositionalNumbers(inputNum, inputBase, outputBase, canvas, preferredWidth)
                }else{
                    //base 2x to 2x
                    return quick2To2Conversion(inputNum, inputBase, outputBase, canvas, preferredWidth)
                }
            }else{
                //base 10 to 2x
                return consecutiveDivisions(inputNum, outputBase, canvas, preferredWidth)
            }
        }

        private fun consecutiveDivisions(inputNum: String, outputBase: Int, canvas: Group, preferredWidth: Double): String {
            var lastDivision = canvas
            var toDivide:Int = inputNum.toInt()
            //write number
            var toDivideDrawable = Text(toDivide.toString())
            lastDivision.add(toDivideDrawable)

            var remainders = arrayListOf<Int>()
            while(toDivide != 0){
                remainders.add(toDivide % outputBase)
                toDivide /= outputBase

                lastDivision = division(lastDivision.last() as Text, outputBase, lastDivision)

            }


            return remainders.map { it.toString(outputBase) }.reversed().joinToString("")
        }

        private fun division(toDivideText: Text, dividend:Int, canvas : Group) : Group{
            //creating an output division group
            val output = canvas.group(toDivideText.x, toDivideText.y)
            canvas.add(output)

            //reassigning the text to the new division group
            canvas.remove(toDivideText)
            output.add(toDivideText)
            toDivideText.y = .0; toDivideText.x = .0

            //adding the dividend
            val dividendText = Text(dividend.toString(), toDivideText.right + 10)
            output.add(dividendText)

            //starting the effective division procedure
            var boughtDownNumbers = 0
            var remainder = 0
            var resultString = ""
            while(remainder >= dividend || boughtDownNumbers < toDivideText.text.length){
                //reverse multiply and subtract if the remainder is bigger than the dividend
                if(remainder >= dividend){
                    val multiple = (remainder / dividend)
                    val toSubtract = multiple * dividend
                    remainder -= toSubtract
                    resultString += multiple

                    val toSubtractString =
                            if(output.count()!=2)
                                spaceFill(toSubtract.toString(), (output.last() as Text).text.length)
                            else
                                spaceFill(toSubtract.toString(), remainder.toString().length)
                    val remainderString = spaceFill(remainder.toString(), (output.last() as Text).text.length)

                    output.add(Text(toSubtractString, y = (output.last() as Text).bottom))
                    output.add(Text(remainderString, y = (output.last() as Text).bottom))
                }

                //bought down a number to the remainder if you need to
                if(remainder<dividend && boughtDownNumbers < toDivideText.text.length){
                    val bringDown = toDivideText.text[boughtDownNumbers]
                    remainder = (remainder.toString() + bringDown).toInt()
                    if(output.count()!=2) //2 = input and base
                        (output.last() as Text).text += bringDown
                    boughtDownNumbers++
                }
            }

            if(resultString.isEmpty())
                resultString = "0"

            //adding the result
            output.add(Text(resultString, x = dividendText.x, y = dividendText.bottom))

            return output
        }

        private fun spaceFill(string: String, length: Int): String = charFill(string,length, ' ')

        private fun charFill(string: String, length: Int, char: Char): String {
            var toFill = length - string.length
            toFill = if(toFill<0) 0 else toFill

            return char.toString().repeat(toFill) + string
        }

        private fun quick2To2Conversion(inputNum: String, inputBase: Int, outputBase: Int, canvas: Group, preferredWidth: Double): String {
            TODO("Not yet implemented")
        }

        private fun sumsOfPositionalNumbers(inputNum: String, inputBase: Int, outputBase: Int, canvas: Group, preferredWidth: Double): String {
            //building number(base) =
            var output = "$inputNum($inputBase) = "

            //building character*base^position + ...
            var index = inputNum.length;
            val sums = inputNum.map { number ->
                index--
                number + " ⋅ " + inputBase + index.toString().map { Constants.SUPERSCRIPT[it.toString().toInt()]}.joinToString()
            }
            output += sums.joinToString(" + ") + " = "

            if (inputBase == 16){
                // A*16^2 -> 10*16^2
                sums.map { it[0].toString().toInt(inputBase).toString() + it.substring(1) }
                output += sums.joinToString(" + ") + " = "
            }

            //creating number sum
            index = inputNum.length;
            val numbers = inputNum.map {number ->
                index--
                number.toString().toInt(inputBase) * (inputBase.toDouble().pow(index).toInt() )
            }
            output += numbers.joinToString(" + ") + " = "

            //calculating the actual result
            val returnValue = numbers.sum().toString()
            output += returnValue

            //showing it in the output
            canvas.add(Text(output))

            return returnValue
        }

    }
}