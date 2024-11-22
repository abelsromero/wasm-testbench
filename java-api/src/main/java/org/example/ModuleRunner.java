package org.example;

import java.util.List;

/**
 * Runs WASM module.
 */
interface ModuleRunner {

    String run(String modulePath);

    String run(String modulePath, List<String> args, Options... options);

}
