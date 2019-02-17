package com.winsonmac.core.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import com.winsonmac.core.R
import com.winsonmac.core.helper.UIHelper
import android.content.pm.PackageManager
import android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission


/* =========================================================================================== */
/*  Base activity to reuse code, easy to manage and maintain                                   */
/* =========================================================================================== */

@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity() {

    private val mUIHelper by lazy { UIHelper(this, this) }
    private val mContainer by lazy { findViewById<LinearLayout>(R.id.container) }

    /* =========================================================================================== */
    /*  Lifecycle methods                                                                          */
    /* =========================================================================================== */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Only allow portrait mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Init root layout
        setContentView(R.layout.layout_root)

        // Layout & init view's components
        setupViews()
    }

    override fun onPause() {
        super.onPause()
        ui().hideLoading()
    }

    override fun onResume() {
        super.onResume()
        ui().shouldShowLoading()
    }


    /* =========================================================================================== */
    /*  Implement super methods                                                                    */
    /* =========================================================================================== */

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        super.onNewIntent(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    /* =========================================================================================== */
    /*  Additional methods                                                                         */
    /* =========================================================================================== */

    fun <T : ViewDataBinding> bindingContentLayout(layoutId: Int): T {
        val binding = DataBindingUtil.inflate<T>(layoutInflater, layoutId, mContainer, false)
        mContainer.addView(binding.root, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return binding
    }

    /** Determines if the context calling has the required permission
     * @param permission - The permissions to check
     * @return true if the IPC has the granted permission
     */
    fun hasPermission(permission: String): Boolean {
        val res = checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }

    /** Determines if the context calling has the required permissions
     * @param permissions - The permissions to check
     * @return true if the IPC has the granted permission
     */
    fun hasPermissions(vararg permissions: String): Boolean {

        var hasAllPermissions = true
        for (permission in permissions) {
            //you can return false instead of assigning, but by assigning you can log all permission values
            if (!hasPermission(permission)) hasAllPermissions = false
        }
        return hasAllPermissions

    }


    /* =========================================================================================== */
    /*  Getter & setter                                                                            */
    /* =========================================================================================== */

    fun ui(): UIHelper {
        return mUIHelper
    }


    /* =========================================================================================== */
    /*  Abstract methods                                                                           */
    /* =========================================================================================== */

    abstract fun setupViews()
}