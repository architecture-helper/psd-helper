package dev.federicocapece.drawzone.drawables

import dev.federicocapece.drawzone.DrawZone
import dev.federicocapece.drawzone.Drawable
import javafx.geometry.VPos
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment

class Text(
        var text: String,
        override var x: Double = 0.0,
        override var y: Double = 0.0,
        var font: Font = DrawZone.MONOSPACED,
        width: Double = Double.MAX_VALUE,
        var color: Paint = DrawZone.BLACK
): Drawable {

    override var width = Double.MAX_VALUE
        set(value) {
            field = value
        }
        get(){
            if(field == Double.MAX_VALUE)
                return with(javafx.scene.text.Text(text)){
                    font = this@Text.font
                    getLayoutBounds().getWidth();
                }
            else return field
        }

    override var height: Double = 0.0
        get() = with(javafx.scene.text.Text(text)) {
            font = this@Text.font
            getLayoutBounds().height;
        }

    override fun draw(g: GraphicsContext) {
        g.fill = color
        g.textBaseline = VPos.TOP
        g.textAlign = TextAlignment.LEFT
        g.font = font
        g.fillText(text, x, y, width)
    }

}