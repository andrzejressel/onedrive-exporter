package com.andrzejressel.onedrivecollector.bean

import io.micrometer.prometheus.PrometheusMeterRegistry
import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import org.slf4j.LoggerFactory
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes

@ApplicationScoped
class AppLifecycleBean(
    val prometheusMeterRegistry: PrometheusMeterRegistry,
    val prometheusCollectorBean: PrometheusCollectorBean
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun onStart(@Observes ev: StartupEvent?) {
        prometheusMeterRegistry.prometheusRegistry.register(prometheusCollectorBean)
        logger.info("The application is starting...")
    }

    fun onStop(@Observes ev: ShutdownEvent?) {
        logger.info("The application is stopping...")
    }

}