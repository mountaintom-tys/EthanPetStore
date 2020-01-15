package com.petstore.entity;

import org.springframework.util.StringUtils;

public class Goods {
    private Integer id;
    private String name;
    private String cover;
    private Integer price;
    private String intro;
    private Integer stock;
    private Integer typeId;
    private Types type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name = name == null ? null : name.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover = cover == null ? null : cover.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro = intro == null ? null : intro.trim();
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;//地址相等
        }
        if(obj == null){
            return false;//非空性：对于任意非空引用x，x.equals(null)应该返回false。
        }
        if(obj instanceof Goods){
            Goods other = (Goods) obj;
            //需要比较的字段相等，则这两个对象相等
            if(equalsStr(this.name, other.name)
                    && equalsStr(this.cover, other.cover)&&equalsStr(this.intro,other.intro)
                    &&this.price.equals(other.price)&&this.stock.equals(other.stock)&&this.typeId.equals(other.typeId)){
                return true;
            }
        }
        return false;
    }
    private boolean equalsStr(String str1, String str2){
        if(StringUtils.isEmpty(str1) && StringUtils.isEmpty(str2)){
            return true;
        }
        if(!StringUtils.isEmpty(str1) && str1.equals(str2)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result=17;
        result=31*result+(name==null?0:name.hashCode());
        result=31*result+(cover==null?0:cover.hashCode());
        result=31*result+(intro==null?0:intro.hashCode());
        result=31*result+(price==null?0:price.hashCode());
        result=31*result+(stock==null?0:stock.hashCode());
        result=31*result+(typeId==null?0:typeId.hashCode());
        return result;
    }
}
