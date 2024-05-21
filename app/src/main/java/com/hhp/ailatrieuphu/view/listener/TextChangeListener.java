package com.hhp.ailatrieuphu.view.listener;

import android.text.TextWatcher;

public interface TextChangeListener extends TextWatcher {
    @Override
    default void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    default void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

}
