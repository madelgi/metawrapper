package com.docone.metawrapper;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class MetaWrapperConfig extends Configuration {
    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    @NotNull
    @JsonProperty("metamap")
    private MetamapConnection metamap;

    public void setDatabase(DataSourceFactory factory) {
        this.database = factory;
    }

    public DataSourceFactory getDatabase() {
        return database;
    }

    public void setMetamap(MetamapConnection metamap) {
        this.metamap = metamap;
    }

    public MetamapConnection getMetamap() {
        return metamap;
    }
}
