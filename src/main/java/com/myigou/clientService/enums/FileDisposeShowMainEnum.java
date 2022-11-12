package com.myigou.clientService.enums;

/**
 * 配置文件key标识
 * fileDispose.showMain
 */
public enum FileDisposeShowMainEnum {

    collect("文件归集", "file_collect"),
    search("文件夹搜索", "file_search"),
    rename("批量文件重命名", "batch_file_rename"),
    folder("创建文件夹", "create_folder"),
    ;

    private String name;
    private String keyValue;

    FileDisposeShowMainEnum(String name, String keyValue) {
        this.name = name;
        this.keyValue = keyValue;
    }

    public String getName() {
        return name;
    }

    public String getKeyValue() {
        return keyValue;
    }

    /**
     * 值
     *
     * @param name
     * @return
     */
    public static String getKeyValue(String name) {
        FileDisposeShowMainEnum[] enums = FileDisposeShowMainEnum.values();
        for (FileDisposeShowMainEnum it : enums) {
            if (name.equals(it.getName())) return it.getKeyValue();
        }
        return "";
    }

    /**
     * 获取颜色值
     *
     * @param keyValue
     * @return
     */
    public static String getName(String keyValue) {
        FileDisposeShowMainEnum[] enums = FileDisposeShowMainEnum.values();
        for (FileDisposeShowMainEnum it : enums) {
            if (keyValue.equals(it.getKeyValue())) return it.getName();
        }
        return "";
    }
}
