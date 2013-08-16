This directory contains the source code for IMP, imported from the following SVN repository:

  http://dev.eclipse.org/svnroot/technology/org.eclipse.imp
  
And patches to make IMP work with Spoofax in the spoofax branch. The spoofax branch is based 
on SVN revision 22255 or git revision 9fb42ba57e2b0e277e978f6c9694c530a74bab71.

Note that building IMP from source code is not required for building Spoofax/IMP;
instead, the latest Spoofax/IMP binary plugin can be installed and used as a
baseline for rebuilding the remainder of the components.

We currently use the following plugins to build Spoofax/IMP:

- org.eclipse.imp.cheatsheets
- org.eclipse.imp.java.hosted
- org.eclipse.imp.lpg
- org.eclipse.imp.lpg.ide
- org.eclipse.imp.lpg.metatooling
- org.eclipse.imp.metatooling
- org.eclipse.imp.preferences
- org.eclipse.imp.prefspecs
- org.eclipse.imp.presentation
- org.eclipse.imp.runtime
- org.eclipse.imp.smapi
- org.eclipse.imp.smapifier
- org.eclipse.imp.xform

And:
- com.ibm.wala.shrike (https://wala.svn.sourceforge.net/svnroot/wala/trunk/com.ibm.wala.shrike@r3073)
- lpg.runtime.java (version 2.0.17.qualifier, mirrored at https://svn.strategoxt.org/repos/StrategoXT/sglr-recovery/trunk/lpg.runtime.java)
