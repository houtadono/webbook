package com.example.webbook.entity;

import java.util.ArrayList;

public class Cart {
    ArrayList<Item> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    
    public int getQuantityById(int id){
        return getItemById(id).getQuantity();
    }
    private Item getItemById(int id){
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
            m.setQuantity(quantity_new);          
        }
        else{
            items.add(t);
        }
    }
    public void removeItem(int id){
        if (getItemById(id)!=null){
            items.remove(getItemById(id));
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

}
