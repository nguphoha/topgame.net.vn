/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.entities;

import java.io.Serializable;

/**
 *
 * @author TOM
 */
public class Pagination implements Serializable {

    private String label;
    private int value;
    private boolean render;
    private boolean disable;
    private String style;

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public String toString() {
        return "Pagination{" + "label=" + label + ", value=" + value + ", render=" + render + ", disable=" + disable + ", style=" + style + '}';
    }
    
}
