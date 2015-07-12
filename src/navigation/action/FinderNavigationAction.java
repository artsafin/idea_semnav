package navigation.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.Field;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import navigation.exception.ContextMissingException;
import navigation.finder.BackwardFinder;
import navigation.finder.Finder;
import util.ActionUtil;
import util.ElementUtil;

import javax.annotation.Nonnull;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public abstract class FinderNavigationAction extends AnAction
{
    protected void jumpToElement(Editor editor, PsiElement el)
    {
        if (el != null) {
            int pos = ElementUtil.getBeginOffset(el);
            editor.getCaretModel().moveToOffset(pos);
        }
    }
}
