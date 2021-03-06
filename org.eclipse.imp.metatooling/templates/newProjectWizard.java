package $PACKAGE_NAME$;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;
import org.eclipse.jdt.internal.ui.actions.WorkbenchRunnableAdapter;
import org.eclipse.jdt.internal.ui.util.CoreUtility;
import org.eclipse.jdt.internal.ui.util.ExceptionHandler;
import org.eclipse.jdt.internal.ui.wizards.NewWizardMessages;
import org.eclipse.jdt.ui.wizards.JavaCapabilityConfigurationPage;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class $CLASS_NAME_PREFIX$ProjectWizard extends Wizard implements INewWizard {
    private $CLASS_NAME_PREFIX$ProjectWizardFirstPage fFirstPage;

    private $CLASS_NAME_PREFIX$ProjectWizardSecondPage fSecondPage;

    public $CLASS_NAME_PREFIX$ProjectWizard() {
	super();
	// setDefaultPageImageDescriptor(JavaPluginImages.DESC_WIZBAN_NEWJPRJ);
	// setDialogSettings(JavaPlugin.getDefault().getDialogSettings());
	setWindowTitle("New $LANG_NAME$ Project");
    }

    public boolean performFinish() {
	IWorkspaceRunnable op= new IWorkspaceRunnable() {
	    public void run(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		try {
		    finishPage(monitor);
		} catch (InterruptedException e) {
		    throw new OperationCanceledException(e.getMessage());
		}
	    }
	};
	try {
	    ISchedulingRule rule= null;
	    Job job= Platform.getJobManager().currentJob();
	    if (job != null)
		rule= job.getRule();
	    IRunnableWithProgress runnable= null;
	    if (rule != null)
		runnable= new WorkbenchRunnableAdapter(op, rule, true);
	    else
		runnable= new WorkbenchRunnableAdapter(op, getSchedulingRule());
	    getContainer().run(canRunForked(), true, runnable);
	} catch (InvocationTargetException e) {
	    handleFinishException(getShell(), e);
	    return false;
	} catch (InterruptedException e) {
	    return false;
	}
	return true;
    }

    /**
     * Returns the scheduling rule for creating the element.
     */
    protected ISchedulingRule getSchedulingRule() {
	return ResourcesPlugin.getWorkspace().getRoot(); // look all by default
    }

    protected boolean canRunForked() {
	return true;
    }

    protected void handleFinishException(Shell shell, InvocationTargetException e) {
	String title= NewWizardMessages.NewElementWizard_op_error_title;
	String message= NewWizardMessages.NewElementWizard_op_error_message;
	ExceptionHandler.handle(e, shell, title, message);
    }

    /*
     * @see Wizard#addPages
     */
    public void addPages() {
	super.addPages();
	fFirstPage= new $CLASS_NAME_PREFIX$ProjectWizardFirstPage();
	addPage(fFirstPage);
	fSecondPage= new $CLASS_NAME_PREFIX$ProjectWizardSecondPage(fFirstPage);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jdt.internal.ui.wizards.NewElementWizard#finishPage(org.eclipse.core.runtime.IProgressMonitor)
     */
    protected void finishPage(IProgressMonitor monitor) throws InterruptedException, CoreException {
	fSecondPage.performFinish(monitor); // use the full progress monitor
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {}
}
