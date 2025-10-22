package com.springboot.project.model;

public class BookDo {
    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private String content;
    private int price;
    private String imageUrl;
    private long sellerId;
    private String sellerName;
    
    // --- 책 상태 정보 필드 추가 ---
    private boolean underline_pencil;
    private boolean underline_pen;
    private boolean writing_pencil;
    private boolean writing_pen;
    private boolean cover_clean;
    private boolean no_name;
    private boolean no_discoloration;
    private boolean no_damage;
    
    // --- Getter and Setter methods ---
    
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    

    // --- 책 상태 정보 Getter/Setter 추가 ---
    
    public boolean isUnderline_pencil() { return underline_pencil; }
    public void setUnderline_pencil(boolean underline_pencil) { this.underline_pencil = underline_pencil; }
    public boolean isUnderline_pen() { return underline_pen; }
    public void setUnderline_pen(boolean underline_pen) { this.underline_pen = underline_pen; }
    public boolean isWriting_pencil() { return writing_pencil; }
    public void setWriting_pencil(boolean writing_pencil) { this.writing_pencil = writing_pencil; }
    public boolean isWriting_pen() { return writing_pen; }
    public void setWriting_pen(boolean writing_pen) { this.writing_pen = writing_pen; }
    public boolean isCover_clean() { return cover_clean; }
    public void setCover_clean(boolean cover_clean) { this.cover_clean = cover_clean; }
    public boolean isNo_name() { return no_name; }
    public void setNo_name(boolean no_name) { this.no_name = no_name; }
    public boolean isNo_discoloration() { return no_discoloration; }
    public void setNo_discoloration(boolean no_discoloration) { this.no_discoloration = no_discoloration; }
    public boolean isNo_damage() { return no_damage; }
    public void setNo_damage(boolean no_damage) { this.no_damage = no_damage; }
    
    public long getSellerId() { return sellerId; }
    public void setSellerId(long sellerId) { this.sellerId = sellerId; }
    
    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }

}