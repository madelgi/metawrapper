package com.docone.metawrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class MetamapConnection {
    //private static final String METAMAP_HOST = "ec2-3-14-126-186.us-east-2.compute.amazonaws.com";
    private static String host;
    private static Integer port;

    public MetamapConnection() {}

    public MetamapConnection(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public static MetaMapApi getConnection() {
        MetaMapApi connection = new MetaMapApiImpl();
        connection.setHost(host);
        connection.setPort(port);
        connection.setOptions(defaultOptions());
        return connection;
    }

    public static MetaMapApi getConnection(String optionString) {
        MetaMapApi connection = new MetaMapApiImpl();
        connection.setHost(host);
        connection.setPort(port);
        connection.setOptions(optionString);
        return connection;
    }

    private static List<String> defaultOptions() {
        return new OptionBuilder()
                .showCandidates()
                // TODO why isn't this working?
                //.restrictToSources(Arrays.asList("MSH", "CPT"))
                .relaxedModel()
                .dataVersion("NLM")
                .termProcessing()
                .allowConceptGaps()
                // TODO very slow, don't know if it's feasible to use this
                //.allowOvermatches()
                .build();
    }

    @NotEmpty
    @JsonProperty("host")
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @NotEmpty
    @JsonProperty("port")
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public static class OptionBuilder {
        private List<String> options = new ArrayList<>();

        /**
         * Data options
         */
        private OptionBuilder knowledgeSource(String source) {
            options.add("-Z " + source);
            return this;
        }

        private OptionBuilder dataVersion(String version) {
            options.add("-V");
            options.add(version);
            return this;
        }

        private OptionBuilder strictModel() {
            options.add("-A");
            return this;
        }

        private OptionBuilder relaxedModel() {
            options.add("-C");
            return this;
        }

        /**
         * Output options
         */
        private OptionBuilder displayTaggerOutput() {
            options.add("-T");
            return this;
        }

        private OptionBuilder displayVariants() {
            options.add("-v");
            return this;
        }

        private OptionBuilder hidePlainSyntax() {
            options.add("-p");
            return this;
        }

        private OptionBuilder syntax() {
            options.add("-x");
            return this;
        }

        private OptionBuilder showCandidates() {
            options.add("-c");
            return this;
        }

        private OptionBuilder numberCandidates() {
            options.add("-n");
            return this;
        }

        private OptionBuilder numberMappings() {
            options.add("-f");
            return this;
        }

        private OptionBuilder shortSemanticTypes() {
            options.add("-s");
            return this;
        }

        private OptionBuilder showCUIs() {
            options.add("-I");
            return this;
        }

        private OptionBuilder machineOutput() {
            options.add("-q");
            return this;
        }

        private OptionBuilder formattedXmlOutput() {
            options.add("--XMLf");
            return this;
        }

        private OptionBuilder unformattedXmlOutput() {
            options.add("--XMLn");
            return this;
        }

        private OptionBuilder formattedJsonOutput() {
            options.add("--JSONf 2");
            return this;
        }

        private OptionBuilder unformattedJsonOutput() {
            options.add("--JSONn");
            return this;
        }

        private OptionBuilder formalTaggerOutput() {
            options.add("-F");
            return this;
        }

        private OptionBuilder fieldedMMIOutput() {
            options.add("-N");
            return this;
        }

        private OptionBuilder showConceptsSources() {
            options.add("-G");
            return this;
        }

        private OptionBuilder showAcronymAbbreviations() {
            options.add("-j");
            return this;
        }

        private OptionBuilder showBracketedOutput() {
            options.add("-+");
            return this;
        }

        /**
         * Browse mode options
         */
        private OptionBuilder termProcessing() {
            options.add("-z");
            return this;
        }

        private OptionBuilder allowOvermatches() {
            options.add("-o");
            return this;
        }

        private OptionBuilder allowConceptGaps() {
            options.add("-g");
            return this;
        }

        /**
         * Behavior options
         */
        private OptionBuilder noMappings() {
            options.add("-m");
            return this;
        }

        private OptionBuilder enableNegEx() {
            options.add("--negex");
            return this;
        }

        private OptionBuilder conjProcessing() {
            options.add("--conj");
            return this;
        }

        private OptionBuilder compositePhrases() {
            options.add("-Q");
            return this;
        }

        // TODO finish behavior options

        /**
         * Source restriction/exclusion
         */
        private OptionBuilder restrictToSources(List<String> sources) {
            String s = StringUtils.join(sources, " ");
            options.add("-R");
            for (String source : sources) {
                options.add(source);
            }

            return this;
        }

        private OptionBuilder excludeSources(List<String> sources) {
            String s = StringUtils.join(sources, " ");
            options.add("-e " + s);
            return this;
        }

        private OptionBuilder restrictSemanticTypes(List<String> types) {
            String s = StringUtils.join(types, " ");
            options.add("-J " + s);
            return this;
        }

        private OptionBuilder excludeSemanticTypes(List<String> types) {
            String s = StringUtils.join(types, " ");
            options.add("-k " + s);
            return this;
        }

        public List<String> build() {
            return this.options;
        }
    }

}
