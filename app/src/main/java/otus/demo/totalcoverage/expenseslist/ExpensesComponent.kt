package otus.demo.totalcoverage.expenseslist

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import otus.demo.totalcoverage.addexpense.AddExpensesComponent
import otus.demo.totalcoverage.di.AppComponent
import otus.demo.totalcoverage.di.FeatureScope
import javax.inject.Named

@FeatureScope
@Subcomponent(
    modules = [ExpensesModule::class],

    )
interface ExpensesComponent {

    //    companion object {
//
//        fun getExpensesComponent(appComponent: AppComponent): ExpensesComponent {
//            return DaggerExpensesComponent.builder().appComponent(appComponent).build()
//        }
//    }

    @Subcomponent.Factory //если в сабкомпоненте
    interface Factory {
        fun create(): ExpensesComponent
    }

    fun inject(expensesFragment: ExpensesFragment)
}

@Module
interface ExpensesModule {

    companion object {

        @Provides
        fun providesIoDispatcher(): CoroutineDispatcher {
            return Dispatchers.IO
        }
    }

    @Binds
    fun bindRepository(expensesRepositoryImpl: ExpensesRepositoryImpl): ExpensesRepository

    @Binds
    fun bindFactory(expensesViewModelFactory: ExpensesViewModelFactory): ViewModelProvider.Factory
}