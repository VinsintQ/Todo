package com.HW.Todo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"password","userProfile","recipeList","categoryList"})
@Entity
@Table(name="users")
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;

    @Column(unique = true)
    private String emailAddress;


    @JsonProperty(access =JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "profile_id",referencedColumnName = "id")
    private String password;


    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Item> itemList;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Category> categoryList;

    @JsonIgnore
    public String getPassword(){
        return password;
    }


}