/*
 * Copyright (c) 2016. Sunghyouk Bae <sunghyouk.bae@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package debop4k.data

import debop4k.core.utils.isNotNull

/**
 * JdbcDrivers
 * @author debop sunghyouk.bae@gmail.com
 */
object JdbcDrivers {

  /**
   * H2 DataSource class name
   */
  @JvmField val DATASOURCE_CLASS_H2 = "org.h2.jdbcx.JdbcDataSource"
  /**
   * H2 Jdbc driver class name
   */
  @JvmField val DRIVER_CLASS_H2 = "org.h2.Driver"
  /**
   * H2 hibernate dialect
   */
  @JvmField val DIALECT_H2 = "org.hibernate.dialect.H2Dialect"

  /**
   * hsqldb DB DataSource class name
   */
  @JvmField val DATASOURCE_CLASS_HSQL = "org.hsqldb.jdbc.JDBCDataSource"
  /**
   * hsqldb Jdbc driver class name
   */
  @JvmField val DRIVER_CLASS_HSQL = "org.hsqldb.jdbc.JDBCDriver"
  /**
   * hsqldb hibernate dialect
   */
  @JvmField val DIALECT_HSQL = "org.hibernate.dialect.HSQLDialect"

  /**
   * MySQL DB DataSource class name
   */
  @JvmField val DATASOURCE_CLASS_MYSQL = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource"
  /**
   * MySQL Jdbc driver class name
   */
  @JvmField val DRIVER_CLASS_MYSQL = "com.mysql.cj.jdbc.Driver"
  /**
   * MySQL hibernate dialect
   */
  @JvmField val DIALECT_MYSQL = "org.hibernate.dialect.MySQL5InnoDBDialect"

  /**
   * Maria DB DataSource class name
   */
  @JvmField val DRIVER_CLASS_MARIADB = "org.mariadb.jdbc.Driver"

  /**
   * PostgreSql DB DataSource class name
   */
  @JvmField val DATASOURCE_CLASS_POSTGRESQL = "org.postgresql.ds.PGSimpleDataSource"
  /**
   * PostgreSql Jdbc driver class name
   */
  @JvmField val DRIVER_CLASS_POSTGRESQL = "org.postgresql.Driver"
  /**
   * PostgreSql hibernate dialect for Postgresql 9.4 or higher
   */
  @JvmField val DIALECT_POSTGRESQL = "org.hibernate.dialect.PostgreSQL94Dialect"
  /**
   * PostgreSql hibernate dialect for Postgresql 9.0 or higher
   */
  @JvmField val DIALECT_POSTGRESQL9 = "org.hibernate.dialect.PostgreSQL9Dialect"
  /**
   * PostgreSql hibernate dialect for Postgresql 8.2 or higher
   */
  @JvmField val DIALECT_POSTGRESQL82 = "org.hibernate.dialect.PostgreSQL82Dialect"


  /**
   * Oracle DB DataSource class name
   */
  @JvmField val DATASOURCE_CLASS_ORACLE = "oracle.jdbc.pool.OracleDataSource"
  /**
   * Oracle Jdbc driver class name
   */
  @JvmField val DRIVER_CLASS_ORACLE = "oracle.jdbc.driver.OracleDriver"
  /**
   * hibernate dialect for Oracle 12c or higher
   */
  @JvmField val DIALECT_ORACLE12 = "org.hibernate.dialect.Oracle12cDialect"
  /**
   * hibernate dialect for Oracle 9i or higher
   */
  @JvmField val DIALECT_ORACLE9i = "org.hibernate.dialect.Oracle9iDialect"
  /**
   * hibernate dialect for Oracle 10g or higher
   */
  @JvmField val DIALECT_ORACLE10g = "org.hibernate.dialect.Oracle10gDialect"

  /** driverClass 가 MySQL 인가? */
  @JvmStatic
  fun isMySQL(driverClassName: String? = null): Boolean {
    return driverClassName.isNotNull() &&
           (driverClassName == DRIVER_CLASS_MYSQL ||
            driverClassName == DRIVER_CLASS_MARIADB)
  }

  /** driverClass 가 PostgreSQL 인가? */
  @JvmStatic
  fun isPostgreSQL(driverClassName: String? = null): Boolean {
    return driverClassName.isNotNull() && driverClassName == DRIVER_CLASS_POSTGRESQL
  }
}