package com.codingchallenge.subscriptionsystem.persistenceservice.model;

import com.codingchallenge.subscriptionsystem.common.model.IUser;
import com.codingchallenge.subscriptionsystem.common.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity implements IUser {

    @Column(name = "userEmail")
    @Id private String email;
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;
    @Column(name = "consent")
    private Boolean consent;
    @Column(name ="firstName")
    private String firstName;
    @Column(name ="gender")
    private Gender gender;

    public UserEntity(User user){
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
        this.firstName = user.getFirstName();
        this.gender = user.getGender();
    }

    public User toUser(){
        return new User(this.email, this.birthDate, this.consent, this.firstName, this.gender);
    }

}
