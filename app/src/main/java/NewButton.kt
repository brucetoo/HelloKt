import android.content.Context
import android.view.View

/**
 * Created by Bruce Too
 * On 27/07/2017.
 * At 15:36
 */
class NewButton : View {
    constructor(ctx: Context): super(ctx) { }
}

class DelegatingCollection<T>(
        val innerList: Collection<T> = ArrayList<T>()
) : Collection<T> by innerList {
    //用innerList代理实现 Arraylist逻辑 加上自己代码
    override fun contains(element: T): Boolean {
        //在此加上自己代码
        return innerList.contains(element)
    }
}

class DelegatingCollection1<T> : Collection<T> {
    private val innerList = arrayListOf<T>()
    override val size: Int get() = innerList.size
    override fun isEmpty(): Boolean = innerList.isEmpty()
    override fun contains(element: T): Boolean = innerList.contains(element)
    override fun iterator(): Iterator<T> = innerList.iterator()
    override fun containsAll(elements: Collection<T>): Boolean = innerList.containsAll(elements)
}