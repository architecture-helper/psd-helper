package com.psd_helper.tabs

import javafx.scene.control.Tab
import tornadofx.*

@Index(1)
class SumTab : Tab("Sum"){

    init {
        content = button("SUM")
    }


}