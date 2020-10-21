package com.psd_helper.backend

import com.psd_helper.Constants
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
                return consecutiveDivisions(inputNum, inputBase, outputBase, canvas, preferredWidth)
            }
        }

        private fun consecutiveDivisions(inputNum: String, inputBase: Int, outputBase: Int, canvas: Group, preferredWidth: Double): String {
            TODO("Not yet implemented")
            var toDivide:Int = inputNum.toInt()
            //write number
            var numberDrawable = Text(inputNum)
            canvas.add(numberDrawable)

            var divisionResult = -1
            while(divisionResult != 0){

            }
            //write division
        }

        private fun division(text: Text, dividend:Int, canvas : Group) : Text{
            TODO("Not yet implemented")
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