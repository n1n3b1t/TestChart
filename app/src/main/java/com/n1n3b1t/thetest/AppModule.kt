package com.n1n3b1t.thetest

import com.n1n3b1t.thetest.navigator.Navigator
import com.n1n3b1t.thetest.ui.NavigatorImpl
import org.koin.dsl.module

val appModule = module {
   single<Navigator> { NavigatorImpl(get()) }
}