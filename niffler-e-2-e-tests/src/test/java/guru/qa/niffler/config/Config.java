package guru.qa.niffler.config;

public interface Config {

    static Config getInstance() {
        return "docker".equals(System.getProperty("test.env"))
                ? DockerConfig.instance
                : LocalConfig.instance;
    }

    String frontUrl();

    String jdbcHost();

    default String jdbcUser() {
        return "postgres";
    }

    default String jdbcPassword() {
        return "secret";
    }

    default int jdbcPort() {
        return 5432;
    }

    default String spendUrl() {
        return "http://127.0.0.1:8093";
    }

    default String currencyUrl() {
        return "http://127.0.0.1:8091";
    }

    default String userDataUrl() {
        return "http://127.0.0.1:8089";
    }

    default String mainPageUrl() {
        return "http://127.0.0.1:3000/main";
    }
}
