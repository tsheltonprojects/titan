/*
 * Copyright (c) 2019 by Delphix. All rights reserved.
 */

package io.titandata.titan.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import io.titandata.titan.Dependencies
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.provider

class Delete : CliktCommand(
        help = "Delete objects from titan"
) {
    private val dependencies: Dependencies by requireObject()
    private val repository by argument()
    private val commit by option("-c", "--commit", help = "Commit GUID to delete")
    private val tags by option("-t", "--tag", help = "Tags to remove from a commit").multiple()
    override fun run() {
        val (provider, repoName) = dependencies.providers.byRepository(repository)
        provider.delete(repoName, commit, tags)
    }
}

val deleteModule = Kodein.Module("delete") {
    bind<CliktCommand>().inSet() with provider {
        Delete()
    }
}
