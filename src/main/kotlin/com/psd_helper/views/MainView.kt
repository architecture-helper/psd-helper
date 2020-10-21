package com.psd_helper.views

import com.psd_helper.tabs.ConversionTab
import com.psd_helper.tabs.Index
import javafx.scene.Parent
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import org.reflections.Reflections
import tornadofx.*

class MainView : View() {
    override val root: Parent = tabpane{
        tabs.addAll(Reflections("com.psd_helper.tabs").getSubTypesOf(Tab::class.java)
                .sortedBy {
                    //getting the annotation types and finding Index
                    val indexAnnotations = it.declaredAnnotations.filter {ann -> ann.annotationClass == Index::class }
                    var sortOrder = Int.MAX_VALUE
                    if(indexAnnotations.count()>0){
                        sortOrder = (indexAnnotations[0] as Index).value
                    }
                    return@sortedBy sortOrder
                }
                .map{ it.constructors[0].newInstance() as Tab }
        )
    }
}

