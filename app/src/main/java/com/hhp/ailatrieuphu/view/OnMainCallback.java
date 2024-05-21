package com.hhp.ailatrieuphu.view;

public interface OnMainCallback {
    void callback (String key, Object data);
    void showFragment(String tag, Object data, Boolean isBack, int slideAnim);
    void showFragment(String tag, Object data, Boolean isBack);
}
