package com.example.icepicksample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import icepick.Icepick
import icepick.State

/**
 * samplecode:
 * https://github.com/frankiesardo/icepick
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    // icepick: data hold
    @JvmField @State var icepickValue: Int = 0

    // android FW: data hold
    var text1: String? = null
    var value: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // リスナー一括登録.
        ViewUtil.setButtonOnClickListener(findViewById<View>(R.id.rootView), this)
        
        // Icepick restore
        Icepick.restoreInstanceState(this, savedInstanceState)

        // Android FW restore
        if (null != savedInstanceState) {
            text1 = savedInstanceState.getString(BUNDLE_KEY_TEXT1)
            value = savedInstanceState.getInt(BUNDLE_KEY_VALUE)
        }

        // NOTE: Icepick3.2.0では、
        // メモリ不足等によるActivity破棄(開発者オプション: Activityを保持しないONで実現可能)時の考慮がされていないのか、
        // Activity,Fragment破棄後の再生成時に Icepick.restoreInstanceStateで、 InstanceStateで管理している値が取得できない.
        Toast.makeText(this, "onCreate: " + getValue(), Toast.LENGTH_LONG).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Icepick saveInstance
        Icepick.saveInstanceState(this, outState)

        // Android FW saveInstance
        outState.putString(BUNDLE_KEY_TEXT1, text1)
        outState.putInt(BUNDLE_KEY_VALUE, value)

        Toast.makeText(this, "onSaveInstanceState: " + getValue(), Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy: " + getValue(), Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.icepickValue -> icepickValue = 123
            R.id.text1 -> text1 = "set text1"
            R.id.value -> value = 456
            R.id.checkValue -> {
                Toast.makeText(this, getValue(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getValue(): String {
        return "icepickValue: $icepickValue ,text1: $text1 ,value: $value"
    }

    companion object {
        const val BUNDLE_KEY_TEXT1 = "BUNDLE_KEY_TEXT1"
        const val BUNDLE_KEY_VALUE = "BUNDLE_KEY_VALUE"
    }
}
