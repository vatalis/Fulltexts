package com.flipbox.fulltext.interfaces;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by bukhorimuhammad on 4/15/16.
 */
public interface BaseMethods
{
    /**
     * initialize UI elements here
     */
    void initUI();

    /**
     * initialize events here
     */
    void initEvent();

    /**
     * insert dummy data or call networking methods here
     */
    void initData(@Nullable Bundle savedInstanceState);

}
