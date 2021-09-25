package org.example.floodbusters.dataholder;

public class WarningItemDataHolder {

    private String warningColor;
    private String warningShortText;
    private String warningLongText;

    public WarningItemDataHolder(String color, String shortText, String longText) {
        this.warningColor = color;
        this.warningShortText = shortText;
        this.warningLongText = longText;
    }

    public String getWarningColor() {
        return warningColor;
    }

    public void setWarningColor(String warningColor) {
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
