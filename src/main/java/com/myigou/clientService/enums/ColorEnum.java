package com.myigou.clientService.enums;

/**
 * Created by ab1324ab on 2018/4/3.
 */
public enum ColorEnum {

    red("红","1"),
    orange("橙","2"),
    green("绿","3"),
    young("青","4"),
    blue("蓝","5"),
    purple("紫","6"),
    black("黑","7"),
    powder("粉","8");

    private String color;
    private String value;

    ColorEnum(String color, String value) {
        this.color = color;
        this.value = value;
    }

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
