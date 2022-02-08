package org.seed.adapter.controller

import org.eclipse.microprofile.health.HealthCheck
import org.eclipse.microprofile.health.HealthCheckResponse
import org.eclipse.microprofile.health.Liveness
import org.eclipse.microprofile.health.Readiness
import org.eclipse.microprofile.health.Startup

@Liveness
class LivenessCheck : HealthCheck {

    override fun call(): HealthCheckResponse {
        return HealthCheckResponse.up("alive")
    }

}

@Readiness
class ReadinessCheck : HealthCheck {

    override fun call(): HealthCheckResponse {
        return HealthCheckResponse.up("ready")
    }

}

@Startup
class StartupCheck : HealthCheck {

    override fun call(): HealthCheckResponse {
        return HealthCheckResponse.up("started")
    }

}
