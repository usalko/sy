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
//
//package io.github.usalko.sy.sqlite3;
//
//import org.hibernate.MappingException;
//import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
//
//public class SQLite3IdentityColumnSupport extends IdentityColumnSupportImpl {
//
//    @Override
//    public boolean supportsIdentityColumns() {
//        return true;
//    }
//
//    @Override
//    public boolean hasDataTypeInIdentityColumn() {
//        return false;
//    }
//
//    @Override
//    public String getIdentitySelectString(String table, String column, int type)
//            throws MappingException {
//        return "select last_insert_rowid()";
//    }
//
//    @Override
//    public String getIdentityColumnString(int type) throws MappingException {
//        return "integer";
//    }
//}