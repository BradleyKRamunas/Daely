package com.bramuna.daely.data;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class Response<T> {

    private final Status status;

    @Nullable
    private final T data;

    @Nullable
    private final Throwable error;

    public Response(Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }
}
