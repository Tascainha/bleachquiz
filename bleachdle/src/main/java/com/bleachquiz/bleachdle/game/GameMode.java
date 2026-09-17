package com.bleachquiz.bleachdle.game;

public enum GameMode {
    DESCRIPTION(0),
    PHOTO(57),
    CLASSIC(113);

    private final int offset;

    GameMode(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }
}
