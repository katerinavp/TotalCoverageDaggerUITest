package otus.demo.totalcoverage.di

import dagger.*
import otus.demo.totalcoverage.addexpense.AddExpensesComponent
import otus.demo.totalcoverage.baseexpenses.ExpensesService
import otus.demo.totalcoverage.baseexpenses.FakeExpensesServiceImpl
import otus.demo.totalcoverage.expenseslist.ExpensesComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Scope
import javax.inject.Singleton

private const val URL = "http://localhost"

@Singleton
@Component(modules = [NetworkModule::class, SubcomponentsModule::class])
interface AppComponent {

//    //провишион метод
//    fun provideExpensesService(): ExpensesService

    fun addExpensesComponent(): AddExpensesComponent.Factory
    fun expensesComponent(): ExpensesComponent.Factory
}


@Module(subcomponents = [ExpensesComponent::class, AddExpensesComponent::class])
interface SubcomponentsModule

@Module
interface NetworkModule {

//    @Provides
//    @Singleton
//    fun provideExpensesNetworkService(retrofit: Retrofit): ExpensesService {
//        return retrofit.create(ExpensesService::class.java)
//    }

    companion object {
        @Provides
        @Singleton
        fun provideRetrofitClient(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    @Binds
    @Singleton
    fun provideFakeExpensesNetworkService(fakeExpensesServiceImpl: FakeExpensesServiceImpl): ExpensesService  // везде  будет один и тот же экземпляр expensesService

//    @Provides
//    @Singleton
//    fun provideRetrofitClient(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(URL)
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
}