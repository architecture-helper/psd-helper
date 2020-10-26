package dev.federicocapece.drawzone

import javafx.scene.canvas.Canvas
import javafx.scene.layout.Pane
import javafx.scene.text.Font
import tornadofx.*


class DrawZone(width: Double = 500.0, height: Double = 500.0) : Pane() {
    private val canvas: Canvas = Canvas()
    val items = Group(toUpdate = this)

    init {
        //creating the canvas and linking it to the pane
        this.add(canvas)
        canvas.widthProperty().bind(widthProperty())
        canvas.heightProperty().bind(heightProperty())

        //hooking the redraw to the size
        canvas.widthProperty().onChange { refresh()}
        canvas.heightProperty().onChange { refresh()}

        //setting the size of the draw pane
        setHeight(height)
        setWidth(width)
    }

    fun refresh() {
        //clear
        canvas.graphicsContext2D.clearRect(0.0,0.0,canvas.width,canvas.height)

        //redraw
        items.draw(canvas.graphicsContext2D)
    }

    companion object{
        val MONOSPACED: Font = Font.font("monospace", 16.0)
    }
}