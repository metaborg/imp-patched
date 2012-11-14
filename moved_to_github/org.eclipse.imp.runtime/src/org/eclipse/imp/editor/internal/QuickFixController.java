/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.imp.editor.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.imp.editor.EditorUtility;
import org.eclipse.imp.editor.UniversalEditor;
import org.eclipse.imp.editor.hover.ProblemLocation;
import org.eclipse.imp.editor.internal.quickfix.MarkerResolutionProposal;
import org.eclipse.imp.editor.quickfix.IAnnotation;
import org.eclipse.imp.language.Language;
import org.eclipse.imp.language.ServiceFactory;
import org.eclipse.imp.model.ICompilationUnit;
import org.eclipse.imp.model.ModelFactory;
import org.eclipse.imp.services.IQuickFixAssistant;
import org.eclipse.imp.services.IQuickFixInvocationContext;
import org.eclipse.imp.services.base.DefaultQuickFixAssistant;
import org.eclipse.imp.utils.AnnotationUtils;
import org.eclipse.imp.utils.MarkerUtils;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.quickassist.IQuickAssistInvocationContext;
import org.eclipse.jface.text.quickassist.IQuickAssistProcessor;
import org.eclipse.jface.text.quickassist.QuickAssistAssistant;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.MarkerAnnotation;
import org.eclipse.ui.texteditor.SimpleMarkerAnnotation;

public class QuickFixController extends QuickAssistAssistant implements IQuickAssistProcessor {
	private IQuickFixAssistant fAssistant;
	private ICompilationUnit fCU;

	public QuickFixController(IMarker marker) {
		this(MarkerUtils.getLanguage(marker), null);

		IFileEditorInput input = MarkerUtils.getInput(marker);

		if (input != null) {
			fCU = ModelFactory.open(input.getFile(), EditorUtility.getSourceProject(input));
		}
	}

	public QuickFixController(UniversalEditor editor) {
		this(editor.getLanguage(), null);

		if (editor.getEditorInput() instanceof FileEditorInput) {
			FileEditorInput input = (FileEditorInput) editor.getEditorInput();
			fCU = ModelFactory.open(input.getFile(), EditorUtility.getSourceProject(input));
		}
	}
	
	public QuickFixController(Language lang, ICompilationUnit cu) {
		super();
		this.fCU = cu;
		setQuickAssistProcessor(this);

		if (lang != null) {
			fAssistant = ServiceFactory.getInstance().getQuickFixAssistant(lang);
		}

		if (fAssistant == null) {
			fAssistant = new DefaultQuickFixAssistant();
		}
	}

	public IQuickFixInvocationContext getContext(IQuickAssistInvocationContext quickAssistContext) {
		return new DefaultQuickFixInvocationContext(quickAssistContext, fCU);
	}

	public String getErrorMessage() {
		return null;
	}

	@Override
	public boolean canFix(Annotation annotation) {
		return fAssistant.canFix(annotation);
	}

	@Override
	public boolean canAssist(IQuickAssistInvocationContext quickAssistContext) {
		return fAssistant.canAssist(getContext(quickAssistContext));
	}

	public boolean canFix(IMarker marker) throws CoreException {
		for (String type : fAssistant.getSupportedMarkerTypes()) {
			if (marker.getType().equals(type)) {
				MarkerAnnotation ma = new MarkerAnnotation(marker);
				return fAssistant.canFix(ma);
			}
		}

		return false;
	}

	public void collectProposals(IQuickFixInvocationContext context,
			IAnnotationModel model, Collection<Annotation> annotations,
			boolean addQuickFixes, boolean addQuickAssists,
			Collection<ICompletionProposal> proposals) {
		ArrayList<ProblemLocation> problems = new ArrayList<ProblemLocation>();

		// collect problem locations and corrections from marker annotations
		for (Annotation curr : annotations) {
			ProblemLocation problemLocation = null;

			if (curr instanceof IAnnotation) {
				problemLocation = getProblemLocation((IAnnotation) curr, model);
				if (problemLocation != null) {
					problems.add(problemLocation);
				}
			}
			if (problemLocation == null && addQuickFixes
					&& curr instanceof SimpleMarkerAnnotation) {
				collectMarkerProposals((SimpleMarkerAnnotation) curr, proposals);
			}
		}

		ProblemLocation[] problemLocations =
		    (ProblemLocation[]) problems.toArray(new ProblemLocation[problems.size()]);
		if (addQuickFixes) {
			collectCorrections(context, problemLocations, proposals);
		}
		if (addQuickAssists) {
			collectAssists(context, problemLocations, proposals);
		}
	}

	private static ProblemLocation getProblemLocation(IAnnotation annotation, IAnnotationModel model) {
		int problemId = annotation.getId();
		if (problemId != -1) {
			Position pos = model.getPosition((Annotation) annotation);
			if (pos != null) {
				return new ProblemLocation(pos.getOffset(), pos.getLength(),
						annotation); // java problems all handled by the quick
				// assist processors
			}
		}
		return null;
	}

	public static void collectAssists(IQuickAssistInvocationContext context,
			ProblemLocation[] locations, Collection<ICompletionProposal> proposals) {
		return;
	}

	private static void collectMarkerProposals(SimpleMarkerAnnotation annotation, Collection<ICompletionProposal> proposals) {
		IMarker marker = annotation.getMarker();
		IMarkerResolution[] res = IDE.getMarkerHelpRegistry().getResolutions(marker);

		if (res.length > 0) {
			for (int i = 0; i < res.length; i++) {
				proposals.add(new MarkerResolutionProposal(res[i], marker));
			}
		}
	}

	public ICompletionProposal[] computeQuickAssistProposals(IQuickAssistInvocationContext quickAssistContext) {
		ArrayList<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		ISourceViewer viewer = quickAssistContext.getSourceViewer();
		collectProposals(getContext(quickAssistContext), AnnotationUtils
				.getAnnotationModel(viewer),
				AnnotationUtils.getAnnotationsForOffset(viewer,
						quickAssistContext.getOffset()), true, true, proposals);
		return proposals.toArray(new ICompletionProposal[proposals.size()]);
	}

	private static class DefaultQuickFixInvocationContext implements
			IQuickFixInvocationContext {

		private IQuickAssistInvocationContext context;
		private ICompilationUnit model;

		public DefaultQuickFixInvocationContext(
				IQuickAssistInvocationContext context, ICompilationUnit model) {
			this.context = context;
			this.model = model;
		}

		public int getLength() {
			return context.getLength();
		}

		public int getOffset() {
			return context.getOffset();
		}

		public ISourceViewer getSourceViewer() {
			return context.getSourceViewer();
		}

		public ICompilationUnit getModel() {
			return model;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * IAssistProcessor#getCorrections(org.eclipse.jdt.internal.ui.text.correction
	 * .IAssistContext,
	 * org.eclipse.jdt.internal.ui.text.correction.IProblemLocation[])
	 */
	public void collectCorrections(IQuickAssistInvocationContext quickAssistContext,
			ProblemLocation[] locations, Collection<ICompletionProposal> proposals) {
		if (locations == null || locations.length == 0) {
			return;
		}

		HashSet<Integer> handledProblems = new HashSet<Integer>(locations.length);
		for (int i = 0; i < locations.length; i++) {
			ProblemLocation curr = locations[i];
			Integer id = new Integer(curr.getProblemId());
			if (handledProblems.add(id)) {
				fAssistant.addProposals(getContext(quickAssistContext), curr,
						proposals);
			}
		}
	}
}
