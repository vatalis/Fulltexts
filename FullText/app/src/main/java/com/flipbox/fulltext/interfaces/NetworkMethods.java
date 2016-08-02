package com.flipbox.fulltext.interfaces;

/**
 * Created by bukhorimuhammad on 4/15/16.
 * using generic to pass on success handler
 */
public interface NetworkMethods<T>
{
    /**
     * implement your loading method here
     */
    void startLoading();

    /**
     * and finish it
     */
    void finishLoading();

    /**
     * called when request is successfully called ( request code 200 )
     */
    void onSuccess(T result);

    /**
     * called when request is returning != 200 OK
     *
     * @param message
     */
    void onError(String message);

    /**
     * called when request get cancelled
     */
    void onCancelled();
}
