package com.gmail.evanloafakahaitao.store.servlets.model;

public enum CommandEnum {
    LOGIN("/dispatcher?command=login"),
    USERS("/dispatcher?command=users"),
    ITEMS("/dispatcher?command=items"),
    LOAD_ITEMS("/dispatcher?command=load_items");

    private final String url;

    CommandEnum(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static CommandEnum getCommand(String command) {
        try {
            return CommandEnum.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.printf("Command %s not found%n", command.toUpperCase());
            e.printStackTrace();
        }
        return null;
    }
}
