package util;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import navigation.exception.ContextMissingException;
import navigation.exception.MissingEditorException;
import navigation.exception.MissingProjectException;
import navigation.exception.MissingRequiredChildException;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class ActionUtil {

    public static Project requireProject(@NotNull AnActionEvent e) throws MissingProjectException {
        Project project = e.getProject();
        if (project == null) {
            throw new MissingProjectException();
        }
        return project;
    }
    public static Editor requireEditor(@NotNull AnActionEvent e) throws MissingEditorException {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            throw new MissingEditorException();
        }
        return editor;
    }

    public static PsiFile requirePsiFile(@NotNull AnActionEvent e)
    {
        return e.getData(LangDataKeys.PSI_FILE);
    }

    public static PsiElement requireCurrentPsi(@NotNull AnActionEvent e) throws ContextMissingException {
        PsiElement el = PsiUtilBase.getElementAtCaret(requireEditor(e));
        if (el == null) {
            throw new MissingRequiredChildException();
        }

        return el;
    }

    public static <T extends PsiElement> T requirePsiChild(@NotNull PsiFile psiFile, @NotNull Class<T> aClass) throws MissingRequiredChildException {
        T el = PsiTreeUtil.findChildOfType(psiFile, aClass);
        if (el == null) {
            throw new MissingRequiredChildException();
        }

        return el;
    }

    public static <T extends PsiElement> Collection<T> requirePsiChildren(@NotNull PsiFile psiFile, @NotNull Class<T> aClass) throws MissingRequiredChildException {
        Collection<T> el = PsiTreeUtil.findChildrenOfType(psiFile, aClass);
        if (el.size() == 0) {
            throw new MissingRequiredChildException();
        }

        return el;
    }
}
