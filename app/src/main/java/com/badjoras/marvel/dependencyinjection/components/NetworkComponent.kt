package com.badjoras.marvel.dependencyinjection.components

import com.badjoras.marvel.dependencyinjection.modules.NetworkModule
import dagger.Component

@Component(modules = [(NetworkModule::class)])
interface NetworkComponent { }