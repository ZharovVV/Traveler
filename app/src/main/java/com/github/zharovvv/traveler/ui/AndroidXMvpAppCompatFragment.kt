package com.github.zharovvv.traveler.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.arellomobile.mvp.MvpDelegate

abstract class AndroidXMvpAppCompatFragment : Fragment() {
    private var mIsStateSaved = false
    private var mMvpDelegate: MvpDelegate<out AndroidXMvpAppCompatFragment>? = null
    private var mIsRestoredFromBackStack: Boolean = false
    val isRestoredFromBackStack get() = mIsRestoredFromBackStack

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("fragment_lifecycle", "$this#onCreate")
        super.onCreate(savedInstanceState)
        mvpDelegate.onCreate(savedInstanceState)
        mIsRestoredFromBackStack = false
    }

    //onCreateView

    //onViewCreated

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.i(
            "fragment_lifecycle",
            "$this#onViewStateRestored"
        )
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        Log.i("fragment_lifecycle", "$this#onStart")
        super.onStart()
        mIsStateSaved = false
        mvpDelegate.onAttach()
    }

    override fun onResume() {
        Log.i("fragment_lifecycle", "$this#onResume")
        super.onResume()
        mIsStateSaved = false
        mvpDelegate.onAttach()
    }

    override fun onPause() {
        Log.i("fragment_lifecycle", "$this#onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.i("fragment_lifecycle", "$this#onStop")
        super.onStop()
        mvpDelegate.onDetach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i(
            "fragment_lifecycle",
            "$this#onSaveInstanceState"
        )
        super.onSaveInstanceState(outState)
        mIsStateSaved = true
        mvpDelegate.onSaveInstanceState(outState)
        mvpDelegate.onDetach()
    }

    override fun onDestroyView() {
        Log.i("fragment_lifecycle", "$this#onDestroyView")
        super.onDestroyView()
        mvpDelegate.onDetach()
        mvpDelegate.onDestroyView()
        mIsRestoredFromBackStack = true
    }

    override fun onDestroy() {
        Log.i("fragment_lifecycle", "$this#onDestroy")
        super.onDestroy()

        //We leave the screen and respectively all fragments will be destroyed
        if (activity!!.isFinishing) {
            mvpDelegate.onDestroy()
            return
        }

        // When we rotate device isRemoving() return true for fragment placed in backstack
        // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
        if (mIsStateSaved) {
            mIsStateSaved = false
            return
        }

        // See https://github.com/Arello-Mobile/Moxy/issues/24
        var anyParentIsRemoving = false
        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }
        if (isRemoving || anyParentIsRemoving) {
            mvpDelegate.onDestroy()
        }
    }

    /**
     * @return The [MvpDelegate] being used by this Fragment.
     */
    private val mvpDelegate: MvpDelegate<*>
        get() {
            if (mMvpDelegate == null) {
                mMvpDelegate = MvpDelegate(this)
            }
            return mMvpDelegate!!
        }

    fun <F : AndroidXMvpAppCompatFragment> F.transitionTo(rootContainer: ViewGroup, fragment: F) {
        prepareTransitionTo(rootContainer, fragment)
            .commit()
    }

    @SuppressLint("DefaultLocale")
    fun <F : AndroidXMvpAppCompatFragment> F.prepareTransitionTo(
        rootContainer: ViewGroup,
        fragment: F
    ): FragmentTransaction {
        val fragmentTag: String = fragment.javaClass.simpleName.decapitalize()
        return this.requireFragmentManager().beginTransaction()
            .replace(rootContainer.id, fragment, fragmentTag)
            .addToBackStack(null)
    }
}