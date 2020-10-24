package com.psd_helper.components

import com.psd_helper.Constants.DIGITS
import com.psd_helper.backend.Conversions
import javafx.application.Platform
import javafx.beans.value.ChangeListener
import javafx.event.EventTarget
import javafx.scene.control.TextField
import tornadofx.*

class BaseTextField(base: Conversions.Base, value: String = "0", var validate: Boolean = true) : TextField(){

    var base = base
        set(value) {
            field = value
            if(validate && !isValid(text)){
                text = "0"
                selectAll()
            }
        }

    private val listener: ChangeListener<String> = ChangeListener<String> { _, oldValue, newValue ->
        if(validate && !isValid(newValue)){
            Platform.runLater {
                if(isValid(oldValue)){
                    text = oldValue
                    positionCaret(text.length)
                }else{
                    text = "0"
                }
            }
        }
    }

    init {
        textProperty().addListener(listener)
        this.text = value
    }

    private fun isValid(newValue:String?): Boolean {
        val newValue = newValue?.toUpperCase() ?: return false

        newValue.forEach {
            if(DIGITS.indexOf(it) !in 0 until base.toInt())
                return false
        }
        return true
    }

}

fun EventTarget.baseTextField(
        base: Conversions.Base,
        value: String = "0",
        validate:Boolean = true,
        op: BaseTextField.() -> Unit = {}
) = BaseTextField(base, value, validate).attachTo(this, op)