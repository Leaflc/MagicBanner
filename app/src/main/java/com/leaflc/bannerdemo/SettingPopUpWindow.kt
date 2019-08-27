package com.leaflc.bannerdemo

import android.content.Context
import android.view.WindowManager
import android.widget.PopupWindow

/**
 * SettingPopUpWindow class
 *
 * @author leaflc
 * @date 2019-08-24
 */
open class SettingPopUpWindow(context: Context) : PopupWindow(context) {
    init {
        isOutsideTouchable = true
        width = Util.Dp2px(context, 320F)
        height = WindowManager.LayoutParams.WRAP_CONTENT
    }

}