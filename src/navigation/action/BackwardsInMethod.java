package navigation.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.jetbrains.php.lang.psi.elements.*;
import navigation.exception.ContextMissingException;
import navigation.finder.DeepBackwardFinder;
import navigation.finder.Finder;
import navigation.finder.matcher.*;
import org.jetbrains.annotations.NotNull;
import util.ActionUtil;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class BackwardsInMethod extends FinderNavigationAction
{
    @Override
    public void actionPerformed(@NotNull AnActionEvent e)
    {
        try {
            PsiElement el = ActionUtil.requireCurrentPsi(e);

            PsiElementMatcher matcher = new AndMatcher(new ParentClassMatcher(Variable.class), new LeafTypeMatcher("variable"));

            PsiElementMatcher stop = new OrMatcher(new ClassMatcher(PsiFile.class),
                    new AndMatcher(new ParentClassMatcher(Method.class), new ClassMatcher(GroupStatement.class)));

            Finder finder = new DeepBackwardFinder(matcher, stop);

            PsiElement nextElement = finder.next(el);

            jumpToElement(ActionUtil.requireEditor(e), nextElement);
        } catch (ContextMissingException exc) {
            // It's normal
        }
    }
}
