package com.cs.pojo;

/**
 * Created by smi1e
 * Date 2019/5/24 10:02
 * Description
 */
public enum BookType {
    //书籍类型
    EDUCATE(1,"教育"),
    NOVEL(2,"小说"),
    ART(3,"艺术"),
    LITERATURE(4,"文学"),
    ANIMATION(5,"动漫"),
    CHILDREN(6,"童书"),
    SOCIALSCIENCE(7,"人文社科"),
    LIFE(8,"生活"),
    SCIENCEANDTECHNOLOGY(9,"科技"),
    ENGLISH(10,"英文"),
    PERIODICAL(11,"期刊"),
    SUCCESS(12,"成功/励志"),
    EXAMINATION(13,"考试"),
    EBOOK(14,"电子书"),
    BIOGRAPHT(15,"传记")
    ;

    private int typeId;
    private String typeName;

    BookType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public static BookType getTypeIdByName(String typeName){
        for (BookType bookType : BookType.values()){
            if (bookType.typeName.equals(typeName))
                return bookType;
        }
        return null;
    }
}
