package jessehj.urllinker.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.github.ybq.android.spinkit.style.FoldingCube
import jessehj.urllinker.R

class AppProgressDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (window != null) {
            window!!.requestFeature(Window.FEATURE_NO_TITLE)

            window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window!!.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            }
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        }
        setContentView(R.layout.dialog_app_progress)
        setCancelable(false)

        val progressBar = findViewById<ProgressBar>(R.id.spin_kit_view)
        val doubleBounce = FoldingCube()
        progressBar.indeterminateDrawable = doubleBounce
    }
}
