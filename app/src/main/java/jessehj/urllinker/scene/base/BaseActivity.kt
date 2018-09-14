package jessehj.urllinker.scene.base

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import jessehj.urllinker.util.NavigateUtils
import jessehj.urllinker.view.AppProgressDialog

open class BaseActivity : AppCompatActivity() {

    private lateinit var progress: AppProgressDialog
    var finishStyle = NavigateUtils.Style.Backward

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        stopProgressDialog()
        super.onDestroy()
    }

    override fun finish() {
        super.finish()
        NavigateUtils.applyTransitionStyle(this@BaseActivity, finishStyle)
    }

    fun configToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    fun showProgressDialog() {
        if (!::progress.isInitialized) {
            progress = AppProgressDialog(this)
        }
        if (!progress.isShowing) {
            progress.show()
        }
    }

    fun stopProgressDialog() {
        if (::progress.isInitialized) {
            progress.dismiss()
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showSnackbar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }
}