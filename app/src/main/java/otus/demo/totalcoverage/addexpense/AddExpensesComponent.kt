package otus.demo.totalcoverage.addexpense

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import io.reactivex.disposables.CompositeDisposable
import otus.demo.totalcoverage.di.AppComponent
import otus.demo.totalcoverage.di.FeatureScope

@FeatureScope
@Subcomponent(
    modules = [AddExpensesModule::class],
)
interface AddExpensesComponent {

//    companion object {
//        fun getAddExpensesComponent(appComponent: AppComponent): AddExpensesComponent {
//            return DaggerAddExpensesComponent.builder().appComponent(appComponent).build()
//        }
//    }

    // Варианты прокинуть что то в граф фабрика или билдер
    //1 variant в create передаем параметр который необходимо прокинуть
    @Subcomponent.Factory
    interface Factory {
        fun create(): AddExpensesComponent
    }

    // builder нужен чтобы прокидывать что то в рантайме в граф


    //2 variant
//    interface Builder {
//        @BindsInstance
//        fun setId(id: String): Builder // сами прописываем методы которые билдер будет генерировать. по дефолту даггер генерирует билдер,но его можно кастомизировать
//        fun build(): AddExpensesComponent
//    }


    fun inject(expensesListFragment: AddExpenseFragment)
}

@Module
interface AddExpensesModule {

    companion object {

        @Provides
        fun providesScope(): CompositeDisposable {
            return CompositeDisposable()
        }
    }

    @Binds
    fun bindFactory(expensesViewModelFactory: AddExpenseViewModelModelFactory): ViewModelProvider.Factory
}