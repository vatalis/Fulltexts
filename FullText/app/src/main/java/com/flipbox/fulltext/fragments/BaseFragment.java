package com.flipbox.fulltext.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.flipbox.fulltext.consts.S;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.HashMap;
import java.util.Map;

import io.socket.client.Socket;

/**
 * Created by bukhorimuhammad on 4/14/16.
 */
public abstract class BaseFragment extends Fragment
{

    protected Map ionParams;
    protected ProgressDialog dialog;
    protected Socket mSocket;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ionParams = new HashMap();
        dialog = new ProgressDialog(getContext());
    }

    protected void launchIon(final Map ionParam)
    {
        //local variables
        String header = (String) ionParam.get(S.ion_param_header);
        Context ctx = (Context) ionParam.get(S.ion_param_context);
        String method = (String) ionParam.get(S.ion_param_method);
        FutureCallback<Response<String>> callback = (FutureCallback<Response<String>>) ionParam
                .get(S.ion_param_callback);

        Ion.with(ctx)
           .load(method, (String) ionParam.get(S.ion_param_url))
           .setHeader(S.ion_header, header)
           .addHeader("Content-Length", "0") // content length for image upload?
           .addHeader("Cache-Control", "max-age=10000") // request cache control header
           .setLogging(S.ion_log, Log.DEBUG)
           // TODO: 4/10/16 HANDLE TIMEOUT
           .setTimeout(50 * 1000) //set timeout to 50 seconds
           .asString()
           .withResponse()
           .setCallback(callback);

    }

    protected void showLoading(String message)
    {
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.show();
    }

    protected void hideLoading()
    {
        if (dialog.isShowing())
        {
            dialog.dismiss();
        }
    }

    protected void hideKeyboard()
    {
        View view = getActivity().getCurrentFocus();
        if (view != null)
        {
            ((InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected int getPropertyTypeID(String idx, String type)
    {
        if (idx.equals(S.idx_sale))
        {
            return type.equals(S.type_house) ? 1 :
                   type.equals(S.type_shop_house) ? 3 :
                   type.equals(S.type_apartment) ? 5 :
                   type.equals(S.type_warehouse) ? 7 :
                   type.equals(S.type_office) ? 9 :
                   type.equals(S.type_land) ? 11 : 0;
        }
        else if (idx.equals(S.idx_rent))
        {
            return type.equals(S.type_house) ? 2 :
                   type.equals(S.type_shop_house) ? 4 :
                   type.equals(S.type_apartment) ? 6 :
                   type.equals(S.type_warehouse) ? 8 :
                   type.equals(S.type_office) ? 10 : 0;
        }
        else
        {
            return 99;
        }
    }

    protected boolean isRequestSuccess(Response<String> response)
    {
        if (response == null)
        {
            return false;
        }
        else
        {
            String result = response.getResult();
            if (response.getResult() == null)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    protected boolean isRequestOrigin(Response<String> response, String origin)
    {
        AsyncHttpRequest request = response.getRequest();
        String urlServiceParam = request.getUri().toString();

        return urlServiceParam.contains(origin);
    }


}
