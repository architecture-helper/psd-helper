package com.psd_helper.tabs

import com.psd_helper.backend.Conversions
import com.psd_helper.components.BasedNumberInput
import com.psd_helper.components.basedNumberInput
import dev.federicocapece.drawzone.DrawZone
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.ComboBox
import javafx.scene.control.Tab
import javafx.scene.control.TextField
import tornadofx.*
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.declaredMembers

@Index(0)
class ConversionTab : Tab("Base Conversion"){
    private val canvas = DrawZone()


    lateinit var input : BasedNumberInput
    lateinit var output: BasedNumberInput

    init {
        val padding = 10.0

        content = borderpane {
            paddingAll = padding
            top = vbox {
                paddingAll = padding
                input = basedNumberInput(Conversions.Base.B10, "1025")
                output = basedNumberInput(Conversions.Base.B2, "0", false){
                    val output = this
                    with(BasedNumberInput::class.java.getDeclaredField("fieldValue")){
                        isAccessible = true
                        get(output) as TextField
                    }.isEditable = false
                }
                hbox {
                    button("work") {setOnAction {convert()}}
                    button("swap") {setOnAction {swap()}}
                }
                center = canvas
            }
        }
    }

    private fun convert(){
        canvas.items.clear()
        output.value = Conversions.convert(
                inputNum = input.value,
                inputBase = input.base,
                outputBase = output.base,
                canvas = canvas.items,
                inputCa2 = input.ca2Bits,
                outputCa2 = output.ca2Bits
        )
    }

    private fun swap(){
        //saving values before the validation after the swap ruins them
        var valueIn = input.value
        var valueOut = output.value

        //swapping bases
        input.base = output.base.also { output.base = input.base }

        //reassigning values
        input.value = valueOut
        output.value = valueIn
    }
}
