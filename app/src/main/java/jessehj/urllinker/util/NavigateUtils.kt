package jessehj.urllinker.util

import android.app.Activity
import jessehj.urllinker.R

object NavigateUtils {

    enum class Style {
        None,
        Forward,
        Backward,
        Up,
        Down
    }

    enum class Direction {
        Enter,
        Exit
    }

    fun applyTransitionStyle(activity: Activity, style: Style) {
        when (style) {
            Style.Forward -> activity.overridePendingTransition(R.anim.forward_enter, R.anim.forward_exit)
            Style.Backward -> activity.overridePendingTransition(R.anim.backward_enter, R.anim.backward_exit)
            Style.Up -> activity.overridePendingTransition(R.anim.slide_up, R.anim.forward_exit)
            Style.Down -> activity.overridePendingTransition(R.anim.backward_enter, R.anim.slide_down)
            else -> activity.overridePendingTransition(0, 0)
        }
    }
}