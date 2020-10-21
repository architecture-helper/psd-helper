package com.psd_helper

import com.psd_helper.views.MainView
import javafx.application.Application
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*

fun main(){
    Application.launch(PSDHelper::class.java)
}

class PSDHelper : App(MainView::class)