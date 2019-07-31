package luclx.com.beapp.ui.home

import android.os.Bundle
import luclx.com.beapp.R
import luclx.com.beapp.databinding.ActivityMainBinding
import luclx.com.beapp.ui.base.BaseActivity
import luclx.com.beapp.utils.Constant
import luclx.com.beapp.utils.FragmentUtils

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutResource() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentUtils.replaceFragment(
            this,
            CardListFragment.newInstance(),
            R.id.container,
            false,
            Constant.TRANSITION_NONE
        )
    }
}
