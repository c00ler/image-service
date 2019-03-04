package io.interstellar.image.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.CaseFormat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Set;

public enum ChannelMap {

    VISIBLE(4, 3, 2),

    VEGETATION(5, 6, 7),

    WATER_VAPOR(null, null, 9);

    private static final Set<ChannelMap> VALUES = EnumSet.allOf(ChannelMap.class);

    private final String value;

    private final Integer red;

    private final Integer green;

    private final Integer blue;

    ChannelMap(final Integer red, final Integer green, final Integer blue) {
        this.value = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL).convert(name());

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

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    @Nonnull
    public static ChannelMap fromString(@Nonnull final String value) {
        for (final ChannelMap channelMap : VALUES) {
            if (channelMap.getValue().equals(value)) {
                return channelMap;
            }
        }

        throw new IllegalArgumentException(String.format("Unknown channel map value: '%s'", value));
    }

}
