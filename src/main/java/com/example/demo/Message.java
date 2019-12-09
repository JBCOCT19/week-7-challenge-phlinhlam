package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="Message_Data")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="mess_id")
    private long id;

    @NotNull
    @Size(min = 3)
    @Column(name="title")
    private String title;

    @NotNull
    @Size(min = 3)
    @Column(name="content")
    private String content;
    @Column(name="date")
    private String datePosted;
    @Column(name="time")
    private String timePosted;
    @Column(name="photo")
    private String photo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    public User user;

    public Message() {
    }

    public Message(@NotNull @Size(min = 3) String title, @NotNull @Size(min = 3) String content, String datePosted, String timePosted, String photo, User user) {
        this.title = title;
        this.content = content;
        this.datePosted = datePosted;
        this.timePosted = timePosted;
        this.photo = photo;
        this.user = user;
    }

    public Message(@NotNull @Size(min = 3) String title, @NotNull @Size(min = 3) String content, String datePosted) {
        this.title = title;
        this.content = content;
        this.datePosted = datePosted;
    }

    public Message(@NotNull @Size(min = 3) String title, @NotNull @Size(min = 3) String content, String datePosted, String photo) {
        this.title = title;
        this.content = content;
        this.datePosted = datePosted;
        this.photo = photo;
    }

    public Message(@NotNull @Size(min = 3) String title, @NotNull @Size(min = 3) String content, String datePosted, User user) {
        this.title = title;
        this.content = content;
        this.datePosted = datePosted;
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(String timePosted) {
        this.timePosted = timePosted;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
