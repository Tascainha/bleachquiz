package com.bleachquiz.bleachdle.game;

public record ClassicComparisonDto(
        MatchStatus race,
        MatchStatus gender,
        MatchStatus abilities,
        ValueTrend firstAppearanceValue,
        MatchStatus status,
        MatchStatus height,
        ValueTrend heightTrend) {
}
