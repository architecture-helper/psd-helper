package com.psd_helper.components

import com.psd_helper.backend.Conversions
import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import tornadofx.*

class BasedNumberInput(startBase:Conversions.Base = Conversions.Base.B10, startValue: String = "0", validate: Boolean = true) : Pane(){
    //region public properties
    var base: Conversions.Base
        get() = fieldBase.selectedItem!!
        set(value) = fieldBase.selectionModel.select(value)

    var value: String
        get() = fieldValue.text
        set(value) {fieldValue.text = value}


    var ca2Bits: Int
        get() = fieldCa2Bits.text.toInt()
        set(value) {fieldCa2Bits.text = value.toString()}


    //endregion

    //region private fields
    private lateinit var fieldBase : ComboBox<Conversions.Base>
    private lateinit var fieldValue: BaseTextField
    private lateinit var fieldCa2Bits: BaseTextField

    private var showOnCa2 = arrayListOf<Node>()
    //endregion

    //UI DEFINITION
    private val root by lazy {
        gridpane {
            row {
                label("value:")
                label("base:")
                showOnCa2.add(label("Ca2 bits(0 = count them):"))
            }
            row {
                fieldValue = baseTextField(startBase, startValue, validate)
                fieldBase = combobox<Conversions.Base>(values = Conversions.Base.values().toList()) {
                    selectionModel.selectedItemProperty().onChange(updateBase)
                }
                fieldBase.selectionModel.select(startBase)
                fieldCa2Bits = baseTextField(startBase, "0")
                showOnCa2.add(fieldCa2Bits)
            }
        }
    }

    //events
    private val updateBase : (Conversions.Base?) -> Unit = { newBase ->
        fieldValue.base = newBase!!
        val isCa2 = newBase == Conversions.Base.CA2
        showOnCa2.forEach {node->
            node.isVisible = isCa2
        }
    }


    init {
        add(root)
        updateBase(base)
    }
}


fun EventTarget.basedNumberInput(
        base: Conversions.Base,
        value: String = "0",
        validate:Boolean = true,
        op: BasedNumberInput.() -> Unit = {}
)= BasedNumberInput(base, value, validate).attachTo(this, op)