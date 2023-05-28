package otus.demo.totalcoverage.addexpense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import otus.demo.totalcoverage.Open
import otus.demo.totalcoverage.baseexpenses.Category
import otus.demo.totalcoverage.baseexpenses.Expense
import otus.demo.totalcoverage.utils.NeedsTesting
import java.io.Serializable
import javax.inject.Inject
import javax.inject.Named

@NeedsTesting
@Open
class AddExpenseViewModel(
    private val addExpensesInteractor: AddExpensesInteractor,
    private val id:Long, // прокинуть id в граф, можно также прокинуть через сеттер
    private val pageId: Long
) : ViewModel() {

    private val _liveData: MutableLiveData<AddExpenseResult> = MutableLiveData()
    var liveData: LiveData<AddExpenseResult> = _liveData

    var disposable: Disposable? = null

    fun addExpense(
        title: String,
        amount: String,
        category: Category,
        comment: String? = null
    ) {

        //getexpenses(id,pageId)
        disposable = addExpensesInteractor.addExpense(title, amount.toLong(), category, comment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _liveData.value = Success(it) }, { _liveData.value = Error(it) })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}

@Open
class AddExpenseViewModelModelFactory @Inject constructor(
    private val addExpensesInteractor: AddExpensesInteractor,
    //1 вариант с @Named
    //@Named("id") private val id:Long,
    private val id:Long,
    //2 вариант с   @Qualifier
    @AddExpensesComponent.PageId private val pageId: Long
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java))
            return AddExpenseViewModel(addExpensesInteractor, id, pageId) as T
        else throw IllegalArgumentException()
    }
}

sealed class AddExpenseResult : Serializable
data class Error(val throwable: Throwable?) : AddExpenseResult()
data class Success(val value: Expense) : AddExpenseResult()