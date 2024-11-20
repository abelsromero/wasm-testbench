package org.example;

import org.example.ChicoryModuleRunner.RunResult;

import java.util.List;

/**
 * Runs WASM module.
 */
public class RubyRunner {

    private final RunResult instance;

    public RubyRunner(String rubyRuntimePath) {
        ChicoryModuleRunner moduleRunner = new ChicoryModuleRunner();
        this.instance = moduleRunner.load(rubyRuntimePath, List.of());
    }

    public void run(String script) {

    }
}
