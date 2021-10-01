package amaterek.base.test.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.CoroutineScope
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class ViewModelTestRule(private val coroutineScope: CoroutineScope) : TestWatcher() {

    private val viewModelExtensionClass = "androidx.lifecycle.ViewModelKt"

    override fun starting(description: Description) {
        super.starting(description)
        mockkStatic(viewModelExtensionClass)
        every { any<ViewModel>().viewModelScope } returns coroutineScope
    }

    override fun finished(description: Description) {
        unmockkStatic(viewModelExtensionClass)
        super.finished(description)
    }
}