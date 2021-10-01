package amaterek.base.test.android

import amaterek.base.test.CoroutineTest
import org.junit.Rule

abstract class ViewModelTest : CoroutineTest() {

    @get:Rule
    val viewModelTestRule = ViewModelTestRule(coroutineTestRule)
}