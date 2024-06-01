package com.myigou.clientService.enums;

/**
 * Created by ab1324ab on 2018/4/3.
 */
public enum ColorEnum {

    red("红","255,51,51"),
    orange("橙","255,184,17"),
    green("绿","0,128,0"),
    young("青","8,214,196"),
    blue("蓝","10,117,230"),
    purple("紫","158,10,230"),
    black("黑","68,68,68"),
    powder("粉","243,63,227"),
    ;

    private String color;
    private String value;

    ColorEnum(String color, String value) {
        this.color = color;
        this.value = value;
    }

    /**
     * 获取颜色值
     * @param color
     * @return
     */
    public static String getColorValue(String color) {
         ColorEnum[] colorEna = ColorEnum.values();
        for(ColorEnum colorEnum : colorEna){
            if (color.equals(colorEnum.getColor())){
                return colorEnum.getValue();
            }
        }
        return "";
    }

    /**
     * 颜色个数
     * @return
     */
    public static int getColorCount(){
        return ColorEnum.values().length;
    }

    /**
     * 获取颜色显示
     * @param count
     * @return
     */
    public static String getColor(int count){
       return ColorEnum.values()[count].getColor();
    }

    public String getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }
}
