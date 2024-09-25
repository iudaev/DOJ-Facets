package com.company.facets.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum Destinations implements EnumClass<String> {

    LONDON("A"),
    ROME("B"),
    CAIRO("C"),
    BERLIN("E"),
    DELHI("F"),
    BEIJING("H"),
    OTTAWA("I");

    private final String id;

    Destinations(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Destinations fromId(String id) {
        for (Destinations at : Destinations.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}