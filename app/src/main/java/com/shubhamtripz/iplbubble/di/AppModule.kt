package com.shubhamtripz.iplbubble.di

import androidx.room.Room
import com.shubhamtripz.iplbubble.data.local.AppDatabase
import com.shubhamtripz.iplbubble.data.local.ImageCacheUtil
import com.shubhamtripz.iplbubble.data.remote.HomeApiService
import com.shubhamtripz.iplbubble.data.remote.IplFixturesApiService
import com.shubhamtripz.iplbubble.data.remote.PointsTableApiService
import com.shubhamtripz.iplbubble.data.remote.StatsApiService
import com.shubhamtripz.iplbubble.data.repository.HomeRepository
import com.shubhamtripz.iplbubble.data.repository.IplFixturesRepository
import com.shubhamtripz.iplbubble.data.repository.MatchRepository
import com.shubhamtripz.iplbubble.data.repository.PointsTableRepository
import com.shubhamtripz.iplbubble.data.repository.StatsRepository
import com.shubhamtripz.iplbubble.data.utils.NetworkUtils
import com.shubhamtripz.iplbubble.domain.usecases.GetIplFixturesUseCase
import com.shubhamtripz.iplbubble.domain.usecases.GetPointsTableUseCase
import com.shubhamtripz.iplbubble.presentation.home.HomeViewModel
import com.shubhamtripz.iplbubble.presentation.home.livescore.MatchViewModel
import com.shubhamtripz.iplbubble.presentation.matches.iplfixtures.IplFixturesViewModel
import com.shubhamtripz.iplbubble.presentation.matches.pointstable.PointsTableViewModel
import com.shubhamtripz.iplbubble.presentation.matches.stats.StatsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {


    // Database
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "iplbubble_database"
        ).build()
    }

    // DAOs
    single { get<AppDatabase>().homeResponseDao() }

    // Utilities
    single { NetworkUtils(androidContext()) }
    single { ImageCacheUtil(androidContext()) }

    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://script.google.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(IplFixturesApiService::class.java) }
    single { get<Retrofit>().create(PointsTableApiService::class.java) }
    single { get<Retrofit>().create(StatsApiService::class.java) }

    // Services
    single { get<Retrofit>().create(HomeApiService::class.java) }



    // Repository
    single { IplFixturesRepository(get()) }
    single { HomeRepository(get(), get(), get(), get()) }
    single { StatsRepository(get()) }
    single { PointsTableRepository(get()) }

    // Use Case
    single { GetIplFixturesUseCase(get()) }
    single { GetPointsTableUseCase(get()) }

    // ViewModel
    viewModel { IplFixturesViewModel(get()) }
    viewModel { PointsTableViewModel(get()) }
    viewModel { StatsViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { IplFixturesViewModel(get()) }

    // Match Related Dependencies
    factory { (webView: android.webkit.WebView) -> MatchRepository(webView) }
    viewModel { (webView: android.webkit.WebView) -> MatchViewModel(get { parametersOf(webView) }) }

}
