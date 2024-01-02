package com.example.webbook.entity;

import java.util.ArrayList;

public class Cart implements Cloneable {
    private int uid;
    private ArrayList<Item> items;
    private int size;
    private int totalMoney;
    private int oid; 

    public int getUid() {
        return uid;
    }

    @Override
    public String toString() {
        return "Cart [uid=" + uid + ", items=" + items + ", size=" + size + ", totalMoney=" + totalMoney + ", oid="
                + oid + "]";
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setUid(int i) {
        this.uid = i;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Cart() {
        items = new ArrayList<>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    
    public int getQuantityById(int id){
        return getItemById(id).getQuantity();
    }
    public Item getItemById(int id){
        for (Item i : items) {
            if (i.getBook().getId()==id){
                return i;
            }
        }
        return null;
    }

    public void addItem(Item t){
        if (getItemById(t.getBook().getId())!=null){
            Item m = getItemById(t.getBook().getId());
            int max_quantity = t.getBook().getQuantity();
            int quantity_new = m.getQuantity()+t.getQuantity();
            if (quantity_new> max_quantity) {
                quantity_new = max_quantity;
            }
            this.size +=  quantity_new - m.getQuantity();
            this.totalMoney += m.getPrice()*(quantity_new - m.getQuantity());
            m.setQuantity(quantity_new);
        }
        else{
            this.totalMoney += t.getPrice()*t.getQuantity();
            items.add(t);    
            this.size += t.getQuantity();
        }
    }
    public void removeItem(int id){
        if (getItemById(id)!=null){
            Item i = getItemById(id);
            items.remove(i);
            size -= i.getQuantity();
            totalMoney -= i.getPrice()*i.getQuantity();
        }
    }

    private Book getBookById(int id,ArrayList<Book> list){
        for (Book i : list) {
            if (i.getId()==id){
                return i;
            }
        }
        return null;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

    @Override
    public boolean equals(Object arg0) {
        // TODO Auto-generated method stub
        return super.equals(arg0);
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }
   
}
