package utils

/**
 * Created by Bruce Too
 * On 26/07/2017.
 * At 21:37
 */

//定义一个全局方法，类似java中的静态方法
fun joinToString(str: String): String {
    return "I'm joinToString method"
}

//类功能的扩展实例，放在top-level的包下 能全局引用
fun String.lastChar(): Char =  get(length - 1);

//类属性的扩展 Extension Properties
var StringBuilder.lastChar: Char
get() = get(length - 1)
set(value) {setCharAt(length - 1,value)}

fun salute() = println("Salute!")