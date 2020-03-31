package com.vsj.common.base.config;


import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ConfigurationProperties
@PropertySource(value = "classpath:executor.properties",encoding = "utf-8")
@EnableAsync
public class ExecutorConfig {
	
	@Value("${commonExecutor.corePoolSize}")
	private int corePoolSize;

	@Value("${commonExecutor.maxPoolSize}")
	private int maxPoolSize;

	@Value("${commonExecutor.queueCapacity}")
	private int queueCapacity;

	@Value("${commonExecutor.keepAliveSeconds}")
	private int keepAliveSeconds;

	@Value("${commonExecutor.waitCompleteOnShutdown}")
	private boolean waitCompleteOnShutdown;
	
	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getQueueCapacity() {
		return queueCapacity;
	}

	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}

	public int getKeepAliveSeconds() {
		return keepAliveSeconds;
	}

	public void setKeepAliveSeconds(int keepAliveSeconds) {
		this.keepAliveSeconds = keepAliveSeconds;
	}

	public boolean isWaitCompleteOnShutdown() {
		return waitCompleteOnShutdown;
	}

	public void setWaitCompleteOnShutdown(boolean waitCompleteOnShutdown) {
		this.waitCompleteOnShutdown = waitCompleteOnShutdown;
	}
	
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心线程数
		executor.setCorePoolSize(corePoolSize);
		// 设置最大线程数
		executor.setMaxPoolSize(maxPoolSize);
		// 设置队列容量
		executor.setQueueCapacity(queueCapacity);
		// 设置线程活跃时间（秒）
		executor.setKeepAliveSeconds(keepAliveSeconds);
		// 设置默认线程名称
		executor.setThreadNamePrefix("common-executor");
		// 设置拒绝策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 等待所有任务结束后再不关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(waitCompleteOnShutdown);
		return executor;
	}
	
	
}
