package com.psd_helper.tabs

import com.psd_helper.*
import com.psd_helper.backend.Conversions
import dev.federicocapece.drawzone.DrawZone
import dev.federicocapece.drawzone.Group
import dev.federicocapece.drawzone.drawables.*
import javafx.beans.value.ObservableValue
import javafx.scene.control.ComboBox
import javafx.scene.control.Tab
import javafx.scene.control.TextField
import tornadofx.*
import java.lang.Exception
import kotlin.math.pow

@Index(0)
class ConversionTab : Tab("Conversion"){
    private val canvas = DrawZone()

    init {
        val padding = 10.0

        var inputBaseComboBox : ComboBox<Int>? = null
        var outputBaseComboBox : ComboBox<Int>? = null
        var inputTextField :TextField? = null
        var outputTextField: TextField? = null

        content = borderpane {
            paddingAll = padding
            top = gridpane {
                hgap = padding
                vgap = hgap
                row {
                    label("input")
                    inputTextField = textfield("10")
                    inputBaseComboBox = combobox<Int>(values = Constants.NUMERIC_BASES){
                        selectionModel.selectFirst()
                    }
                }
                row{
                    label("output")
                    outputTextField = textfield("out") {
                        isEditable = false
                    }
                    outputBaseComboBox = combobox<Int>(values = Constants.NUMERIC_BASES){
                        selectionModel.selectLast()
                        selectionModel.selectPrevious()
                    }
                }
            }
            center = canvas
            bottom = button("work") { setOnAction { outputTextField!!.text =
                    Conversions.convert(
                            inputNum = inputTextField!!.text,
                            inputBase = inputBaseComboBox!!.selectedItem!!,
                            outputBase =  outputBaseComboBox!!.selectedItem!!,
                            canvas =  canvas.items
                    )
            } }
        }

    }


}