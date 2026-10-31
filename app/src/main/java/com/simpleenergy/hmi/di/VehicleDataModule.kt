package com.simpleenergy.hmi.di

import com.simpleenergy.hmi.core.ElapsedClock
import com.simpleenergy.hmi.core.SystemElapsedClock
import com.simpleenergy.hmi.data.provider.SimulatedVehicleDataProvider
import com.simpleenergy.hmi.data.provider.VehicleDataProvider
import com.simpleenergy.hmi.data.repository.VehicleRepositoryImpl
import com.simpleenergy.hmi.domain.repository.VehicleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class VehicleDataModule {

    @Binds
    @Singleton
    abstract fun bindVehicleDataProvider(
        implementation: SimulatedVehicleDataProvider
    ): VehicleDataProvider

    @Binds
    @Singleton
    abstract fun bindVehicleRepository(
        implementation: VehicleRepositoryImpl
    ): VehicleRepository

    @Binds
    @Singleton
    abstract fun bindElapsedClock(
        implementation: SystemElapsedClock
    ): ElapsedClock
}