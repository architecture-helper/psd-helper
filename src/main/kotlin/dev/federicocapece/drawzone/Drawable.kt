package dev.federicocapece.drawzone

import javafx.scene.canvas.GraphicsContext

interface Drawable {
    var x : Double
    var y : Double

    fun draw(g:GraphicsContext)
}