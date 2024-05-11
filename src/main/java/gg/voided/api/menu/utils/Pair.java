package gg.voided.api.menu.utils;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Pair<X, Y> {
    private final X x;
    private final Y y;
}
