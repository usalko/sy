//// Sy (Share your mood with anyone)
//// Copyright (C) July 2021 Ivan Usalko <ivict@rambler.ru>
////
//// This program is free software: you can redistribute it and/or modify
//// it under the terms of the GNU General Public License as published by
//// the Free Software Foundation, either version 3 of the License, or
//// (at your option) any later version.
////
//// This program is distributed in the hope that it will be useful,
//// but WITHOUT ANY WARRANTY; without even the implied warranty of
//// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//// GNU General Public License for more details.
////
//// You should have received a copy of the GNU General Public License
//// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//package io.github.usalko.sy.model;
//
//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//
//import javax.persistence.*;
//import javax.validation.Valid;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "tokens")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "tokenOwnMoods")
//public class Token {
//
//    @Id
//    private String id;
//
//    private LocalDateTime created;
//
//    @OneToMany(mappedBy = "pk.token", fetch = FetchType.LAZY)
//    @Valid
//    private List<TokenOwnMood> tokenOwnMoods = new ArrayList<>();
//
//    public Token(String id) {
//        this.id = id;
//        this.created = LocalDateTime.now();
//    }
//
//    public Token() {
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public LocalDateTime getCreated() {
//        return created;
//    }
//
//    public void setCreated(LocalDateTime created) {
//        this.created = created;
//    }
//
//    public List<TokenOwnMood> getTokenOwnMoods() {
//        return tokenOwnMoods;
//    }
//
//    public void setTokenOwnMoods(List<TokenOwnMood> tokenOwnMoods) {
//        this.tokenOwnMoods = tokenOwnMoods;
//    }
//}
