package com.psd_helper.backend

import com.psd_helper.Constants
import com.psd_helper.drawable.Division
import com.psd_helper.spaceFill
import dev.federicocapece.drawzone.Drawable
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
            //write number
            var toDivide:Int = inputNum.toInt()
            val firstDivisionText = Text(toDivide.toString())
            canvas.add(firstDivisionText)

            var lastDivision: Division? = null
            val remainders = arrayListOf<Int>()
            while(toDivide != 0){
                //effective calculations for the return result
                remainders.add(toDivide % outputBase)
                toDivide /= outputBase

                val divisionStartingNumber: Text = lastDivision?.resultText ?: firstDivisionText
                lastDivision = Division(divisionStartingNumber, outputBase, lastDivision ?: canvas)
            }


            return remainders.map { it.toString(outputBase) }.reversed().joinToString("").toUpperCase()
        }

        private fun quick2To2Conversion(inputNum: String, inputBase: Int, outputBase: Int, canvas: Group, preferredWidth: Double): String {
            TODO("Not yet implemented")
        }

        private fun sumsOfPositionalNumbers(inputNum: String, inputBase: Int, outputBase: Int, canvas: Group, preferredWidth: Double): String {
            //building number(base) =
            var output = "$inputNum($inputBase) = "

            val endLine = " =\n" + " = ".spaceFill(output.length)

            //building character*base^position + ...
            var index = inputNum.length;
            val sums = inputNum.map { number ->
                index--
                if(number == '0')
                    ""
                else
                    number + " ⋅ " + inputBase + index.toString().map { Constants.SUPERSCRIPT[it.toString().toInt()]}.joinToString("")
            }.filter { it != "" }
            output += sums.joinToString(" + ") + endLine

            if (inputBase == 16){
                // A*16^2 -> 10*16^2
                output += sums.map { it[0].toString().toInt(inputBase).toString() + it.substring(1) }
                        .joinToString(" + ") + endLine
            }

            //creating number sum
            index = inputNum.length;
            val numbers = inputNum.map {number ->
                index--
                if(number == '0')
                    0
                else
                    number.toString().toInt(inputBase) * (inputBase.toDouble().pow(index).toInt())
            }.filter { it != 0 }
            output += numbers.joinToString(" + ") + endLine

            //calculating the actual result
            val returnValue = numbers.sum().toString()
            output += returnValue

            //adding the returns

            //showing it in the output
            canvas.add(Text(output))

            return returnValue
        }

    }
}