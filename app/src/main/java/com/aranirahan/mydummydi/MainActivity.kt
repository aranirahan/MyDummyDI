package com.aranirahan.mydummydi

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Scope
import javax.inject.Singleton

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
//class MainActivity : AppCompatActivity() {
//
//    @Inject lateinit var info: Info
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
//
//class Info constructor(val text: String)
//
//
//@Component(modules = [Bag::class])
//interface MagicBox {
//    fun poke(app: MainActivity)
//}
//
//
//@Module
//class Bag {
//
//    @Provides
//    fun sayLoveDagger2(): Info {
//        return Info("Love Dagger 2")
//    }
//}

/**With Qualifier**/
//class MainActivity : AppCompatActivity() {
//
//    @Inject
//    @field:Choose("LOVE")
//    lateinit var infoLove : Info
//
//    @Inject
//    @field:Choose("HELLO")
//    lateinit var infoHello : Info
//
//    @SuppressLint("SetTextI18n")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        DaggerMagicBox.create().poke(this)
//        tv_view.text = """${infoLove.text}
//            |${infoHello.text}
//        """.trimMargin()
//
//    }
//}
//
//
//class Info constructor(val text: String)
//
//
//@Component(modules = [Bag::class])
//interface MagicBox {
//    fun poke(app: MainActivity)
//}
//
//
//@Module
//class Bag {
//
//    @Provides
//    @Choose("LOVE")
//    fun sayLoveDagger2(): Info {
//        return Info("Love Dagger 2")
//    }
//
//    @Provides
//    @Choose("HELLO")
//    fun sayHelloDagger2(): Info {
//        return Info("Hello Dagger 2")
//    }
//}
//
//
//@Qualifier
//@MustBeDocumented
//@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
//annotation class Choose(val value: String = "")

/**With Scope**/
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var magicBox: MagicBox
//
//    @SuppressLint("SetTextI18n")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        magicBox = DaggerMagicBox.create()
//
//        btn_create.setOnClickListener {
//            val storage = Storage()
//            magicBox.poke(storage)
//            tv_view.text = """ Unique = ${storage.uniqueMagic.count}
//            |Normal = ${storage.normalMagic.count}
//        """.trimMargin()
//        }
//
//    }
//}
//
//@MagicScope
//@Component
//interface MagicBox {
//    fun poke(app: Storage)
//}
//
//class Storage {
//    @Inject
//    lateinit var uniqueMagic: UniqueMagic
//    @Inject
//    lateinit var normalMagic: NormalMagic
//}
//
//@Scope
//@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
//annotation class MagicScope
//
//var staticCounter = 0
//
//@MagicScope
//class UniqueMagic @Inject constructor() {
//    val count = staticCounter++
//}
//
//class NormalMagic @Inject constructor() {
//    val count = staticCounter++
//}

/**With Subcomponent**/
class MainActivity : AppCompatActivity() {

    private lateinit var mainBox: SingletonBox
    private lateinit var magicBox: MagicBox

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainBox = DaggerSingletonBox.create()

        btn_create.setOnClickListener {
            magicBox = mainBox.getMagicBox()
            useStorage()
        }

        btn_reuse.setOnClickListener {
            useStorage()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun useStorage() {
        val storage = Storage()
        magicBox.poke(storage)
        tv_view.text = """SingletonOne = ${storage.singletonOne.count}
            |Unique = ${storage.uniqueMagic.count}
            |Normal = ${storage.normalMagic.count}
        """.trimMargin()
    }
}

@MagicScope
@Subcomponent
interface MagicBox {
    fun poke(app: Storage)
}

@Singleton
@Component
interface SingletonBox {
    fun getMagicBox(): MagicBox
}

class Storage {
    @Inject
    lateinit var singletonOne: SingletonOne
    @Inject
    lateinit var uniqueMagic: UniqueMagic
    @Inject
    lateinit var normalMagic: NormalMagic
}

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class MagicScope

@Singleton
class SingletonOne @Inject constructor() {
    val count = staticCounter++
}

var staticCounter = 0

@MagicScope
class UniqueMagic @Inject constructor() {
    val count = staticCounter++
}

class NormalMagic @Inject constructor() {
    val count = staticCounter++
}

