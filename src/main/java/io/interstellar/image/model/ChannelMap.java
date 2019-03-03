package io.interstellar.image.model;

import javax.annotation.Nullable;

public enum ChannelMap {

    VISIBLE(4, 3, 2),

    VEGETATION(5, 6, 7),

    WATER_VAPOR(null, null, 9);

    private final Integer red;

    private final Integer green;

    private final Integer blue;

    ChannelMap(final Integer red, final Integer green, final Integer blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Nullable
    public Integer red() {
        return red;
    }

    @Nullable
    public Integer green() {
        return green;
    }

    @Nullable
    public Integer blue() {
        return blue;
    }

}
