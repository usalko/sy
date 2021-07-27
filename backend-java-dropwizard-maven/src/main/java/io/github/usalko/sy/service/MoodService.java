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
//package io.github.usalko.sy.service;
//
//import io.github.usalko.sy.model.OwnMood;
//import io.github.usalko.sy.model.SharedMood;
//import io.github.usalko.sy.model.Token;
//import org.springframework.validation.annotation.Validated;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//
//@Validated
//public interface MoodService {
//
//    @NotNull Iterable<SharedMood> getSharedMoods(int limit);
//
//    @NotNull Iterable<OwnMood> getOwnMoods(@NotNull(message = "The token cannot be null.") @Valid Token token,
//                                           int limit);
//
//    void share(@NotNull(message = "The mood cannot be null.") @Valid SharedMood mood);
//
//    void keep(@NotNull(message = "The token cannot be null.") @Valid Token token,
//              @NotNull(message = "The mood cannot be null.") @Valid OwnMood mood);
//}