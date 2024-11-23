package org.example;

import java.util.List;

/**
 * Runs WASM module.
 */
interface ModuleRunner {

    String run(String modulePath);

    String run(String modulePath, List<String> args, Options... options);

    LoadResult load(String modulePath, List<String> args, Options... options);
}

record LoadResult<T>(T instance, String output) {
}
