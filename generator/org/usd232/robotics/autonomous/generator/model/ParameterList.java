package org.usd232.robotics.autonomous.generator.model;

import java.util.Collection;
import org.usd232.robotics.autonomous.AutonomousRoute;

public class ParameterList extends ListModelBase<String, AutonomousRoute, VersionList> {
    private static final long serialVersionUID = -5765812431044211292L;

    @Override
    protected String toString(String obj) {
        return obj;
    }

    @Override
    protected Collection<String> load(AutonomousRoute element) {
        return element.getSupportedConfigurations();
    }

    @Override
    protected void onRemove(String element) {
        parentElement.removeSupportedConfiguration(element);
    }

    @Override
    protected String create(String name) {
        parentElement.addSupportedConfiguration(name);
        return name;
    }
}
