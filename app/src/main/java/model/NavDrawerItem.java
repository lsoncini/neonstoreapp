package model;

public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private int icon;
    private String counter = "0";
    private boolean hasCounter = false;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, int icon) {
        this.showNotify = showNotify;
        this.title = title;
        this.icon = icon;
    }

    public NavDrawerItem(boolean showNotify, String title, int icon, boolean hasCounter, String counter) {
        this.showNotify = showNotify;
        this.title = title;
        this.icon = icon;
        this.hasCounter = hasCounter;
        this.counter = counter;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getCounter(){
        return counter;
    }

    public void setCounter(String counter){
        this.counter=counter;
    }

    public boolean getHasCounter(){
        return hasCounter;
    }

    public void setHasCounter(boolean hasCounter){
        this.hasCounter = hasCounter;
    }
}