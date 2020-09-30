package com.docone.metawrapper;

import com.docone.metawrapper.dao.MRConsoDao;
import com.docone.metawrapper.resource.CPTResource;
import com.docone.metawrapper.resource.RXResource;
import com.docone.metawrapper.service.MetamapService;
import io.dropwizard.jdbi3.JdbiFactory;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import com.docone.metawrapper.healthchecks.MetathesaurusDatabaseHealthcheck;
import com.docone.metawrapper.resource.MeshResource;
import com.docone.metawrapper.resource.MetaWrapperResource;
import com.docone.metawrapper.sql.MetathesaurusSqlExecutor;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class MetaWrapperApplication extends Application<MetaWrapperConfig> {
    public static void main(String[] args) throws Exception {
        new MetaWrapperApplication().run(args);
    }

    @Override
    public String getName() {
        return "meta-wrapper";
    }

    @Override
    public void initialize(Bootstrap<MetaWrapperConfig> bootstrap) {

    }

    @Override
    public void run(MetaWrapperConfig metaWrapperConfig, Environment environment) throws Exception {
        // Resources
        MetamapService metamap = new MetamapService();

        // DB properties
        final JdbiFactory jdbiFactory = new JdbiFactory();
        final Jdbi jdbi = jdbiFactory.build(environment, metaWrapperConfig.getDatabase(), "mysql");
        final MRConsoDao mrConsoDao = jdbi.onDemand(MRConsoDao.class);

        final CPTResource cptResource = new CPTResource(mrConsoDao);
        final MetaWrapperResource metaWrapperResource = new MetaWrapperResource(metamap);
        final MeshResource meshResource = new MeshResource(metamap, mrConsoDao);
        final RXResource rxResource = new RXResource(mrConsoDao);
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        // TODO this shouldn't be a wildcard, get a list of sites
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        // Register applications.
        environment.jersey().register(metaWrapperResource);
        environment.jersey().register(meshResource);
        environment.jersey().register(rxResource);
        environment.jersey().register(cptResource);

        // Register healthchecks.
        environment.healthChecks().register("metathesaurus-db", new MetathesaurusDatabaseHealthcheck(new MetathesaurusSqlExecutor()));
    }
}
