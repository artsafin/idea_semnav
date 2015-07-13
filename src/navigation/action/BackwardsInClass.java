package navigation.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.Field;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;
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
public class BackwardsInClass extends FinderNavigationAction
{
    @Override
    public void actionPerformed(@NotNull AnActionEvent e)
    {
        try {
            PsiElement el = ActionUtil.requireCurrentPsi(e);

            PsiElementMatcher matcher = new OrMatcher(
                    new ClassMatcher(PhpClass.class),
                    new AndMatcher(new ParentClassMatcher(Method.class), new LeafTypeMatcher("identifier")),
                    new AndMatcher(new ParentClassMatcher(Field.class), new LeafTypeMatcher("variable"))
            );

            Finder finder = new DeepBackwardFinder(matcher, getFileScopeMatcher());

            PsiElement nextElement = finder.next(el);

            jumpToElement(ActionUtil.requireEditor(e), nextElement);
        } catch (ContextMissingException exc) {
            // It's normal
        }
    }
}
