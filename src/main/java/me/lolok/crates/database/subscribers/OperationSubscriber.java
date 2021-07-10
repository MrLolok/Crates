package me.lolok.crates.database.subscribers;

import org.reactivestreams.Subscription;

public class OperationSubscriber<T> extends ObservableSubscriber<T> {
    @Override
    public void onSubscribe(Subscription subscription) {
        super.onSubscribe(subscription);
        subscription.request(Integer.MAX_VALUE);
    }
}
