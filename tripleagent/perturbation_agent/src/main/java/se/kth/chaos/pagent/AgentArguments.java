package se.kth.chaos.pagent;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class AgentArguments {
    private int tcIndex;
    private OperationMode operationMode;
    private double chanceOfFailure;
    private FilterByClassAndMethodName filter;
    private FilterByExceptionType exceptionFilter;
    private String lineNumber;
    private String configFile;
    private String csvfilepath;
    private String defaultMode;
    private int perturbationCountdown;
    private int interval;
    private boolean readFromEnv;

    public AgentArguments(String args) {
        Map<String, String> configuration = argumentMap(args == null ? "" : args);
        this.configFile = configuration.getOrDefault("config", null);
        this.readFromEnv = Boolean.valueOf(configuration.getOrDefault("readFromEnv", "false"));

        if (this.configFile != null) {
            refreshConfig();
        } else if (this.readFromEnv) {
            readConfigFromEnv();
        } else {
            this.tcIndex = Integer.valueOf(configuration.getOrDefault("tcindex", "-1"));
            this.operationMode = OperationMode.fromLowerCase(configuration.getOrDefault("mode", OperationMode.ARRAY_PONE.name()));
            this.chanceOfFailure = Double.valueOf(configuration.getOrDefault("rate", "1"));
            this.filter = new FilterByClassAndMethodName(configuration.getOrDefault("filter", ".*"));
            this.exceptionFilter = new FilterByExceptionType(configuration.getOrDefault("efilter", ".*"));
            this.lineNumber = configuration.getOrDefault("lineNumber", "*");
            this.csvfilepath = configuration.getOrDefault("csvfilepath", "perturbationPointsList.csv");
            this.defaultMode = configuration.getOrDefault("defaultMode", "off");
            this.perturbationCountdown = Integer.valueOf(configuration.getOrDefault("countdown", "-1"));
            this.interval = Integer.valueOf(configuration.getOrDefault("interval", "1"));
        }
    }

    public AgentArguments(long latency, double activationRatio, int tcIndex, String operationMode, String filter, String configFile) {
        this.tcIndex = tcIndex;
        this.operationMode = OperationMode.fromLowerCase(operationMode);
        this.filter = new FilterByClassAndMethodName(filter);
        this.configFile = configFile;

        if (this.configFile != null) {
            refreshConfig();
        }
    }

    private Map<String, String> argumentMap(String args) {
        return Arrays
            .stream(args.split(","))
            .map(line -> line.split(":"))
            .filter(line -> line.length == 2)
            .collect(Collectors.toMap(
                    keyValue -> keyValue[0],
                    keyValue -> keyValue[1])
            );
    }

    private void refreshConfig() {
        Properties p = new Properties();
        try {
            InputStream inputStream = new FileInputStream(this.configFile);
            p.load(inputStream);
            this.tcIndex = Integer.valueOf(p.getProperty("tcindex", "-1"));
            this.operationMode = OperationMode.fromLowerCase(p.getProperty("mode", OperationMode.ARRAY_PONE.name()));
            this.chanceOfFailure = Double.valueOf(p.getProperty("rate", "1"));
            this.filter = new FilterByClassAndMethodName(p.getProperty("filter", ".*"));
            this.exceptionFilter = new FilterByExceptionType(p.getProperty("efilter", ".*"));
            this.lineNumber = p.getProperty("lineNumber", "*");
            this.csvfilepath = p.getProperty("csvfilepath", "perturbationPointsList.csv");
            this.defaultMode = p.getProperty("defaultMode", "off");
            this.perturbationCountdown = Integer.valueOf(p.getProperty("countdown", "1"));
            this.interval = Integer.valueOf(p.getProperty("interval", "1"));
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readConfigFromEnv() {
        this.tcIndex = Integer.valueOf(this.getenvOrDefault("TRIPLEAGENT_TCINDEX", "-1"));
        this.operationMode = OperationMode.fromLowerCase(this.getenvOrDefault("mode", OperationMode.THROW_E.name()));
        this.chanceOfFailure = Double.valueOf(this.getenvOrDefault("TRIPLEAGENT_RATE", "1"));
        this.filter = new FilterByClassAndMethodName(this.getenvOrDefault("TRIPLEAGENT_FILTER", ".*"));
        this.exceptionFilter = new FilterByExceptionType(this.getenvOrDefault("TRIPLEAGENT_EFILTER", ".*"));
        this.lineNumber = this.getenvOrDefault("TRIPLEAGENT_LINENUMBER", "*");
        this.csvfilepath = this.getenvOrDefault("TRIPLEAGENT_CSVPATH", "/home/tripleagent/logs/perturbationPointsList.csv");
        this.defaultMode = this.getenvOrDefault("TRIPLEAGENT_DEFAULTMODE", "off");
        this.perturbationCountdown = Integer.valueOf(this.getenvOrDefault("TRIPLEAGENT_COUNTDOWN", "1"));
        this.interval = Integer.valueOf(this.getenvOrDefault("TRIPLEAGENT_INTERVAL", "1"));
    }

    private String getenvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

     public int tcIndex() {
        if (this.configFile != null) {
            refreshConfig();
        }
        return tcIndex;
    }

    public OperationMode operationMode() {
        if (this.configFile != null) {
            refreshConfig();
        }
        return operationMode;
    }

    public double chanceOfFailure() {
        if (this.configFile != null) {
            refreshConfig();
        }
        return chanceOfFailure;
    }

    public FilterByClassAndMethodName filter() {
        if (this.configFile != null) {
            refreshConfig();
        }
        return filter;
    }

    public FilterByExceptionType exceptionFilter() {
        if (this.configFile != null) {
            refreshConfig();
        }
        return exceptionFilter;
    }

    public String lineNumber() {
        if (this.configFile != null) {
            refreshConfig();
        }
        return lineNumber;
    }

    public String csvfilepath() {
        if (this.configFile != null) {
            refreshConfig();
        }
        return csvfilepath;
    }

    public String defaultMode() {
        if (this.configFile != null) {
            refreshConfig();
        }
        return defaultMode;
    }

    public int countdown() {
        if (this.configFile != null) {
            refreshConfig();
        }
        return perturbationCountdown;
    }

    public int interval() {
        if (this.configFile != null) {
            refreshConfig();
        }
        return interval;
    }
}
