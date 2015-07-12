package navigation.finder.matcher;

import com.intellij.psi.PsiElement;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class OrMatcher implements PsiElementMatcher
{
    private final PsiElementMatcher[] matchers;

    public OrMatcher(PsiElementMatcher... matchers)
    {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(PsiElement e)
    {
        for (PsiElementMatcher m : matchers) {
            if (m.matches(e)) {
                return true;
            }
        }

        return false;
    }
}
