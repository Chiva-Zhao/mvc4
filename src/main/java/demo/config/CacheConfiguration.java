package demo.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class CacheConfiguration {

	@Bean
	public CacheManager cacheManager() {
		// SimpleCacheManager cacheManager = new SimpleCacheManager();
		// cacheManager.setCaches(Arrays.asList(new
		// ConcurrentMapCache("searches")));
		GuavaCacheManager cacheManager = new GuavaCacheManager("searches");
		cacheManager.setCacheBuilder(CacheBuilder.newBuilder().softValues().expireAfterWrite(10, TimeUnit.MINUTES));
		return cacheManager;
	}
}
