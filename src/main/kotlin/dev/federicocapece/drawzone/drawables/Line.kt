package dev.federicocapece.drawzone.drawables

import dev.federicocapece.drawzone.DrawZone
import dev.federicocapece.drawzone.Drawable
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import kotlin.math.absoluteValue

class Line(x1: Double, y1: Double, var x2: Double, var y2: Double, var color: Paint = Color.BLACK) : Drawable {

    override var x = x1
    override var y = y1

    override var width: Double
        get() = (x - x2).absoluteValue
        set(value) = TODO()
    override var height: Double
        get() = (y - y2).absoluteValue
        set(value) = TODO()

    override fun draw(g: GraphicsContext) {
        g.stroke = color
        g.lineWidth = 1.0
        g.strokeLine(x,y,x2,y2)
    }

    companion object{
        fun vertical(x: Double, y1: Double, y2: Double, color: Paint = Color.BLACK) : Line = Line(x,y1,x,y2, color)
        fun horizontal(y: Double, x1: Double, x2: Double, color: Paint = Color.BLACK) : Line = Line(x1,y,x2,y, color)
    }
}