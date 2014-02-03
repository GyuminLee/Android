package com.loopj.android.http.sample;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

public class GetSample extends SampleParentActivity {
    private static final String LOG_TAG = "GetSample";

    @Override
    protected void executeSample(AsyncHttpClient client, String URL, Header[] headers, HttpEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.get(this, URL, headers, null, responseHandler);
    }

    @Override
    protected int getSampleTitle() {
        return R.string.title_get_sample;
    }

    @Override
    protected boolean isRequestBodyAllowed() {
        return false;
    }

    @Override
    protected boolean isRequestHeadersAllowed() {
        return true;
    }

    @Override
    protected String getDefaultURL() {
        return "https://httpbin.org/get";
    }

    @Override
    protected AsyncHttpResponseHandler getResponseHandler() {
        return new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                clearOutputs();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugResponse(LOG_TAG, new String(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugThrowable(LOG_TAG, e);
                if (errorResponse != null) {
                    debugResponse(LOG_TAG, new String(errorResponse));
                }
            }
        };
    }
}
