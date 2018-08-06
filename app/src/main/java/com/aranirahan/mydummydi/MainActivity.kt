package com.aranirahan.mydummydi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dagger.Component
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**No Injection, No Dagger*/
//class MainActivity : AppCompatActivity() {
//
//    val info = Info()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
//}
//
//class Info {
//    val text = "Hello Dagger 2"
//}

/**With Injection, No Dagger*/
//class MainActivity(val info : Info) : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
//}
//
//class Info {
//    val text = "Hello Dagger 2"
//}

/**With Injection also Dagger*/
class MainActivity : AppCompatActivity() {

    lateinit var info : Info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMagicBox.create().poke(this)
        tv_view.text = info.text

    }
}

class Info @Inject constructor(){
    val text = "Hello Dagger 2"
}

@Component
interface MagicBox {
    fun poke(app : MainActivity)
}

