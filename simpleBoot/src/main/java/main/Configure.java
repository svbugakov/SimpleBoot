package main;

import org.springframework.cache.CacheManager;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.spring.SpringCacheManager;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableCaching
public class Configure {
    @Bean
    public CacheManager cacheManager() {
        SpringCacheManager cacheManager = new SpringCacheManager();
        cacheManager.setConfiguration(igniteConfiguration());
        return cacheManager;
    }

    @Bean(name = "igniteConfiguration")
    public IgniteConfiguration igniteConfiguration() {
        // Preparing IgniteConfiguration using Java APIs
        IgniteConfiguration cfg = new IgniteConfiguration();
        // The node will be started as a client node.
        cfg.setClientMode(false);

        // Classes of custom Java logic will be transferred over the wire from this app.
        cfg.setPeerClassLoadingEnabled(false);

        final String ipIgnite = System.getenv("IGN_IP");
        System.out.println("---------IP IGNITE: " + ipIgnite);

        // Setting up an IP Finder to ensure the client can locate the servers.
        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        ipFinder.setAddresses(Collections.singletonList(ipIgnite+":47500..47509"));
        cfg.setDiscoverySpi(new TcpDiscoverySpi().setIpFinder(ipFinder));

        cfg.setCacheConfiguration(cacheConfiguration());

        return cfg;

    }

    @Bean(name = "cacheConfiguration")
    public CacheConfiguration[] cacheConfiguration() {
        List<CacheConfiguration> cacheConfigurations = new ArrayList<>();
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setCacheMode(CacheMode.REPLICATED);
        cacheConfiguration.setName("myCache");

        cacheConfigurations.add(cacheConfiguration);

        return cacheConfigurations.toArray(new CacheConfiguration[cacheConfigurations.size()]);
    }
}
