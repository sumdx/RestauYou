package com.example.restauyou.ModelClass;

public class MenuFilter {
    private String category;
    private boolean selected;
    public MenuFilter(String category, boolean selected) {
        this.category = category;
        this.selected = selected;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
