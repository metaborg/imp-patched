package org.eclipse.imp.ant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.TaskContainer;

public class ForTask extends Task implements TaskContainer {
    private List<Task> fTasks= new ArrayList<Task>();
    private String fValueStr;
    private String fSeparator= ",";
    private String fParamName;

    public void setSeparator(String sep) {
        fSeparator= sep;
    }

    public void setParam(String name) {
        fParamName= name; 
    }

    public void setValues(String valueStr) {
        fValueStr= valueStr; 
    }

    public void addTask(Task task) {
        fTasks.add(task);
    }

    public void execute() throws BuildException {
        String[] values= fValueStr.split(fSeparator);
        List<String> valueList= new ArrayList<String>();
        for(int i= 0; i < values.length; i++) {
            valueList.add(values[i]);
        }
        for(Iterator<String> valueIter= valueList.iterator(); valueIter.hasNext(); ) {
            String value= valueIter.next();
            getProject().setProperty(fParamName, value);

            for(Iterator<Task> taskIter= fTasks.iterator(); taskIter.hasNext();) {
                Task task= taskIter.next();
                task.perform();
            }
        }
    }

}
