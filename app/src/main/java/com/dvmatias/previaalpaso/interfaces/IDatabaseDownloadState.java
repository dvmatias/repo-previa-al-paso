package com.dvmatias.previaalpaso.interfaces;

/**
 * Created by dvmatias on 17/09/17.
 */

public interface IDatabaseDownloadState {

    public void onLoadingStarted();
    public void onLoadingCompleted();
    public void onLoadingFailed();
}
