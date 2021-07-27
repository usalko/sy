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
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//
//@Entity
//@Cacheable
//@Table(name = "geometry_shapes",
//        uniqueConstraints = @UniqueConstraint(columnNames = {"mnemonic"}))
//public class GeometryShape {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotNull(message = "GeometryShape mnemonic is required.")
//    @Basic(optional = false)
//    private String mnemonic;
//
//    public GeometryShape(Long id, @NotNull(message = "GeometryShape mnemonic is required.") String mnemonic) {
//        this.id = id;
//        this.mnemonic = mnemonic;
//    }
//
//    public GeometryShape() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getMnemonic() {
//        return mnemonic;
//    }
//
//    public void setMnemonic(String mnemonic) {
//        this.mnemonic = mnemonic;
//    }
//}
