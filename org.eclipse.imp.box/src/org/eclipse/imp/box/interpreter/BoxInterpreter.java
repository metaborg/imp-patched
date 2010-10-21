/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation
 *******************************************************************************/
package org.eclipse.imp.box.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import lpg.runtime.IAst;

import org.eclipse.imp.box.parser.Ast.*;

/**
 * @author rfuhrer
 */
public class BoxInterpreter {
    public enum Alignment {
        LEFT, RIGHT, CENTER
    }

    public enum BoxOperator {
        H, V, HV, HOV, I, G, WD
    }

    public static class GroupOptions {
        private final BoxOperator fOperator;
        private final int fGroupSize;
        public GroupOptions(BoxOperator op, int gs) {
            fOperator= op;
            fGroupSize= gs;
        }
        public BoxOperator operator() {
            return fOperator;
        }
        public int groupSize() {
            return fGroupSize;
        }
    }

    private final static int DEFAULT_WIDTH= 80;

    private final Stack<Integer> fIndentStack= new Stack<Integer>();

    private int fCurWidth= DEFAULT_WIDTH;

    public BoxInterpreter() {
        this(DEFAULT_WIDTH);
    }

    public BoxInterpreter(int width) {
        fCurWidth= width;
    }

    private void pushIndent(int indent) {
        fIndentStack.push(fCurWidth);
        fCurWidth -= indent;
    }

    private void popIndent() {
        if (fIndentStack.size() < 1) {
            System.err.println("Unmatched call to popIndent()!");
            fCurWidth= DEFAULT_WIDTH;
        } else {
            fCurWidth= fIndentStack.pop();
        }
    }

