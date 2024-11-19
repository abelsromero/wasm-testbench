package org.example;

import org.example.ModuleRunner.RunResult;

import java.util.List;

/**
 * Runs WASM module.
 */
public class RubyRunner {

    private final RunResult instance;

    public RubyRunner(String rubyRuntimePath) {
        ModuleRunner moduleRunner = new ModuleRunner();
        this.instance = moduleRunner.load(rubyRuntimePath, List.of());
        System.out.println("12");
    }

    public void run(String script) {



    }
}
