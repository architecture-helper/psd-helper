package dev.federicocapece.drawzone.drawables

import dev.federicocapece.drawzone.DrawZone
import dev.federicocapece.drawzone.Drawable
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Paint

class Rect(
        override var x: Double,
        override var y: Double,
        var width: Double,
        var height: Double,
        var color: Paint = DrawZone.BLACK
): Drawable {

    override fun draw(g: GraphicsContext) {
        g.fill = color
        g.fillRect(x, y, width, height)
    }

}