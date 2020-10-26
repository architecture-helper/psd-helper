package com.psd_helper.drawable

import com.psd_helper.spaceFill
import dev.federicocapece.drawzone.Group
import dev.federicocapece.drawzone.drawables.Line
import dev.federicocapece.drawzone.drawables.Text
import javafx.scene.paint.Color

class Division(toDivideText: Text, dividend:Int, parent : Group, reassign : Boolean = true) :
        Group(toDivideText.x, toDivideText.y, toUpdate = parent.toUpdate) {

    private val VGAP = 3
    private val HGAP = 7

    val toDivideText        = toDivideText
    val dividendText        = Text(dividend.toString(), toDivideText.width + HGAP)

    val calculationsTexts   = arrayListOf<Text>()
    val resultText          = Text("", x = dividendText.x, y = dividendText.bottom + VGAP)

    val remainderText
        get() = if(calculationsTexts.isNotEmpty()) calculationsTexts.last() else toDivideText


    init {
        //region effective division procedure
        var boughtDownNumbers = 0
        var remainder = 0
        var resultString = ""
        while(remainder >= dividend || boughtDownNumbers < toDivideText.text.length){
            //reverse multiply and subtract if the remainder is bigger than the dividend
            if(remainder >= dividend){
                //calculating the string length for the next 2 items and their height
                var toFill = remainder.toString().length
                var yPos = dividendText.bottom
                if(calculationsTexts.isNotEmpty()){
                    toFill = calculationsTexts.last().text.length
                    yPos = calculationsTexts.last().bottom
                }

                //region effective calculations
                //calculating the closest lower multiple (to subtract)
                val multiple = (remainder / dividend)
                val toSubtract = multiple * dividend

                //subtracting the lower multiple to the remainder
                remainder -= toSubtract
                //adding the multiple number to the result
                resultString += multiple
                //endregion

                //space-filling the strings to move them to the right
                val toSubtractString = toSubtract.toString().spaceFill(toFill)
                val remainderString = remainder.toString().spaceFill(toFill)

                //creating the texts
                calculationsTexts.add(Text(toSubtractString, y = yPos))
                calculationsTexts.add(Text(remainderString, y = calculationsTexts.last().bottom + VGAP))
            }

            //bought down a number to the remainder if you need to
            if(remainder<dividend && boughtDownNumbers < toDivideText.text.length){
                //adding the number bought down to the remainder
                val bringDown = toDivideText.text[boughtDownNumbers]
                remainder = (remainder.toString() + bringDown).toInt()

                //if it isn't the first calculation add the bought down number to the subtraction result
                if(calculationsTexts.isNotEmpty())
                    calculationsTexts.last().text+= bringDown

                boughtDownNumbers++

                //if after this number is down i can't divide then i add a 0 to the result
                if(remainder<dividend && resultString.isNotEmpty())
                    resultString += "0"

            }
        }

        //extra step, if the

        //setting the result
        resultText.text = if(resultString.isNotEmpty()) resultString.trim() else "0"
        //endregion

        //region adding the created texts to the drawing group
        //toDivideText
        if(reassign){
            //reassigning the text from the parent to this new division
            parent.remove(this.toDivideText)
            this.add(toDivideText)
            toDivideText.y = .0; toDivideText.x = .0

            //if the user is reassigning i automatically assume he want this division to go into the parent
            parent.add(this)
        }

        //dividendText
        this.add(dividendText)

        //resultText
        this.add(resultText)

        //calculations
        this.addAll(calculationsTexts)
        //endregion

        //region adding the lines
        //common precalculation
        val divisionMiddle =  dividendText.x - (HGAP/2)


        //vertical line
        this.add(Line.vertical(divisionMiddle,
                dividendText.y,
                if(calculationsTexts.isNotEmpty()) calculationsTexts.last().bottom else resultText.bottom
        ))

        //horizontal division line
        this.add(Line.horizontal(resultText.y - (VGAP/2), divisionMiddle, resultText.right))

        //horizontal subtraction lines
        for (i in 0 until calculationsTexts.count() step 2){
            val text = calculationsTexts[i]

            //calculating wasted spaces
            val spaces = text.text.count { it == ' ' }

            //calculating used width percentage (by removing wasted spaces)
            val wastedWidthPercentage = 1.0 - ((text.text.length - spaces).toDouble() / text.text.length)

            //creating the line
            this.add(Line.horizontal(
                    text.bottom + (VGAP/2),
                    text.x + (text.width * wastedWidthPercentage) + (HGAP/2),
                    divisionMiddle,
            ))
            calculationsTexts[i]
        }

        //region red line under the remainder
        //calculating wasted spaces
        val spaces = remainderText.text.count { it == ' ' }

        //calculating used width percentage (by removing wasted spaces)
        val wastedWidthPercentage = 1.0 - ((remainderText.text.length - spaces).toDouble() / remainderText.text.length)

        //creating the line
        this.add(Line.horizontal(
                remainderText.bottom + (VGAP/2),
                remainderText.x + (remainderText.width * wastedWidthPercentage) + (HGAP/2),
                remainderText.right,
                Color.RED
        ))
        //endregion

        //endregion
    }
}
