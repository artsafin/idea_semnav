package navigation.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.inspections.phpdoc.PhpDocCommentGenerator;
import com.jetbrains.php.lang.psi.PhpCodeEditUtil;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import navigation.exception.ContextMissingException;
import navigation.exception.MissingProjectException;
import org.jetbrains.annotations.NotNull;
import util.ActionUtil;
import util.ElementUtil;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class ClassDocblockAction extends AnAction
{
    public static final Logger LOG = Logger.getInstance("#ClassDocblockAction");

    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            final Editor editor = ActionUtil.requireEditor(e);
            final PsiFile psiFile = ActionUtil.requirePsiFile(e);
            final PhpClass phpClass = ActionUtil.requirePsiChild(psiFile, PhpClass.class);

            PhpDocComment docComment = phpClass.getDocComment();

            LOG.info(docComment == null ? "Doc not found" : "Doc found");

            if (docComment == null) {
                docComment = tryCreateClassDoc(e, psiFile, phpClass);
            }

            if (docComment != null) {
                int pos = ElementUtil.getEndOffset(docComment);
                editor.getCaretModel().moveToOffset(pos);
            }
        } catch (ContextMissingException exc) {
            // Ignore the action if anything from the context is missing
        }
    }

    private PhpDocComment tryCreateClassDoc(@NotNull AnActionEvent e, PsiFile psiFile, PhpClass phpClass) throws MissingProjectException {
        final String actionTitle = e.getPresentation().getText();
        final Project project = ActionUtil.requireProject(e);

        int res = Messages.showYesNoDialog(project, "Docblock not found. Create?", actionTitle, "Create", "Do not create", null);
        if (res == Messages.YES) {
            return generateClassDoc(project, psiFile, phpClass);
        } else {
            return null;
        }
    }

    private PhpDocComment generateClassDoc(final Project project, final PsiFile psiFile, final PhpClass phpClass) {
        new WriteCommandAction.Simple(project, psiFile){
            @Override
            protected void run() throws Throwable {
                PhpDocComment comment = PhpDocCommentGenerator.constructDocComment(project, phpClass);
                if (comment != null) {
                    PhpCodeEditUtil.insertDocCommentBefore(phpClass, comment);
                }
            }
        }.execute();

        return phpClass.getDocComment();
    }
}
