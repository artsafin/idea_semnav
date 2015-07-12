package navigation.finder.matcher;

import com.intellij.psi.PsiElement;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class AndMatcher implements PsiElementMatcher
{
    private final PsiElementMatcher[] matchers;

    public AndMatcher(PsiElementMatcher... matchers)
    {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(PsiElement e)
    {
        for (PsiElementMatcher m : matchers) {
            if (!m.matches(e)) {
                return false;
            }
        }

        return true;
    }
}
