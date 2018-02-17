package org.usd232.robotics.autonomous.generator.model;

import javax.swing.event.ListSelectionListener;
import org.usd232.robotics.autonomous.AutonomousStep;
import org.usd232.robotics.autonomous.generator.SideList;

public class GeneratorModel {
    public final LocalDatabase localDatabase;
    public final VersionList   versionList;
    public final ParameterList parameterList;
    public final StepList      stepList;
    public final SideList      localDatabaseView;
    public final SideList      versionListView;
    public final SideList      parameterListView;
    public final SideList      stepListView;

    public void addListSelectionListener(ListSelectionListener listener) {
        localDatabaseView.addListSelectionListener(listener);
        versionListView.addListSelectionListener(listener);
        parameterListView.addListSelectionListener(listener);
        stepListView.addListSelectionListener(listener);
    }

    public void removeListSelectionListener(ListSelectionListener listener) {
        localDatabaseView.removeListSelectionListener(listener);
        versionListView.removeListSelectionListener(listener);
        parameterListView.removeListSelectionListener(listener);
        stepListView.removeListSelectionListener(listener);
    }

    public void addStep(AutonomousStep step) {
        stepList.add(null).setParameter(step.getGenericParameter());
    }

    public GeneratorModel() {
        localDatabase = new LocalDatabase();
        versionList = new VersionList();
        parameterList = new ParameterList();
        stepList = new StepList();
        localDatabaseView = new SideList("Route Name:", localDatabase, true, true, null);
        versionListView = new SideList("Route Versions:", versionList, false, true, localDatabaseView);
        parameterListView = new SideList("Version Parameters:", parameterList, true, true, versionListView);
        stepListView = new SideList("Autonomous Steps:", stepList, false, false, versionListView);
    }
}
