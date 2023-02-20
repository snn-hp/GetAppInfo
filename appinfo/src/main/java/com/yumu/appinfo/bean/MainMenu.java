package com.yumu.appinfo.bean;

/**
 * @author sunan
 * @date 2023/2/20 11:45
 */
public class MainMenu {
    private String title;
    private int res_id;
    private String type;
    private String description;


    public MainMenu() {

    }

    public MainMenu(String title, int res_id, String type) {
        this.res_id = res_id;
        this.title = title;
        this.type = type;
    }

    public MainMenu(String title, int res_id, String type, String description) {
        this.res_id = res_id;
        this.title = title;
        this.type = type;
        this.description = description;
    }

    public MainMenu(String title, String type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRes_id() {
        return res_id;
    }

    public void setRes_id(int res_id) {
        this.res_id = res_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
