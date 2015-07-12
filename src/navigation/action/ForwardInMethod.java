package navigation.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.jetbrains.php.lang.psi.elements.*;
import navigation.exception.ContextMissingException;
import navigation.finder.DeepForwardFinder;
import navigation.finder.Finder;
import navigation.finder.matcher.*;
import org.jetbrains.annotations.NotNull;
import util.ActionUtil;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class ForwardInMethod extends FinderNavigationAction
{
    @Override
    public void actionPerformed(@NotNull AnActionEvent e)
    {
        try {
            PsiElement el = ActionUtil.requireCurrentPsi(e);
            el = normalizeElement(el);

            PsiElementMatcher matcher = new AndMatcher(new ParentClassMatcher(Variable.class), new LeafTypeMatcher("variable"));

            PsiElementMatcher stop = new OrMatcher(new ClassMatcher(PsiFile.class),
                    new AndMatcher(new ParentClassMatcher(Method.class), new ClassMatcher(GroupStatement.class)));

            Finder finder = new DeepForwardFinder(matcher, stop);

            PsiElement nextElement = finder.next(el);

            jumpToElement(ActionUtil.requireEditor(e), nextElement);
        } catch (ContextMissingException exc) {
            // It's normal
        }
    }

    private PsiElement normalizeElement(PsiElement el)
    {
        PsiElement parent = el.getParent();

        if (parent != null
                && (parent instanceof Field || parent instanceof Method)
                && el.getTextOffset() >= parent.getTextOffset()) {
            return parent;
        }

        return el;
    }
}
