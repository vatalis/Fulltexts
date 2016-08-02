package com.flipbox.fulltext.interfaces;

/**
 * Created by Irvan on 3/11/2016.
 */
public interface ICallback<T>
{
    void onEmpty(T result);

    void onError(Exception e);

    void onFalseReturn(T result);

    void onSuccess(T result);
}