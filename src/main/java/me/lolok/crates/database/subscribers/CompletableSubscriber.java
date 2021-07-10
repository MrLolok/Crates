package me.lolok.crates.database.subscribers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class CompletableSubscriber<T, V> extends OperationSubscriber<T> {
    @Getter
    private CompletableFuture<V> result;
    private final Supplier<V> runnable;

    @Override
    public void onComplete() {
        result.complete(runnable.get());
    }
}
