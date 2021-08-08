package com.example.itstrending

import androidx.lifecycle.SavedStateHandle
import com.example.itstrending.data.TrendingResponse
import com.example.itstrending.viewmodel.TrendingViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TrendingReposAdapterTest {
    private var selectedPosition: Int = -1

    @Mock
    var savedStateHandle: SavedStateHandle = SavedStateHandle()

    @Mock
    var viewModel: TrendingViewModel = TrendingViewModel(savedStateHandle)

    @Mock
    var viewModel2: TrendingViewModel? = null

    @Before
    fun init(){
         selectedPosition = -1
    }

    //tests the default case when no selection is made by user
    @Test
    fun setSelectionPrev() {
        if (selectedPosition == -1 && viewModel.getSelectedIndex().value!=null &&
            viewModel.getSelectedIndex().value!! == -1
        ) {
            selectedPosition = viewModel.getSelectedIndex().value!!
        }
        assertEquals(selectedPosition, -1)
    }

    //where viewmodel is null - fun should not proceed further with the check
    // which would otherwise lead to crash
    @Test
    fun setSelectionPrev2() {
        if (selectedPosition == -1 && viewModel2?.getSelectedIndex()?.value!=null &&
            viewModel2?.getSelectedIndex()?.value!! == -1
        ) {
            selectedPosition = viewModel2?.getSelectedIndex()?.value!!
        }
        assertEquals(selectedPosition, -1)
    }

    //TODO: this requires activity context, would thus require instrumentation testing..
    //this is a delegated initialised variable, not a fun!
    /*private var delegateVar by Delegates.observable(-1) { _, oldPos, newPos ->
        if (oldPos >= 0 && newPos in simulatedList.indices) {
            adapter.notifyItemChanged(oldPos)
            adapter.notifyItemChanged(newPos)
        }
    }*/
    /*@Test
    fun testDelegateCode(){
       delegateVar = 2 //test value for position
    }*/


}