package com.brucetoo.hellokt

/**
 * Created by Bruce Too
 * On 27/05/2017.
 * At 14:31
 */
open class Person (){
    var name: String = ""
    var email: String = ""

    init {
        println("This is init block!")
    }

    constructor(name: String) : this(){
        this.name = name
    }

    //次构造函数
    constructor(name: String,email: String) : this(name){
        this.name = name
        this.email = email
    }

    open fun foo(){

    }

    class Person1 : Person(){
        //open 必须显示声明才能被继承
        override fun foo() {

        }
    }

}
