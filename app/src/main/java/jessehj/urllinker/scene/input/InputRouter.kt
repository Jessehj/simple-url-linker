package jessehj.urllinker.scene.input;

import android.app.Activity.RESULT_OK
import java.lang.ref.WeakReference


interface InputRoutingLogic {
    fun navigateToMain()
}

class InputRouter : InputRoutingLogic {

    lateinit var activity: WeakReference<InputActivity>
    lateinit var dataStore: InputDataStore

    override fun navigateToMain() {
        activity.get()!!.setResult(RESULT_OK)
        activity.get()!!.finish()
    }
}