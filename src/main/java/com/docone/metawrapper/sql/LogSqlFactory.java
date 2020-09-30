package com.docone.metawrapper.sql;

import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.core.statement.StatementCustomizer;
import org.jdbi.v3.sqlobject.customizer.SqlStatementCustomizer;
import org.jdbi.v3.sqlobject.customizer.SqlStatementCustomizerFactory;
import org.jdbi.v3.sqlobject.customizer.SqlStatementCustomizingAnnotation;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SqlStatementCustomizingAnnotation(LogSqlFactory.Factory.class)
public @interface LogSqlFactory {

    Logger LOGGER = Logger.getLogger(LogSqlFactory.class.getName());

    class Factory implements SqlStatementCustomizerFactory {

        @Override
        public SqlStatementCustomizer createForMethod(Annotation annotation, Class sqlObjectType, Method method) {
            return null;
        }

        @Override
        public SqlStatementCustomizer createForType(Annotation annotation, Class sqlObjectType) {
            return q -> q.addCustomizer(new StatementCustomizer() {
                @Override
                public void beforeExecution(PreparedStatement stmt, StatementContext ctx) throws SQLException {
                    LOGGER.info(stmt.toString());
                }

                @Override
                public void afterExecution(PreparedStatement stmt, StatementContext ctx) throws SQLException { }
            });
        }
    }

}
