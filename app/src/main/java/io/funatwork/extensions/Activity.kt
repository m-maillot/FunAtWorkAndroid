package io.funatwork.extensions

import android.app.Activity
import android.app.Fragment

/**
 * Adds a [Fragment] to this activity's layout.

 * @param containerViewId The container view to where add the fragment.
 * *
 * @param fragment The fragment to be added.
 */
fun Activity.addFragment(containerViewId: Int, fragment: Fragment) =
        fragmentManager.beginTransaction().add(containerViewId, fragment).commit()