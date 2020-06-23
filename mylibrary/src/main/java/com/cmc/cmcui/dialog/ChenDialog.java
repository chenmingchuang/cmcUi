package com.cmc.cmcui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class ChenDialog extends Dialog{
    public ChenDialog(@NonNull Context context) {
        super(context);
    }

    public ChenDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void dismiss() {
        View view = getCurrentFocus();
        if (view != null && view instanceof TextView) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        super.dismiss();
    }
}
