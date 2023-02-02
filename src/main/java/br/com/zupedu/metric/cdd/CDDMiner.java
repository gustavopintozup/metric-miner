package br.com.zupedu.metric.cdd;

import java.util.ArrayList;
import java.util.List;

import br.com.stackedu.cdd.Miner.FormatOption.Format;
import br.com.stackedu.cdd.config.Config;
import br.com.stackedu.cdd.config.DefaultUserDefinitionFactory;
import br.com.stackedu.cdd.config.SupportedRules;
import br.com.stackedu.cdd.printer.PrintMetrics;
import br.com.stackedu.cdd.storage.StoreMetrics;
import spoon.Launcher;
import spoon.processing.Processor;
import spoon.reflect.factory.Factory;

public class CDDMiner {

    private final String repo;

    public CDDMiner(String repo) {
        this.repo = repo;
    }

    public String compute() {
        Launcher spoon = new Launcher();
        final Factory factory = spoon.getFactory();
        factory.getEnvironment().setComplianceLevel(17);
        factory.getEnvironment().setCopyResources(false);
        spoon.addInputResource(repo);

        Config config = DefaultUserDefinitionFactory.load("cdd.json");

        StoreMetrics context = new StoreMetrics();

        List<Processor> requiredProcessors = new ArrayList<>();

        for (SupportedRules rule : config.getDefinedRules()) {
            List<Processor> processors = rule.resolveProcessors(config, context);
            requiredProcessors.addAll(processors);
        }

        requiredProcessors.forEach(spoon::addProcessor);

        spoon.run();

        var metrics = new PrintMetrics(config, context, true);

        return metrics.as(Format.JSON).print();
    }
}
