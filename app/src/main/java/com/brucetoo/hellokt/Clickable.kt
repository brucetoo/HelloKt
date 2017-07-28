package com.brucetoo.hellokt

import java.io.File

/**
 * Created by Bruce Too
 * On 27/07/2017.
 * At 14:18
 */
interface Clickable {
    //接口定义方法时  abstract 默认被省略
    fun click()

    //接口可以有默认的实现，而java中不可~ 有默认实现的子类可以不继承
    fun showOff() = print("i'm clickable")
}

interface Focusable {
    fun showOff() = println("I'm focusable!")
}

interface Reachable {
    fun reach()
}

//类实现方法
class Button: Clickable, Focusable {

    //kotlin中使用override是强制性的(mandatory)
    override fun click() = print("You clicked me~~~~")

    //如果多个接口同时有相同的方法需要继承，则需要显式的在子类继承
    override fun showOff() {
        //显式继承 Clickable的showOff方法
        super<Clickable>.showOff()
    }

    //可以有自己名字或者没有 调用方法 A.Name.bar()/A.bar()都一样
    companion object Name: Reachable{
        override fun reach() {

        }

        fun bar() {
            println("Companion object called")
        }
    }

}

fun getReach(reachable: Reachable){

}

object CaseComparator : Comparator<File>{
    override fun compare(o1: File?, o2: File?): Int {
        if (o1 != null && o2 != null) {
            return o1.path.compareTo(o2.path,true)
        }
        return 0
    }
}




fun main(args: Array<String>) {
    val files = arrayOf(File("/user"), File("/User"))
    println(files.sortWith(CaseComparator))
    getReach(Button)
}
