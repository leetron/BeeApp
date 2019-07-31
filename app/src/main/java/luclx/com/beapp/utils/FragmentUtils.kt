package luclx.com.beapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import luclx.com.beapp.R
import luclx.com.beapp.utils.Constant.TRANSITION_FADE_IN_OUT
import luclx.com.beapp.utils.Constant.TRANSITION_NONE
import luclx.com.beapp.utils.Constant.TRANSITION_POP
import luclx.com.beapp.utils.Constant.TRANSITION_SLIDE_LEFT_RIGHT
import luclx.com.beapp.utils.Constant.TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT

class FragmentUtils {

    companion object {
        fun replaceFragment(
            activity: AppCompatActivity,
            fragment: Fragment,
            id: Int,
            addToBackStack: Boolean,
            animationType: Int
        ) {
            val fragmentManager = activity.supportFragmentManager
            val transaction = fragmentManager.beginTransaction()

            when (animationType) {
                TRANSITION_POP -> transaction.setCustomAnimations(
                    R.anim.anim_enter,
                    R.anim.anim_exit,
                    R.anim.anim_pop_enter,
                    R.anim.anim_pop_exit
                )
                TRANSITION_FADE_IN_OUT -> transaction.setCustomAnimations(
                    R.anim.anim_frag_fade_in,
                    R.anim.anim_frag_fade_out
                )
                TRANSITION_SLIDE_LEFT_RIGHT -> transaction.setCustomAnimations(
                    R.anim.slide_in_from_rigth,
                    R.anim.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right
                )
                TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT -> transaction.setCustomAnimations(
                    R.anim.slide_in_from_rigth,
                    0
                )
                TRANSITION_NONE -> transaction.setCustomAnimations(0, 0)
                else -> transaction.setCustomAnimations(0, 0)
            }

            if (addToBackStack)
                transaction.addToBackStack(fragment.javaClass.canonicalName)

            transaction.add(id, fragment, fragment.javaClass.canonicalName)
            transaction.commit()
        }
    }
}