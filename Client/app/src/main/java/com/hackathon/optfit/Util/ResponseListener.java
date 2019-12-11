package com.hackathon.optfit.Util;

public interface ResponseListener<TEntity> {
    public void onComplete(TEntity entity);
}
