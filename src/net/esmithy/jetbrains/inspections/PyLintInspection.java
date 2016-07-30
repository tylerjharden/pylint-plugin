/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.esmithy.jetbrains.inspections;

import com.intellij.codeInspection.*;
import com.intellij.codeInspection.ui.ListEditForm;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.jetbrains.python.inspections.PyInspection;
import com.jetbrains.python.validation.Pep8ExternalAnnotator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dummy inspection for configuring the PyLint checker. The checking itself is performed by
 * PyLintExternalAnnotator.
 *
 * @author yole, Eric
 */
public class PyLintInspection extends PyInspection {
    public List<String> ignoredErrors = new ArrayList<String>();
    public static final String INSPECTION_SHORT_NAME = "PyLintInspection";
    public static final Key<PyLintInspection> KEY = Key.create(INSPECTION_SHORT_NAME);

    @Override
    public JComponent createOptionsPanel() {
        ListEditForm form = new ListEditForm("Ignore errors", ignoredErrors);
        return form.getContentPanel();
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder,
                                          boolean isOnTheFly,
                                          @NotNull LocalInspectionToolSession session) {
        return new ExternalAnnotatorInspectionVisitor(holder, new Pep8ExternalAnnotator(), isOnTheFly);
    }

    @Nullable
    @Override
    public ProblemDescriptor[] checkFile(@NotNull PsiFile file, @NotNull InspectionManager manager, boolean isOnTheFly) {
        return ExternalAnnotatorInspectionVisitor.checkFileWithExternalAnnotator(file, manager, isOnTheFly, new Pep8ExternalAnnotator());
    }
}