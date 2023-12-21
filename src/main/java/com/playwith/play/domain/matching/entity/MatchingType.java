package com.playwith.play.domain.matching.entity;

public enum MatchingType {
    TYPE_1("type_1", "용병 매칭"),
    TYPE_2("type_2", "팀 매칭");

    private final String value;
    private final String label;

    MatchingType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
