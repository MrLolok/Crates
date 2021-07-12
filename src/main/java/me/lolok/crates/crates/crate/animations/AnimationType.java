package me.lolok.crates.crates.crate.animations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AnimationType {
    GLASSES_ROTATION(new GlassesRotationAnimation()),
    QUICK(new QuickAnimation());

    @Getter
    private final Animation animation;
}
