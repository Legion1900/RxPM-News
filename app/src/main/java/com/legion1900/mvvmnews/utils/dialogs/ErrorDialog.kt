import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class ErrorDialog :
    DialogFragment() {

    companion object {
        const val KEY_MSG = "msg"
        const val KEY_TXT = "text"
        const val KEY_CALLBACK = "callback"

        fun createErrorDialog(
            @StringRes msg: Int,
            @StringRes btnText: Int,
            callback: PositiveCallback
        ): DialogFragment {
            val dialogFragment = ErrorDialog()
            val args = Bundle()
            ErrorDialog.run {
                args.putInt(KEY_MSG, msg)
                args.putInt(KEY_TXT, btnText)
                args.putParcelable(KEY_CALLBACK, callback)
            }
            dialogFragment.arguments = args
            return dialogFragment
        }
    }

    private var msg = 0
    private var btnText = 0
    private lateinit var callback: PositiveCallback

    private var isShowing = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        getParams()
        val builder = AlertDialog.Builder(context)
        builder.setMessage(msg)
            .setPositiveButton(
                btnText
            ) { _, _ ->
                isShowing = false
                dismiss()
                callback.onPositive()
            }
        return builder.create()
    }

    private fun getParams() {
        val params = arguments!!
        msg = params.getInt(KEY_MSG)
        btnText = params.getInt(KEY_TXT)
        callback = params.getParcelable(KEY_CALLBACK)!!
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (!isShowing) {
            isShowing = true
            super.show(manager, tag)
        }
    }

    interface PositiveCallback : Parcelable {
        fun onPositive()
        override fun writeToParcel(dest: Parcel?, flags: Int) {
            /*
            * Nothing to do here.
            * */
        }

        override fun describeContents(): Int = 0
    }
}