    public String interpret(final IBox rootNode) {
        final Map<IAst,String> translation= new HashMap<IAst, String>(); 

        rootNode.accept(new Visitor() {
            private int fRemain;
            private int fCol;

            {
                initline();
            }

            private void initline() {
                fRemain= fCurWidth;
                fCol= 0;
            }

            private String spaces(int num) {
                StringBuilder sb= new StringBuilder(num);
                for(int i=0; i < num; i++) {
                    sb.append(' ');
                }
                return sb.toString();
            }

            private String newlines(int num) {
                StringBuilder sb= new StringBuilder(num);
                for(int i=0; i < num; i++) {
                    sb.append('\n');
                }
                return sb.toString();
            }

            private void emit(String s, StringBuilder sb) {
                int len= s.length();
                sb.append(s);
                fRemain -= len;
                fCol += len;
            }

            private void newlines(int num, StringBuilder sb) {
                sb.append(newlines(num));
                initline();
            }

            public boolean preVisit(final IAst element) {
                return true;
            }

            public void postVisit(final IAst element) {}

            public void endVisit(final ASTNode n) {
                System.out.println("an " + n);
            }

            public void endVisit(final ASTNodeToken n) {
//              System.out.println("ant " + n);
            }

            public void endVisit(final BoxList n) {
                // only appears as a child of a Box; taken care of there
//              System.out.println("bl " + n);
            }

            public void endVisit(final SpaceOptionList n) {
                // only appears as child of the various BoxOperators; taken care of there
//              System.out.println("sol " + n);
            }

            public void endVisit(final SpaceOption n) {
//              System.out.println("so " + n);
            }

            public void endVisit(final GroupOptionList n) {
                System.out.println("gol " + n);
            }

            public void endVisit(final Box__STRING strLit) {
                String strLitStr= strLit.toString();
                String contents= strLitStr.substring(1, strLitStr.length()-1);

                translation.put(strLit, contents);
            }

            public void endVisit(final Box__BoxOperator_LEFTBRACKET_BoxList_RIGHTBRACKET n) {
                IBoxOperator op= n.getBoxOperator();
                BoxList children= n.getBoxList();
                String result= "";

                if (op instanceof BoxOperator__H_SpaceOptionList) {
                    result= handleHorizontal((BoxOperator__H_SpaceOptionList) op, children);
                } else if (op instanceof BoxOperator__V_SpaceOptionList) {
                    result= handleVertical((BoxOperator__V_SpaceOptionList) op, children);
                } else if (op instanceof BoxOperator__HV_SpaceOptionList) {
                    result= handleHV((BoxOperator__HV_SpaceOptionList) op, children);
                } else if (op instanceof BoxOperator__HOV_SpaceOptionList) {
                    result= handleHOV((BoxOperator__HOV_SpaceOptionList) op, children);
                } else if (op instanceof BoxOperator__I_SpaceOptionList) {
                    result= handleIndent((BoxOperator__I_SpaceOptionList) op, children);
                } else if (op instanceof BoxOperator__G_GroupOptionList) {
                    result= handleGroup((BoxOperator__G_GroupOptionList) op, children);
                } else if (op instanceof BoxOperator__WD) {
                    result= handleWidth((BoxOperator__WD) op, children);
                }
                translation.put(n, result);
            }

            private SpacingOptions processOptions(SpaceOptionList optionList) {
                SpacingOptions result= new SpacingOptions();

                for(int i=0; i < optionList.size(); i++) {
                    SpaceOption opt= optionList.getSpaceOptionAt(i);
                    ISpaceSymbol sym= opt.getSpaceSymbol();
                    ISpaceValue val= opt.getSpaceValue();
                    int ival= Integer.valueOf(((SpaceValue__NUMBER) val).getNUMBER().toString());

                    if (sym instanceof SpaceSymbol__vs) {
                        // vs
                        result.fVerticalSpacing= ival;
                    } else if (sym instanceof SpaceSymbol__hs) {
                        // hs
                        result.fHorizontalSpacing= ival;
                    } else if (sym instanceof SpaceSymbol__is) {
                        // is
                        result.fIndentationSpacing= ival;
                    } else if (sym instanceof SpaceSymbol__ts) {
                        // ts
                        result.fTabStopSpacing= ival;
                    } 
                }
                return result;
            }

            private String handleWidth(BoxOperator__WD op, BoxList children) {
                throw new UnsupportedOperationException("WD operator not supported");
            }

            private String handleIndent(BoxOperator__I_SpaceOptionList op, BoxList children) {
                assert(children.size() == 1);

                SpacingOptions spaceOptions= processOptions(op.getSpaceOptionList());
                IBox child= children.getBoxAt(0); // Only one child

                popIndent();
                return spaces(spaceOptions.indentationSpacing()) + translation.get(child);
            }

            private String handleHOV(BoxOperator__HOV_SpaceOptionList op, BoxList children) {
                SpacingOptions spaceOptions= processOptions(op.getSpaceOptionList());
                int hs= spaceOptions.horizontalSpacing();
                int vs= spaceOptions.verticalSpacing() + 1;
                int totalWid= 0;

                for(int i=0; i < children.size(); i++) {
                    IBox child= children.getBoxAt(i);

                    totalWid += translation.get(child).length() + (i > 0 ? hs : 0);
                }

                StringBuilder sb= new StringBuilder();

                if (totalWid <= fRemain) {
                    // lay out horizontally
                    for(int i=0; i < children.size(); i++) {
                        IBox child= children.getBoxAt(i);
                        String childStr= translation.get(child);

                        if (i > 0) {
                            emit(spaces(hs), sb);
                        }
                        emit(childStr, sb);
                    }
                } else {
                    // lay out vertically
                    for(int i=0; i < children.size(); i++) {
                        IBox child= children.getBoxAt(i);
                        String childStr= translation.get(child);

                        if (i > 0) {
                            emit(newlines(vs), sb);
                        }
                        emit(childStr, sb);
                    }
                }
                return sb.toString();
            }

            private String handleHV(BoxOperator__HV_SpaceOptionList op, BoxList children) {
                SpacingOptions spaceOptions= processOptions(op.getSpaceOptionList());
                int hs= spaceOptions.horizontalSpacing();
                int vs= spaceOptions.verticalSpacing() + 1;
                StringBuilder sb= new StringBuilder();

                for(int i=0; i < children.size(); i++) {
                    IBox child= children.getBoxAt(i);
                    String childStr= translation.get(child);
                    int childWid= childStr.length();

                    if (i > 0) {
                        if (fRemain >= hs) {
                            emit(spaces(hs), sb);
                        } else {
                            newlines(vs, sb);
                        }
                    }
                    if (childWid <= fRemain) {
                        emit(childStr, sb);
                    } else {
                        if (fCol == 0) {
                            // Child too wide to fit even a single line.
                            // Oh well, let it overflow rather than dropping it on the floor.
                            emit(childStr, sb);
                        } else {
                            // Push child to next line (regardless of how wide it is)
                            newlines(vs, sb);
                            emit(childStr, sb);
                        }
                    }
                }
                return sb.toString();
            }

            private String handleVertical(BoxOperator__V_SpaceOptionList op, BoxList children) {
                SpacingOptions spaceOptions= processOptions(op.getSpaceOptionList());
                int vs= spaceOptions.verticalSpacing() + 1;
                StringBuilder sb= new StringBuilder();

                for(int i=0; i < children.size(); i++) {
                    IBox child= children.getBoxAt(i);
                    if (i > 0) {
                        newlines(vs, sb);
                    }
                    sb.append(translation.get(child));
                }
                return sb.toString();
            }

            private String handleHorizontal(BoxOperator__H_SpaceOptionList op, BoxList children) {
                SpacingOptions spaceOptions= processOptions(op.getSpaceOptionList());
                int hs= spaceOptions.horizontalSpacing();
                StringBuilder sb= new StringBuilder();

                for(int i=0; i < children.size(); i++) {
                    IBox child= children.getBoxAt(i);
                    if (i > 0) {
                        sb.append(spaces(hs));
                    }
                    sb.append(translation.get(child));
                }
                return sb.toString();
            }

            private GroupOptions processOptions(GroupOptionList optionList) {
                BoxOperator opEnum= BoxOperator.H;
                int groupSize= 1;

                for(int i=0; i < optionList.size(); i++) {
                    IGroupOption opt= optionList.getGroupOptionAt(i);

                    if (opt instanceof GroupOption__op_EQUAL_BoxOperator) {
                        // op = BoxOperator
                        GroupOption__op_EQUAL_BoxOperator opt0= (GroupOption__op_EQUAL_BoxOperator) opt;
                        IBoxOperator op= opt0.getBoxOperator();

                        if (op instanceof BoxOperator__H_SpaceOptionList) {
                            opEnum= BoxOperator.H;
                        } else if (op instanceof BoxOperator__V_SpaceOptionList) {
                            opEnum= BoxOperator.V;
                        } else if (op instanceof BoxOperator__HV_SpaceOptionList) {
                            opEnum= BoxOperator.HV;
                        } else if (op instanceof BoxOperator__HOV_SpaceOptionList) {
                            opEnum= BoxOperator.HOV;
                        } else if (op instanceof BoxOperator__I_SpaceOptionList) {
                            opEnum= BoxOperator.I;
                        } else if (op instanceof BoxOperator__G_GroupOptionList) {
                            opEnum= BoxOperator.G;
                        } else if (op instanceof BoxOperator__WD) {
                            opEnum= BoxOperator.WD;
                        } 
                    } else if (opt instanceof GroupOption__gs_EQUAL_NUMBER) {
                        // gs = NUMBER
                        GroupOption__gs_EQUAL_NUMBER opt1= (GroupOption__gs_EQUAL_NUMBER) opt;

                        groupSize= Integer.parseInt(opt1.getNUMBER().toString());
                    } 
                }
                return new GroupOptions(opEnum, groupSize);
            }

            private String handleGroup(BoxOperator__G_GroupOptionList op, BoxList children) {
                GroupOptions groupOptions= processOptions(op.getGroupOptionList());
                throw new UnsupportedOperationException("G operator is unsupported");
            }

            public void endVisit(final BoxOperator__H_SpaceOptionList n) {}

            public void endVisit(final BoxOperator__V_SpaceOptionList n) {}

            public void endVisit(final BoxOperator__HV_SpaceOptionList n) {}

            public void endVisit(final BoxOperator__HOV_SpaceOptionList n) {}

            public void endVisit(final BoxOperator__I_SpaceOptionList n) {}

            public void endVisit(final BoxOperator__G_GroupOptionList n) {}

            public void endVisit(final BoxOperator__WD n) {}

            public void endVisit(final SpaceSymbol__vs n) {}

            public void endVisit(final SpaceSymbol__hs n) {}

            public void endVisit(final SpaceSymbol__is n) {}

            public void endVisit(final SpaceSymbol__ts n) {}

            public void endVisit(final SpaceValue__NUMBER n) {}

            public void endVisit(final SpaceValue__IDENT n) {}

            public void endVisit(final GroupOption__op_EQUAL_BoxOperator n) {}

            public void endVisit(final GroupOption__gs_EQUAL_NUMBER n) {}

            public boolean visit(final ASTNode n) {
                return true;
            }

            public boolean visit(final ASTNodeToken n) {
                return true;
            }

            public boolean visit(final BoxList n) {
                return true;
            }

            public boolean visit(final SpaceOptionList n) {
                return true;
            }

            public boolean visit(final SpaceOption n) {
                return true;
            }

            public boolean visit(final GroupOptionList n) {
                return true;
            }

            public boolean visit(final Box__STRING n) {
                return true;
            }

            public boolean visit(final Box__BoxOperator_LEFTBRACKET_BoxList_RIGHTBRACKET n) {
                return true;
            }

            public boolean visit(final BoxOperator__H_SpaceOptionList op) {
                return true;
            }

            public boolean visit(final BoxOperator__V_SpaceOptionList op) {
                return true;
            }

            public boolean visit(final BoxOperator__HV_SpaceOptionList op) {
                return true;
            }

            public boolean visit(final BoxOperator__HOV_SpaceOptionList op) {
                return true;
            }

            public boolean visit(final BoxOperator__I_SpaceOptionList op) {
                SpacingOptions spaceOptions= processOptions(op.getSpaceOptionList());

                pushIndent(spaceOptions.indentationSpacing());
                return true;
            }

            public boolean visit(final BoxOperator__G_GroupOptionList op) {
                return true;
            }

            public boolean visit(final BoxOperator__WD op) {
                return true;
            }

            public boolean visit(final SpaceSymbol__vs n) {
                return true;
            }

            public boolean visit(final SpaceSymbol__hs n) {
                return true;
            }

            public boolean visit(final SpaceSymbol__is n) {
                return true;
            }

            public boolean visit(final SpaceSymbol__ts n) {
                return true;
            }

            public boolean visit(final SpaceValue__NUMBER n) {
                return true;
            }

            public boolean visit(final SpaceValue__IDENT n) {
                return true;
            }

            public boolean visit(final GroupOption__op_EQUAL_BoxOperator n) {
                return true;
            }

            public boolean visit(final GroupOption__gs_EQUAL_NUMBER n) {
                return true;
            }
        });
        return translation.get(rootNode);
    }
}
