package com.androprogrammer.dagger2sample.domain.listeners;

public interface RequestListener {

    void onSuccess(int id, String response);

    void onError(int id, String message);

    void onStartLoading(int id);
    void onStopLoading(int id);
}
