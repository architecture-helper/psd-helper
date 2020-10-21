package dev.federicocapece.drawzone.drawables

import dev.federicocapece.drawzone.DrawZone
import dev.federicocapece.drawzone.Drawable
import javafx.geometry.VPos
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment

class Text(
        var text: String,
        override var x: Double = 0.0,
        override var y: Double = 0.0,
        var font: Font = DrawZone.MONOSPACED,
        var width: Double = 0.0,
        var color: Paint = DrawZone.BLACK
): Drawable {

    override fun draw(g: GraphicsContext) {
        g.fill = color
        g.textBaseline = VPos.TOP
        g.textAlign = TextAlignment.LEFT
        g.font = font
        if(width>0)
            g.fillText(text, x, y, width)
        else
            g.fillText(text, x, y)
    }

}