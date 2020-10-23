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
class ConversionTab : Tab("Base Conversion"){
    private val canvas = DrawZone()

    init {
        val padding = 10.0

        var inputBaseComboBox : ComboBox<Conversions.Base>? = null
        var outputBaseComboBox : ComboBox<Conversions.Base>? = null
        var inputTextField :TextField? = null
        var outputTextField: TextField? = null
        var inputCa2 :TextField? = null
        var outputCa2 :TextField? = null

        content = borderpane {
            paddingAll = padding
            top = vbox {
                paddingAll = padding
                gridpane {
                    hgap = padding
                    vgap = hgap
                    row {
                        label("input")
                        inputTextField = textfield("10")
                        inputCa2 = textfield("4")

                        inputBaseComboBox = combobox<Conversions.Base>(values = Conversions.Base.values().toList()){
                            selectionModel.selectFirst()
                        }
                    }
                    row{
                        label("output")
                        outputTextField = textfield("out") {isEditable = false}
                        outputCa2 = textfield("4")
                        outputBaseComboBox = combobox<Conversions.Base>(values = Conversions.Base.values().toList()){
                            selectionModel.selectLast()
                            selectionModel.selectPrevious()
                        }
                    }
                }
                gridpane {
                    hgap = padding
                    vgap = hgap
                    row{
                        button("work") { setOnAction {
                            //clearing previous elaborations
                            canvas.items.clear()
                            outputTextField!!.text =
                                Conversions.convert(
                                        inputNum = inputTextField!!.text,
                                        inputBase = inputBaseComboBox!!.selectedItem!!,
                                        outputBase =  outputBaseComboBox!!.selectedItem!!,
                                        canvas =  canvas.items,
                                        inputCa2 = inputCa2!!.text.toIntOrNull() ?: 0,
                                        outputCa2 = outputCa2!!.text.toIntOrNull() ?: 0,
                                )
                            canvas.refresh()
                        } }
                    }
                }
            }


            center = canvas
        }
    }


}