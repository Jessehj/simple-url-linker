package jessehj.urllinker.scene.main;

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import jessehj.urllinker.LinkDefault
import jessehj.urllinker.scene.input.InputActivity
import jessehj.urllinker.util.NavigateUtils
import java.lang.ref.WeakReference;


interface MainRoutingLogic {
    fun navigateWithUrl()
    fun navigateToInput()
    fun navigateToModify()
}

class MainRouter : MainRoutingLogic {

    lateinit var activity: WeakReference<MainActivity>
    lateinit var dataStore: MainDataStore

    override fun navigateWithUrl() {
        val url = dataStore.selectedLink()!!.url
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.get()!!.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(LinkDefault.URL_MARKET)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.get()!!.startActivity(intent)
        }
    }

    override fun navigateToInput() {
        Intent(activity.get(), InputActivity::class.java).apply {
            activity.get()!!.startActivityForResult(this, MainActivity.REQ_CODE_INPUT)
        }
        NavigateUtils.applyTransitionStyle(activity.get()!!, NavigateUtils.Style.Forward)
    }

    override fun navigateToModify() {
        Intent(activity.get(), InputActivity::class.java).apply {
            if (dataStore.selectedLink() != null) {
                putExtra(InputActivity.EXTRA_SELECTED_LINK_ID, dataStore.selectedLink()!!.id)
            }
            activity.get()!!.startActivityForResult(this, MainActivity.REQ_CODE_INPUT)
        }
        NavigateUtils.applyTransitionStyle(activity.get()!!, NavigateUtils.Style.Forward)
    }
}