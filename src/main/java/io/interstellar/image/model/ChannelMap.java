package io.interstellar.image.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.Set;

public enum ChannelMap {

    VISIBLE(4, 3, 2),

    VEGETATION(5, 6, 7),

    WATER_VAPOR(null, null, 9);

    private static final Set<ChannelMap> VALUES = EnumSet.allOf(ChannelMap.class);

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

    public Collection<Integer> channels() {
        final Collection<Integer> result = new LinkedList<>();

        if (red != null) {
            result.add(red);
        }
        if (green != null) {
            result.add(green);
        }
        if (blue != null) {
            result.add(blue);
        }

        return result;
    }

    @JsonCreator
    @Nonnull
    public static ChannelMap fromString(@Nonnull final String value) {
        for (final ChannelMap channelMap : VALUES) {
            if (channelMap.name().equalsIgnoreCase(value)) {
                return channelMap;
            }
        }

        throw new IllegalArgumentException(String.format("Unknown channel map value: '%s'", value));
    }

}
