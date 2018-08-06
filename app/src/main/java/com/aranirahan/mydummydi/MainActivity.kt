package com.aranirahan.mydummydi

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Qualifier

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
//class MainActivity : AppCompatActivity() {
//
//    @Inject lateinit var info : Info
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        DaggerMagicBox.create().poke(this)
//        tv_view.text = info.text
//
//    }
//}
//
//class Info @Inject constructor(){
//    val text = "Hello Dagger 2"
//}
//
//@Component
//interface MagicBox {
//    fun poke(app : MainActivity)
//}

/**With @Provide and @Module**/
class MainActivity : AppCompatActivity() {

    @Inject
    @field:Choose("LOVE")
    lateinit var infoLove : Info

    @Inject
    @field:Choose("HELLO")
    lateinit var infoHello : Info

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMagicBox.create().poke(this)
        tv_view.text = """${infoLove.text}
            |${infoHello.text}
        """.trimMargin()

    }
}


class Info constructor(val text: String)


@Component(modules = [Bag::class])
interface MagicBox {
    fun poke(app: MainActivity)
}


@Module
class Bag {

    @Provides
    @Choose("LOVE")
    fun sayLoveDagger2(): Info {
        return Info("Love Dagger 2")
    }

    @Provides
    @Choose("HELLO")
    fun sayHelloDagger2(): Info {
        return Info("Hello Dagger 2")
    }
}


@Qualifier
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Choose(val value: String = "")