package dev.federicocapece.drawzone

import javafx.collections.ListChangeListener
import javafx.collections.ModifiableObservableListBase
import javafx.scene.canvas.GraphicsContext
import kotlin.math.absoluteValue

open class Group(override var x:Double = 0.0, override var y:Double = 0.0, var parent: DrawZone? = null) : ModifiableObservableListBase<Drawable>(), Drawable {

    init {
        //setting the edit of a single element in the whole group to refresh the parent
        addListener{ _: ListChangeListener.Change<out Drawable>? ->
            parent?.refresh();
        }
    }

    fun group(x:Double = 0.0, y:Double = 0.0, parent: DrawZone? = this.parent): Group {
        val subGroup = Group(x,y,parent)
        add(subGroup)
        return subGroup
    }

    override var width: Double
        get(){
            val rightestPoint = map {it.x + it.width}.max() ?: .0
            val leftestPoint = map { it.x }.min() ?: .0

            return (rightestPoint-leftestPoint).absoluteValue
        }
        set(value) {}

    override var height: Double
        get(){
            val lowestPoint = map {it.y + it.height}.max() ?: .0
            val highestPoint = map { it.y }.min() ?: .0

            return (lowestPoint-highestPoint).absoluteValue
        }
        set(value) {}

    //the draw of a group simply calls the draw of each drawable that it has inside
    override fun draw(g: GraphicsContext) {
        forEach {
            g.translate(x,y)
            it.draw(g)
            g.translate(-x,-y)
        }
    }

    //region Array part
    private val items = arrayListOf<Drawable?>()

    override fun get(index: Int): Drawable? =
            items[index]

    override fun doAdd(index: Int, element: Drawable){
        items.add(index, element)
        (element as? Group)?.parent = this.parent
    }

    override fun doSet(index: Int, element: Drawable?): Drawable? =
            items.set(index, element)

    override fun doRemove(index: Int): Drawable? =
            items.removeAt(index)

    override val size: Int
        get() = items.size
    //endregion

}