package me.lolok.crates.database.subscribers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExistSubscriber extends OperationSubscriber<String> {
    private final String name;
    @Getter
    private boolean exist = false;

    public void onNext(String string) {
        if (string.equalsIgnoreCase(name))
            exist = true;
    }
}
