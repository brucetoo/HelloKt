package com.brucetoo.hellokt

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = findViewById(R.id.message) as TextView
        view.setText("fuck")
        view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        println("This is max one ${max(1, 3)}")

        val date = Date()
        date.isTuesDay()

        //对一个实例调用多个方法
        with(this) {
            testLoop()
            testUnit(1, 1)
        }

        val l = mutableListOf("1", "2", "3")
        l.swap(0, 2)

        //函数作为另一个函数的参数传递
        val numbers = listOf(1, 2, 3)
        numbers.maxBy { it }
        numbers.joinToString(separator = " ", transform = { it.toString() })
        numbers.joinToString(" ") { it.toString() }
//        println(numbers.filter(::isOdd))

        testFunctionalApi()
        testFlatMap()
    }

    //String不是null就调用isNullOrBlack，否则不调用
    fun String?.isNullOrBlack(): Boolean {
        return this == null || this.isBlank()
    }

    fun verifyUserInput(input: String?) {
        if (input.isNullOrBlank()) {
            println("Please fill in the required fields")
        }
    }

    fun printAllCaps(s: String?) {
        val result: String? = s?.toUpperCase()
        //才分成两组:
        //1.result: String? 表示可以返回null String
        //2.如果不为Null 执行toUpperCase操作 s?.toUpperCase()
        println(result)
    }


    fun alphabet(): String {
        val result = StringBuilder()
        for (letter in 'A'..'Z') {
            result.append(letter)
        }
        result.append("\nNow I know the alphabet!")
        return result.toString()
    }

    fun alphabet1(): String {
        val result = StringBuilder()
        //with同时做多个操作
        return with(result) {
            for (letter in 'A'..'Z') {
                append(letter)
            }
            append("\nNow I know the alphabet!")
            toString()
        }

    }

    fun alphabet2() = with(StringBuilder()) {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet!")
        toString()
    }

    fun alphabet3() = StringBuilder().apply {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet!")
    }.toString()

    fun alphabet4() = buildString {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet!")
    }.toString()

    fun createViewWithCustomAttributes(context: Context) = TextView(context).apply {
        textSize = 10.0f
        text = "I am a text"
        setTextColor(Color.RED)
        setPadding(10, 0, 0, 0)

        val p1 = Point(1, 2)
        val p2 = Point(3, 4)
        println(p1 + p2)
        twoAndThree { a, b -> a + b }
    }

    val sum = { x: Int, y: Int -> x + y }
    //返回值可为null
    val sum1: ((Int, Int) -> Int)? = null

    val sum2: (Int, Int) -> Int? = { _: Int, _: Int -> null }

    //定义参数operation 作为函数类型
    fun twoAndThree(operation: (Int, Int) -> Int) {
        //内部调用函数类型的参数
        val result = operation(2, 3)
        println("The result is ${result}")

        "ab2233c".filter { it in 'a'..'z' }
    }

    //定义String的扩展函数filter，参数是Char,函数predicate返回值是 Boolean
    //该函数相当于是根据条件来过滤String中的char， = {false}表示默认值
    fun String.filter(predicate: (Char) -> Boolean = {false}): String {
        val sb = StringBuilder()
        //遍历String
        for (index in 0..length - 1) {
            val char = get(index)
            //如果函数predicate的char参数满足条件，就添加到sb中
            if (predicate(char)){
                sb.append(char)
            }
        }
        return sb.toString()
    }

    fun lookForAlice(people: List<Person>) {
        //定义no-local返回
        people.forEach label@ {
            //条件满足就和 break的操作一样，后续的语句能执行
            //而区别于 return 是直接方法返回，后续的语句不能执行
            if (it.name == "Alice") return@label
        }
        println("Alice might be somewhere")
    }


    class Point(val name: Int, count: Int) {
        val count: Int by Delegates.observable(count) {
            property, oldValue, newValue ->
            println("name: ${property.name} oldValue:${oldValue}")
        }

    }

    operator fun Point.plus(point: Point): Point {
        return Point(name + point.name, count + point.count)
    }


    data class Book(val name: String, val authors: List<String>)

    fun testFlatMap() {
        val books = listOf(Book("Thursday Next", listOf("Jasper Fforde")), Book("Mort", listOf("Terry Pratchett")),
                Book("Good Omens", listOf("Terry Pratchett", "Neal Gaiman")))
        //toSet将重复值过滤掉
        println(books.flatMap { it.authors }.toSet())

        books.asSequence()
                .map(Book::name)
                .filter { it.startsWith("T") }
                .toList()

        generateSequence(0) { it + 1 }.takeWhile { it <= 100 }.sum()
    }

    fun testFunctionalApi() {

        val numbers = mapOf(0 to "zero", 1 to "one")
        //迭代values
        println(numbers.mapValues { it.value.toUpperCase() })
        //迭代keys
        println(numbers.mapKeys { it.key.plus(1) })

        numbers.filterNot { it.key == 0 }

        numbers.all { it.key == 0 }
    }


    //扩展函数的使用-在不继承对应的类的时候支持扩展 函数或者属性
    fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
        val tmp = this[index1]
        this[index1] = this[index2]
        this[index2] = tmp
    }

    //扩展属性举例
    val <T> List<T>.lastIndex: Int
        get() = size - 1

    data class Customer(val name: String, val email: String)

    fun testUnit(a: Int?, b: Int): Unit {
        // ?表示 a值可能为Null,需要做Null检查
        if (a != null) {
            println("a$a+ b$b = ${a + b}")
        }
        val customer = Customer("brucetoo", "brucetoo14@gmail.com")
        val newCustomer = customer.copy(email = "brucetoo13@gmail.com")
        customer.component1()
        newCustomer.component2()
        //pair获取到对应的值
        val (name, email) = customer
        println("name is $name,email is $email")

        val map = mapOf("one" to 1, "two" to 2)
        //遍历map
        for ((k, v) in map) {
            println(k + v)
        }

        val a: Int = 1
        val b: Long = a.toLong()

        val person = Person("name", "email")
    }

    fun max(a: Int, b: Int) = if (a > b) a else b

    fun min(a: Int, b: Int): Int {
        return a + b
    }

    //静态方法的定义 -- 这个只能在同一个类中访问，
    fun Date.isTuesDay(): Boolean {
        //day是 Date 中 set/get的缩写
        return day == 2
    }

    fun getStringLength(obj: Any): Int? {
        //Int 后面的?表示可以为null
//        if(obj is String) {
//            return obj.length
//        }
//        return null

        val sp = getSharedPreferences("sp_config", Context.MODE_PRIVATE)
        sp.edit().clear().apply()


        if (obj !is String) return null

        return obj.length
    }

    //空返回 Unit 可以省去
    fun testLoop(): Unit {
        //val类似final不可改变  var可改变
        val names = listOf("banana", "avocado", "apple", "kiwi")
        for (name in names) {
            println("name = $name")
        }

        for (index in names.indices) {
            println("name = ${names[index]}")
        }

        var index = 0
        while (index < names.size) {
            println("name = ${names[index]}")
            index++
        }

        //in的使用 区间判断
        if (index in 0..100) {
            //index是否在0-100之间
        }

        for (x in 4..1) {
            //这个不能表示倒序 且什么也不会输出
        }
        for (x in 100 downTo 1 step 2) {
            //x表示 100 -> 1 倒序输出 且步长为2
        }


        //函数式处理数据
        names.filter { it.startsWith("a") }
                .sortedBy { it }
                .map { it.toUpperCase() }
                .forEach { print(it) }

    }


    fun testWhen(obj: Any): String =
            //when 在kotlin中和 switch case 差不多,else 替代了 default执行
            when (obj) {
                1 -> "one"
                "Hello" -> "is test"
                is Long -> "is long"
                !is String -> "is not string"
                else -> "can't be cast"
            }

    //Delegate属性
    class LazyExample {
        //通常保存第一次调动get()方法时返回的值在后续使用
        val lazy: String by lazy {
            "this is lazy value"
        }

        //值变化的代理监听
        val observe: String by Delegates.observable("name") {
            o, old, new ->
            println("$old - $new")
        }

        //属性非Null的保护,这个会在未设值时抛出异常提醒开发者
        var name: String by Delegates.notNull<String>()

    }

}
