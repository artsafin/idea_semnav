package navigation.finder.matcher;

import com.intellij.psi.PsiElement;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public interface PsiElementMatcher
{
    boolean matches(PsiElement e);
}
