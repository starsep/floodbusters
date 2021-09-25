package org.example.floodbusters.dataholder;

public class WarningItemDataHolder {

    private int warningColor;
    private String warningShortText;
    private String warningLongText;

    public WarningItemDataHolder(int color, String shortText, String longText) {
        this.warningColor = color;
        this.warningShortText = shortText;
        this.warningLongText = longText;
    }

    public int getWarningColor() {
        return warningColor;
    }

    public void setWarningColor(int warningColor) {
        this.warningColor = warningColor;
    }

    public String getWarningShortText() {
        return warningShortText;
    }

    public void setWarningShortText(String warningShortText) {
        this.warningShortText = warningShortText;
    }

    public String getWarningLongText() {
        return warningLongText;
    }

    public void setWarningLongText(String warningLongText) {
        this.warningLongText = warningLongText;
    }
}
