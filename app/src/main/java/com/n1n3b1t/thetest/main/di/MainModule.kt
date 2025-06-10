package com.n1n3b1t.thetest.main.di

import com.n1n3b1t.thetest.main.domain.PointRepository
import com.n1n3b1t.thetest.main.domain.usecase.GetPointsUseCase
import com.n1n3b1t.thetest.main.navigation.MainNavigatorImpl
import com.n1n3b1t.thetest.main.ui.data.DataViewModel
import com.n1n3b1t.thetest.main.ui.home.MainScreenViewModel
import com.n1n3b1t.thetest.navigator.MainNavigator
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    singleOf<MainNavigator>(::MainNavigatorImpl)
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::DataViewModel)

    singleOf(::PointRepository)
    factoryOf(::GetPointsUseCase)
}