package com.docone.metawrapper.healthchecks;

import com.codahale.metrics.health.HealthCheck;
import com.docone.metawrapper.sql.MetathesaurusSqlExecutor;

public class MetathesaurusDatabaseHealthcheck extends HealthCheck {

    private final MetathesaurusSqlExecutor sqlExecutor;

    public MetathesaurusDatabaseHealthcheck(MetathesaurusSqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    @Override
    protected HealthCheck.Result check() throws Exception {
        if (sqlExecutor.ping()) {
            return HealthCheck.Result.healthy();
        }

        return HealthCheck.Result.unhealthy("Can't ping the metathesaurus database");
    }
}
