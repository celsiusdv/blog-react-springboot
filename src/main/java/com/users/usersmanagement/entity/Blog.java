package com.users.usersmanagement.entity;

import javax.persistence.*;

@Table(name = "blogs")
@Entity(name = "Blog")
public class Blog {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "blog_id")
    private Integer blogId;

    @Column(columnDefinition = "TEXT")
    private String post;

    @ManyToOne
    @JoinColumn(name = "user_id_junction", referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name ="user_id_fk" ))
    private User user;

    public Blog(){}
    public Blog(String post){ this.post=post; }
    public Blog(User user){ this.user=user; }

    public void setPost(String post){this.post=post;}
    public void setUser(User user){ this.user=user;}

    public Integer getBlogId(){return blogId;}
    public String getPost(){return post;}
    public User getUser(){ return user;}

}
