package com.example.sharifytest2.di

import com.example.sharifytest2.data.auth.repository.AuthRepositoryImpl
import com.example.sharifytest2.domain.repository.AuthRepository
import com.example.sharifytest2.domain.useCase.LoginUseCase
import com.example.sharifytest2.domain.useCase.RegisterUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    companion object {
        @Provides
        fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
            return LoginUseCase(authRepository)
        }

        @Provides
        fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase {
            return RegisterUseCase(authRepository)
        }
    }
}
