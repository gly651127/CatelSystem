package com.catel.views.popuJarLib.popuJar;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class PopuItem {
    private Drawable icon;
    private String title;
    private int actionId;
    private boolean selected;
    private boolean sticky;
    private int textColor = Color.BLACK;
    private int textSize = 16;

    /**
     * Constructor
     *
     * @param actionId Action id for case statements
     * @param title    Title
     * @param icon     Icon to use
     */
    public PopuItem(int actionId, String title, Drawable icon) {
        this.title = title;
        this.icon = icon;
        this.actionId = actionId;
    }

    /**
     * Constructor
     */
    public PopuItem() {
        this(-1, null, null);
    }

    /**
     * Constructor
     *
     * @param actionId Action id of the item
     * @param title    Text to show for the item
     */
    public PopuItem(int actionId, String title, int textColor, int textSize) {
        this.title = title;
        this.textColor = textColor;
        this.textSize = textSize;
    }

    public PopuItem(String title, int actionId, int textColor, int textSize) {
        super();
        this.title = title;
        this.actionId = actionId;
        this.textColor = textColor;
        this.textSize = textSize;
    }

    public PopuItem(int actionId, String title) {
        super();
        this.title = title;
        this.actionId = actionId;
    }

    /**
     * Constructor
     *
     * @param icon {@link Drawable} action icon
     */
    public PopuItem(Drawable icon) {
        this(-1, null, icon);
    }

    /**
     * Constructor
     *
     * @param actionId Action ID of item
     * @param icon     {@link Drawable} action icon
     */
    public PopuItem(int actionId, Drawable icon) {
        this(actionId, null, icon);
    }

    /**
     * Set action title
     *
     * @param title action title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get action title
     *
     * @return action title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set action icon
     *
     * @param icon {@link Drawable} action icon
     */
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    /**
     * Get action icon
     *
     * @return {@link Drawable} action icon
     */
    public Drawable getIcon() {
        return this.icon;
    }

    /**
     * Set action id
     *
     * @param actionId Action id for this action
     */
    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    /**
     * @return Our action id
     */
    public int getActionId() {
        return actionId;
    }

    /**
     * Set sticky status of button
     *
     * @param sticky true for sticky, pop up sends event but does not disappear
     */
    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    /**
     * @return true if button is sticky, menu stays visible after press
     */
    public boolean isSticky() {
        return sticky;
    }

    /**
     * Set selected flag;
     *
     * @param selected Flag to indicate the item is selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Check if item is selected
     *
     * @return true or false
     */
    public boolean isSelected() {
        return this.selected;
    }


    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

}