package com.example.android.accenturefeed;

class Items{

    String itemtitle;
    String itemid;
    String pubdate;
    String itemlink;
    String itemdesc;

    public String getItemTitle(){
        return itemtitle;
    }
    public void setItemTitle(String Ititle){
        this.itemtitle=Ititle;
    }
    public String getItemId(){
        return itemid;
    }
    public void setItemId(String Iitem){
        this.itemid=Iitem;
    }
    public String getItemPubdate(){
        return pubdate;
    }
    public void setItemPubdate(String itempubdate){
        this.pubdate=itempubdate;
    }
    public String getItemlink(){
        return itemlink;
    }
    public void setItemLink(String link){
        this.itemlink=link;
    }
    public String getItemDesc(){
        return itemdesc;
    }
    public void setItemDesc(String desc){
        this.itemdesc=desc;
    }


}