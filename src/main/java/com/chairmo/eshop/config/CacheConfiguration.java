package com.chairmo.eshop.config;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;

public class CacheConfiguration {

	public CacheManager cacheManager(Ticker ticker) {
		CaffeineCache productCache = buildCache("product", ticker, (long) 10, TimeUnit.MINUTES);
		CaffeineCache cartCache = buildCache("cart", ticker, (long) 10, TimeUnit.MINUTES);

		SimpleCacheManager manager = new SimpleCacheManager();

		manager.setCaches(Arrays.asList(productCache, cartCache));

		return manager;
	}

	private CaffeineCache buildCache(String name, Ticker ticker, Long duration, TimeUnit unit) {
		return new CaffeineCache(name, Caffeine.newBuilder().expireAfterWrite(duration, unit).maximumSize(400_000)
				.executor(Executors.newCachedThreadPool()).ticker(ticker).build());
	}

	@Bean
	public Ticker ticker() {
		return Ticker.systemTicker();
	}
}
