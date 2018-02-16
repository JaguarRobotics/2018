package org.usd232.robotics.autonomous.generator.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.usd232.robotics.autonomous.AutonomousModel;
import org.usd232.robotics.autonomous.AutonomousRoute;

public class VersionList extends ListModelBase<AutonomousRoute, Tuple2<String, AutonomousModel>, LocalDatabase> {
    private static final long            serialVersionUID = 7254741187358811541L;
    private Map<AutonomousRoute, String> strings;
    private int                          stringCount;

    @Override
    protected String toString(AutonomousRoute obj) {
        return strings.get(obj);
    }

    @Override
    protected Collection<AutonomousRoute> load(Tuple2<String, AutonomousModel> element) {
        strings = new HashMap<>();
        stringCount = 0;
        for (AutonomousRoute route : element.obj2.getRoutes()) {
            strings.put(route, Integer.toString(++stringCount));
        }
        return element.obj2.getRoutes();
    }

    @Override
    protected void onRemove(AutonomousRoute element) {
        parentElement.obj2.removeRoute(element);
    }

    @Override
    protected AutonomousRoute create(String name) {
        AutonomousRoute route = new AutonomousRoute();
        parentElement.obj2.addRoute(route);
        strings.put(route, Integer.toString(++stringCount));
        return route;
    }
}
